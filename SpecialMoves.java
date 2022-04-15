public class SpecialMoves extends Move{
    protected double BasePower;

    public SpecialMoves(int id, String moveType, String name, ElementType type, int accuracy, int priority, 
                      int ammunition, String target, double BasePower) {
        super(id, moveType, name, type, accuracy, priority, ammunition, target);
        this.BasePower = BasePower;
    }

    public void applyMove(Monster ourMonster, Monster enemyMonster, Effectivity effectivity){
        double randomAccuracy =  1 + (int)(Math.random() * ((100 - 1) + 1));
        if (randomAccuracy > super.getAccuracy()){
            System.out.printf("%s Attack Missed...\n", ourMonster.getName());
        }
        else {
            // Menghitung Effectivity Type
            double efective = 1.00;    
            for (int i = 0; i < enemyMonster.getElementType().size(); i++){
                    ElementEffectivityKey effectivityTemp = new ElementEffectivityKey(super.getType(), enemyMonster.getElementType().get(i));
                    efective = efective * Effectivity.getEffectivity(effectivityTemp);
            }
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
            float randomValue = ((int)Math.floor(Math.random()*(max-min + 1) + min));
            randomValue = randomValue/100;

            // Menghitung Damage Calculation
            double damage;
            double sourceAttack = ourMonster.getStats().getSpecialAttack();
            double targetDefense = enemyMonster.getStats().getSpecialDefense();
            damage = Math.floor((BasePower * (sourceAttack / targetDefense) + 2) * randomValue * efective * Burn);

            // Update Hasil Move
            Stats currentStats = enemyMonster.getStats();
            double attackEffect = currentStats.getHealthPoint() - damage;
            currentStats.setHealthPoint(attackEffect);
            enemyMonster.setStats(currentStats);
            this.ammunition = this.ammunition - 1;
        }
    }
}
