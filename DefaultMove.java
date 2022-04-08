import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class DefaultMove extends Move{
    protected double BasePower;

    public DefaultMove(int id, String moveType, String name, ElementType type, int accuracy, int priority, 
                      int ammunition, String target, double BasePower) {
        super(1, "DEFAULT", "Default Move", ElementType.NORMAL, 100, 0, 999, "ENEMY", 50);
    }

    public void applyMove(Monster ourMonster, Monster enemyMonster) {
        // Enemy
        double damage = damageCalculation(ourMonster, enemyMonster);
        Stats currentStats = enemyMonster.getStats();
        double attackEffect = currentStats.getHealthPoint() - damage;
        currentStats.setHealthPoint(attackEffect);
        enemyMonster.setStats(currentStats);

        // Self
        Stats ourMonsterStats = ourMonster.getStats();
        double updateOwnHP = Math.floor(ourMonsterStats.getMaxHealth() * 1 / 4);
        double MonsterHealth = ourMonsterStats.getHealthPoint();
        double currentHealth = MonsterHealth - updateOwnHP;
        ourMonsterStats.setHealthPoint(currentHealth);
        setAmmunition(getAmmunition() - 1);
    }
}
