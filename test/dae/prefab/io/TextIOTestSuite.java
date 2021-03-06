package dae.prefab.io;

import com.jme3.asset.AssetKey;
import com.jme3.asset.AssetManager;
import dae.GlobalObjects;
import dae.animation.rig.Rig;
import dae.animation.rig.io.RigLoader;
import dae.animation.rig.io.RigWriter;
import dae.io.ObjectTypeReader;
import dae.io.SceneLoader;
import dae.io.SceneSaver;
import dae.prefab.io.rig.ReadWriteRigTest;
import dae.prefabs.Prefab;
import dae.prefabs.brush.TerrainBrush;
import dae.prefabs.types.ObjectType;
import dae.prefabs.types.ObjectTypeCategory;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xml.sax.SAXException;

/**
 *
 * @author Koen Samyn
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({dae.prefab.io.ReadWriteCopyStrokeTest.class,
    dae.prefab.io.ReadWriteCopyBrushTest.class,
    ReadWriteCopyMeshTest.class,
    ReadWriteCopyGridTest.class,
    ReadWriteCopyHingeJoint.class,
    ReadWriteCopyWaypointTest.class,
    ReadWriteCopyCharacterPath.class,
    ReadWriteCopySpotLightTest.class,
    ReadWriteCopyAmbientLightTest.class,
    ReadWriteCopyDirectionalLightTest.class,
    ReadWriteCopyPointLightTest.class,
    ReadWriteCopySoundEntityTest.class,
    ReadWriteCopyCameraEntityTest.class,
    ReadWriteCopyPlayerStartEntityTest.class,
    ReadWriteCopyTerrainTest.class,
    ReadWriteCopyRevoluteJointTest.class
})
public class TextIOTestSuite {

    public static void createMockGame() {
        try {
            ObjectTypeReader reader = new ObjectTypeReader();
            MockAssetManager manager = new MockAssetManager();
            MockAssetInfo assetInfo = new MockAssetInfo(manager, new AssetKey("/Objects/ObjectTypes.types"));
            ObjectTypeCategory types = (ObjectTypeCategory) reader.load(assetInfo);
            GlobalObjects.getInstance().setObjectTypeCategory(types);
            GlobalObjects.getInstance().setAssetManager(manager);
        } catch (IOException ex) {
            Logger.getLogger(ReadWriteCopyStrokeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Object createObject(String category, String label, Class expected) {
        ObjectTypeCategory types = GlobalObjects.getInstance().getObjectsTypeCategory();
        AssetManager manager = GlobalObjects.getInstance().getAssetManager();
        ObjectType type = types.getObjectType(category, label);
        assertNotNull("Type [" + category + "," + label + "] not found", type);
        Object object = type.create(manager, label + "1");
        assertNotNull("Could not create " + category + ":" + label + "!", object);
        assertTrue(category+":"+label +" object not an instance of " + expected.getName(), expected.isInstance(object));
        return object;
    }

    public static String writeObject(Prefab toWrite) {
        dae.project.Level l = new dae.project.Level("test", false);
        l.attachChild(toWrite);

        StringWriter sw = new StringWriter();
        BufferedWriter bw = new BufferedWriter(sw);
        try {
            SceneSaver.writeScene(bw, l);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(TextIOTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
        String result = sw.getBuffer().toString();
        Logger.getLogger("DArtE").log(Level.INFO, "Result of writing the object");
        Logger.getLogger("DartE").log(Level.INFO, result);
        return result;
    }

    public static dae.project.Level readLevel(String result, Class expected, int childCount) {
        ObjectTypeCategory types = GlobalObjects.getInstance().getObjectsTypeCategory();
        AssetManager manager = GlobalObjects.getInstance().getAssetManager();

        InputStream stream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
        dae.project.Level loadedLevel = new dae.project.Level("test2", false);
        SceneLoader.loadScene(stream, manager, loadedLevel, types, null);
        List<TerrainBrush> childElements = loadedLevel.descendantMatches(expected);
        assertEquals(childCount + " child element(s) expected", childCount, childElements.size());
        return loadedLevel;
    }
    
    public static String writeRig(Rig rig){
        StringWriter sw = new StringWriter();
        BufferedWriter bw = new BufferedWriter(sw);
        try {
            
            RigWriter.writeRig(bw, rig);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(TextIOTestSuite.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        String result = sw.getBuffer().toString();
        Logger.getLogger("DArtE").log(java.util.logging.Level.INFO, "Result of writing the object");
        Logger.getLogger("DartE").log(java.util.logging.Level.INFO, result);
        return result;
    }
    
    public static Rig readRig(String contents){
        ObjectTypeCategory types = GlobalObjects.getInstance().getObjectsTypeCategory();
        AssetManager manager = GlobalObjects.getInstance().getAssetManager();

        InputStream stream = new ByteArrayInputStream(contents.getBytes(StandardCharsets.UTF_8));
        
        try {
            RigLoader rl = new RigLoader();
            return rl.readRig(stream,manager);
        } catch (IOException | ParserConfigurationException | ClassNotFoundException | InstantiationException | SAXException | NumberFormatException | IllegalAccessException ex) {
            Logger.getLogger(ReadWriteRigTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return null;
    }

    @Before
    public void setup() {
        createMockGame();
    }
}