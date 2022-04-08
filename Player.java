import java.util.ArrayList;
import java.util.List;

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
        this.currentMonster = monsterList.get(0);
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

    // buat switch monster
    public void switchMonster() {
        this.printMonsters();
        System.out.println("input id monster:");
        try {
            int selected = scan.nextInt();
            Monster temporaryMonster = this.monsterList.get(selected - 1);
            if (temporaryMonster.getStats().getHealthPoint() <= 0) {
                System.out.println("monster sudah mati");
                this.switchMonster();
            } else {
                System.out.println("monster sudah diganti");
                this.currentMonster.ChangeMonster();
                this.currentMonster = temporaryMonster;
            }
        } catch (Exception e) {
            System.out.println("monster is unavailable");
            this.switchMonster();
        }

    }

    public void selectMove() {
        System.out.println("select move:");
        int selected = scan.nextInt();
        if (selected > this.currentMonster.getSkillsLenght() || selected <= 0) {
            this.selectMove();
        } else {
            this.currentMonster.setMove(selected - 1);
        }
    }

    public void printMonsters() {
        int id = 0;
        System.out.println("*** Available Monsters ***");
        System.out.println("---------------------------");
        for (Monster monster : monsters) {
            System.out.println("(" + String.valueOf(id + 1) + "]" + monster.getName());
            id++;
        }
        System.out.println("---------------------------");
    }
}
