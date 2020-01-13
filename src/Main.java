import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import sun.rmi.server.DeserializationChecker;

import java.io.*;

public class Main extends Application {
        VBox vBox;

    SubScene subScene;

    public CrystalCell crystalCell;


    private double anchorX, anchorY;

    private double anchorAngleX = 0;
    private double anchorAngleY = 0;

    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    Group group3d;

    private static Main instance;

    public Main(){
        instance = this;
    }

    public static Main getInstance(){
        return instance;
    }

    public SubScene getSubScene(){
        return subScene;
    }

    @Override
    public void start (Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        //this.BorderPane1 = FXMLLoader.load(getClass().getResource("main.fxml"));



        group3d = new Group();

        Camera camera = new PerspectiveCamera(true);
        camera.setNearClip(1.0);
        camera.setFarClip(10000.0);

        camera.getTransforms().addAll(
                yUpRotate,
                //cameraXRotate,
                //cameraYRotate,
                cameraPosition,
                cameraLookXRotate,
                cameraLookZRotate);
        camera.setTranslateZ(-50);

        group3d.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraPosition.setZ(-10);
        // camera.setTranslateZ(-cameraDistance);

        subScene = new SubScene(group3d,400, 400, true, SceneAntialiasing.BALANCED);
        //subScene.setPickOnBounds(true);
        subScene.setCamera(camera);
        subScene.setFill(Color.BLACK);

        subScene.addEventHandler(MouseEvent.ANY, mouseEventHandler);
        subScene.addEventHandler(ScrollEvent.ANY, scrollEventHandler);

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("main.fxml")), 1024, 600);

        primaryStage.setTitle("Space Group");
        primaryStage.setScene(scene);
        primaryStage.show();

        //splitPane.getItems().add(new SubSceneResizer(subScene, navigationPanel)/*, settingsPanel*/);
        //splitPane.getDividers().get(0).setPosition(1);

        //StackPane sp = new StackPane();

        //subScene.setManaged(false);

        //BorderPane1.setCenter(subScene);

        //subScene.heightProperty().bind(BorderPane1.heightProperty());
        //subScene.widthProperty().bind(BorderPane1.widthProperty());





        //initMouseControl(group3d, scene);


        //subScene.widthProperty().bind(rootPane.widthProperty());
        //subScene.heightProperty().bind(rootPane.heightProperty());

        crystalCell = new CrystalCell(
                        20.06999, 19.92, 13.42,
                        90, 90, 90, 5365.24); //HARD CODE (CHANGE NEEDED)

        buildAxes(crystalCell);
    }

    public void AddElementToCell(Atom atom){
        crystalCell.AddElement(atom);

        Sphere sphere = new Sphere(1);

        PhongMaterial phongMaterial = new PhongMaterial();
        phongMaterial.setDiffuseColor(atom.getColor());

        sphere.setMaterial(phongMaterial);

        group3d.getChildren().add(sphere);;

        sphere.setTranslateX(atom.getY() * crystalCell.getyLength());
        sphere.setTranslateY(atom.getZ() * crystalCell.getzLength() * (-1));
        sphere.setTranslateZ(atom.getX() * crystalCell.getxLength() * (-1));

    }

    public void SerializeCell(){
        String filename = "test.ser";

        try
        {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(crystalCell);

            out.close();
            file.close();

            System.out.println("Object has been serialized");

        }

        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public void DeserCell(){
        String filename = "test.ser";

        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            crystalCell = (CrystalCell) in.readObject();

            in.close();
            file.close();

            buildLoadedCell();
        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }

        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }
    }

    private void buildAxes(CrystalCell crystalCell) {
        final Group axisGroup = new Group();
        System.out.println("buildAxes()");
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final Box xAxis = new Box(crystalCell.getyLength(), 0.2, 0.2);
        final Box yAxis = new Box(0.2, crystalCell.getzLength(), 0.2);
        final Box zAxis = new Box(0.2, 0.2, crystalCell.getxLength());
        xAxis.setTranslateX(0.5 * crystalCell.getyLength());
        yAxis.setTranslateY(-0.5 * crystalCell.getzLength());
        zAxis.setTranslateZ(-0.5 * crystalCell.getxLength());

        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);

        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        group3d.getChildren().addAll(axisGroup);
    }

    void deleteCell(){
        group3d.getChildren().remove(2, group3d.getChildren().size());
    }

    void hideSelected() {

        deleteCell();

        crystalCell.getList().forEach(node -> {
            Atom atom = Atom.class.cast(node);

            Sphere sphere = new Sphere(1);

            PhongMaterial phongMaterial = new PhongMaterial();
            phongMaterial.setDiffuseColor(atom.getColor());
            System.out.println("Set" + atom.getColor());
            sphere.setMaterial(phongMaterial);

            group3d.getChildren().add(sphere);

            sphere.setVisible(!atom.getSelect().isSelected());

            sphere.setTranslateX(atom.getY() * crystalCell.getyLength());
            sphere.setTranslateY(atom.getZ() * crystalCell.getzLength() * (-1));
            sphere.setTranslateZ(atom.getX() * crystalCell.getxLength() * (-1));
        });
    }

    void buildLoadedCell(){

        System.out.println("buildLoadedCell");

        deleteCell();

        crystalCell.getList().forEach(node -> {
            Atom atom = Atom.class.cast(node);

            Sphere sphere = new Sphere(1);

            PhongMaterial phongMaterial = new PhongMaterial();
            phongMaterial.setDiffuseColor(atom.getColor());
            System.out.println("Set" + atom.getColor());
            sphere.setMaterial(phongMaterial);

            group3d.getChildren().add(sphere);

            sphere.setTranslateX(atom.getY() * crystalCell.getyLength());
            sphere.setTranslateY(atom.getZ() * crystalCell.getzLength() * (-1));
            sphere.setTranslateZ(atom.getX() * crystalCell.getxLength() * (-1));
        });
    }


    //CAMERA CONTROL

    private final Rotate cameraXRotate = new Rotate(-20,0,0,0,Rotate.X_AXIS);
    private final Rotate cameraYRotate = new Rotate(-20,0,0,0,Rotate.Y_AXIS);

    private double dragStartX, dragStartY, dragStartRotateX, dragStartRotateY;

    private double mousePosX;
    private double mousePosY;
    private double mouseOldX;
    private double mouseOldY;
    private double mouseDeltaX;
    private double mouseDeltaY;

    private final Xform cameraXform = new Xform();
    private final Xform cameraXform2 = new Xform();
    private final Xform cameraXform3 = new Xform();

    private final double cameraDistance = 200;


    private Rotate yUpRotate = new Rotate(0,0,0,0,Rotate.X_AXIS);

    private final Rotate cameraLookXRotate = new Rotate(0,0,0,0,Rotate.X_AXIS);
    private final Rotate cameraLookZRotate = new Rotate(0,0,0,0,Rotate.Z_AXIS);

    private final Translate cameraPosition = new Translate(0,0,0);



    private final EventHandler<MouseEvent> mouseEventHandler = event -> {
        // System.out.println("MouseEvent ...");


        double yFlip = -1.0;

        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            dragStartX = event.getSceneX();
            dragStartY = event.getSceneY();
            dragStartRotateX = cameraXRotate.getAngle();
            dragStartRotateY = cameraYRotate.getAngle();
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
            mouseOldX = event.getSceneX();
            mouseOldY = event.getSceneY();

        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            double xDelta = event.getSceneX() -  dragStartX;
            double yDelta = event.getSceneY() -  dragStartY;
            //cameraXRotate.setAngle(dragStartRotateX - (yDelta*0.7));
            //cameraYRotate.setAngle(dragStartRotateY + (xDelta*0.7));

            double modifier = 1.0;
            double modifierFactor = 0.3;

            if (event.isControlDown()) {
                modifier = 0.1;
            }
            if (event.isShiftDown()) {
                modifier = 10.0;
            }

            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX); //*DELTA_MULTIPLIER;
            mouseDeltaY = (mousePosY - mouseOldY); //*DELTA_MULTIPLIER;

            double flip = -1.0;

            boolean alt = (true || event.isAltDown());  // For now, don't require ALT to be pressed
            if (alt && (event.isMiddleButtonDown() || (event.isPrimaryButtonDown() && event.isSecondaryButtonDown()))) {
                cameraXform2.t.setX(cameraXform2.t.getX() + flip*mouseDeltaX*modifierFactor*modifier*0.3);  // -
                cameraXform2.t.setY(cameraXform2.t.getY() + yFlip*mouseDeltaY*modifierFactor*modifier*0.3);  // -
            }
            else if (alt && event.isPrimaryButtonDown()) {
                cameraXform.ry.setAngle(cameraXform.ry.getAngle() - yFlip*mouseDeltaX*modifierFactor*modifier*2.0);  // +
                cameraXform.rx.setAngle(cameraXform.rx.getAngle() + flip*mouseDeltaY*modifierFactor*modifier*2.0);  // -
            }
            else if (alt && event.isSecondaryButtonDown()) {
                double z = cameraPosition.getZ();
                // double z = camera.getTranslateZ();
                // double newZ = z + yFlip*flip*mouseDeltaX*modifierFactor*modifier;
                double newZ = z - flip*(mouseDeltaX+mouseDeltaY)*modifierFactor*modifier;
                System.out.println("newZ = " + newZ);
                cameraPosition.setZ(newZ);
                // camera.setTranslateZ(newZ);
            }

        }
    };

    private final EventHandler<ScrollEvent> scrollEventHandler = event -> {
        if (event.getTouchCount() > 0) { // touch pad scroll
            cameraXform2.t.setX(cameraXform2.t.getX() - (0.01*event.getDeltaX()));  // -
            cameraXform2.t.setY(cameraXform2.t.getY() + (0.01*event.getDeltaY()));  // -
        } else {
            double z = cameraPosition.getZ()-(event.getDeltaY()*0.2);
            z = Math.max(z,-1000);
            z = Math.min(z,0);
            cameraPosition.setZ(z);
        }
    };

}
