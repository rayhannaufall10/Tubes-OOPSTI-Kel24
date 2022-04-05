import java.lang.system;

public class StatsBuff {
    protected int attackBuff;
    protected int defenseBuff;
    protected int specialAttackBuff;
    protected int specialDefenseBuff;
    protected int speedBuff;

    public StatsBuff() {
        attackBuff = 0;
        defenseBuff = 0;
        specialAttackBuff = 0;
        specialDefenseBuff = 0;
        speedBuff = 0;
    }

    public void setAttackBuff(int attackBuff) {
        this.attackBuff = attackBuff;
    }

    public void setDefenseBuff(int defenseBuff) {
        this.defenseBuff = defenseBuff;
    }

    public void setSpecialAttackBuff(int specialAttackBuff) {
        this.specialAttackBuff = specialAttackBuff;
    }

    public void setSpecialDefenseBuff(int specialDefenseBuff) {
        this.specialDefenseBuff = specialDefenseBuff;
    }

    public void setSpeedBuff(int speedBuff) {
        this.speedBuff = speedBuff;
    }
    
    public void resetBuff() {
        attackBuff = 0;
        defenseBuff = 0;
        specialAttackBuff = 0;
        specialDefenseBuff = 0;
        speedBuff = 0;
    }
    
    public int getAttackBuff() {
        return attackBuff;
    }

    public int getDefenseBuff() {
        return defenseBuff;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public int getSpeedBuff() {
        return speedBuff;
    }
}
