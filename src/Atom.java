import javafx.scene.control.CheckBox;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class Atom implements Serializable {
    private String element;
    private double x, y , z;
    private Color color;
    private CheckBox select;

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

    public Atom(String element, String x, String y, String z, Color color){

        if(element.isEmpty() || x.isEmpty() || y.isEmpty() || z.isEmpty()){
            throw new NullPointerException("Ошибка добавления элемента!");
        }

        this.element = element;
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
        this.z = Double.parseDouble(z);
        this.color = color;
        this.select = new CheckBox();
        select.setSelected(false);


        if (this.x < 0)
            this.x += 1;

        if (this.y < 0)
            this.y += 1;

        if (this.z < 0)
            this.z += 1;
    }

    public Color getColor() {
        return color;
    }

    public CheckBox getSelect(){return select; }

    public void setSelect(CheckBox select){
        this.select = select;
    }
}
