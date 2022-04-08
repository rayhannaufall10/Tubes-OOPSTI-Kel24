import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class DefaultMove extends Move{
    
    public DefaultMove() {
        EffectMove effect = new EffectMove(6);
        Move Default = new Move(1, "DEFAULT", DefaultMove, ElementType.NORMAL, 100, 0, 999, "ENEMY", effect);
    }

    public void applyMove(Monster ourMonster, Monster enemyMonster) {
        
    }
}
