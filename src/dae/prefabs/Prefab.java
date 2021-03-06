package dae.prefabs;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.*;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import dae.GlobalObjects;
import dae.components.ComponentList;
import dae.components.ComponentType;
import dae.components.ObjectComponent;
import dae.components.PrefabComponent;
import dae.components.TransformComponent;
import dae.prefabs.events.ComponentListener;
import dae.prefabs.gizmos.Axis;
import dae.prefabs.gizmos.Gizmo;
import dae.prefabs.gizmos.RotateGizmo;
import dae.prefabs.magnets.FillerParameter;
import dae.prefabs.magnets.Magnet;
import dae.prefabs.magnets.MagnetParameter;
import dae.prefabs.magnets.Quad;
import dae.prefabs.parameters.Parameter;
import dae.prefabs.standard.ConnectorPrefab;
import dae.prefabs.standard.UpdateObject;
import dae.prefabs.types.ObjectType;
import dae.prefabs.types.ObjectTypeCategory;
import dae.project.ProjectTreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author Koen Samyn
 */
public class Prefab extends Node implements ProjectTreeNode, LayerTaggable {

    private String prefix = "prefab";
    private final ArrayList<UpdateObject> workList
            = new ArrayList<>();
    private final ArrayList<Runnable> taskList
            = new ArrayList<>();
    protected ObjectType objectType;
    private boolean selected = false;
    private Material originalMaterial;
    private Vector3f offset;
    private String type;
    private String category;
    private String physicsMesh;
    private ArrayList<ComponentListener> componentListeners;
    private Vector3f pivot = Vector3f.ZERO;
    private boolean changed = false;
    private final HashMap<String, PrefabComponent> componentMap
            = new HashMap<>();
    private final ArrayList<PrefabComponent> sortedComponents
            = new ArrayList<>();
    /**
     * Defines if this prefab can have children.
     */
    protected boolean canHaveChildren = false;
    /**
     * When a prefab is locked, its translation/rotation and scale cannot be
     * changed anymore.
     */
    private boolean locked;
    /**
     * Field for editing purposes. For examples it is sometimes easier to view a
     * building in wireframe mode.
     */
    private boolean wireframe;
    /**
     * To simplify construction of complex objects, such as houses.
     */
    private MagnetParameter magnets;
    /**
     * The name of the closest magnet.
     */
    private Magnet attachMagnet;
    /**
     * The fillerparameter defines how filler geometry will be generated.
     */
    private FillerParameter fillers;

    /**
     * Creates a new empty prefab object.
     */
    public Prefab() {
    }

    /**
     * Returns the name of the layer.
     *
     * @return the name of the layer.
     */
    public String getLayerName() {
        try {
            Parameter p = objectType.findParameter("ObjectComponent", "layer");
            Object layer = p.invokeGet(this);
            return layer != null ? layer.toString() : "default";
        } catch (NullPointerException ex) {
            return "default";
        }
    }

    /**
     * Sets the layerName of the layer.
     *
     * @param layerName the name of the layer.
     */
    public final void setLayerName(String layerName) {
        Parameter p = objectType.findParameter("ObjectComponent", "layer");
        p.invokeSet(this, layerName, true);
    }

    /**
     * Returns the object type of this prefab object.
     *
     * @return the object type of the prefab object.
     */
    public ObjectType getObjectType() {
        return objectType;
    }

    public String getType() {
        return type;
    }

    public final void setType(String type) {
        this.type = type;
    }

    /**
     * The category of this prefab type.
     *
     * @return the category of this prefab type.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of this prefab type.
     *
     * @param category the category of this type.
     */
    public final void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the transform component from this prefab, or null if no such
     * component exists, or if the component does not have the required type.
     *
     * @return the TransformComponent object.
     */
    public TransformComponent getTransformComponent() {
        PrefabComponent c = getComponent("TransformComponent");
        return c instanceof TransformComponent ? (TransformComponent) c : null;
    }
    
      /**
     * Gets the transform component from this prefab, or null if no such
     * component exists, or if the component does not have the required type.
     *
     * @return the TransformComponent object.
     */
    public ObjectComponent getObjectComponent() {
        PrefabComponent c = getComponent("ObjectComponent");
        return c instanceof ObjectComponent ? (ObjectComponent) c : null;
    }

    public void setOriginalMaterial(Material material) {
        this.originalMaterial = material;
    }

    public Material getOriginalMaterial() {
        return originalMaterial;
    }

    public String getPrefix() {
        return prefix;
    }

    /**
     * Initializes the prefab object.
     *
     * @param manager the asset manager to load assets.
     * @param objectType the object type of the prefab.
     * @param extraInfo extra info for the creation process.
     */
    public void initialize(AssetManager manager, ObjectType objectType, String extraInfo) {
        this.objectType = objectType;
        create(manager, extraInfo);
    }

    /**
     * Creates a new prefab object.
     *
     * @param manager the asset manager to load assets.
     * @param extraInfo extra info for the creation process.
     */
    protected void create(AssetManager manager, String extraInfo) {
    }

    /**
     * Sets the name of this prefab.
     *
     * @param name the name of the prefab.
     */
    @Override
    public final void setName(String name) {
        Parameter p = objectType.findParameter("ObjectComponent", "name");
        if (p != null) {
            p.invokeSet(this, name, true);
        } else {
            super.setName(name);
        }
    }

    @Override
    public String getName() {
        if (objectType == null) {
            return "<error>";
        }
        Parameter p = objectType.findParameter("ObjectComponent", "name");
        if (p != null) {
            Object objectname = p.invokeGet(this);
            if (objectname != null) {
                return objectname.toString();
            } else {
                return "error with " + this.getClass().getName();
            }
        } else {
            return super.getName();
        }

    }

    /**
     * Sets a parameter on this object.
     *
     * @param property the property to set.
     * @param value the value to set.
     * @param undoableEdit true if this is an undoable edit, false otherwise.
     */
    public void setParameter(Parameter property, Object value, boolean undoableEdit) {
        if (locked && !"locked".equals(property.getProperty())) {
            return;
        }
        this.addUpdateObject(new UpdateObject(property, value, undoableEdit));
    }

    private void setParameterFromUpdateThread(UpdateObject uo) {
        uo.execute(this, uo.isUndoableEdit());
    }

    public void call(Parameter parameter) {
        this.addUpdateObject(new UpdateObject(parameter, null, false));
    }

    public void addUpdateObject(UpdateObject uo) {
        synchronized (workList) {
            workList.add(uo);
        }
    }

    public void addTask(Runnable r) {
        synchronized (taskList) {
            taskList.add(r);
        }
    }

    @Override
    public void updateLogicalState(float tpf) {
        synchronized (workList) {
            for (UpdateObject uo : this.workList) {
                //System.out.println("setting property : " + uo.getProperty() + "," + uo.getValue());
                this.setParameterFromUpdateThread(uo);
            }
            workList.clear();
        }
        synchronized (taskList) {
            for (Runnable r : this.taskList) {
                r.run();
            }
            taskList.clear();
        }
        for (PrefabComponent pc : this.getComponents()) {
            pc.update(tpf);
        }

        super.updateLogicalState(tpf);
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Checks if this prefab is changed.
     *
     * @return true if the prefab is changed, false otherwise.
     */
    public boolean isChanged() {
        return changed;
    }

    public boolean isChanged(boolean recursively) {
        if (changed || !recursively) {
            return changed;
        } else {
            for (Spatial s : this.children) {
                if (s instanceof Prefab) {
                    boolean childChanged = ((Prefab) s).isChanged(recursively);
                    if (childChanged) {
                        return true;
                    }
                }
            }
            return changed;
        }
    }

    /**
     * Sets the changed property of the prefab recursively.
     *
     * @param changed true if the prefab is changed, false otherwise.
     * @param recursively also mark the children as changed.
     */
    public void setChanged(boolean changed, boolean recursively) {
        this.changed = changed;
        for (Spatial s : this.children) {
            if (s instanceof Prefab) {
                ((Prefab) s).setChanged(changed, recursively);
            }
        }
    }

    public void setOffset(Vector3f offset) {
        if (offset == null) {
            this.offset = Vector3f.ZERO;
        } else {
            this.offset = offset;
        }
        // this.setLocalTranslation(offset);
        // add the offset as local translation of the children.
        /*
         for (Spatial s : this.getChildren()) {
         //System.out.println("children to offset : " + s.getName());
         Vector3f lt = s.getLocalTranslation();
         s.setLocalTranslation(lt.x-this.offset.x, lt.y-this.offset.y, lt.z-this.offset.z);
         }
         */
    }

    /**
     * Sets the pivot point of this prefab.
     *
     * @param pivot the new pivot point of the prefab.
     */
    public void setPivot(Vector3f pivot) {
        this.pivot = pivot.clone();
    }

    /**
     * Returns the pivot point of the prefab.
     *
     * @return the pivot point of the prefab.
     */
    public Vector3f getPivot() {
        return pivot;
    }

    public Vector3f getOffset() {
        return offset;
    }

    public boolean hasMagnets() {
        return magnets != null && magnets.hasMagnets();
    }

    public void setMagnets(MagnetParameter mp) {
        this.magnets = mp;
        if (hasMagnets()) {
            Magnet magnet = mp.getPivotMagnet();

//            for (Magnet m : magnets.iterate()) {
//                MagnetObject mo = new MagnetObject(m);
//                
//                attachChild(mo);
//            }
            this.setOffset(magnet.getLocation());
        }
    }

    public Iterable<Magnet> getMagnets() {
        return magnets.iterate();
    }

    public void cyclePivot() {
        Magnet pivot = magnets.cyclePivotMagnet();
        this.setOffset(pivot.getLocation());
        this.setLocalRotation(pivot.getLocalFrame());
    }

    public void selectPivot(String pivotName) {
        Magnet pivot = magnets.selectPivot(pivotName);
        //System.out.println("Selecting pivot : " + pivotName +", actual : " + pivot.getName());
        this.setOffset(pivot.getLocation());
        this.setLocalRotation(pivot.getLocalFrame());
    }

    public void nextRotationValue() {
        if (attachMagnet != null) {
            attachMagnet.nextRotationValue();
        }
    }

    public void previousRotationValue() {
        if (attachMagnet != null) {
            attachMagnet.previousRotationValue();
        }
    }

    public void setAttachMagnet(Magnet magnet) {
        this.attachMagnet = magnet;
    }

    public Magnet getAttachMagnet() {
        return attachMagnet;
    }

    @Override
    public String toString() {
        //String result = "Prefab (" + this.getType() + ") : " + this.getName() + "\n";
        //result += "Current magnet : " + magnets.getPivotMagnet().getName() + "\n";
        return this.getName();
    }

    public void setFillers(FillerParameter fp) {
        this.fillers = fp;
    }
    private static CollisionResults results = new CollisionResults();

    /**
     * Tries to find objects in the scene to connect with.
     *
     * @param sceneElements
     */
    public void connect(Node sceneElements) {
        if (fillers != null) {
            for (Quad q : fillers.iterable()) {
                Vector3f start = q.getConnectorLocation();
                Vector3f startWithOffset = start.subtract(offset);
                Vector3f dir = q.getConnectorDirection();

                Vector3f startWorld = new Vector3f();
                this.localToWorld(startWithOffset, startWorld);
                //this.getWorldRotation().
                Vector3f dirWorld = this.getWorldRotation().mult(dir);

//                Sphere sphere0 = new Sphere(6, 6, .05f);
//                Geometry trianglepoint0 = new Geometry("tr1", sphere0);
//                trianglepoint0.setMaterial(originalMaterial);
//                trianglepoint0.move(startWorld);
//                //sceneElements.attachChild(trianglepoint0);
                Ray ray = new Ray(startWorld, dirWorld);
                //ray.setLimit(q.getMaxLength());
                results = new CollisionResults();
                sceneElements.collideWith(ray, results);
                CollisionResult cr = results.getClosestCollision();

                if (cr != null) {
                    System.out.println("cr.getDistance :" + cr.getDistance());
                } else {
//                    Sphere sphere0 = new Sphere(6, 6, .01f);
//                    Geometry raystart= new Geometry("tr1", sphere0);
//                    raystart.setMaterial(originalMaterial);
//                    raystart.move(startWorld);
//                    
//                    Sphere sphere1 = new Sphere(6, 6, .01f);
//                    Geometry rayend = new Geometry("tr2", sphere1);
//                    rayend.setMaterial(originalMaterial);
//                    rayend.move(startWorld.add(dirWorld));
//                    
//                    sceneElements.attachChild(raystart);
//                    sceneElements.attachChild(rayend);
                    System.out.println("Raycasting failed");
                }
                if (cr != null && cr.getDistance() < q.getMaxLength()) {
                    Prefab other = findPrefabParent(cr.getGeometry(), sceneElements);
                    if (other == null) {
                        //System.out.println("no prefab parent!");
                        return;
                    }
                    FillerParameter otherfp = other.getFillerParameter();
                    if (otherfp == null) {
                        //System.out.println("No filler found!");
                        continue;
                    }
                    Quad otherQuad = otherfp.getQuad(cr.getTriangleIndex());
                    if (otherQuad == null) {
                        //System.out.println("No quad found for triangle index : " + cr.getTriangleIndex());
                        continue;
                    }
                    //System.out.println("Connecting " + q.getName() + " to " + otherQuad.getName());
                    Vector3f lps[] = new Vector3f[8];

                    lps[0] = q.getP1();
                    lps[1] = q.getP2();
                    lps[2] = q.getP3();
                    lps[3] = q.getP4();

                    lps[4] = otherQuad.getP1();
                    lps[5] = otherQuad.getP2();
                    lps[6] = otherQuad.getP3();
                    lps[7] = otherQuad.getP4();
                    // 
                    Vector3f wps[] = new Vector3f[8];
                    for (int i = 0; i < 4; ++i) {
                        wps[i] = new Vector3f();
                        this.localToWorldWithOffset(lps[i], wps[i]);
                    }

                    for (int i = 4; i < 8; ++i) {
                        wps[i] = new Vector3f();
                        other.localToWorldWithOffset(lps[i], wps[i]);
                    }

                    // check angle, if the angle is too large compared to the direction of the normal , do not connect, but 
                    // extrude the points according to the measured raycast distance.
                    Vector3f diff = wps[4].subtract(wps[0]);
                    diff.normalizeLocal();
                    // angle of 45 degrees
                    // todo , make it configurable

                    if (Math.abs(diff.dot(dirWorld)) < 0.707) {
                        //System.out.println("extruding the raycast quad :" + cr.getDistance());
                        dirWorld.multLocal(cr.getDistance());
                        for (int i = 0; i < 4; ++i) {
                            wps[i + 4] = dirWorld.add(wps[i]);
                        }
                    }
                    ConnectorPrefab cp = new ConnectorPrefab();
                    cp.setPoints(wps, q.isClockWise());

                    sceneElements.attachChild(cp);
                }
            }
        }
    }

    private void localToWorldWithOffset(Vector3f local, Vector3f world) {
        Vector3f l = local.subtract(this.getOffset());
        this.localToWorld(l, world);
    }

    private Prefab findPrefabParent(Geometry g, Node sceneElements) {
        Node pnode = g.getParent();
        while (!(pnode instanceof Prefab) && pnode != sceneElements && pnode != null) {
            pnode = pnode.getParent();
        }
        if (pnode != null && pnode instanceof Prefab) {
            return (Prefab) pnode;
        } else {
            return null;
        }
    }

    private FillerParameter getFillerParameter() {
        return this.fillers;
    }
    
    /**
     * @return the physicsMesh
     */
    public String getPhysicsMesh() {
        return physicsMesh;
    }

    /**
     * @param physicsMesh the physicsMesh to set
     */
    public void setPhysicsMesh(String physicsMesh) {
        this.physicsMesh = physicsMesh;
    }

    public boolean hasPhysicsMesh() {
        return physicsMesh != null && physicsMesh.length() > 0;
    }

    /**
     * Duplicates a prefab by duplicating all the components.
     *
     * @param assetManager the assetmanager to use for duplication.
     * @param duplicateChildren if true, also duplicate all the children of this
     * prefab.
     * @return a duplicate of the object, components included.
     */
    public final Prefab duplicate(AssetManager assetManager, boolean duplicateChildren) {
        ObjectTypeCategory otc = GlobalObjects.getInstance().getObjectsTypeCategory();
        Prefab duplicate = null;
        if (objectType != null) {
            duplicate = objectType.createDefault(assetManager, getName(), false);
            duplicateComponents(duplicate, otc);

        }
        if (duplicateChildren && duplicate != null) {
            for (int i = 0; i < this.getPrefabChildCount(); ++i) {
                Prefab child = (Prefab) this.getPrefabChildAt(i);
                Prefab childCopy = child.duplicate(assetManager, true);
                duplicate.attachChild(childCopy);
            }
        }
        return duplicate;
    }

    public final void duplicateComponents(Prefab target, ObjectTypeCategory otc) {
        /*
        for (Parameter p : objectType.getAllParameters()) {
            Object value = p.invokeGet(this);
            Object cloned = PropertyReflector.clone(value);
            if (value != null) {
                p.invokeSet(target, cloned, false);
            }
        }*/

        for (PrefabComponent pc : this.getComponents()) {
            ComponentType ct = otc.getComponent(pc.getId());
            if (ct != null) {
                if (!target.hasPrefabComponent(ct.getId())) {
                    PrefabComponent copy = ct.create();
                    target.addPrefabComponent(copy);
                }

                ct.copy(this, target);
                PrefabComponent copied = target.getComponent(ct.getId());
                if (copied != null) {
                    copied.install(target);
                }
            }
        }
    }

    /**
     * @return the locked
     */
    public boolean getLocked() {
        return locked;
    }

    /**
     * @param locked the locked to set
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * @return the wireframe
     */
    public boolean getWireframe() {
        return wireframe;
    }

    /**
     * @param wireframe the wireframe to set
     */
    public void setWireframe(boolean wireframe) {
        if (this.wireframe == wireframe) {
            return;
        }
        setWireFrameRecursively(this, wireframe);
        this.wireframe = wireframe;
    }

    private void setWireFrameRecursively(Node parent, boolean wireframe) {
        for (Spatial child : parent.getChildren()) {
            if (child instanceof Gizmo) {
                continue;
            }
            if (child instanceof Geometry) {
                Geometry g = (Geometry) child;
                if (wireframe) {
                    Material original = g.getMaterial();
                    g.setUserData("OriginalMaterial", original);
                    g.setMaterial(GlobalObjects.getInstance().getWireFrameMaterial());
                } else {
                    Material original = g.getUserData("OriginalMaterial");
                    if (original != null) {
                        g.setMaterial(original);
                    }
                }
            } else if (child instanceof Node) {
                setWireFrameRecursively((Node) child, wireframe);
            }
        }
    }

    public int getNumParentLayers() {
        int numberOfDots = 0;
        int startIndex = -1;
        String layerName = this.getLayerName();
        while ((startIndex = layerName.indexOf('.', startIndex + 1)) > 0) {
            ++numberOfDots;
        }
        return numberOfDots + 1;
    }

    public void notifyLoaded() {
    }

    @Override
    public void setLocalRotation(Matrix3f rotation) {
        super.setLocalRotation(rotation);
        Parameter prot = this.getObjectType().findParameter("TransformComponent", "rotation");
        if (prot != null) {
            prot.notifyChangeInValue(this);
        }
        rotationChanged();
    }

    @Override
    public void setLocalRotation(Quaternion quaternion) {
        super.setLocalRotation(quaternion);
        Parameter prot = this.getObjectType().findParameter("TransformComponent", "rotation");
        if (prot != null) {
            prot.notifyChangeInValue(this);
        }
        rotationChanged();
    }

    public void setLocalPrefabRotation(Quaternion quaternion) {
        //To change body of generated methods, choose Tools | Templates.
        if (locked) {
            return;
        }

        Vector3f origPivot = null;
        if (pivot != Vector3f.ZERO) {
            origPivot = getLocalPrefabRotation().mult(pivot);
        }

        RigidBodyControl rbc = this.getControl(RigidBodyControl.class);
        if (parent != null && rbc != null && rbc.isEnabled()) {
            rbc.setPhysicsRotation(parent.getWorldRotation().mult(quaternion));
            // handle pivot.
        } else {
            this.setLocalRotation(quaternion);
        }
        if (pivot != Vector3f.ZERO) {
            Vector3f newPivot = quaternion.mult(pivot);
            Vector3f diff = newPivot.subtract(origPivot);
            Vector3f newt = this.getLocalPrefabTranslation().subtract(diff);
            this.setLocalPrefabTranslation(newt);
        }
    }

    public Quaternion getLocalPrefabRotation() {
        //To change body of generated methods, choose Tools | Templates.
        RigidBodyControl rbc = this.getControl(RigidBodyControl.class);
        if (rbc != null && rbc.isEnabled()) {
            Quaternion q = rbc.getPhysicsRotation();
            Quaternion world = getParent().getWorldRotation();
            return world.inverse().mult(q);

        } else {
            return getLocalRotation();
        }

    }

    @Override
    public void setLocalScale(float localScale) {
        if (locked) {
            return;
        }
        super.setLocalScale(localScale);
        Parameter pscale = this.getObjectType().findParameter("TransformComponent", "scale");
        pscale.notifyChangeInValue(this);
        scaleChanged();
    }

    @Override
    public void setLocalScale(float x, float y, float z) {
        if (locked) {
            return;
        }
        super.setLocalScale(x, y, z);
        Parameter pscale = this.getObjectType().findParameter("TransformComponent", "scale");
        if (pscale != null) {
            pscale.notifyChangeInValue(this);
        }
        scaleChanged();
    }

    @Override
    public void setLocalScale(Vector3f localScale) {
        if (locked) {
            return;
        }
        super.setLocalScale(localScale);
        Parameter pscale = this.getObjectType().findParameter("TransformComponent", "scale");
        if (pscale != null) {
            pscale.notifyChangeInValue(this);
        }
    }

    public void setLocalPrefabTranslation(Vector3f localTranslation) {
        if (locked) {
            return;
        }
        //To change body of generated methods, choose Tools | Templates.
        RigidBodyControl rbc = this.getControl(RigidBodyControl.class);
        if (parent != null && rbc != null && rbc.isEnabled()) {
            Vector3f pt = getParent().localToWorld(localTranslation, null);
            rbc.setPhysicsLocation(pt);
        } else {
            this.setLocalTranslation(localTranslation);
        }
    }

    public Vector3f getLocalPrefabTranslation() {
        //To change body of generated methods, choose Tools | Templates.
        RigidBodyControl rbc = this.getControl(RigidBodyControl.class);
        if (rbc != null && rbc.isEnabled()) {
            Vector3f world = rbc.getPhysicsLocation();
            return getParent().worldToLocal(world, null);
        } else {
            return getLocalTranslation();
        }
    }

    @Override
    public void setLocalTranslation(Vector3f localTranslation) {
        super.setLocalTranslation(localTranslation);
        Parameter ptrans = this.getObjectType().findParameter("TransformComponent", "translation");
        if (ptrans != null) {
            ptrans.notifyChangeInValue(this);
        }

        translationChanged();
    }

    @Override
    public void setLocalTranslation(float x, float y, float z) {
        super.setLocalTranslation(x, y, z);
        Parameter ptrans = this.getObjectType().findParameter("TransformComponent", "translation");
        if (ptrans != null) {
            ptrans.notifyChangeInValue(this);
        }
        translationChanged();
    }

    /**
     * Hides the target objects of this object.
     */
    public void hideTargetObjects() {
    }

    public void showTargetObjects() {
    }

    /**
     * Checks if a prefab has children that need to be saved when the prefab is
     * written to file. First the state of the canHaveChildren is checked, next
     * a check will be performed to see if the object has savable children.
     *
     * @return true if this prefab has savable children, false otherwise.
     */
    public boolean hasSavableChildren() {
        if (!canHaveChildren) {
            return false;
        } else {
            return getPrefabChildCount() > 0;
        }
    }

    public boolean canHaveChildren() {
        return canHaveChildren;
    }

    public Object getPrefabChildAt(int index) {
        int pindex = 0;
        for (Spatial s : this.getChildren()) {
            if (s instanceof Prefab) {
                if (pindex == index) {
                    return s;
                }
                ++pindex;
            }
        }
        return null;
    }

    public int getPrefabChildCount() {
        int pindex = 0;
        for (Spatial s : this.getChildren()) {
            if (s instanceof Prefab) {
                ++pindex;
            }
        }
        return pindex;
    }

    public int indexOfPrefab(Prefab prefab) {
        int pindex = 0;
        for (Spatial s : this.getChildren()) {
            if (s == prefab) {
                return pindex;

            }
            ++pindex;
        }
        return -1;
    }

    public void addPhysics(PhysicsSpace space) {
    }

    public void addPhysics(PhysicsSpace space, float mass) {
    }

    public void disablePhysics() {
        PhysicsControl pc = this.getControl(PhysicsControl.class);
        if (pc != null) {
            pc.setEnabled(false);
        }
    }

    public void enablePhysics() {
        PhysicsControl pc = this.getControl(PhysicsControl.class);
        if (pc != null) {
            pc.setEnabled(true);
        }
    }

    @Override
    public int attachChild(Spatial child) {
        if (child instanceof Axis || child instanceof RotateGizmo) {
            child.setLocalTranslation(this.pivot);
        }
        return super.attachChild(child); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasChildren() {
        return this.getPrefabChildCount() > 0;
    }

    @Override
    public ProjectTreeNode getProjectChild(int index) {
        return (ProjectTreeNode) this.getPrefabChildAt(index);
    }

    @Override
    public int getIndexOfChild(ProjectTreeNode object) {
        int pindex = 0;
        for (Spatial s : this.getChildren()) {
            if (s instanceof Prefab) {
                if (s == object) {
                    return pindex;
                }
                ++pindex;
            }
        }
        return -1;
    }

    @Override
    public ProjectTreeNode getProjectParent() {
        Node p = this;
        do {
            p = p.getParent();
        } while (p != null && !(p instanceof ProjectTreeNode));

        if (p instanceof dae.project.Level) {
            dae.project.Level l = (dae.project.Level) p;
            String layerName = this.getLayerName();
            return l.getLayer(layerName);
        } else if (p instanceof ProjectTreeNode) {
            return (ProjectTreeNode) p;
        } else {
            return null;
        }
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    /**
     * Returns an extra rotation that should be applied to the quaternion.
     *
     * @return null, if no extra rotation exists, or a Quaternion with an extra
     * rotation.
     */
    public Quaternion getGizmoRotation() {
        return null;
    }

    /**
     * Adds all the components in the list to this prefab object.
     *
     * @param cl the component list to add.
     */
    public void addComponents(ComponentList cl) {
        for (PrefabComponent c : cl.getComponents()) {
            addPrefabComponent(c, true);
        }
    }

    /**
     * Adds a single PrefabComponent to the list of components. The
     * PrefabComponent will be installed immediately.
     *
     * @param pc the PrefabComponent to add.
     */
    public final void addPrefabComponent(PrefabComponent pc) {
        addPrefabComponent(pc, true);
    }

    /**
     * Adds a component with the option to install it immediately or not. If a
     * component of the same type is allready present it will be removed.
     *
     * @param pc the prefab component to add.
     * @param install true if the component should be installed , false
     * otherwise.
     */
    public final void addPrefabComponent(PrefabComponent pc, boolean install) {
        this.componentMap.put(pc.getId(), pc);
        this.sortedComponents.add(pc);
        Collections.sort(sortedComponents);
        if (install) {
            pc.install(this);
        }
        notifyComponentAdded(pc);
    }

    public final void removePrefabComponent(PrefabComponent pc) {
        componentMap.remove(pc.getId());
        sortedComponents.remove(pc);
    }

    public void installAllComponents() {
        Collections.sort(sortedComponents);

        //System.out.println("Installing components for : " + this.getName());
        for (PrefabComponent pc : sortedComponents) {
            //System.out.println("Installing : " + pc.getId());
            pc.install(this);
        }
    }

    /**
     * Returns the PrefabComponent with the given id, or null if no component is
     * found.
     *
     * @param id the id of the prefab component.
     * @return the PrefabComponent.
     */
    public PrefabComponent getComponent(String id) {
        return componentMap.get(id);
    }

    /**
     * Checks if this prefab has components.
     *
     * @return true if the prefab has components, false otherwise.
     */
    public boolean hasComponents() {
        return componentMap.size() > 0;
    }

    /**
     * Returns a list of components.
     *
     * @return the list of components.
     */
    public Iterable<PrefabComponent> getComponents() {
        return sortedComponents;
    }

    /**
     * Adds a component listener to the list of listeners to receive component
     * added or removed events.
     *
     * @param cl the ComponentListener to add.
     */
    public void addComponentListener(ComponentListener cl) {
        if (componentListeners == null) {
            componentListeners = new ArrayList<>();
        }
        componentListeners.add(cl);
    }

    /**
     * Removes a component listener from the list of listeners to receive
     * component added or removed events.
     *
     * @param cl the ComponentListener to add.
     */
    public void removeComponentListener(ComponentListener cl) {
        if (componentListeners != null) {
            componentListeners.remove(cl);
        }
    }

    private void notifyComponentAdded(PrefabComponent pc) {
        if (componentListeners != null) {
            for (ComponentListener cl : componentListeners) {
                cl.componentAdded(this, pc);
            }
        }
    }

    /**
     * This is a notification for subclasses that the rotation has changed.
     */
    public void rotationChanged() {
    }

    /**
     * This is a notification for subclasses that the translation has changed.
     */
    public void translationChanged() {
    }

    /**
     * This is a notification for subclasses that the scale has changed.
     */
    public void scaleChanged() {
    }

    /**
     * Checks if this prefab has a component with the given id.
     *
     * @param id the id to check.
     * @return true if this component has the prefabcomponent, false otherwise.
     */
    public boolean hasPrefabComponent(String id) {
        return this.componentMap.containsKey(id);
    }

    /**
     * Removes the component from this prefab. If recursively is set to true the
     * component will be removed from the entire tree. This can be useful to
     * 'bake' physics.
     *
     * @param id the id of the component.
     * @param recursively remove the component from this entire subtree.
     */
    public void removePrefabComponent(String id, boolean recursively) {
        removePrefabComponent(id);
        if (recursively) {
            for (int i = 0; i < getPrefabChildCount(); ++i) {
                Prefab child = (Prefab) this.getPrefabChildAt(i);
                child.removePrefabComponent(id, recursively);
            }
        }
    }

    /**
     * Removes the prefabcomponent with the given id.
     *
     * @param id the id of the prefab component.
     */
    public void removePrefabComponent(final String id) {
        this.addTask(new Runnable() {
            public void run() {
                PrefabComponent pc = getComponent(id);
                if (pc != null) {
                    pc.deinstall();
                }
                componentMap.remove(id);
                sortedComponents.remove(pc);
            }
        });
    }

    public void removeComponents(String id) {
        PrefabComponent pc = componentMap.get(id);
        if (pc != null) {
            removePrefabComponent(pc);
        }
    }

    @Override
    public void addToLayer(String layer) {
        ObjectComponent oc = getObjectComponent();
        if ( oc != null ){
            oc.setLayer(layer);
        }
    }
    
    @Override
    public void removeFromLayer(String layer){
        ObjectComponent oc = getObjectComponent();
        if ( oc != null ){
            oc.setLayer(layer);
        }
    }

    @Override
    public void setVisible(boolean visible) {
        if ( visible ){
            setCullHint(CullHint.Dynamic);
        }else{
            setCullHint(CullHint.Always);
        }
    }

    @Override
    public boolean isVisible() {
        return getCullHint() != CullHint.Always;
    }
}
