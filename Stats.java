import java.lang.system;

public class Stats {
    protected double healthPoint;
    protected double attack;
    protected double defense;
    protected double specialAttack;
    protected double specialDefense;
    protected double speed;

    public Stats(double healthPoint, double attack, double defense, double specialAttack, 
                 double specialDefense, double speed) {
        this.healthPoint = healthPoint;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
    }

    public double getHealthPoint() {
        return this.healthPoint;
    }

    public double getAttack(){
        return this.attack;
    }

    public double getDefense(){
        return this.defense;
    }

    public double getSpecialAttack(){
        return this.specialAttack;
    }

    public double getSpecialDefense(){
        return this.specialDefense;
    }

    public double getSpeed(){
        return this.speed;
    }
}
