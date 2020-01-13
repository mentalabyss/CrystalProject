import java.io.Serializable;

public class SerAtom implements Serializable {
    private String element;
    private double x, y , z, red, green, blue, alpha;
    private Boolean checkBox;

    public String getElement(){
        return element;
    }

    public double getX(){
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public SerAtom(Atom atom){
        element = atom.getElement();
        x = atom.getX();
        y = atom.getY();
        z = atom.getZ();
        red = atom.getColor().getRed();
        green = atom.getColor().getGreen();
        blue = atom.getColor().getBlue();
        alpha = atom.getColor().getOpacity();
        checkBox = atom.getSelect().isSelected();
    }

    public double getRed() {
        return red;
    }

    public double getGreen() {
        return green;
    }

    public double getBlue() {
        return blue;
    }

    public double getAlpha() {
        return alpha;
    }

    public Boolean getCheckBox() {
        return checkBox;
    }
}
