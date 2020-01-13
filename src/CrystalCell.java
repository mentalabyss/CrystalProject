import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.smartcardio.ATR;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CrystalCell implements Serializable {

    private ObservableList<Atom> list;

    private double xLength, yLength, zLength, alpha, beta, gamma, volume;

    public CrystalCell(double xLength, double yLength, double zLength, double alpha, double beta, double gamma, double volume){
        this.xLength = xLength;
        this.yLength = yLength;
        this.zLength = zLength;
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        this.volume = volume;

        list = FXCollections.observableArrayList();
    }
    public void AddElement(Atom atom){
        list.add(atom);
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
}
