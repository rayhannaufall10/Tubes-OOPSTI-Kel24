import java.util.List;
import java.util.ArrayList;

public abstract class Move {
    protected int id;
    protected String moveType;
    protected String name;
    protected ElementType type;
    protected int accuracy;
    protected int priority;
    protected int ammunition;
    protected String target;
    protected EffectMove effect;

    public Move(int id, String moveType, String name, ElementType type, int accuracy, int priority, 
                int ammunition, String target, EffectMove effect) {
        this.id = id;
        this.moveType = moveType;
        this.name = name;
        this.type = type;
        this.accuracy = accuracy;
        this.priority = priority;
        this.ammunition = ammunition;
        this.target = target;
        this.effect = effect;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void getMoveType(String moveType) {
        this.moveType = moveType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setAmmunition(int ammunition) {
        this.ammunition = ammunition;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getId(){
        return this.id;
    }
    
    public void getMoveType(){
        return this.moveType;
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

    public void damageCalculation(Monster ourMonster, Monster enemyMonster){
        int min = 85;
        int max = 100;
        double Damage;
        double Effectivity = Effectivity.effectivity.getEffectivity(ourMonster.getElementType(), enemyMonster.getElementType());
        double Burn;

        // yang effect.getAttack itu bonus
        if (ourMonster.getMoveType().equals("NORMAL")){
            double sourceAttack = effect.getAttack() * ourMonster.getStats().getAttack();
            double targetDefense = enemyMonster.getStats().getDefense();
            float randomValue = ((int)Math.floor(Math.random()*(max-min+1)+min)/100);
            if (ourMonster.getStats().getStatusCondition().equals("BURN")){
                Burn = 0.5;
            }
            else{
                Burn = 1;
            }
            Damage = Math.floor(())
        }
        else if(ourMonster.getMoveType().equals("SPECIAL")){
            double sourceAttack = effect.getAttack() * ourMonster.getStats().getSpecialAttack();
            double targetDefense = enemyMonster.getStats().getSpecialDefense();
            float randomValue = ((int)Math.floor(Math.random()*(max-min+1)+min)/100);
            if (ourMonster.getStats().getStatusCondition().equals("BURN")){
                Burn = 0.5;
            }
            else{
                Burn = 1;
            }
        }
    }
    public abstract void applyMove(Monster ourMonster, Monster enemyMonster);

}
