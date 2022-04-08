import java.util.List;
import java.util.ArrayList;

public class EffectMove {
    protected statusConditions;
    int effectPoint[] = new int[6];

    public EffectMove (String statusConditions) {
        this.statusConditions = statusConditions;
        for (int i = 0; i < effectPoint.length; i++) {
            effectPoint[i] = 0;
        }
    }

    public int getHealthPoint(){
        return effectPoint[0];
    }

    public int getAttack() {
        return effectPoint[1];
    }

    public int getDefense() {
        return effectPoint[2];
    }

    public int getSpecialAttack() {
        return effectPoint[3];
    }

    public int getSpecialDefense() {
        return effectPoint[4];
    }

    public int getSpeed() {
        return effectPoint[5];
    }

    public String getStatusCondition() {
        return statusCondition;
    }

    public double convertedFactorBuff(double stats, int effect) {
        double finalValue;
        if (effect == 0) {
            finalValue = Math.floor(stats * 1);
        }
        else if (effect == 1) {
            finalValue = Math.floor(stats * 3 / 2);
        }
        else if (effect == 2) {
            finalValue = Math.floor(stats * 4 / 2);
        }
        else if (effect == 3) {
            finalValue = Math.floor(stats * 5 / 2);
        }
        else if (effect == 4) {
            finalValue = Math.floor(stats * 6 / 2);
        }
        else if (effect == -1) {
            finalValue = Math.floor(stats * 2 / 3);
        }
        else if (effect == -2) {
            finalValue = Math.floor(stats * 2 / 4); 
        }
        else if (effect == -3) {
            finalValue = Math.floor(stats * 2 / 5); 
        }
        else if (effect == -4) {
            finalValue = Math.floor(stats * 2 / 6);
        }
        return finalValue;
    }
}
