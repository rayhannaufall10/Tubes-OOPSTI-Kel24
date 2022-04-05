import java.util.List;
import java.util.ArrayList;

public class Move {
    protected int id;
    protected String name;
    protected ElementType type;
    protected int accuracy;
    protected int priority;
    protected int ammunition;

    public Move(int id, String name, ElementType type, int accuracy, int priority, int ammunition){
        this.id = id;
        this.name = name;
        this.type = type;
        this.accuracy = accuracy;
        this.priority = priority;
        this.ammunition = ammunition;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public ElementType type(){
        return this.type;
    }

    public int accuracy(){
        return this.accuracy;
    }

    public int priority(){
        return this.priority;
    }

    public int ammunition(){
        return this.ammunition;
    }
}
