import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
public class PeriodicTableColorSaver {
    public void serialize() {
        // Creating a HashMap of Integer keys and String values
        HashMap<String, Color> hashmap = new HashMap<String, Color>();
        hashmap.put("Carbon", Color.BLACK);
        hashmap.put("Hydrogen ", Color.WHITE);
        //hashmap.put("Nitrogen", Color.);
        //hashmap.put("", "Value4");
        //hashmap.put("", "Value5");
        try
        {
            FileOutputStream fos =
                    new FileOutputStream("hashmap.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(hashmap);
            oos.close();
            fos.close();
            System.out.printf("Serialized HashMap data is saved in hashmap.ser");
        }catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}