import java.util.List;
import java.util.ArrayList;

public class NormalMove extends Move {
    protected double BasePower;

    public NormalMove(int id, String moveType, String name, ElementType type, int accuracy, int priority, 
                      int ammunition, String target, double BasePower) {
        super(id, moveType, name, type, accuracy, priority, ammunition, target);
        this.BasePower = BasePower;
    }

    public void applyMove(Monster ourMonster, Monster enemyMonster){
        // Menghitung Effectivity Type
        double e = Effectivity.effectivity.getEffectivity(this.type, enemyMonster.getElementType().get(0));
        
        // Menghitung Efek Burn
        double Burn;
        if (ourMonster.getStatusCondition() == StatusCondition.BURN){
            Burn = 0.5;
        }
        else{
            Burn = 1;
        }

        // Generate Random Values
        int min = 85;
        int max = 100;
        float randomValue = ((int)Math.floor(Math.random()*(max-min+1)+min)/100);

        // Menghitung Damage Calculation
        double damage;
        double sourceAttack = ourMonster.getStats().getAttack();
        double targetDefense = enemyMonster.getStats().getDefense();
        damage = Math.floor((BasePower * (sourceAttack / targetDefense) + 2) * randomValue * e * Burn);

        // Update Hasil Move
        Stats currentStats = enemyMonster.getStats();
        double attackEffect = currentStats.getHealthPoint() - damage;
        currentStats.setHealthPoint(attackEffect);
        enemyMonster.setStats(currentStats);
        this.ammunition = this.ammunition - 1;
    }
}
