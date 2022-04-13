import util.CSVReader;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;

public class Main {
    private static final List<String> CSV_FILE_PATHS = Collections.unmodifiableList(Arrays.asList(
            "configs/monsterpool.csv",
            "configs/movepool.csv",
            "configs/element-type-effectivity-chart.csv"));
    
    public static List<Move> readMovePool(String file){
        List<Move> moveList = new ArrayList<Move>();
        
        try {
            CSVReader readerMoves = new CSVReader(new File(Main.class.getResource(file).toURI()), ";");
            readerMoves.setSkipHeader(true);
            List<String[]> listOfMoves = readerMoves.read();
        
            for(String[] line : listOfMoves){
                int id = Integer.valueOf(line[0]);
                String moveType = line[1];
                String name = line[2];
                ElementType elementType = ElementType.valueOf(line[3]);
                int accuracy = Integer.valueOf(line[4]);
                int priority = Integer.valueOf(line[5]);
                int ammunition = Integer.valueOf(line[6]);
                String target = line[7];
        
                if (line.length == 9) {
                    int powerBase = Integer.valueOf(line[8]);
                    if (moveType.equals("NORMAL")) {
                        NormalMoves normalMove = new NormalMoves(id,moveType, name, elementType, accuracy, 
                                                                priority, ammunition, target, powerBase);
                        moveList.add(normalMove);
                    } 
                    else if (moveType.equals("SPECIAL")) {
                        SpecialMoves specialMove = new SpecialMoves(id,moveType, name, elementType, accuracy, 
                                                                    priority, ammunition, target, powerBase);
                        moveList.add(specialMove);
                    }
                } 
                else {
                    String statusEffect = line[8];
                    String[] stats = (line[9]).split(",");
                    int[] effectPoint = { 0, 0, 0, 0, 0, 0 };
                    for (int i = 0; i < 6; i++) {
                        effectPoint[i] = Integer.valueOf(stats[i]);
                    }
                    StatusMoves statusMove = new StatusMoves(id,moveType, name, elementType, accuracy, 
                                                            priority, ammunition, target, statusEffect,
                                                            effectPoint);
                    moveList.add(statusMove);
                }
            }
        }
        catch (Exception e) {
            System.out.println("Failed to Load Moves...");
        }
        /*
        for (Move move : moveList) {
            System.out.println("ID  : " + move.getId());
            System.out.println("Name    : " + move.getName());
        }
        */
        return moveList;
    }
        
    public static List<Monster> readConfig(String file){
        List<Move> movePool = new ArrayList<Move>();
        movePool = readMovePool(CSV_FILE_PATHS.get(1));
        List<Monster> monsterList = new ArrayList<Monster>();
                
        try{
            CSVReader readerMonsters = new CSVReader(new File(Main.class.getResource(file).toURI()), ";");
            readerMonsters.setSkipHeader(true);
            List<String[]> listOfMonsters = readerMonsters.read();
            
            for(String[] line : listOfMonsters){
                // Create ID
                int id = Integer.valueOf(line[0]);
                // Create Name
                String name = line[1];
                // Create ElementType
                ArrayList<ElementType> elementTypes = new ArrayList<ElementType>();
                String[] arrOfEltypesTemp = line[2].split(",", 7);
                for(String eltype : arrOfEltypesTemp){
                    if(eltype.equals("FIRE")){
                        elementTypes.add(ElementType.FIRE);
                    }
                    if(eltype.equals("NORMAL")){
                        elementTypes.add(ElementType.NORMAL);
                    }
                    if(eltype.equals("GRASS")){
                        elementTypes.add(ElementType.GRASS);
                    }
                    if(eltype.equals("WATER")){
                        elementTypes.add(ElementType.WATER);
                    }
                }

                // Create Monster Stats
                String[] tempBS = line[3].split(",");
                ArrayList<Double> arrayStats = new ArrayList<Double>();

                for(String baseStats : tempBS) {
                    Double eachStat = Double.parseDouble(baseStats);
                    arrayStats.add(eachStat);
                }
                Stats monStats = new Stats(arrayStats.get(0), arrayStats.get(1), arrayStats.get(2), 
                                    arrayStats.get(3), arrayStats.get(4), arrayStats.get(5));
                /*
                System.out.println("HP          : " + monStats.getHealthPoint());
                System.out.println("Attack      : " + monStats.getAttack());
                System.out.println("Defense     : " + monStats.getDefense());
                System.out.println("Sp Attack   : " + monStats.getSpecialAttack());
                System.out.println("Sp Defense  : " + monStats.getSpecialDefense());
                System.out.println("Speed       : " + monStats.getSpeed());
                */

                // SET UP MOVE MILIK MONSTER
                String move = line[4];
                String[] arrOfMove = move.split(",");
                List<Move> monsMove = new ArrayList<Move>();
                DefaultMoves defaultMove = new DefaultMoves();
                monsMove.add(defaultMove);
                for(int i = 0; i < arrOfMove.length; i++){
                    Move originMove = movePool.get(Integer.valueOf(arrOfMove[i]) - 1);
                    monsMove.add(originMove);
                }
                /*
                for (Move moves : monsMove) {
                    System.out.println("ID  : " + moves.getId());
                    System.out.println("Name    : " + moves.getName());
                }
                */

                // Create Monster Object
                Monster monsterTemp = new Monster(id, name, elementTypes, monStats, monsMove);
                monsterList.add(monsterTemp);
            }
            /*
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            int i = 0;
            for(Monster monster : monsterList){
                i++;
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.printf("Id monster \t: %s%n", i);
                System.out.printf("Nama monster \t: %s%n", monster.getName());
                List<ElementType> elementTypes = monster.getElementType();
                for (ElementType elementType : elementTypes){
                    if(elementType.equals(ElementType.NORMAL)){
                        System.out.printf("Element Type \t: %s%n", ElementType.NORMAL);
                    }
                    else if(elementType.equals(ElementType.FIRE)){
                        System.out.printf("Element Type \t: %s%n", ElementType.FIRE);
                    }
                    else if(elementType.equals(ElementType.WATER)){
                        System.out.printf("Element Type \t: %s%n", ElementType.WATER);
                    }   
                    else if(elementType.equals(ElementType.GRASS)){
                        System.out.printf("Element Type \t: %s%n", ElementType.GRASS);
                    } 
                }
                System.out.println("Health Point    : ");
                System.out.println("Attack          : " + monster.getStats().getAttack());
                System.out.println("Defense         : " + monster.getStats().getDefense());
                System.out.println("Special Attack  : " + monster.getStats().getSpecialAttack());
                System.out.println("Special Defense : " + monster.getStats().getSpecialDefense());
                System.out.println("Speed           : " + monster.getStats().getSpeed());   	
            }
            */

        }
        catch (Exception e) {
            System.out.println("Failed to Load Monster...");
        }
        return monsterList;
    }

    public static void main(String[] args) {
        List<Monster> monsterList = readConfig(CSV_FILE_PATHS.get(0));
        Effectivity listOfEffectivity = new Effectivity();
        try {
            CSVReader readerEffectivity = new CSVReader(new File(Main.class.getResource(CSV_FILE_PATHS.get(2)).toURI()), ";");
            readerEffectivity.setSkipHeader(true);
            List<String[]> linesOfEffectivity = readerEffectivity.read();
            for (String [] line : linesOfEffectivity){
                String source = line[0];
                String target = line[1];
                Double effectivity = Double.parseDouble(line[2]);          
                ElementType s = ElementType.NORMAL;
                ElementType t = ElementType.NORMAL;
                switch (source){
                    case ("NORMAL"):
                        s = ElementType.NORMAL;
                        break;
                    case ("FIRE"):
                        s = ElementType.FIRE;
                        break;  
                    case ("WATER"):
                        s = ElementType.WATER;
                        break; 
                    case ("GRASS"):
                        s = ElementType.GRASS;
                        break; 
                }
                switch (target){
                    case ("NORMAL"):
                        t = ElementType.NORMAL;
                        break;
                    case ("FIRE"):
                        t = ElementType.FIRE;
                        break;  
                    case ("WATER"):
                        t = ElementType.WATER;
                        break; 
                    case ("GRASS"):
                        t = ElementType.GRASS;
                        break; 
                }
                ElementEffectivityKey source_target = new ElementEffectivityKey(s,t);
                listOfEffectivity.add(source_target, effectivity);
            }
        }
        catch (Exception e) {
            System.out.println("Failed to Load Effectivity...");
        }

        //Interface.loadingGame();
        //Interface.newGame();
        Interface.menuDisplay();

        boolean GameActive = true;
        Scanner scan = new Scanner(System.in);

        while (GameActive) {
            String input;
            input = scan.nextLine();
            if (input.equals("START GAME")) {
                Interface.startGame();

                // Bagian Input Nama Pemain
                System.out.printf("Enter player 1 name : ");
                String playerName1 = scan.next();
                System.out.printf("%nWelcome %s, Happy Monster Fighting :D\n", playerName1);
                System.out.printf("\nEnter player 2 name : ");
                String playerName2 = scan.next();
                System.out.printf("%nWelcome %s, Happy Monster Fighting :D\n\n", playerName2);

                Random random = new Random();
                Integer countMonster = monsterList.size();

                // Random Monster Player 1
                ArrayList<Monster> player1Monster = new ArrayList<Monster>();
                boolean done1 = false;
                while(!done1) {
                    Integer randomMonster = random.nextInt(countMonster);
                    Monster monsterP1 = monsterList.get(randomMonster);
                    while (player1Monster.contains(monsterP1)){
                        randomMonster = random.nextInt(countMonster);
                        monsterP1 = monsterList.get(randomMonster);
                    }
                    player1Monster.add(monsterP1);
                    if (player1Monster.size() == 6) {
                        done1 = true;
                    }
                }
                
                // Random Monster Player 2
                ArrayList<Monster> player2Monster = new ArrayList<Monster>();
                boolean done2 = false;
                while (!done2) {
                    Integer randomMonster = random.nextInt(countMonster);
                    Monster monsterP2 = monsterList.get(randomMonster);
                    while (player2Monster.contains(monsterP2)){
                        randomMonster = random.nextInt(countMonster);
                        monsterP2 = monsterList.get(randomMonster);
                    }
                    player2Monster.add(monsterP2);
                    if (player2Monster.size() == 6) {
                        done2 = true;
                    }
                }

                // Create Object Player
                Player player1 = new Player(1, playerName1, player1Monster);
                Player player2 = new Player(2, playerName2, player2Monster);

                // Random First Monster
                System.out.printf("%s, This Is Your Monster", playerName1);
                int randomFirst1 = random.nextInt(6);
                player1.setCurrentMonster(player1Monster.get(randomFirst1));
                player1.printMonstersName();
                int randomFirst2 = random.nextInt(6);
                player2.setCurrentMonster(player2Monster.get(randomFirst2));
                System.out.printf("%s, This Is Your Monster", playerName2);
                player2.printMonstersName();
                
                // Starting Round
                boolean RoundActive = true;
                int countRound = 0;

                while (RoundActive) {
                    countRound++;
                    
                    // Check Monster Status Condition Burn
                    if (player1.getCurrentMonster().getStatusCondition() == StatusCondition.BURN) {
                        double newHealth1 = (player1.getCurrentMonster().getStats().getHealthPoint()) 
                                           - (player1.getCurrentMonster().getStats().getMaxHealth() * 1/8);
                        player1.getCurrentMonster().getStats().setHealthPoint(newHealth1);
                    }
                    else if (player2.getCurrentMonster().getStatusCondition() == StatusCondition.BURN) {
                        double newHealth2 = (player2.getCurrentMonster().getStats().getHealthPoint()) 
                                           - (player2.getCurrentMonster().getStats().getMaxHealth() * 1/8);
                        player2.getCurrentMonster().getStats().setHealthPoint(newHealth2);
                    }
                    
                    // Check Monster Status Condition Poison
                    else if (player1.getCurrentMonster().getStatusCondition() == StatusCondition.POISON) {
                        double newHealth1 = (player1.getCurrentMonster().getStats().getHealthPoint()) 
                                           - (player1.getCurrentMonster().getStats().getMaxHealth() * 1/16);
                        player1.getCurrentMonster().getStats().setHealthPoint(newHealth1);
                    }
                    else if (player2.getCurrentMonster().getStatusCondition() == StatusCondition.POISON) {
                        double newHealth2 = (player2.getCurrentMonster().getStats().getHealthPoint()) 
                                           - (player2.getCurrentMonster().getStats().getMaxHealth() * 1/16);
                        player2.getCurrentMonster().getStats().setHealthPoint(newHealth2);
                    }

                    // Check Monster Status Condition Paralyze 
                    else if (player1.getCurrentMonster().getStatusCondition() == StatusCondition.PARALYZE) {
                        int chanceParalyze1 = random.nextInt(4);
                        if (chanceParalyze1 == 1) {
                            player1.getCurrentMonster().setIsMoveable(false);
                        }
                        else{
                            // Then IsMoveable = true
                            continue;
                        }
                    }
                    else if (player2.getCurrentMonster().getStatusCondition() == StatusCondition.PARALYZE) {
                        int chanceParalyze2 = random.nextInt(4);
                        if (chanceParalyze2 == 1) {
                            player2.getCurrentMonster().setIsMoveable(false);
                        }
                        else{
                            // Then IsMoveable = true
                            continue;
                        }
                    }

                    // Check Monster Status Condition Sleep
                    if(countRound == 1) {
                        continue;
                    } 
                    else {
                        for (Monster monster1 : player1Monster) {
                            monster1.setSleepTime(monster1.getSleepTime() - 1);
                            if (monster1.getSleepTime() <= 0) {
                                if (monster1.getStatusCondition() == StatusCondition.SLEEP) {
                                    System.out.printf(" %s Woke Up\n", monster1.getName());
                                    monster1.setStatusCondition(StatusCondition.NONE);
                                }
                                else {
                                    continue;
                                }
                            }
                            else{
                                System.out.printf(" %s Is Still In A Deep Sleep", monster1.getName());
                            }
                        }
                        for (Monster monster2 : player2Monster){
                            monster2.setSleepTime(monster2.getSleepTime() - 1);
                            if (monster2.getSleepTime() <= 0) {
                                if (monster2.getStatusCondition() == StatusCondition.SLEEP) {
                                    System.out.printf(" %s Woke Up\n", monster2.getName());
                                    monster2.setStatusCondition(StatusCondition.NONE);
                                }
                                else {
                                    continue;
                                }
                            }
                            else{
                                System.out.printf(" %s Still In A Deep Sleep\n", monster2.getName());
                            }
                        }
                    }
                    
                    // Round Start
                    int input1 = 0;
                    int input2 = 0;
                    // Player 1 Turn 
                    boolean p1Turn = true;
                    while(p1Turn) {
                        System.out.println(" ------- PLAYER 1 TURN -------");
                        Interface.roundStart();
                        input1 = scan.nextInt();
                        if (input1 == 1){
                            System.out.println("===== Moves of " + player1.getName() + " =====");
                            int index1 = 1;
                            for (Move move : player1.getCurrentMonster().getMoves()) {
                                System.out.println("Id Move     : " + index1);
                                System.out.println("Move Name   : " + move.getName());
                                System.out.println(" ");
                                index1++;
                            }
                            boolean outOfAmmunition = false;
                            while(!outOfAmmunition) {
                                System.out.printf("Select Your Move : ");
                                int movePlayer1 = scan.nextInt();
                                Move newMove1 = player1.getCurrentMonster().getMoves().get(movePlayer1 - 1);
                                player1.getCurrentMonster().setCurrentMove(newMove1);
                                if (player1.getCurrentMonster().getCurrentMove().getAmmunition() <= 0){
                                    System.out.printf("Amunisi Untuk Move %s Sudah Habis\n", player1.getCurrentMonster().getCurrentMove().getName());
                                }
                                else {
                                    outOfAmmunition = true;
                                    String nowMove1 = player1.getCurrentMonster().getCurrentMove().getName();
                                    System.out.printf("%s Use %s\n\n", player1.getCurrentMonster().getName(), nowMove1);
                                }
                            }
                            p1Turn = false;
                        }
                        else if (input1 == 2) {
                            boolean isGanti = true;
                            while (isGanti) {
                                player1.printMonsters();
                                System.out.printf("Switch Monster To : ");
                                int ganti = scan.nextInt();
                                if ((ganti > 6) || (ganti <= 0)){
                                    System.out.printf("Input Monster Yang Bener!!!");
                                }
                                else{
                                    if(player1.getMonsters().get(ganti-1).getStats().getHealthPoint() <= 0){
                                        System.out.printf("Monster sudah mati");
                                    }
                                    else{
                                        player1.setCurrentMonster(player1.getMonsters().get(ganti-1));
                                        isGanti = false;
                                    }
                                }
                            }
                            p1Turn = false;
                        }

                        else if (input1 == 3){
                            player1.printMonsters();
                        }
                        else if (input1 == 4) {
                            System.out.printf("\nSekarang ada di ronde %d\n", countRound-1);
                            String playMonster = player1.getCurrentMonster().getName();
                            System.out.printf("Monster yang sedang bertarung adalah %s\n", playMonster);
                            System.out.println("Ini adalah list monster yang tersisa : ");
                            int id = 0;
                            for (Monster monster : player1Monster){
                                if (monster.getStats().getHealthPoint() <= 0){
                                    System.out.println("(" + String.valueOf(id + 1) + ") " + monster.getName());
                                }    
                                id++;
                            }
                        }
                        else{
                            continue;
                        }
                    }

                    // Player 2 Turn
                    boolean p2Turn = true;
                    while(p2Turn) {
                        System.out.println("\n------- PLAYER 2 TURN -------");
                        Interface.roundStart();
                        input2 = scan.nextInt();
                        if (input2 == 1){
                            System.out.println("===== Moves of " + player2.getName() + " =====");
                            int index2 = 1;
                            for (Move move : player2.getCurrentMonster().getMoves()) {
                                System.out.println("Id Move : " + index2);
                                System.out.println("Move Name : " + move.getName());
                                System.out.println(" ");
                                index2++;
                            }
                            boolean outOfAmmunition = false;
                            while(!outOfAmmunition) {
                                System.out.printf("Select Your Move : ");
                                int movePlayer2 = scan.nextInt();
                                Move newMove2 = player2.getCurrentMonster().getMoves().get(movePlayer2 - 1);
                                player2.getCurrentMonster().setCurrentMove(newMove2);
                                if (player2.getCurrentMonster().getCurrentMove().getAmmunition() <= 0){
                                    System.out.printf("Amunisi Untuk Move %s Sudah Habis\n", player2.getCurrentMonster().getCurrentMove().getName());
                                }
                                else {
                                    outOfAmmunition = true;
                                    String nowMove2 = player2.getCurrentMonster().getCurrentMove().getName();
                                    System.out.printf("%s Use %s\n\n", player2.getCurrentMonster().getName(), nowMove2);
                                }
                            }
                            p2Turn = false;
                        }
                        else if (input2 == 2) {
                            boolean isGanti = true;
                            while (isGanti) {
                                player1.getMonsters();
                                System.out.printf("Switch Monster To : ");
                                int ganti = scan.nextInt();
                                if ((ganti > 6) || (ganti <= 0)){
                                    System.out.printf("Input Monster Yang Bener!!!");
                                }
                                else{
                                    if(player2.getMonsters().get(ganti-1).getStats().getHealthPoint() <= 0){
                                        System.out.printf("Monster sudah mati");
                                    }
                                    else{
                                        player2.setCurrentMonster(player2.getMonsters().get(ganti-1));
                                        isGanti = false;
                                    }
                                }
                            }
                            p2Turn = false;
                        }

                        else if (input2 == 3){
                            player2.printMonsters();
                        }
                        else if (input2 == 4){
                            System.out.printf("Sekarang ada di ronde %d\n", countRound);
                            String playMonster = player2.getCurrentMonster().getName();
                            System.out.printf("Monster yang sedang bertarung adalah %s\n", playMonster);
                            System.out.println("Ini adalah list monster yang tersisa : ");
                            int id = 0;
                            for (Monster monster : player2Monster){
                                if (monster.getStats().getHealthPoint() != 0){
                                    System.out.println("(" + String.valueOf(id + 1) + ") " + monster.getName());
                                }    
                                id++;
                            }
                        }
                        else{
                            System.out.println("Input masukan salah, Mohon ulangi!");
                        }
                    }
                    
                    // Compare Moves
                    if ((input1 == 1) && (input2 == 1)) {
                        int player1MovePriority = player1.getCurrentMonster().getCurrentMove().getPriority();
                        int player2MovePriority = player2.getCurrentMonster().getCurrentMove().getPriority();
                        System.out.println(player1MovePriority);
                        System.out.println(player2MovePriority);
                        Double player1Speed = player1.getCurrentMonster().getStats().getSpeed();
                        Double player2Speed = player2.getCurrentMonster().getStats().getSpeed();
                        // Adu Priority 
                        if((player1MovePriority > player2MovePriority) || ((player1MovePriority == player2MovePriority) && (player1Speed > player2Speed))) {
                            // Player 1 Move First
                            System.out.printf("%s's %s Attack %s's %s\n", player1.getName(), player1.getCurrentMonster().getName(), 
                                             player2.getName(), player2.getCurrentMonster().getName());
                            player1.getCurrentMonster().getCurrentMove().applyMove(player1.getCurrentMonster(), player2.getCurrentMonster(), listOfEffectivity);
                            if (player2.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                System.out.printf("%s is Fainted Out\n", player2.getCurrentMonster().getName());
                                if (player2.isLose()) {
                                    RoundActive = false;
                                }
                                else {
                                    System.out.printf("Choose Your Next Monster : ");
                                    int nextMonster = scan.nextInt();
                                    while (player2.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                        System.out.printf("%s Is Already Fainted Out\n", player2.getMonsters().get(nextMonster - 1).getName());
                                        System.out.printf("Choose Monster Id : ");
                                        nextMonster = scan.nextInt();
                                    }
                                    Monster newMonster = player2.getMonsters().get(nextMonster - 1);
                                    player2.setCurrentMonster(newMonster);
                                    System.out.printf("Go %s!!!\n", player2.getCurrentMonster().getName());
                                }
                            }
                            else if (player1.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                System.out.printf("%s is Fainted Out\n", player1.getCurrentMonster().getName());
                                if (player1.isLose()) {
                                    RoundActive = false;
                                }
                                else {
                                    System.out.printf("Choose Your Next Monster : ");
                                    int nextMonster = scan.nextInt();
                                    while (player1.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                        System.out.printf("%s Is Already Fainted Out\n", player1.getMonsters().get(nextMonster - 1).getName());
                                        System.out.printf("Choose Monster Id : ");
                                        nextMonster = scan.nextInt();
                                    }
                                    Monster newMonster = player1.getMonsters().get(nextMonster - 1);
                                    player1.setCurrentMonster(newMonster);
                                    System.out.printf("Go %s!!!\n", player1.getCurrentMonster().getName());
                                }
                            }
                            else {
                                System.out.printf("%s's %s Attack %s's %s\n", player2.getName(), player2.getCurrentMonster().getName(), 
                                             player1.getName(), player1.getCurrentMonster().getName());
                                player2.getCurrentMonster().getCurrentMove().applyMove(player2.getCurrentMonster(), player1.getCurrentMonster(), listOfEffectivity);
                                if (player2.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                    System.out.printf("%s is Fainted Out\n", player2.getCurrentMonster().getName());
                                    if (player2.isLose()) {
                                        RoundActive = false;
                                    }
                                    else {
                                        System.out.printf("Choose Your Next Monster : ");
                                        int nextMonster = scan.nextInt();
                                        while (player2.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                            System.out.printf("%s Is Already Fainted Out\n", player2.getMonsters().get(nextMonster - 1).getName());
                                            System.out.printf("Choose Monster Id : ");
                                            nextMonster = scan.nextInt();
                                        }
                                        Monster newMonster = player2.getMonsters().get(nextMonster - 1);
                                        player2.setCurrentMonster(newMonster);
                                        System.out.printf("Go %s!!!\n", player2.getCurrentMonster().getName());
                                    }
                                }
                                else if (player1.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                    System.out.printf("%s is Fainted Out\n", player1.getCurrentMonster().getName());
                                    if (player1.isLose()) {
                                        RoundActive = false;
                                    }
                                    else {
                                        System.out.printf("Choose Your Next Monster : ");
                                        int nextMonster = scan.nextInt();
                                        while (player1.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                            System.out.printf("%s Is Already Fainted Out\n", player1.getMonsters().get(nextMonster - 1).getName());
                                            System.out.printf("Choose Monster Id : ");
                                            nextMonster = scan.nextInt();
                                        }
                                        Monster newMonster = player1.getMonsters().get(nextMonster - 1);
                                        player1.setCurrentMonster(newMonster);
                                        System.out.printf("Go %s!!!\n", player1.getCurrentMonster().getName());
                                    }
                                }
                            }
                        }
                        
                        else if((player1MovePriority < player2MovePriority) || ((player1MovePriority == player2MovePriority) && (player1Speed < player2Speed))) {
                            // Player 2 Move
                            System.out.printf("%s's %s Attack %s's %s\n", player2.getName(), player2.getCurrentMonster().getName(), 
                                             player1.getName(), player1.getCurrentMonster().getName());
                            player2.getCurrentMonster().getCurrentMove().applyMove(player2.getCurrentMonster(), player1.getCurrentMonster(), listOfEffectivity);
                            if (player2.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                System.out.printf("%s is Fainted Out\n", player2.getCurrentMonster().getName());
                                if (player2.isLose()) {
                                    RoundActive = false;
                                }
                                else {
                                    System.out.printf("Choose Your Next Monster : ");
                                    int nextMonster = scan.nextInt();
                                    while (player2.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                        System.out.printf("%s Is Already Fainted Out\n", player2.getMonsters().get(nextMonster - 1).getName());
                                        System.out.printf("Choose Monster Id : ");
                                        nextMonster = scan.nextInt();
                                    }
                                    Monster newMonster = player2.getMonsters().get(nextMonster - 1);
                                    player2.setCurrentMonster(newMonster);
                                    System.out.printf("Go %s!!!\n", player2.getCurrentMonster().getName());
                                }
                            }
                            else if (player1.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                System.out.printf("%s is Fainted Out\n", player1.getCurrentMonster().getName());
                                if (player1.isLose()) {
                                    RoundActive = false;
                                }
                                else {
                                    System.out.printf("Choose Your Next Monster : ");
                                    int nextMonster = scan.nextInt();
                                    while (player1.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                        System.out.printf("%s Is Already Fainted Out\n", player1.getMonsters().get(nextMonster - 1).getName());
                                        System.out.printf("Choose Monster Id : ");
                                        nextMonster = scan.nextInt();
                                    }
                                    Monster newMonster = player1.getMonsters().get(nextMonster - 1);
                                    player1.setCurrentMonster(newMonster);
                                    System.out.printf("Go %s!!!\n", player1.getCurrentMonster().getName());
                                }
                            }
                            else {
                                // Player 1 Move First
                                System.out.printf("%s's %s Attack %s's %s\n", player1.getName(), player1.getCurrentMonster().getName(), 
                                player2.getName(), player2.getCurrentMonster().getName());
                                player1.getCurrentMonster().getCurrentMove().applyMove(player1.getCurrentMonster(), player2.getCurrentMonster(), listOfEffectivity);
                                if (player2.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                    System.out.printf("%s is Fainted Out\n", player2.getCurrentMonster().getName());
                                    if (player2.isLose()) {
                                        RoundActive = false;
                                    }
                                    else {
                                        System.out.printf("Choose Your Next Monster : ");
                                        int nextMonster = scan.nextInt();
                                        while (player2.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                            System.out.printf("%s Is Already Fainted Out\n", player2.getMonsters().get(nextMonster - 1).getName());
                                            System.out.printf("Choose Monster Id : ");
                                            nextMonster = scan.nextInt();
                                        }
                                        Monster newMonster = player2.getMonsters().get(nextMonster - 1);
                                        player2.setCurrentMonster(newMonster);
                                        System.out.printf("Go %s!!!\n", player2.getCurrentMonster().getName());
                                    }
                                }
                                else if (player1.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                    System.out.printf("%s is Fainted Out\n", player1.getCurrentMonster().getName());
                                    if (player1.isLose()) {
                                        RoundActive = false;
                                    }
                                    else {
                                        System.out.printf("Choose Your Next Monster : ");
                                        int nextMonster = scan.nextInt();
                                        while (player1.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                            System.out.printf("%s Is Already Fainted Out\n", player1.getMonsters().get(nextMonster - 1).getName());
                                            System.out.printf("Choose Monster Id : ");
                                            nextMonster = scan.nextInt();
                                        }
                                        Monster newMonster = player1.getMonsters().get(nextMonster - 1);
                                        player1.setCurrentMonster(newMonster);
                                        System.out.printf("Go %s!!!\n", player1.getCurrentMonster().getName());
                                    }
                                }
                            }
                        }
                        else {
                            int firstMove = random.nextInt(2);
                            if (firstMove == 0) {
                                // Player 1 Move First
                                System.out.printf("%s's %s Attack %s's %s\n", player1.getName(), player1.getCurrentMonster().getName(), 
                                                player2.getName(), player2.getCurrentMonster().getName());
                                player1.getCurrentMonster().getCurrentMove().applyMove(player1.getCurrentMonster(), player2.getCurrentMonster(), listOfEffectivity);
                                if (player2.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                    System.out.printf("%s is Fainted Out\n", player2.getCurrentMonster().getName());
                                    if (player2.isLose()) {
                                        RoundActive = false;
                                    }
                                    else {
                                        System.out.printf("Choose Your Next Monster : ");
                                        int nextMonster = scan.nextInt();
                                        while (player2.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                            System.out.printf("%s Is Already Fainted Out\n", player2.getMonsters().get(nextMonster - 1).getName());
                                            System.out.printf("Choose Monster Id : ");
                                            nextMonster = scan.nextInt();
                                        }
                                        Monster newMonster = player2.getMonsters().get(nextMonster - 1);
                                        player2.setCurrentMonster(newMonster);
                                        System.out.printf("Go %s!!!\n", player2.getCurrentMonster().getName());
                                    }
                                }
                                else if (player1.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                    System.out.printf("%s is Fainted Out\n", player1.getCurrentMonster().getName());
                                    if (player1.isLose()) {
                                        RoundActive = false;
                                    }
                                    else {
                                        System.out.printf("Choose Your Next Monster : ");
                                        int nextMonster = scan.nextInt();
                                        while (player1.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                            System.out.printf("%s Is Already Fainted Out\n", player1.getMonsters().get(nextMonster - 1).getName());
                                            System.out.printf("Choose Monster Id : ");
                                            nextMonster = scan.nextInt();
                                        }
                                        Monster newMonster = player1.getMonsters().get(nextMonster - 1);
                                        player1.setCurrentMonster(newMonster);
                                        System.out.printf("Go %s!!!\n", player1.getCurrentMonster().getName());
                                    }
                                }
                                else {
                                    System.out.printf("%s's %s Attack %s's %s\n", player2.getName(), player2.getCurrentMonster().getName(), 
                                                    player1.getName(), player1.getCurrentMonster().getName());
                                    player2.getCurrentMonster().getCurrentMove().applyMove(player2.getCurrentMonster(), player1.getCurrentMonster(), listOfEffectivity);
                                    if (player2.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                        System.out.printf("%s is Fainted Out\n", player2.getCurrentMonster().getName());
                                        if (player2.isLose()) {
                                            RoundActive = false;
                                        }
                                        else {
                                            System.out.printf("Choose Your Next Monster : ");
                                            int nextMonster = scan.nextInt();
                                            while (player2.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                System.out.printf("%s Is Already Fainted Out\n", player2.getMonsters().get(nextMonster - 1).getName());
                                                System.out.printf("Choose Monster Id : ");
                                                nextMonster = scan.nextInt();
                                            }
                                            Monster newMonster = player2.getMonsters().get(nextMonster - 1);
                                            player2.setCurrentMonster(newMonster);
                                            System.out.printf("Go %s!!!\n", player2.getCurrentMonster().getName());
                                        }
                                    }
                                    else if (player1.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                        System.out.printf("%s is Fainted Out\n", player1.getCurrentMonster().getName());
                                        if (player1.isLose()) {
                                            RoundActive = false;
                                        }
                                        else {
                                            System.out.printf("Choose Your Next Monster : ");
                                            int nextMonster = scan.nextInt();
                                            while (player1.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                System.out.printf("%s Is Already Fainted Out\n", player1.getMonsters().get(nextMonster - 1).getName());
                                                System.out.printf("Choose Monster Id : ");
                                                nextMonster = scan.nextInt();
                                            }
                                            Monster newMonster = player1.getMonsters().get(nextMonster - 1);
                                            player1.setCurrentMonster(newMonster);
                                            System.out.printf("Go %s!!!\n", player1.getCurrentMonster().getName());
                                        }
                                    }
                                }
                            }
                            else {
                                // Player 2 Move
                                System.out.printf("%s's %s Attack %s's %s\n", player2.getName(), player2.getCurrentMonster().getName(), 
                                player1.getName(), player1.getCurrentMonster().getName());
                                player2.getCurrentMonster().getCurrentMove().applyMove(player2.getCurrentMonster(), player1.getCurrentMonster(), listOfEffectivity);
                                if (player2.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                    System.out.printf("%s is Fainted Out\n", player2.getCurrentMonster().getName());
                                    if (player2.isLose()) {
                                        RoundActive = false;
                                    }
                                    else {
                                        System.out.printf("Choose Your Next Monster : ");
                                        int nextMonster = scan.nextInt();
                                        while (player2.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                            System.out.printf("%s Is Already Fainted Out\n", player2.getMonsters().get(nextMonster - 1).getName());
                                            System.out.printf("Choose Monster Id : ");
                                            nextMonster = scan.nextInt();
                                        }
                                        Monster newMonster = player2.getMonsters().get(nextMonster - 1);
                                        player2.setCurrentMonster(newMonster);
                                        System.out.printf("Go %s!!!\n", player2.getCurrentMonster().getName());
                                    }
                                }
                                else if (player1.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                    System.out.printf("%s is Fainted Out\n", player1.getCurrentMonster().getName());
                                    if (player1.isLose()) {
                                        RoundActive = false;
                                    }
                                    else {
                                        System.out.printf("Choose Your Next Monster : ");
                                        int nextMonster = scan.nextInt();
                                        while (player1.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                            System.out.printf("%s Is Already Fainted Out\n", player1.getMonsters().get(nextMonster - 1).getName());
                                            System.out.printf("Choose Monster Id : ");
                                            nextMonster = scan.nextInt();
                                        }
                                        Monster newMonster = player1.getMonsters().get(nextMonster - 1);
                                        player1.setCurrentMonster(newMonster);
                                        System.out.printf("Go %s!!!\n", player1.getCurrentMonster().getName());
                                    }
                                }
                                else {
                                    // Player 1 Move First
                                    System.out.printf("%s's %s Attack %s's %s\n", player1.getName(), player1.getCurrentMonster().getName(), 
                                    player2.getName(), player2.getCurrentMonster().getName());
                                    player1.getCurrentMonster().getCurrentMove().applyMove(player1.getCurrentMonster(), player2.getCurrentMonster(), listOfEffectivity);
                                    if (player2.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                        System.out.printf("%s is Fainted Out\n", player2.getCurrentMonster().getName());
                                        if (player2.isLose()) {
                                            RoundActive = false;
                                        }
                                        else {
                                            System.out.printf("Choose Your Next Monster : ");
                                            int nextMonster = scan.nextInt();
                                            while (player2.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                System.out.printf("%s Is Already Fainted Out\n", player2.getMonsters().get(nextMonster - 1).getName());
                                                System.out.printf("Choose Monster Id : ");
                                                nextMonster = scan.nextInt();
                                            }
                                            Monster newMonster = player2.getMonsters().get(nextMonster - 1);
                                            player2.setCurrentMonster(newMonster);
                                            System.out.printf("Go %s!!!\n", player2.getCurrentMonster().getName());
                                        }
                                    }
                                    else if (player1.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                        System.out.printf("%s is Fainted Out\n", player1.getCurrentMonster().getName());
                                        if (player1.isLose()) {
                                            RoundActive = false;
                                        }
                                        else {
                                            System.out.printf("Choose Your Next Monster : ");
                                            int nextMonster = scan.nextInt();
                                            while (player1.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                System.out.printf("%s Is Already Fainted Out\n", player1.getMonsters().get(nextMonster - 1).getName());
                                                System.out.printf("Choose Monster Id : ");
                                                nextMonster = scan.nextInt();
                                            }
                                            Monster newMonster = player1.getMonsters().get(nextMonster - 1);
                                            player1.setCurrentMonster(newMonster);
                                            System.out.printf("Go %s!!!\n", player1.getCurrentMonster().getName());
                                        }
                                    }
                                }
                            }
                        }
                    }

                    else if ((input1 == 1) && (input2 == 2)) {
                        // Player 1 Move
                        System.out.printf("%s's %s Attack %s's %s\n", player1.getName(), player1.getCurrentMonster().getName(), 
                        player2.getName(), player2.getCurrentMonster().getName());
                        player1.getCurrentMonster().getCurrentMove().applyMove(player1.getCurrentMonster(), player2.getCurrentMonster(), listOfEffectivity);
                        if (player2.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                            System.out.printf("%s is Fainted Out\n", player2.getCurrentMonster().getName());
                            if (player2.isLose()) {
                                RoundActive = false;
                            }
                            else {
                                System.out.printf("Choose Your Next Monster : ");
                                int nextMonster = scan.nextInt();
                                while (player2.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                    System.out.printf("%s Is Already Fainted Out\n", player2.getMonsters().get(nextMonster - 1).getName());
                                    System.out.printf("Choose Monster Id : ");
                                    nextMonster = scan.nextInt();
                                }
                                Monster newMonster = player2.getMonsters().get(nextMonster - 1);
                                player2.setCurrentMonster(newMonster);
                                System.out.printf("Go %s!!!\n", player2.getCurrentMonster().getName());
                            }
                        }
                        else if (player1.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                            System.out.printf("%s is Fainted Out\n", player1.getCurrentMonster().getName());
                            if (player1.isLose()) {
                                RoundActive = false;
                            }
                            else {
                                System.out.printf("Choose Your Next Monster : ");
                                int nextMonster = scan.nextInt();
                                while (player1.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                    System.out.printf("%s Is Already Fainted Out\n", player1.getMonsters().get(nextMonster - 1).getName());
                                    System.out.printf("Choose Monster Id : ");
                                    nextMonster = scan.nextInt();
                                }
                                Monster newMonster = player1.getMonsters().get(nextMonster - 1);
                                player1.setCurrentMonster(newMonster);
                                System.out.printf("Go %s!!!\n", player1.getCurrentMonster().getName());
                            }
                        }
                    }

                    else if ((input1 == 2) && (input2 == 1)) {
                        // Player 2 Move
                        System.out.printf("%s's %s Attack %s's %s\n", player2.getName(), player2.getCurrentMonster().getName(), 
                        player1.getName(), player1.getCurrentMonster().getName());
                        player2.getCurrentMonster().getCurrentMove().applyMove(player2.getCurrentMonster(), player1.getCurrentMonster(), listOfEffectivity);
                        if (player2.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                            System.out.printf("%s is Fainted Out\n", player2.getCurrentMonster().getName());
                            if (player2.isLose()) {
                                RoundActive = false;
                            }
                            else {
                                System.out.printf("Choose Your Next Monster : ");
                                int nextMonster = scan.nextInt();
                                while (player2.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                    System.out.printf("%s Is Already Fainted Out\n", player2.getMonsters().get(nextMonster - 1).getName());
                                    System.out.printf("Choose Monster Id : ");
                                    nextMonster = scan.nextInt();
                                }
                                Monster newMonster = player2.getMonsters().get(nextMonster - 1);
                                player2.setCurrentMonster(newMonster);
                                System.out.printf("Go %s!!!\n", player2.getCurrentMonster().getName());
                            }
                        }
                        else if (player1.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                            System.out.printf("%s is Fainted Out\n", player1.getCurrentMonster().getName());
                            if (player1.isLose()) {
                                RoundActive = false;
                            }
                            else {
                                System.out.printf("Choose Your Next Monster : ");
                                int nextMonster = scan.nextInt();
                                while (player1.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                    System.out.printf("%s Is Already Fainted Out\n", player1.getMonsters().get(nextMonster - 1).getName());
                                    System.out.printf("Choose Monster Id : ");
                                    nextMonster = scan.nextInt();
                                }
                                Monster newMonster = player1.getMonsters().get(nextMonster - 1);
                                player1.setCurrentMonster(newMonster);
                                System.out.printf("Go %s!!!\n", player1.getCurrentMonster().getName());
                            }
                        }  
                    }
                    else {
                        System.out.println("Both Players Switched Their Monster");
                    }
                }
                if (player1.isLose() && !player2.isLose()) {
                    System.out.printf("%s Win The Game, Congrats!!!\n", player2.getName());
                    GameActive = false;
                }
                else if (!player1.isLose() && player2.isLose()) {
                    System.out.printf("%s Win The Game, Congrats!!!\n", player1.getName());
                    GameActive = false;
                }
            }

            else if(input.equals("HOW TO PLAY")) {
                Interface.help();
                String back = scan.nextLine();
                if (back.equals("BACK")){
                    Interface.newGame();
                    Interface.menuDisplay();
                    input = scan.nextLine();
                    }
                }
            else if(input.equals("EXIT GAME")){
                Interface.newGame();
                System.out.print("\033[H\033[2J");
                System.out.flush();
                GameActive = false; 
                }
            else{
                System.out.println("\nInput salah, mohon ulangi");
            }
        }
        scan.close();
    }
}