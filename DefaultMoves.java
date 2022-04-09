import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class DefaultMoves extends Move{
    protected double BasePower;

    public DefaultMoves() {
        super(1, "DEFAULT", "Default Move", ElementType.NORMAL, 100, 0, 999, "ENEMY");
        this.BasePower = 50;
    }

    public void applyMove(Monster ourMonster, Monster enemyMonster) {
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

        // Self
        Stats ourMonsterStats = ourMonster.getStats();
        double updateOwnHP = Math.floor(ourMonsterStats.getMaxHealth() * 1 / 4);
        double MonsterHealth = ourMonsterStats.getHealthPoint();
        double currentHealth = MonsterHealth - updateOwnHP;
        ourMonsterStats.setHealthPoint(currentHealth);
        setAmmunition(getAmmunition() - 1);
    }
}
