import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.layout.Pane;

/**
 * Resizable container for a SubScene
 */
public class SubSceneResizer extends Pane {
    private SubScene subScene;

    public SubSceneResizer(SubScene subScene) {
        this.subScene = subScene;
        setPrefSize(subScene.getWidth(),subScene.getHeight());
        setMinSize(50,50);
        setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        getChildren().addAll(subScene);
    }

    public SubSceneResizer(ObjectProperty<SubScene> subScene) {
        this.subScene = subScene.get();
        if (this.subScene != null) {
            setPrefSize(this.subScene.getWidth(),this.subScene.getHeight());
            getChildren().add(this.subScene);
        }
        subScene.addListener((o,old,newSubScene) -> {
            this.subScene = newSubScene;
            if (this.subScene != null) {
                setPrefSize(this.subScene.getWidth(),this.subScene.getHeight());
                if (getChildren().size() == 1) {
                    getChildren().add(0,this.subScene);
                } else {
                    getChildren().set(0,this.subScene);
                }
            }
        });
        setMinSize(50,50);
        setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
    }

    @Override protected void layoutChildren() {
        final double width = getWidth();
        final double height = getHeight();
        if (subScene!=null) {
            subScene.setWidth(width);
            subScene.setHeight(height);
        }
    }
}
