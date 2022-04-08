import java.util.List;
import java.util.ArrayList;

public class Monster {
    private int id;
    private String name;
    private List<ElementType> elementTypes = new ArrayList<ElementType>();
    private Stats baseStats;
    private List<Move> moves = new ArrayList<Move>();
    int selectedMove = -1;
    private StatusCondition statusCondition;

    public Monster(Stats stats) {
        baseStats = stats;
    }

    public Monster(int id, String name, List<ElementType> elementTypes, double healthPoint, double attack,
            double defense, double specialAttack, double specialDefense, double speed, List<Move> moves,
            StatusCondition statusCondition) {
        this.id = id;
        this.name = name;
        this.elementTypes = elementTypes;
        this.baseStats = new Stats(healthPoint, attack, defense, specialAttack, specialDefense, speed);
        this.moves = moves;
        this.statusCondition = StatusCondition.NONE;
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

    public void setMove(int selectedMove) {
        this.selectedMove = selectedMove;
    }

    public void setStatusCondition(StatusCondition statusCondition) {
        this.statusCondition = statusCondition;
    }

    public StatusCondition getStatusCondition() {
        return this.statusCondition;
    }

    public Move getMove(int selected) {
        try {
            return moves.get(selected-1);
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
