package mathapp.gui;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GUI3D extends Application {

    @Override
    public void start(Stage stage) {
        // Create cube at origin
        Box cube = new Box(100, 100, 100);
        PhongMaterial material = new PhongMaterial(Color.BLUE);
        cube.setMaterial(material);

        // Root group
        Group root = new Group(cube);

        // Scene with depth buffer
        Scene scene = new Scene(root, 600, 400, true);
        scene.setFill(Color.LIGHTGRAY);

        // Camera setup
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-500); // move back so cube is visible
        camera.setTranslateX(0);    // centered on origin
        camera.setTranslateY(0);
        camera.setNearClip(0.1);
        camera.setFarClip(1000);
        scene.setCamera(camera);

        // Rotate cube around its own axes
        RotateTransition rotateY = new RotateTransition(Duration.seconds(5), cube);
        rotateY.setAxis(Rotate.Y_AXIS);
        rotateY.setByAngle(360);
        rotateY.setCycleCount(RotateTransition.INDEFINITE);
        rotateY.play();

        RotateTransition rotateX = new RotateTransition(Duration.seconds(5), cube);
        rotateX.setAxis(Rotate.X_AXIS);
        rotateX.setByAngle(360);
        rotateX.setCycleCount(RotateTransition.INDEFINITE);
        rotateX.play();

        stage.setTitle("Spinning Cube");
        stage.setScene(scene);
        stage.show();
    }
}
