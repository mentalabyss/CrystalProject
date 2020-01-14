import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class CrystalCell implements Serializable {

    private transient ObservableList<Atom> list;

    private double xLength, yLength, zLength, alpha, beta, gamma, volume;

    ArrayList<SerAtom> arrayList;

    public CrystalCell(double xLength, double yLength, double zLength, double alpha, double beta, double gamma, double volume){
        this.xLength = xLength;
        this.yLength = yLength;
        this.zLength = zLength;
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        this.volume = volume;

        list = FXCollections.observableArrayList();
        arrayList = new ArrayList<SerAtom>();
        System.out.println("Crystal cell constructed");
    }

//    public CrystalCell(CrystalCell crystalCell){
//        list = FXCollections.observableArrayList();
//        arrayList = new ArrayList<SerAtom>();
//        list = crystalCell.getList();
//        arrayList = crystalCell.arrayList;
//    }

    public void AddElement(Atom atom){
        System.out.println("Added atom to list");
        list.add(atom);
        arrayList.add(new SerAtom(atom));
    }

    public void ArrayListToList(){

        list = FXCollections.observableArrayList();
        arrayList.forEach(serAtom -> {
            list.add(new Atom(serAtom));
            System.out.println("Added element");
        });
    }

    public double getxLength() {
        return xLength;
    }

    public double getyLength() {
        return yLength;
    }

    public double getzLength() {
        return zLength;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }

    public double getGamma() {
        return gamma;
    }

    public ObservableList getList(){return list; }


    public void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
        System.out.println("reading obj...");
        xLength = s.readDouble();
        yLength = s.readDouble();
        zLength = s.readDouble();
        alpha = s.readDouble();
        beta = s.readDouble();
        gamma = s.readDouble();
        volume = s.readDouble();

        arrayList = (ArrayList<SerAtom>) s.readObject();

        arrayList.forEach(serAtom -> {
            Atom atom = new Atom(serAtom);
            AddElement(atom);
        });
    }

    public void writeObject(ObjectOutputStream s) throws IOException{
        System.out.println("yessss");
        //s.defaultWriteObject();
        s.writeDouble(getxLength());
        s.writeDouble(getyLength());
        s.writeDouble(getzLength());
        s.writeDouble(getAlpha());
        s.writeDouble(getBeta());
        s.writeDouble(getGamma());
        s.writeDouble(getVolume());

        arrayList = new ArrayList<>();

        list.forEach(atom ->{
            SerAtom serAtom = new SerAtom(atom);
            arrayList.add(serAtom);
        });

        s.writeObject(arrayList);
    }


    public double getVolume() {
        return volume;
    }
}
