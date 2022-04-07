public class Player {
    private int id;
    private String name;
    private List<Monster> monsterList = new ArrayList<Monster>();

    public Player(int id, String name, List<Monster> monsterList) {
        this.id = id;
        this.name = name;
        this.monsterList = monsterList;
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
