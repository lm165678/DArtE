package dae.prefabs.types;

import com.google.common.io.Files;
import com.jme3.asset.AssetManager;
import com.jme3.asset.AssetNotFoundException;
import com.jme3.asset.ModelKey;
import com.jme3.scene.Node;
import dae.components.ComponentList;
import dae.components.ComponentType;
import dae.components.PrefabComponent;
import dae.prefabs.Prefab;
import dae.prefabs.magnets.FillerParameter;
import dae.prefabs.magnets.MagnetParameter;
import dae.prefabs.parameters.InstanceCreator;
import dae.prefabs.parameters.Parameter;
import dae.prefabs.parameters.ParameterSection;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Koen
 */
public class ObjectType extends ParameterSupport {

    private String label;
    private String category;
    private String objectClass;
    private String extraInfo;
    private boolean loadFromExtraInfo = false;
    private ArrayList<ComponentType> componentTypes = new ArrayList<ComponentType>();
    private HashMap<String, ComponentType> componentMap = new HashMap<String, ComponentType>();
    private HashMap<String, HashMap<String, Object>> defaultMap = new HashMap<String, HashMap<String, Object>>();

    /*
     * Creates a new objectype.
     * @param category the category of the object type.
     * @param label the label of the object.
     * @param objectClass the Java class for this object.
     * @param extraInfo the extra info for this object.
     * @param loadFromExtraInfo use a standard loader for this object.
     */
    public ObjectType(String category, String label, String objectClass, String extraInfo, boolean loadFromExtraInfo) {
        this.category = category;
        this.label = label;
        this.objectClass = objectClass;
        this.extraInfo = extraInfo;
        this.loadFromExtraInfo = loadFromExtraInfo;
    }

    /**
     * @return the objectToCreate
     */
    public String getObjectToCreate() {
        return objectClass;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String asset) {
        this.extraInfo = asset;
    }

    /**
     * Returns the category of the type to create.
     *
     * @return the category of the type to create.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the category of the type to create.
     *
     * @return the category of the type to create.
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param objectToCreate the objectToCreate to set
     */
    public void setObjectToCreate(String objectToCreate) {
        this.objectClass = objectToCreate;
    }

    public boolean usesDefaultLoader() {
        return this.loadFromExtraInfo;
    }

    /**
     * Adds a component type to the object type definition.
     *
     * @param ct the component type to add.
     */
    public void addComponentType(ComponentType ct) {
        this.componentTypes.add(ct);
        componentMap.put(ct.getId(), ct);
    }

    /**
     * Returns the list of component types.
     *
     * @return the list of component types.
     */
    public Iterable<ComponentType> getComponentTypes() {
        return componentTypes;
    }

    /**
     * Creates a Prefab with the info in this object type.
     *
     * @param parent the parent node of the current object.
     * @param manager the AssetManager to use to create the object.
     * @param name the name of the new object.
     */
    public Prefab create(AssetManager manager, String name) {
        Prefab p = null;
        if (usesDefaultLoader()) {
            ModelKey mk = new ModelKey(getExtraInfo());
            p = (Prefab) manager.loadAsset(mk);
            loadComponents(manager, p, mk);

        } else {
            try {

                p = (Prefab) Class.forName(getObjectToCreate()).newInstance();
                p.initialize(manager, this, getExtraInfo());
                p.notifyLoaded();


                MagnetParameter mp = (MagnetParameter) findParameter("magnets");
                p.setMagnets(mp);

                FillerParameter fp = (FillerParameter) findParameter("filler");
                p.setFillers(fp);
            } catch (InstantiationException ex) {
                Logger.getLogger(ObjectType.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(ObjectType.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ObjectType.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (p != null) {
            for (ComponentType c : this.componentTypes) {
                PrefabComponent pc = c.create();
                if (pc != null) {
                    p.addPrefabComponent(pc);
                }
            }
            p.setName(name);

            // set the defaults.
            for (ComponentType c : this.componentTypes) {
                for (Parameter param : c.getAllParameters()) {
                    Object def = this.getDefaultValue(c.getId(), param.getId());

                    if (def instanceof InstanceCreator) {
                        InstanceCreator ic = (InstanceCreator) def;
                        def = ic.createInstance();
                        if (def instanceof Prefab) {
                            p.attachChild((Prefab) def);
                        }
                    } else if (def instanceof Cloneable) {
                        def = cloneDefault(def);
                    }
                    if (def != null) {
                        param.invokeSet(p, def, false);
                    }
                }
            }

            for (ParameterSection section : this.getParameterSections()) {
                for (Parameter param : section.getAllParameters()) {
                    if (param.hasDefaultValue()) {
                        Object def = param.getDefault();
                        if (def instanceof InstanceCreator) {
                            InstanceCreator ic = (InstanceCreator) def;
                            def = ic.createInstance();
                            if (def instanceof Prefab) {
                                p.attachChild((Prefab) def);
                            }
                        } else if (def instanceof Cloneable) {
                            def = cloneDefault(def);
                        }
                        param.invokeSet(p, def, false);
                    }
                }
            }
        }
        return p;
    }

    /**
     * Loads the components if a components file with the same name exists.
     *
     * @param manager the manager object.
     * @param p the prefab to load the component file from.
     * @param mk the modelkey of the original object.
     */
    private void loadComponents(AssetManager manager, Prefab p, ModelKey mk) {
        String folder = mk.getFolder();
        String name = mk.getName();
        String baseName = Files.getNameWithoutExtension(name);
        String components = folder + baseName + ".components";

        try {
            Object result = manager.loadAsset(components);
            if (result instanceof ComponentList) {
                ComponentList cl = (ComponentList) result;
                p.addComponents(cl);
            }
        } catch (AssetNotFoundException ex) {
            // not an error, it is possible that there is no components file.
        }
    }

    /**
     * Returns the component type with the given id.
     *
     * @param id the id for the component type.
     * @return the ComponentType or null if no ComponentType with the given id
     * is found.
     */
    public ComponentType getComponentType(String id) {
        return this.componentMap.get(id);
    }

    /**
     * Finds the parameter that is bound to the given component for the given
     * property.
     *
     * @param transformComponent
     * @param property
     * @return the Parameter object or null if the parameter is not found.
     */
    public Parameter findParameter(String componentId, String property) {
        ComponentType ct = componentMap.get(componentId);
        if (ct != null) {
            return ct.findParameter(property);
        } else {
            return null;
        }
    }

    /**
     * Checks if this object type has prefab parameters
     *
     * @return
     */
    public boolean hasParameters() {
        int numParams = 0;
        for (ParameterSection ps : this.getParameterSections()) {
            numParams += ps.getNrOfParameters();

        }
        return numParams > 0;
    }

    /**
     * Sets a default value that can be used when constructing this object.
     *
     * @param componentId the component id of the default value.
     * @param propertyId the property id of the default value.
     * @param parsed the parsed default value.
     */
    public void setDefaultValue(String componentId, String propertyId, Object parsed) {
        HashMap<String, Object> categoryMap = this.defaultMap.get(componentId);
        if (categoryMap == null) {
            categoryMap = new HashMap<String, Object>();
            defaultMap.put(componentId, categoryMap);
        }
        categoryMap.put(propertyId, parsed);
    }

    /**
     * Returns the default value for a given component and property combination.
     *
     * @param componentId the id of the component.
     * @param propertyId the id of the property.
     * @return Returns the default value if it exists, false otherwise.
     */
    public Object getDefaultValue(String componentId, String propertyId) {
        HashMap<String, Object> categoryMap = this.defaultMap.get(componentId);
        if (categoryMap == null) {
            return null;
        } else {
            return categoryMap.get(propertyId);
        }
    }

    private Object cloneDefault(Object def) {
        try {
            Cloneable c = (Cloneable) def;
            Method m = def.getClass().getMethod("clone");
            def = m.invoke(def);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(ObjectType.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ObjectType.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ObjectType.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ObjectType.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ObjectType.class.getName()).log(Level.SEVERE, null, ex);
        }
        return def;
    }
}