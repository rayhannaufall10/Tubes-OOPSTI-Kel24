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
        double damage;
        Stats currentStats = enemyMonster.getStats();
        double attackEffect = currentStats.getHealthPoint() - damage;
        currentStats.setHealthPoint(attackEffect);
        enemyMonster.setStats(currentStats);
        this.ammunition = this.ammunition - 1;
    }
}
