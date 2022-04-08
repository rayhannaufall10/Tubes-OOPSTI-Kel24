public class statsBuff{

	// Set nilai default 
	private int attack = 0;
	private int defense = 0;
	private int specialAttack = 0;
	private int specialDefense = 0;
	private int speed = 0;

	// Set nilai minamal & maximal
	private static final int MAX = 4;
	private static final int MIN = -4;

	public statsBuff(int attack, int defense, int specialAttack, int specialDefense, int speed){
		this.attack = attack;
		this.defense = defense;
		this.specialAttack = specialAttack;
		this.specialDefense = specialDefense;
		this.speed = speed;
	}

	// Setter
	public void setAttack(int attack){
		this.attack = attack;
	}

	public void setDefense(int defense){
		this.defense = defense;
	}

	public void setSpecialAttack(int specialAttack){
		this.specialAttack = specialAttack;
	}

	public void setSpecialDefense(int specialDefense){
		this.specialDefense = specialDefense;
	}

	public void setSpeed(int speed){
		this.speed = speed;
	}

	//Getter
	public int getAttack(){
		return attack;
	}

	public int getDefense(){
		return defense;
	}

	public int getSpecialAttack(){
		return specialAttack;
	}

	public int getSpecialDefense(){
		return specialDefense;
	}

	public int getSpeed(){
		return speed;
	}

	// Get Factor
	public int getFactor(int statsBuffInput){
		switch (statsBuffInput){
			case -4:
				return ((double) 2/6); 

			case -3:
				return ((double) 2/5);

			case -2:
				return ((double) 2/4);

			case -1:
				return ((double) 2/3);

			case 0:
				return ((double) 1);

			case 1:
				return ((double) 3/2);

			case 2: 
				return ((double) 4/2);

			case 3:
				return ((double) 5/2);

			case 4:
				return ((double) 6/2);
		}
	}

}
