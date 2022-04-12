
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;


public class Effectivity {
    public class ElementTypePair {
        public final ElementType ownType;
        public final ElementType targetType;

        public ElementTypePair(ElementType ownType, ElementType targetType){
            this.ownType = ownType;
            this.targetType = targetType;
        }
    }

    private ElementType toElementType(String name) {
        if (name == "NORMAL"){
            return ElementType.NORMAL;
        }
        else if (name == "FIRE"){
            return ElementType.FIRE;
        }
        else if (name == "WATER"){
            return ElementType.WATER;
        }
        else if (name == "GRASS"){
            return ElementType.GRASS;
        }
        else{
            return ElementType.NONE;
        }
    }

    private Effectivity() {
        this.loadConfig();
    }

    public static Effectivity effectivity = new Effectivity();
    private HashMap<ElementTypePair, Double> elements = new HashMap<ElementTypePair, Double>();

    void loadConfig(){ //filename must have fileName.csv extention
        String path = "./configs/" + "element-type-effectivity-chart.csv";
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            boolean isInit = true;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] temp = data.split(";");
                if (!isInit){
                    ElementTypePair elementPair = new ElementTypePair(
                        toElementType(temp[0]),
                        toElementType(temp[1])
                    );
                double effectivityValue = Double.parseDouble(temp[2]);
                this.elements.put(elementPair,effectivityValue);
                }
                isInit = false;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. could not load configuration file....");
            e.printStackTrace();
            System.exit(200);
        }
    }

    double getEffectivity(ElementType own, ElementType target) {
        try {
            return this.elements.get(new ElementTypePair(own, target));
        } catch (Exception e) {
            return 1;
        }
    }
}

