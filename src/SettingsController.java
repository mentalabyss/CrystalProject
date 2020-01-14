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
    @FXML public TextField cellA;
    @FXML public TextField cellB;
    @FXML public TextField cellC;
    @FXML public TextField cellAlpha;
    @FXML public TextField cellBeta;
    @FXML public TextField cellGamma;
    @FXML public TextField cellVolume;

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
        tableView.setItems(Main.getInstance().loadedCrystalCell.getList());
    }

    public void updateButtonPressed(){
        Main.getInstance().buildLoadedCell(Main.getInstance().loadedCrystalCell);
        tableView.setItems(Main.getInstance().loadedCrystalCell.getList());

        //tableView.refresh();
    }

    public void deleteButtonPressed(){

        ObservableList<Atom> atomsToRemove = FXCollections.observableArrayList();
        ArrayList<SerAtom> serAtomsToRemove = new ArrayList<SerAtom>();

        for(int i = 0; i < Main.getInstance().loadedCrystalCell.getList().size(); i++){
            Atom atom = Atom.class.cast(Main.getInstance().loadedCrystalCell.getList().get(i)) ;
            SerAtom serAtom = Main.getInstance().loadedCrystalCell.arrayList.get(i);
            if(atom.getSelect().isSelected()) {
                atomsToRemove.add(atom);
                serAtomsToRemove.add(serAtom);
            }
        }

        Main.getInstance().loadedCrystalCell.getList().removeAll(atomsToRemove);
        Main.getInstance().loadedCrystalCell.arrayList.removeAll(serAtomsToRemove);

        updateButtonPressed();
        //tableView.refresh();

    }

    public  void hideButtonPressed(){
        Main.getInstance().hideSelected();
    }

    public void cellApplyButtonPressed(){


        CrystalCell crystalCell = new CrystalCell(Double.parseDouble(cellA.getText()), Double.parseDouble(cellB.getText()),
                Double.parseDouble(cellC.getText()), Double.parseDouble(cellAlpha.getText()), Double.parseDouble(cellBeta.getText()),
                Double.parseDouble(cellGamma.getText()), Double.parseDouble(cellVolume.getText()));


        crystalCell.arrayList = Main.getInstance().loadedCrystalCell.arrayList;
        crystalCell.ArrayListToList();

        Main.getInstance().loadedCrystalCell = crystalCell;
        Main.getInstance().buildAxes(crystalCell);
        Main.getInstance().buildCellBoundaries(crystalCell);
        Main.getInstance().buildLoadedCell(crystalCell);
    }
}
