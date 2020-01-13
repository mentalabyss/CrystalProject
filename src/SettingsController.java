import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class SettingsController implements Initializable {


    @FXML public TableView tableView;
    @FXML public TextField addedElement;
    @FXML public TextField addedX;
    @FXML public TextField addedY;
    @FXML public TextField addedZ;
    @FXML public ColorPicker addedColor;

    @FXML private TableColumn<Atom, String> element;
    @FXML private TableColumn<Atom, Double> x;
    @FXML private TableColumn<Atom, Double> y;
    @FXML private TableColumn<Atom, Double> z;
    @FXML private TableColumn<Atom, Color> color;
    @FXML public TableColumn visibleSelect;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        element.setCellValueFactory(new PropertyValueFactory<Atom, String>("element"));
        x.setCellValueFactory(new PropertyValueFactory<Atom, Double>("x"));
        y.setCellValueFactory(new PropertyValueFactory<Atom, Double>("y"));
        z.setCellValueFactory(new PropertyValueFactory<Atom, Double>("z"));
        color.setCellValueFactory(new PropertyValueFactory<Atom, Color>("color"));
        visibleSelect.setCellValueFactory(new PropertyValueFactory<Atom, CheckBox>("select"));


        //tableView.getItems().add(new Atom("", "0", "0", "0"));
    }

    public void addButtonPressed(){
        Color color1 = addedColor.getValue();
        Main.getInstance().AddElementToCell(new Atom(addedElement.getText(), addedX.getText(), addedY.getText(),addedZ.getText(), color1));
        tableView.setItems(Main.getInstance().crystalCell.getList());
    }

    public void updateButtonPressed(){
        Main.getInstance().buildLoadedCell();
        tableView.setItems(Main.getInstance().crystalCell.getList());

        //tableView.refresh();
    }

    public void deleteButtonPressed(){

        ObservableList<Atom> atomsToRemove = FXCollections.observableArrayList();
        ArrayList<SerAtom> serAtomsToRemove = new ArrayList<SerAtom>();

        for(int i = 0; i < Main.getInstance().crystalCell.getList().size(); i++){
            Atom atom = Atom.class.cast(Main.getInstance().crystalCell.getList().get(i)) ;
            SerAtom serAtom = Main.getInstance().crystalCell.arrayList.get(i);
            if(atom.getSelect().isSelected()) {
                atomsToRemove.add(atom);
                serAtomsToRemove.add(serAtom);
            }
        }

        Main.getInstance().crystalCell.getList().removeAll(atomsToRemove);
        Main.getInstance().crystalCell.arrayList.removeAll(serAtomsToRemove);

        updateButtonPressed();
        //tableView.refresh();

    }

    public  void hideButtonPressed(){
        Main.getInstance().hideSelected();
    }
}
