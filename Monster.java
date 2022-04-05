public class Monster {
    private int id;
    private String name;
    private List<ElementType> elementTypes = new ArrayList<ElementType>();
    private Stats baseStats;
    private List<Move> moves = new ArrayList<Move>();
  
    public Monster(){
        baseStats = new Stats();
    }

    public Monster(int id, String name, List<ElementType> elementTypes, double healthPoint, double attack, double defense, double specialAttack, double specialDefense, double speed, List<Move> moves){
        this.id = id;
        this.name = name;
        this.elementTypes = elementTypes;
        this.baseStats = new Stats(healthPoint, attack, defense, specialAttack, specialDefense, speed);
        this.moves = moves;
    }
    public Monster(int id, String name,double healthPoint, double attack, double defense, double specialAttack, double specialDefense, double speed){
        this.id = id;
        this.name = name;
        this.baseStats = new Stats(healthPoint, attack, defense, specialAttack, specialDefense, speed);
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public List<ElementType> getElementType (){
        return elementTypes;
    }
    public void addElementType(ElementType el){
        elementTypes.add(el);
    }

    public Stats getStats(){
        return baseStats;
    }
    public List<Move> getMoves(){
        return moves;
    }
    public void addMoves (Move move){
        moves.add(move);
    }

}
    

}
