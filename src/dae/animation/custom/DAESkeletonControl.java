/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.animation.custom;

import com.jme3.animation.AnimControl;
import com.jme3.animation.Skeleton;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix4f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.UserData;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import com.jme3.util.TempVars;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

/**
 * The Skeleton control deforms a model according to a skeleton, It handles the
 * computation of the deformation matrices and performs the transformations on
 * the mesh
 *
 * @author RÃ©my Bouquet Based on AnimControl by Kirill Vainer
 */
public class DAESkeletonControl extends AbstractControl {

    /**
     * The skeleton of the model
     */
    private DAESkeleton skeleton;
    /**
     * List of targets which this controller effects.
     */
    private Mesh[] targets;
    /**
     * Used to track when a mesh was updated. Meshes are only updated if they
     * are visible in at least one camera.
     */
    private boolean wasMeshUpdated = false;

    /**
     * Serialization only. Do not use.
     */
    public DAESkeletonControl() {
        System.out.println("Creating empty skeleton");
    }

    /**
     * Creates a skeleton control. The list of targets will be acquired
     * automatically when the control is attached to a node.
     *
     * @param skeleton the skeleton
     */
    public DAESkeletonControl(DAESkeleton skeleton) {
        this.skeleton = skeleton;
        int numberOfBones = skeleton.getNumberOfBones();
        this.offsetMatrices = new Matrix4f[numberOfBones];
        for (int i = 0; i < numberOfBones; ++i) {
            offsetMatrices[i] = new Matrix4f();
            offsetMatrices[i].set(Matrix4f.IDENTITY);
        }
    }

    public void addAnimation(DAEBoneAnimation animation) {
        this.skeleton.addAnimation(animation);
    }

    public void addAnimationSet(AnimationSet set) {
        for (DAEBoneAnimation animation : set.getAnimations()) {
            this.skeleton.addAnimation(animation);
        }
    }

    public void activateAnimation(String animation) {
        this.skeleton.activateAnimation(animation);
    }

    private boolean isMeshAnimated(Mesh mesh) {
        return mesh.getBuffer(Type.BindPosePosition) != null;
    }

    private Mesh[] findTargets(Node node) {
        Mesh sharedMesh = null;
        ArrayList<Mesh> animatedMeshes = new ArrayList<Mesh>();

        for (Spatial child : node.getChildren()) {
            if (!(child instanceof Geometry)) {
                continue; // could be an attachment node, ignore.
            }

            Geometry geom = (Geometry) child;

            // is this geometry using a shared mesh?
            Mesh childSharedMesh = geom.getUserData(UserData.JME_SHAREDMESH);

            if (childSharedMesh != null) {

                // Don't bother with non-animated shared meshes
                if (isMeshAnimated(childSharedMesh)) {

                    // child is using shared mesh,
                    // so animate the shared mesh but ignore child
                    if (sharedMesh == null) {
                        sharedMesh = childSharedMesh;
                    } else if (sharedMesh != childSharedMesh) {
                        throw new IllegalStateException("Two conflicting shared meshes for " + node);
                    }
                }
            } else {
                Mesh mesh = geom.getMesh();
                if (isMeshAnimated(mesh)) {
                    animatedMeshes.add(mesh);
                }
            }
        }

        if (sharedMesh != null) {
            animatedMeshes.add(sharedMesh);
        }

        return animatedMeshes.toArray(new Mesh[animatedMeshes.size()]);
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        if (spatial != null) {
            Node node = (Node) spatial;
            targets = findTargets(node);
        } else {
            targets = null;
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        if (this.offsetMatrices == null) {
            System.out.println("Animation  problem for : " + this.getSpatial().getName());
            return;
        }
        if (!wasMeshUpdated) {
            resetToBind(); // reset morph meshes to bind pose

            // if hardware skinning is supported, the matrices and weight buffer
            // will be sent by the SkinningShaderLogic object assigned to the shader
            for (int i = 0; i < targets.length; i++) {
                // only update targets with DAEBone-vertex assignments
                if (targets[i].getBuffer(Type.BoneIndex) != null) {
                    softwareSkinUpdate(targets[i], offsetMatrices);
                }
            }

            wasMeshUpdated = true;
        }
    }
    private Matrix4f[] offsetMatrices;

    @Override
    protected void controlUpdate(float tpf) {
        offsetMatrices = skeleton.computeSkinningMatrices(tpf);
        wasMeshUpdated = false;
    }

    void resetToBind() {
        for (Mesh mesh : targets) {
            if (isMeshAnimated(mesh)) {
                VertexBuffer bi = mesh.getBuffer(Type.BoneIndex);
                ByteBuffer bib = (ByteBuffer) bi.getData();
                if (!bib.hasArray()) {
                    mesh.prepareForAnim(true); // prepare for software animation
                }
                VertexBuffer bindPos = mesh.getBuffer(Type.BindPosePosition);
                VertexBuffer bindNorm = mesh.getBuffer(Type.BindPoseNormal);
                VertexBuffer pos = mesh.getBuffer(Type.Position);
                VertexBuffer norm = mesh.getBuffer(Type.Normal);
                FloatBuffer pb = (FloatBuffer) pos.getData();
                FloatBuffer nb = (FloatBuffer) norm.getData();
                FloatBuffer bpb = (FloatBuffer) bindPos.getData();
                FloatBuffer bnb = (FloatBuffer) bindNorm.getData();
                pb.clear();
                nb.clear();
                bpb.clear();
                bnb.clear();
                pb.put(bpb).clear();
                nb.put(bnb).clear();
            }
        }
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        System.out.println("Making skeleton clone for :" + spatial.getName());
        Node clonedNode = (Node) spatial;

        DAESkeletonControl clone = new DAESkeletonControl();
        clone.setSkeleton((DAESkeleton) this.skeleton.clone());
        clone.setSpatial(clonedNode);

        Mesh[] meshes = new Mesh[targets.length];
        for (int i = 0; i < meshes.length; i++) {
            meshes[i] = ((Geometry) clonedNode.getChild(i)).getMesh();
        }
        clone.targets = meshes;
        return clone;
    }

    /**
     * returns the skeleton of this control
     *
     * @return
     */
    public DAESkeleton getSkeleton() {
        return skeleton;
    }

    /**
     * sets the skeleton for this control
     *
     * @param skeleton
     */
//    public void setSkeleton(Skeleton skeleton) {
//        this.skeleton = skeleton;
//    }
    /**
     * returns the targets meshes of this control
     *
     * @return
     */
    public Mesh[] getTargets() {
        return targets;
    }

    /**
     * sets the target meshes of this control
     *
     * @param targets
     */
//    public void setTargets(Mesh[] targets) {
//        this.targets = targets;
//    }
    private void softwareSkinUpdate(Mesh mesh, Matrix4f[] offsetMatrices) {
        int maxWeightsPerVert = mesh.getMaxNumWeights();
        if (maxWeightsPerVert <= 0) {
            throw new IllegalStateException("Max weights per vert is incorrectly set!");
        }


        int fourMinusMaxWeights = 4 - maxWeightsPerVert;

        // NOTE: This code assumes the vertex buffer is in bind pose
        // resetToBind() has been called this frame
        VertexBuffer vb = mesh.getBuffer(Type.Position);
        FloatBuffer fvb = (FloatBuffer) vb.getData();
        fvb.rewind();

        VertexBuffer nb = mesh.getBuffer(Type.Normal);
        FloatBuffer fnb = (FloatBuffer) nb.getData();
        fnb.rewind();

        // get DAEBoneIndexes and weights for mesh
        ByteBuffer ib = (ByteBuffer) mesh.getBuffer(Type.BoneIndex).getData();
        FloatBuffer wb = (FloatBuffer) mesh.getBuffer(Type.BoneWeight).getData();

        ib.rewind();
        wb.rewind();

        float[] weights = wb.array();
        byte[] indices = ib.array();
        int idxWeights = 0;

        TempVars vars = TempVars.get();


        float[] posBuf = vars.skinPositions;
        float[] normBuf = vars.skinNormals;

        int iterations = (int) FastMath.ceil(fvb.capacity() / ((float) posBuf.length));
        int bufLength;
        for (int i = iterations - 1; i >= 0; i--) {
            // read next set of positions and normals from native buffer
            bufLength = Math.min(posBuf.length, fvb.remaining());
            fvb.get(posBuf, 0, bufLength);
            fnb.get(normBuf, 0, bufLength);
            int verts = bufLength / 3;
            int idxPositions = 0;

            // iterate vertices and apply skinning transform for each effecting DAEBone
            for (int vert = verts - 1; vert >= 0; vert--) {
                float nmx = normBuf[idxPositions];
                float vtx = posBuf[idxPositions++];
                float nmy = normBuf[idxPositions];
                float vty = posBuf[idxPositions++];
                float nmz = normBuf[idxPositions];
                float vtz = posBuf[idxPositions++];

                float rx = 0, ry = 0, rz = 0, rnx = 0, rny = 0, rnz = 0;

                for (int w = maxWeightsPerVert - 1; w >= 0; w--) {
                    float weight = weights[idxWeights];
                    Matrix4f mat = offsetMatrices[indices[idxWeights++]];
                    if (mat == null) {
                        System.out.println("problem with : " + indices[idxWeights++]);;
                    }
                    rx += (mat.m00 * vtx + mat.m01 * vty + mat.m02 * vtz + mat.m03) * weight;
                    ry += (mat.m10 * vtx + mat.m11 * vty + mat.m12 * vtz + mat.m13) * weight;
                    rz += (mat.m20 * vtx + mat.m21 * vty + mat.m22 * vtz + mat.m23) * weight;

                    rnx += (nmx * mat.m00 + nmy * mat.m01 + nmz * mat.m02) * weight;
                    rny += (nmx * mat.m10 + nmy * mat.m11 + nmz * mat.m12) * weight;
                    rnz += (nmx * mat.m20 + nmy * mat.m21 + nmz * mat.m22) * weight;
                }

                idxWeights += fourMinusMaxWeights;

                idxPositions -= 3;
                normBuf[idxPositions] = rnx;
                posBuf[idxPositions++] = rx;
                normBuf[idxPositions] = rny;
                posBuf[idxPositions++] = ry;
                normBuf[idxPositions] = rnz;
                posBuf[idxPositions++] = rz;
            }


            fvb.position(fvb.position() - bufLength);
            fvb.put(posBuf, 0, bufLength);
            fnb.position(fnb.position() - bufLength);
            fnb.put(normBuf, 0, bufLength);
        }

        vars.release();

        vb.updateData(fvb);
        nb.updateData(fnb);

//        mesh.updateBound();
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule oc = ex.getCapsule(this);
        oc.write(targets, "targets", null);
        //oc.write(skeleton, "skeleton", null);
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        Savable[] sav = in.readSavableArray("targets", null);
        if (sav != null) {
            targets = new Mesh[sav.length];
            System.arraycopy(sav, 0, targets, 0, sav.length);
        }
        skeleton = (DAESkeleton) in.readSavable("skeleton", null);
    }

    private void setSkeleton(DAESkeleton skeleton) {
        this.skeleton = skeleton;
        int numberOfBones = skeleton.getNumberOfBones();
        this.offsetMatrices = new Matrix4f[numberOfBones];
        for (int i = 0; i < numberOfBones; ++i) {
            offsetMatrices[i] = new Matrix4f();
            offsetMatrices[i].set(Matrix4f.IDENTITY);
        }
    }
}
