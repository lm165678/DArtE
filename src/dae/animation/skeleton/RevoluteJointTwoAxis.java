/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.animation.skeleton;

import com.jme3.animation.Bone;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import dae.animation.skeleton.constraints.SectorConstraint;
import dae.animation.skeleton.debug.BoneVisualization;
import dae.animation.skeleton.debug.SectorVisualization;
import dae.io.SceneSaver;
import dae.prefabs.Prefab;
import dae.prefabs.gizmos.Axis;
import dae.prefabs.gizmos.RotateGizmo;
import dae.util.MathUtil;
import java.io.IOException;
import java.io.Writer;

/**
 * A Revolute joint that can rotate around two axis. This is useful when
 * constructing a skeleton object.
 *
 * @author Koen Samyn
 */
public class RevoluteJointTwoAxis extends Prefab implements BodyElement {

    /**
     * The current angle of the first axis.
     */
    private float thetaAngle = 0.0f;
    /**
     * The current angle of the second axis.
     */
    private float phiAngle = 0.0f;
    /**
     * The location of the constraint. If a bone is attached this will be the
     * location of the bone otherwise the default of [1,0,0] will be used.
     */
    private Vector3f constraintVector = new Vector3f(1, 0, 0);
    // meta data
    private String group;
    // visualization
    private float radius = 0.5f;
    private float height = 0.6f;
    // the connected bone
    private Bone bone;
    // the transformnode that contains the joint rotation.
    private Node transformNode;
    // constraint geometry
    private Geometry constraintGeometry;

    private AssetManager manager;

    public RevoluteJointTwoAxis() {

    }

    @Override
    protected void create(AssetManager manager, String extraInfo) {
        this.manager = manager;
        transformNode = new Node("transform");
        super.attachChild(transformNode);

        BoneVisualization bv = new BoneVisualization(this.constraintVector, .05f, 1.0f, 12);
        Geometry boneGeo = new Geometry("bone", bv);
        Material boneMat = manager.loadMaterial("Materials/RigMaterial.j3m");

        boneGeo.setMaterial(boneMat);
        transformNode.attachChild(boneGeo);

    }

    /**
     * Returns the group this joint belongs to.
     *
     * @return the group of this joint.
     */
    public String getGroup() {
        return group;
    }

    /**
     * @return the thetaAngle
     */
    public float getThetaAngle() {
        return thetaAngle;
    }

    /**
     * Sets the theta angle and updates the transform of the transform node.
     *
     * @param theta the thetaAngle to set
     */
    public void setThetaAngle(float theta) {
        this.thetaAngle = theta;
        updateTransforms();
    }

    /**
     * @return the phiAngle
     */
    public float getPhiAngle() {
        return phiAngle;
    }

    /**
     * Sets the phi angle.
     *
     * @param phi the phiAngle to set.
     */
    public void setPhiAngle(float phi) {
        this.phiAngle = phi;
        updateTransforms();
    }

    private void updateTransforms() {
        Quaternion q = transformNode.getLocalRotation();
        q.fromAngles(0, phiAngle * FastMath.DEG_TO_RAD, thetaAngle * FastMath.DEG_TO_RAD);

        Vector3f rotated = q.mult(constraintVector);
        SectorConstraint sc = (SectorConstraint) this.getComponent("SectorConstraint");
        boolean isInside = sc.checkConstraint(rotated);
        if (isInside) {
            this.constraintGeometry.getMaterial().setColor("Color", ColorRGBA.Blue);
        } else {

            this.constraintGeometry.getMaterial().setColor("Color", ColorRGBA.Red);
            Vector3f constrainedVertex = sc.calculateCorrection(rotated);
            q = MathUtil.createDof2Rotation(constraintVector, constrainedVertex, Vector3f.UNIT_Y, Vector3f.UNIT_Z, q);
        }
        // calculate projection to rotate point back on the constraint surface.

        transformNode.setLocalRotation(q);

        updateBoneTransform();
    }

    public void updateBoneTransform() {
        if (bone != null) {

            // to calculate the concatenation of transforms until the 
            // next bone node.
            // bone.setUserTransforms(Vector3f.ZERO, relativeBoneRotation, Vector3f.UNIT_XYZ);
        }
    }

    @Override
    public void attachBodyElement(BodyElement element) {
        if (element instanceof Node) {
            Node n = (Node) element;
            this.attachChild((Node) element);
            if (manager != null) {
                // create a visualization for the bone.
                Vector3f localTranslation = n.getLocalTranslation();
                if (localTranslation.length() > FastMath.ZERO_TOLERANCE) {
                    // create cylinder from this translation to the origin.
                    BoneVisualization bv = new BoneVisualization(localTranslation.normalize(), 0.001f, localTranslation.length(), 12);
                    Geometry boneGeo = new Geometry("bone", bv); // using our custom mesh object
                    Material boneMat = manager.loadMaterial("Materials/RigMaterial");
                    //boneMat.setColor("Color", new ColorRGBA(32 / 255.0f, 222 / 255.0f, 61 / 255.0f, 1.0f));
                    boneGeo.setMaterial(boneMat);
                    attachChild(boneGeo);
                }
            }
        }
    }

    @Override
    public void reset() {
        thetaAngle = 0;
        phiAngle = 0;
    }

    @Override
    public Spatial clone() {

        RevoluteJointTwoAxis copy = new RevoluteJointTwoAxis();

        for (Spatial child : this.children) {
            if (child instanceof BodyElement) {
                copy.attachBodyElement((BodyElement) child.clone());
            }
        }
        copy.notifyLoaded();
        return copy;
    }

    public void setAttachedBone(Bone b) {
        this.bone = b;
        b.setUserControl(true);
    }

    /**
     * Creates a visualization of this joint.
     *
     * @param assetManager the assetmanager that can be used to create
     * materials.
     */
    @Override
    public void notifyLoaded() {

        createConstraintGeometry();
    }

    private void createConstraintGeometry() {
        SectorConstraint sc = (SectorConstraint) this.getComponent("SectorConstraint");
        if (sc != null) {
            if (constraintGeometry != null) {
                constraintGeometry.removeFromParent();
            }
            Mesh constraint = new SectorVisualization(sc.getBaseAxis(), sc.getAngle(), this.radius);
            constraintGeometry = new Geometry("sectorConstraint", constraint); // using our custom mesh object
            Material mat = manager.loadMaterial("Materials/TriggerBoxMaterial.j3m");
            //mat.setColor("Color", ColorRGBA.Red);
            constraintGeometry.setMaterial(mat);
            super.attachChild(constraintGeometry);
        }
    }

    @Override
    public void hideTargetObjects() {
        for (Spatial s : children) {
            if (s instanceof BodyElement) {
                ((BodyElement) s).hideTargetObjects();
            }
        }
    }

    @Override
    public void showTargetObjects() {
        for (Spatial s : this.getChildren()) {
            if (s instanceof BodyElement) {
                ((BodyElement) s).showTargetObjects();
            }
        }
    }

    @Override
    public void write(Writer w, int depth) throws IOException {
        SceneSaver.writePrefab(this, w, depth);
    }
    
    @Override
    public int attachChild(Spatial child) {
        if (child instanceof Axis || child instanceof RotateGizmo) {
            child.setLocalTranslation(this.getPivot());
            return super.attachChild(child);
        }else{
            return transformNode.attachChild(child);
        }
    }

    /**
     * Checks if a prefab has children that need to be saved when the prefab is
     * written to file. First the state of the canHaveChildren is checked, next
     * a check will be performed to see if the object has savable children.
     *
     * @return true if this prefab has savable children, false otherwise.
     */
    @Override
    public boolean hasSavableChildren() {
        if (!canHaveChildren) {
            return false;
        } else {
            for (Spatial s : transformNode.getChildren()) {
                if (s instanceof Prefab) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public boolean canHaveChildren() {
        return canHaveChildren;
    }

    @Override
    public Object getPrefabChildAt(int index) {
        int pindex = 0;
        for (Spatial s : transformNode.getChildren()) {
            if (s instanceof Prefab) {
                if (pindex == index) {
                    return s;
                }
                ++pindex;
            }
        }
        return null;
    }

    @Override
    public int getPrefabChildCount() {
        int pindex = 0;
        for (Spatial s : transformNode.getChildren()) {
            if (s instanceof Prefab) {
                ++pindex;
            }
        }
        return pindex;
    }

    @Override
    public int indexOfPrefab(Prefab prefab) {
        int pindex = 0;
        for (Spatial s : transformNode.getChildren()) {
            if (s == prefab) {
                return pindex;

            }
            ++pindex;
        }
        return -1;
    }

    // visualization parameters
    /**
     * @return the radius
     */
    public float getRadius() {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }

    /**
     * @return the height
     */
    public float getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(float height) {
        this.height = height;
    }
}
