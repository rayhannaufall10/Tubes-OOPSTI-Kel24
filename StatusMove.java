import java.util.List;
import java.util.ArrayList;

public class StatusMove extends Move {
    protected String Effect;
    int[] EffectPoint;

    public StatusMove(int id, String moveType, String name, ElementType type, int accuracy, int priority, 
                      int ammunition, String target, String effect, int[] effectPoint){
        super(id, moveType, name, type, accuracy, priority, ammunition, target);
        this.Effect = effect;
        this.EffectPoint = effectPoint;
    }

    public void applyMove(Monster ourMonster, Monster enemyMonster){
        if (this.getTarget().equals("OWN")){
            double currentHP = ourMonster.getStats().getHealthPoint();
            currentHP += this.EffectPoint[0];
            ourMonster.getStats().setHealthPoint(currentHP);
        }
        else{
            if (Effect.equals("BURN")){
                enemyMonster.setStatusCondition(StatusCondition.BURN);
            }
            if (Effect.equals("POISON")){
                enemyMonster.setStatusCondition(StatusCondition.POISON);
            }
            if (Effect.equals("PARALYZE")){
                enemyMonster.setStatusCondition(StatusCondition.PARALYZE);
                double currentSpeed = enemyMonster.getStats().getSpeed();
                enemyMonster.getStats().setSpeed(currentSpeed * 0.5);
            }
            if (Effect.equals("SLEEP")){
                enemyMonster.setStatusCondition(StatusCondition.SLEEP);
            }
        }
    }
}
