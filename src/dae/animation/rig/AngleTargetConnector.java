package dae.animation.rig;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import dae.animation.skeleton.AttachmentPoint;
import dae.animation.skeleton.RevoluteJoint;
import dae.io.XMLUtils;
import dae.prefabs.standard.PrefabPlaceHolder;
import dae.util.MathUtil;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 *
 * @author Koen Samyn
 */
public class AngleTargetConnector implements InputConnector {

    private String jointName;
    private String targetName;
    private String attachmentName;
    protected Joint joint;
    protected AttachmentPoint attachment;
    protected Spatial target;
    private boolean initialized = false;

    public AngleTargetConnector() {
    }
    
    @Override
    public boolean isInitialized(){
        return initialized;
    }

    /**
     * @return the jointName
     */
    @Override
    public String getJointName() {
        return jointName;
    }

    /**
     * @param jointName the jointName to set
     */
    @Override
    public void setJointName(String jointName) {
        this.jointName = jointName;
    }

    /**
     * @return the targetName
     */
    public String getTargetName() {
        return targetName;
    }

    /**
     * @param targetName the targetName to set
     */
    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    /**
     * @return the attachmentName
     */
    public String getAttachmentName() {
        return attachmentName;
    }

    /**
     * @param attachmentName the attachmentName to set
     */
    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    @Override
    public void initialize(Rig rig) {
        initialized = false;
        Spatial sjoint = rig.getChild(jointName);
        if (sjoint instanceof Joint) {
            this.joint = (Joint) sjoint;
        } else {
            return;
        }

        Spatial sattachment = rig.getChild(attachmentName);
        if (sattachment instanceof AttachmentPoint) {
            this.attachment = (AttachmentPoint) sattachment;
        } else {
            return;
        }

        // search for the target from the top level.
        target = rig.getTarget(targetName);
        
        if (target != null && !(target instanceof PrefabPlaceHolder)) {
            initialized = true;
        }

    }

    @Override
    public float getValue() {
        Vector3f axis = joint.getWorldRotationAxis();
        axis.normalizeLocal();
        Vector3f origin = joint.getWorldTranslation();

        Vector3f apLoc = attachment.getWorldTranslation();
        Vector3f targetLoc = target.getWorldTranslation();

        Vector3f vector1 = apLoc.subtract(origin);
        Vector3f vector2 = targetLoc.subtract(origin);
        
        MathUtil.project(vector1, axis);
        MathUtil.project(vector2, axis);
        vector1.normalizeLocal();
        vector2.normalizeLocal();
        float angle = vector1.angleBetween(vector2) * FastMath.RAD_TO_DEG;
        if (vector1.cross(vector2).dot(axis) > 0) {
            angle = -angle;
        }
        return angle;
    }
    
   

    @Override
    public InputConnector cloneConnector() {
        AngleTargetConnector ac = new AngleTargetConnector();
        ac.setAttachmentName(attachmentName);
        ac.setJointName(jointName);
        ac.setTargetName(targetName);
        ac.initialized = false;
        return ac;
    }

    /**
     * Creates an xml representation of this input connector.
     * @return this object as an xml string.
     */
    @Override
    public String toXML() {
        try {
            StringWriter sw = new StringWriter();
            sw.write("<input class='dae.animation.rig.AngleTargetConnector' " );
            XMLUtils.writeAttribute( sw, "jointName", jointName);
            XMLUtils.writeAttribute( sw, "attachmentName", attachmentName);
            XMLUtils.writeAttribute( sw, "targetName", targetName);
            sw.write("/>\n");
            return sw.toString();
        } catch (IOException ex) {
            Logger.getLogger("DArtE").log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    @Override
    public void fromXML(Node inputNode)
    {
        NamedNodeMap map = inputNode.getAttributes();
        this.jointName = XMLUtils.getAttribute("jointName", map);
        this.attachmentName = XMLUtils.getAttribute("attachmentName", map);
        this.targetName = XMLUtils.getAttribute("targetName", map);
    }
}
