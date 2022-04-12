import java.util.*;

public class StatusMoves extends Move {
    protected String Effect;
    int[] EffectPoint;

    Random random = new Random();
    public StatusMoves(int id, String moveType, String name, ElementType type, int accuracy, int priority, 
                      int ammunition, String target, String effect, int[] effectPoint){
        super(id, moveType, name, type, accuracy, priority, ammunition, target);
        this.Effect = effect;
        this.EffectPoint = effectPoint;
    }

    public void applyMove(Monster ourMonster, Monster enemyMonster){
        double randomAccuracy =  1 + (int)(Math.random() * ((100 - 1) + 1));
        if (randomAccuracy > super.getAccuracy()){
            System.out.printf("%s Attack Missed...", ourMonster.getName());
        }
        else {
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
                    int sleep = random.nextInt(7);
                    enemyMonster.setStatusCondition(StatusCondition.SLEEP);
                    enemyMonster.setSleepTime(sleep);
                    
                }
            }
        }
    }
}
