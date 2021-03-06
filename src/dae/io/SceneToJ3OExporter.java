package dae.io;

import com.jme3.asset.AssetManager;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.scene.Node;
import dae.io.writers.Exporter;
import dae.io.game.GameSceneLoader;
import dae.project.Level;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * This class exports a given scene to a J3O file. All the specific
 * sandbox constructs ( waypoints, cameras, ... ) are exported as dummies.
 * @author Koen Samyn
 */
public class SceneToJ3OExporter implements Exporter{

    /**
     * Write the scene to a file.
     *
     * @param location The location of the file.
     * @param node the root node of the scene.
     */
    public void writeScene(File location, AssetManager manager, Level level) {
        File levelLocation = level.getAbsoluteLocation();
        Node result = new Node(level.getName());
        GameSceneLoader.loadScene(levelLocation, manager, result);
        try {
            BinaryExporter.getInstance().save(result, location);
        } catch (IOException ex) {
            Logger.getLogger(SceneToJ3OExporter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
