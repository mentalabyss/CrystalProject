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
import javafx.util.Duration;

import java.net.URL;
import java.util.EmptyStackException;
import java.util.ResourceBundle;

public class MainGUIController implements Initializable  {
    @FXML
    SplitPane splitPane;
    @FXML
    ToggleButton SettingsButton;

    private double settingsLastWidth = -1;
    private Accordion settingsPanel;

    @Override
    public void initialize (URL location, ResourceBundle resources) {
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
}
