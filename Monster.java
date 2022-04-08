import java.util.List;

public class Monster {
    private int id;
    private String name;
    private List<ElementType> elementTypes = new ArrayList<ElementType>();
    private Stats baseStats;
    private List<Move> moves = new ArrayList<Move>();
    int selectedMove = -1;
    private StatusCondition statusCondition;

    public Monster() {
        baseStats = new Stats();
    }

    public Monster(int id, String name, List<ElementType> elementTypes, double healthPoint, double attack,
            double defense, double specialAttack, double specialDefense, double speed, List<Move> moves
            StatusCondition statusCondition) {
        this.id = id;
        this.name = name;
        this.elementTypes = elementTypes;
        this.baseStats = new Stats(healthPoint, attack, defense, specialAttack, specialDefense, speed);
        this.moves = moves;
        this.statusCondition = statusCondition;
    }

    public Monster(int id, String name, double healthPoint, double attack, double defense, double specialAttack,
            double specialDefense, double speed) {
        this.id = id;
        this.name = name;
        this.baseStats = new Stats(healthPoint, attack, defense, specialAttack, specialDefense, speed);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ElementType> getElementType() {
        return elementTypes;
    }

    public void addElementType(ElementType el) {
        elementTypes.add(el);
    }

    public Stats getStats() {
        return baseStats;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void addMoves(Move move) {
        moves.add(move);
    }

    public int getSkillsLenght() {
        return this.moves.size();
    }

    public boolean setMove(int selectedMove) {
        if (moves.get(selectedMove).ammunition <= 0) {
            return false;
        }
        this.selectedMove = selectedMove;
        return true;
    }

    public void setStatusCondition(StatusCondition statusCondition) {
        this.statusCondition = statusCondition;
    }

    public StatusCondition getStatusCondition() {
        return this.statusCondition;
    }

    public Move getMove() {
        try {
            return this.currentMonster.setMove(selected - 1);
        } catch (Exception e) {
            return null;
        }
    }
    /*
     * Move use = currentMonster.getMove();
     * if (user != null) {
     * if use.getMoveType().equals("DEFAULT"){
     * use
     * }
     * }
     */

    public void ChangeMonster() {
        this.selectedMove = -1;
    }
}
