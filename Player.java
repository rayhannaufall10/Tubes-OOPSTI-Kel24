import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    private int id;
    private String name;
    private List<Monster> monsterList = new ArrayList<Monster>();
    private Monster currentMonster;


    Scanner scan = new Scanner(System.in);

    public Player(int id, String name, List<Monster> monsterList) {
        this.id = id;
        this.name = name;
        this.monsterList = monsterList;
    }

    public Player(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<Monster> getMonsters() {
        return this.monsterList;
    }

    public void setCurrentMonster(Monster monster){
        this.currentMonster = monster;
    }

    public Monster getCurrentMonster() {
        return this.currentMonster;
    }

    public void printMonsters() {
        int id = 0;
        System.out.println("*** Available Monsters ***");
        System.out.println("---------------------------");
        for (Monster monster : monsterList){
            System.out.println("(" + String.valueOf(id + 1) + "]" + monster.getName());
            id++;
        }
        System.out.println("---------------------------");
    }

    public boolean isLose(){
        return (monsterList.get(0).getStats().getHealthPoint() <=0 && 
        monsterList.get(1).getStats().getHealthPoint() <=0 && 
        monsterList.get(2).getStats().getHealthPoint() <=0 && 
        monsterList.get(3).getStats().getHealthPoint() <=0 && 
        monsterList.get(4).getStats().getHealthPoint() <=0 && 
        monsterList.get(5).getStats().getHealthPoint() <=0);
    }
}
