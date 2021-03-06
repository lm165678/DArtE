package dae;


import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.Joystick;
import com.jme3.math.Matrix4f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

/**
 * A first person view camera controller.
 * After creation, you must register the camera controller with the
 * dispatcher using #registerWithDispatcher().
 *
 * Controls:
 *  - Move the mouse to rotate the camera
 *  - Mouse wheel for zooming in or out
 *  - WASD keys for moving forward/backward and strafing
 *  - QZ keys raise or lower the camera
 */
public class DAEFlyByCamera extends FlyByCamera {
   
    /**
     * Creates a new FlyByCamera to control the given Camera object.
     * @param cam
     */
    public DAEFlyByCamera(Camera cam){
        super(cam); 
    }
    
    public Camera getCamera(){
        return cam;
    }

    /**
     * Registers the FlyByCamera to receive input events from the provided
     * Dispatcher.
     * @param inputManager
     */
    @Override
    public void registerWithInput(InputManager inputManager){
        this.inputManager = inputManager;
        inputManager.setCursorVisible(dragToRotate || !isEnabled());

        Joystick[] joysticks = inputManager.getJoysticks();
        if (joysticks != null && joysticks.length > 0){
            for (Joystick j : joysticks) {
                mapJoystick(j);
            }
        }
    }
    
    /**
     * @return a copy of the camera location.
     */
    public Vector3f getTranslation(){
        return cam.getLocation().clone();
    }
    
    /**
     * @return a copy of the camera rotation.
     */
    public Quaternion getRotation(){
        return cam.getRotation().clone();
    }
    
    /**
     * @return the projection matrix of the camera.
     */
    public Matrix4f getProjectionMatrix(){
        return cam.getProjectionMatrix();
    }
}
