import java.util.List;
import java.util.ArrayList;

public class specialMove extends Move {
    protected int basePower;

    public normalMove(int id, String name, ElementType type, int accuracy, int priority, int ammunition, 
                      int basePower){
        super(int id, String name, ElementType type, int accuracy, int priority, int ammunition);
        this.basePower = basePower;
    }
}