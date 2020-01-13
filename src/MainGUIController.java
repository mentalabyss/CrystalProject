import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.EmptyStackException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainGUIController implements Initializable  {

    @FXML
    public Button loadButton;
    public Button openButton;

    @FXML
    SplitPane splitPane;
    @FXML
    ToggleButton SettingsButton;



    private double settingsLastWidth = -1;
    private Accordion settingsPanel;
    private File loadedPath;

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        SettingsButton.setSelected(true);
        SubScene subScene = Main.getInstance().getSubScene();

        try {
            settingsPanel = FXMLLoader.load(getClass().getResource("settings.fxml"));

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        splitPane.getItems().addAll(new SubSceneResizer(subScene), settingsPanel);

        splitPane.getDividers().get(0).setPosition(1);




    }

    public void toggleSettings(ActionEvent event) {
        final SplitPane.Divider divider = splitPane.getDividers().get(0);
        if (SettingsButton.isSelected()) {
            if (settingsLastWidth == -1) {
                settingsLastWidth = settingsPanel.prefWidth(-1);
            }
            final double divPos = 1 - (settingsLastWidth / splitPane.getWidth());
            new Timeline(
                    new KeyFrame(Duration.seconds(0.3),
                            new EventHandler<ActionEvent>() {
                                @Override public void handle(ActionEvent event) {
                                    settingsPanel.setMinWidth(Region.USE_PREF_SIZE);
                                }
                            },
                            new KeyValue(divider.positionProperty(),divPos, Interpolator.EASE_BOTH)
                    )
            ).play();
        } else {
            settingsLastWidth = settingsPanel.getWidth();
            settingsPanel.setMinWidth(0);
            new Timeline(new KeyFrame(Duration.seconds(0.3),new KeyValue(divider.positionProperty(),1))).play();
        }
    }

    public void loadButtonPressed(){
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(loadButton.getScene().getWindow());

        if(file != null)
            openFile(file);


    }

    private void openFile(File file) {
        Main.getInstance().DeserCell(file);
    }

    public void openButtonPressed(){
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(openButton.getScene().getWindow());

        if (file != null) {
            Main.getInstance().SerializeCell(file);
        }
    }
}
