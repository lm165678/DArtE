package dae.animation.rig.io;

import com.jme3.math.ColorRGBA;
import dae.animation.rig.AnimationController;
import dae.animation.rig.AnimationListControl;
import dae.animation.rig.Rig;
import dae.animation.rig.timing.Behaviour;
import dae.animation.rig.timing.TimeLine;
import dae.io.SceneSaver;
import dae.io.XMLUtils;
import dae.io.writers.DefaultPrefabExporter;
import dae.io.writers.PrefabTextExporter;
import dae.prefabs.Prefab;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import mlproject.fuzzy.FuzzyRule;
import mlproject.fuzzy.FuzzyRuleBlock;
import mlproject.fuzzy.FuzzySystem;
import mlproject.fuzzy.FuzzyVariable;
import mlproject.fuzzy.LeftSigmoidMemberShip;
import mlproject.fuzzy.MemberShip;
import mlproject.fuzzy.RightSigmoidMemberShip;
import mlproject.fuzzy.SigmoidMemberShip;
import mlproject.fuzzy.SingletonMemberShip;
import mlproject.fuzzy.TrapezoidMemberShip;

/**
 *
 * @author Koen Samyn
 */
public class RigWriter {

    private final static PrefabTextExporter prefabExporter = new DefaultPrefabExporter();

    /**
     * Write the scene to a file.
     *
     * @param location The location of the file.
     * @param rig the rig to write to file.
     */
    public static void writeRig(File location, Rig rig) {
        FileWriter fw;
        BufferedWriter bw = null;
        try {
            if (!location.getParentFile().exists()) {
                location.getParentFile().mkdirs();
            }
            fw = new FileWriter(location);
            bw = new BufferedWriter(fw);
            writeRig(bw, rig);
        } catch (IOException ex) {
            Logger.getLogger("DArtE").log(Level.SEVERE, null, ex);
        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger("DArtE").log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void writeRig(Writer bw, Rig rig) throws IOException {
        bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        bw.write("<rig>\n");
        int depth = 0;
        int prefabCount = rig.getPrefabChildCount();
        for (int i = 0; i < prefabCount; ++i) {
            Prefab p = (Prefab) rig.getPrefabChildAt(i);
            SceneSaver.writePrefab(p, bw, depth);
        }
        bw.write("\t<fuzzysystems>\n");
        writeFuzzySystem(bw, rig.getFuzzySystem());
        bw.write("\t</fuzzysystems>\n");
        // animation targets
        bw.write("\t<animationtargets>\n");
        for (int i = 0; i < rig.getNrOfTargetKeys(); ++i) {
            String key = rig.getTargetKeyAt(i);
            Prefab value = rig.getTarget(key);
            bw.write("\t\t<target ");
            XMLUtils.writeAttribute(bw, "key", key);
            if (value != null) {
                XMLUtils.writeAttribute(bw, "target", value.getName());
            }
            bw.write("/>\n");
        }
        bw.write("\t</animationtargets>\n");
        // controller connections
        AnimationListControl alc = rig.getControl(AnimationListControl.class);
        if (alc != null) {
            bw.write("\t<controllerconnections>\n");
            for (AnimationController ac : alc.getAnimationControllers()) {
                bw.write("\t\t<controller ");
                XMLUtils.writeAttribute(bw, "system", ac.getSystemName());
                XMLUtils.writeAttribute(bw, "name", ac.getName());
                XMLUtils.writeAttribute(bw, "inputToSystem", ac.getControllerInputName());
                XMLUtils.writeAttribute(bw, "outputOfSystem", ac.getControllerOutputName());
                bw.write(">\n");
                bw.write("\t\t\t");
                bw.write(ac.getInput().toXML());
                bw.write("\t\t\t");
                bw.write(ac.getOutput().toXML());
                bw.write("\t\t</controller>\n");

            }
            bw.write("\t</controllerconnections>\n");
        }
        bw.write("\t<behaviours>\n");
        writeBehaviours(bw, rig);
        bw.write("\t</behaviours>\n");
        bw.write("</rig>\n");
    }

    private static void writeFuzzySystem(Writer bw, FuzzySystem fuzzySystem) throws IOException {
        bw.write("\t\t<fuzzysystem ");
        XMLUtils.writeAttribute(bw, "name", fuzzySystem.getName());
        bw.write(">\n");
        bw.write("\t\t\t<fuzzyinputs>\n");
        for (FuzzyVariable fuzzyVariable : fuzzySystem.getInputs()) {
            bw.write("\t\t\t\t<input ");
            XMLUtils.writeAttribute(bw, "name", fuzzyVariable.getName());
            bw.write(">\n");
            for (MemberShip ms : fuzzyVariable.getMemberShips()) {
                if (ms instanceof LeftSigmoidMemberShip) {
                    writeMemberShip(bw, (LeftSigmoidMemberShip) ms);
                } else if (ms instanceof RightSigmoidMemberShip) {
                    writeMemberShip(bw, (RightSigmoidMemberShip) ms);
                } else if (ms instanceof SigmoidMemberShip) {
                    writeMemberShip(bw, (SigmoidMemberShip) ms);
                } else if (ms instanceof TrapezoidMemberShip) {
                    writeMemberShip(bw, (TrapezoidMemberShip) ms);
                }
            }
            bw.write("\t\t\t\t</input>\n");
        }
        bw.write("\t\t\t</fuzzyinputs>\n");

        bw.write("\t\t\t<fuzzyoutputs>\n");
        for (FuzzyVariable fuzzyVariable : fuzzySystem.getOutputs()) {
            bw.write("\t\t\t\t<output ");
            XMLUtils.writeAttribute(bw, "name", fuzzyVariable.getName());
            bw.write(">\n");
            for (MemberShip ms : fuzzyVariable.getMemberShips()) {
                if (ms instanceof LeftSigmoidMemberShip) {
                    writeMemberShip(bw, (LeftSigmoidMemberShip) ms);
                } else if (ms instanceof RightSigmoidMemberShip) {
                    writeMemberShip(bw, (RightSigmoidMemberShip) ms);
                } else if (ms instanceof SigmoidMemberShip) {
                    writeMemberShip(bw, (SigmoidMemberShip) ms);
                } else if (ms instanceof TrapezoidMemberShip) {
                    writeMemberShip(bw, (TrapezoidMemberShip) ms);
                } else if (ms instanceof SingletonMemberShip) {
                    writeMemberShip(bw, (SingletonMemberShip) ms);
                }
            }
            bw.write("\t\t\t\t</output>\n");
        }
        bw.write("\t\t\t</fuzzyoutputs>\n");
        bw.write("\t\t\t<fuzzyrules>\n");
        for (FuzzyRuleBlock ruleBlock : fuzzySystem.getFuzzyRuleBlocks()) {
            if (ruleBlock == null) {
                continue;
            }
            bw.write("\t\t\t\t<ruleblock ");
            XMLUtils.writeAttribute(bw, "name", ruleBlock.getName());
            bw.write(">\n");
            for (FuzzyRule rule : ruleBlock.getRules()) {
                bw.write("\t\t\t\t\t<rule>");
                bw.write(rule.getRuleText());
                bw.write("</rule>\n");
            }
            bw.write("\t\t\t\t</ruleblock>\n");
        }
        bw.write("\t\t\t</fuzzyrules>\n");
        bw.write("\t\t</fuzzysystem>\n");
    }

    private static void writeMemberShip(Writer bw, LeftSigmoidMemberShip lms) throws IOException {
        bw.write("\t\t\t\t\t<membership ");
        XMLUtils.writeAttribute(bw, "type", "left");
        XMLUtils.writeAttribute(bw, "name", lms.getName());
        XMLUtils.writeAttribute(bw, "center", lms.getCenter());
        XMLUtils.writeAttribute(bw, "right", lms.getRight());
        bw.write("/>\n");
    }

    private static void writeMemberShip(Writer bw, RightSigmoidMemberShip rms) throws IOException {
        bw.write("\t\t\t\t\t<membership ");
        XMLUtils.writeAttribute(bw, "type", "right");
        XMLUtils.writeAttribute(bw, "name", rms.getName());
        XMLUtils.writeAttribute(bw, "center", rms.getCenter());
        XMLUtils.writeAttribute(bw, "left", rms.getLeft());
        bw.write("/>\n");
    }

    private static void writeMemberShip(Writer bw, SigmoidMemberShip sms) throws IOException {
        bw.write("\t\t\t\t\t<membership ");
        XMLUtils.writeAttribute(bw, "type", "triangular");
        XMLUtils.writeAttribute(bw, "name", sms.getName());
        XMLUtils.writeAttribute(bw, "left", sms.getLeft());
        XMLUtils.writeAttribute(bw, "center", sms.getCenter());
        XMLUtils.writeAttribute(bw, "right", sms.getRight());
        bw.write("/>\n");
    }

    private static void writeMemberShip(Writer bw, TrapezoidMemberShip sms) throws IOException {
        bw.write("\t\t\t\t\t<membership ");
        XMLUtils.writeAttribute(bw, "type", "trapezoid");
        XMLUtils.writeAttribute(bw, "name", sms.getName());
        XMLUtils.writeAttribute(bw, "left", sms.getLeft());
        XMLUtils.writeAttribute(bw, "centerleft", sms.getCenterLeft());
        XMLUtils.writeAttribute(bw, "centerright", sms.getCenterRight());
        XMLUtils.writeAttribute(bw, "right", sms.getRight());
        bw.write("/>\n");
    }

    private static void writeMemberShip(Writer bw, SingletonMemberShip sms) throws IOException {
        bw.write("\t\t\t\t\t<membership ");
        XMLUtils.writeAttribute(bw, "type", "singleton");
        XMLUtils.writeAttribute(bw, "name", sms.getName());
        XMLUtils.writeAttribute(bw, "center", sms.getValue());
        bw.write("/>\n");
    }

    private static void writeBehaviours(Writer bw, Rig rig) throws IOException {
        for (Behaviour b : rig.getBehaviours()) {
            bw.write("\t\t<behaviour ");
            XMLUtils.writeAttribute(bw, "name", b.getName());
            XMLUtils.writeAttribute(bw, "fps", b.getFPS());
            bw.write(">\n");

            for (TimeLine tl : b.getTimeLines()) {
                bw.write("\t\t\t<timeline ");
                XMLUtils.writeAttribute(bw, "target", tl.getTarget().getName());
                bw.write(">\n");
                for (int f = 0; f <= tl.getMaxFrameNumber(); ++f) {
                    if (tl.containsKey(f)) {
                        bw.write("\t\t\t\t<f ");
                        XMLUtils.writeAttribute(bw,"n",f);
                        XMLUtils.writeAttribute(bw, "r", tl.getRotation(f));
                        bw.write("/>\n");
                    }
                }
                bw.write("\t\t\t</timeline>\n");
            }
            bw.write("\t\t</behaviour>\n");
        }
    }
}
