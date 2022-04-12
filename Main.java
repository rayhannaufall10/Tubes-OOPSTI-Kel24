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

    public static void main(String[] args) {
        // Interface Awal
        List<Monster> monsterList = readConfig(CSV_FILE_PATHS.get(0));
        //List<Move> moveList = readMovePool(CSV_FILE_PATHS.get(1));

        Interface.loadingGame();
        Interface.newGame();
        Interface.menuDisplay();

        boolean GameActive = true;
        Scanner scan = new Scanner(System.in);

        while (GameActive) {
            System.out.println("Select Option");
            System.out.printf("\033[1;33m>\033[0m ");
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
                System.out.printf("%nWelcome %s, Happy Monster Fighting :D\n", playerName2);

                System.out.println("Ini diatas new Random");
                Random random = new Random();
                System.out.println("Ini dibawah new Random");
                Integer countMonster = monsterList.size();
                System.out.println("Ini POKOKHYA BAWAHNYA LAGI");

                // Random Monster Player 1
                ArrayList<Monster> player1Monster = new ArrayList<Monster>();
                for (int i = 0; i < 6; i++){
                    Integer randomMonster = random.nextInt(countMonster);
                    Monster monsterP1 = monsterList.get(randomMonster);
                    while (monsterList.contains(monsterP1)){
                        randomMonster = random.nextInt(countMonster);
                        monsterP1 = monsterList.get(randomMonster);
                    }
                    player1Monster.add(monsterP1);
                }
                System.out.println("cREATE MONSTER P1");
                // Random Monster Player 2
                ArrayList<Monster> player2Monster = new ArrayList<Monster>();
                for (int i = 0; i < 6; i++){
                    Integer randomMonster = random.nextInt(countMonster);
                    Monster monsterP2 = monsterList.get(randomMonster);
                    while (monsterList.contains(monsterP2)){
                        randomMonster = random.nextInt(countMonster);
                        monsterP2 = monsterList.get(randomMonster);
                    }
                    player2Monster.add(monsterP2);
                }
                System.out.println("cREATE MONSTER P2");
                // Create Object Player
                Player player1 = new Player(1, playerName1, player1Monster);
                Player player2 = new Player(2, playerName2, player2Monster);
                System.out.printf(" %s, This Is Your Monster\n", playerName1);
                player1.printMonsters();
                System.out.printf(" %s, This Is Your Monster\n", playerName2);
                player2.printMonsters();

                // Assign Random Monster As Current Monsters
                Integer random1 = random.nextInt(6);
                player1.setCurrentMonster(player1Monster.get(random1));
                Integer random2 = random.nextInt(6);
                player2.setCurrentMonster(player1Monster.get(random2));
                
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
                    for (Monster monster1 : player1Monster){
                        monster1.setSleepTime(monster1.getSleepTime() - 1);
                        if (monster1.getSleepTime() <= 0){
                            System.out.printf(" %s Woke Up\n", monster1.getName());
                            monster1.setStatusCondition(StatusCondition.NONE);
                        }
                        else{
                            System.out.printf(" %s Is Still In A Deep Sleep", monster1.getName());
                        }
                    }
                    for (Monster monster2 : player2Monster){
                        monster2.setSleepTime(monster2.getSleepTime() - 1);
                        if (monster2.getSleepTime() == 0){
                            System.out.printf(" %s Woke Up\n", monster2.getName());
                            monster2.setStatusCondition(StatusCondition.NONE);
                        }
                        else{
                            System.out.printf(" %s Still In A Deep Sleep", monster2.getName());
                        }
                    }
                    
                    // Round Start
                    System.out.printf(" === ROUND %d === \n", countRound);
                    String input1 = null;
                    String input2 = null;
                    // Player 1 Turn 
                    boolean p1Turn = true;
                    while(p1Turn) {
                        Interface.roundStart();
                        input1 = scan.nextLine();
                        if (input1.equals("MOVE")){
                            System.out.println("===== Moves of " + player1.getName() + " =====");
                            for (Move move : player1.getCurrentMonster().getMoves()) {
                                System.out.println("Id Move : " + move.getId());
                                System.out.println("Move Name : " + move.getName());
                                System.out.println(" ");
                            }
                            boolean outOfAmmunition = false;
                            while(!outOfAmmunition) {
                                int movePlayer1 = scan.nextInt();
                                Move newMove1 = player1.getCurrentMonster().getMoves().get(movePlayer1 - 1);
                                player1.getCurrentMonster().setCurrentMove(newMove1);
                                if (player1.getCurrentMonster().getCurrentMove().getAmmunition() <= 0){
                                    System.out.printf("Amunisi Untuk Move %s Sudah Habis\n", player1.getCurrentMonster().getCurrentMove().getName());
                                }
                                else {
                                    continue;
                                }
                            }
                            p1Turn = false;
                        }
                        else if (input1.equals("SWITCH MONSTER")) {
                            boolean isGanti = true;
                            while (isGanti) {
                                player1.getMonsters();
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

                        else if (input1.equals("VIEW MONSTER INFO")){
                            player1.printMonsters();
                        }
                        else if (input1.equals("VIEW GAME INFO")){
                            System.out.printf("Sekarang ada di ronde %d\n", countRound);
                            String playMonster = player1.getCurrentMonster().getName();
                            System.out.printf("Monster yang sedang bertarung adalah %s\n", playMonster);
                            System.out.println("Ini adalah list monster yang tersisa : ");
                            int id = 0;
                            for (Monster monster : player1Monster){
                                if (monster.getStats().getHealthPoint() != 0){
                                    System.out.println("(" + String.valueOf(id + 1) + "]" + monster.getName());
                                }    
                                id++;
                            }
                        }

                        else{
                            System.out.println("Input masukan salah, Mohon ulangi!");
                        }
                    }

                    // Player 2 Turn
                    boolean p2Turn = true;
                    while(p2Turn) {
                        Interface.roundStart();
                        input2 = scan.nextLine();
                        if (input2.equals("MOVE")){
                            System.out.println("===== Moves of " + player2.getName() + " =====");
                            for (Move move : player2.getCurrentMonster().getMoves()) {
                                System.out.println("Id Move : " + move.getId());
                                System.out.println("Move Name : " + move.getName());
                                System.out.println(" ");
                            }
                            boolean outOfAmmunition = false;
                            while(!outOfAmmunition) {
                                int movePlayer2 = scan.nextInt();
                                Move newMove2 = player2.getCurrentMonster().getMoves().get(movePlayer2 - 1);
                                player2.getCurrentMonster().setCurrentMove(newMove2);
                                if (player2.getCurrentMonster().getCurrentMove().getAmmunition() <= 0){
                                    System.out.printf("Amunisi Untuk Move %s Sudah Habis\n", player2.getCurrentMonster().getCurrentMove().getName());
                                }
                                else {
                                    continue;
                                }
                            }
                            p2Turn = false;
                        }
                        else if (input2.equals("SWITCH MONSTER")) {
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

                        else if (input2.equals("VIEW MONSTER INFO")){
                            player2.printMonsters();
                        }
                        else if (input2.equals("VIEW GAME INFO")){
                            System.out.printf("Sekarang ada di ronde %d\n", countRound);
                            String playMonster = player2.getCurrentMonster().getName();
                            System.out.printf("Monster yang sedang bertarung adalah %s\n", playMonster);
                            System.out.println("Ini adalah list monster yang tersisa : ");
                            int id = 0;
                            for (Monster monster : player2Monster){
                                if (monster.getStats().getHealthPoint() != 0){
                                    System.out.println("(" + String.valueOf(id + 1) + "]" + monster.getName());
                                }    
                                id++;
                            }
                        }
                        else{
                            System.out.println("Input masukan salah, Mohon ulangi!");
                        }
                    }

                    // Compare Moves
                    if ((input1.equals("MOVE")) && (input2.equals("MOVE"))) {
                        int player1MovePriority = player1.getCurrentMonster().getCurrentMove().getPriority();
                        int player2MovePriority = player2.getCurrentMonster().getCurrentMove().getPriority();
                        double player1MonsterSpeed = player1.getCurrentMonster().getStats().getSpeed();
                        double player2MonsterSpeed = player2.getCurrentMonster().getStats().getSpeed();
                        
                        // Adu Priority 
                        if(player1MovePriority > player2MovePriority) {
                            // Player 1 Move
                            System.out.printf("%s's %s Attack %s's %s", player1.getName(), player1.getCurrentMonster().getName(), 
                                             player2.getName(), player2.getCurrentMonster().getName());
                            player1.getCurrentMonster().getCurrentMove().applyMove(player1.getCurrentMonster(), player2.getCurrentMonster());
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
                        
                        else if(player1MovePriority < player2MovePriority) {
                            // Player 2 Move
                            System.out.printf("%s's %s Attack %s's %s", player2.getName(), player2.getCurrentMonster().getName(), 
                                             player1.getName(), player1.getCurrentMonster().getName());
                            player2.getCurrentMonster().getCurrentMove().applyMove(player2.getCurrentMonster(), player1.getCurrentMonster());
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
                        else if(player1MovePriority == player2MovePriority) {
                            if (player1MonsterSpeed > player2MonsterSpeed) {
                                // Player 1 Move
                                System.out.printf("%s's %s Attack %s's %s", player1.getName(), player1.getCurrentMonster().getName(), 
                                player2.getName(), player2.getCurrentMonster().getName());
                                player1.getCurrentMonster().getCurrentMove().applyMove(player1.getCurrentMonster(), player2.getCurrentMonster());
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
                            else if (player1MonsterSpeed < player2MonsterSpeed) {
                                // Player 2 Move
                                System.out.printf("%s's %s Attack %s's %s", player2.getName(), player2.getCurrentMonster().getName(), 
                                player1.getName(), player1.getCurrentMonster().getName());
                                player2.getCurrentMonster().getCurrentMove().applyMove(player2.getCurrentMonster(), player1.getCurrentMonster());
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
                            else if (player1MonsterSpeed == player2MonsterSpeed) {
                                int firstMove = random.nextInt(2);
                                if (firstMove == 0) {
                                    // Player 1 Move
                                    System.out.printf("%s's %s Attack %s's %s", player1.getName(), player1.getCurrentMonster().getName(), 
                                    player2.getName(), player2.getCurrentMonster().getName());
                                    player1.getCurrentMonster().getCurrentMove().applyMove(player1.getCurrentMonster(), player2.getCurrentMonster());
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
                                    // Player 2 Move
                                    System.out.printf("%s's %s Attack %s's %s", player2.getName(), player2.getCurrentMonster().getName(), 
                                    player1.getName(), player1.getCurrentMonster().getName());
                                    player2.getCurrentMonster().getCurrentMove().applyMove(player2.getCurrentMonster(), player1.getCurrentMonster());
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
                    else if ((input1.equals("MOVE")) && (input2.equals("SWITCH MONSTER"))) {
                        // Player 1 Move
                        System.out.printf("%s's %s Attack %s's %s", player1.getName(), player1.getCurrentMonster().getName(), 
                        player2.getName(), player2.getCurrentMonster().getName());
                        player1.getCurrentMonster().getCurrentMove().applyMove(player1.getCurrentMonster(), player2.getCurrentMonster());
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
                    else if ((input1.equals("SWITCH")) && (input2.equals("MOVE"))) {
                        // Player 2 Move
                        System.out.printf("%s's %s Attack %s's %s", player2.getName(), player2.getCurrentMonster().getName(), 
                        player1.getName(), player1.getCurrentMonster().getName());
                        player2.getCurrentMonster().getCurrentMove().applyMove(player2.getCurrentMonster(), player1.getCurrentMonster());
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
                    System.out.printf("%s Win The Game, Congrats!!!", player2.getName());
                    GameActive = false;
                }
                else if (!player1.isLose() && player2.isLose()) {
                    System.out.printf("%s Win The Game, Congrats!!!", player1.getName());
                    GameActive = false;
                }
            }

            else if(input.equals("HOW TO PLAY")) {
                Interface.help();
                String back = scan.nextLine();
                if (back.equals("BACK")){
                    Interface.newGame();
                    Interface.menuDisplay();
                    System.out.println("Select Option");
                    System.out.printf("\033[1;33m>\033[0m ");
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
                String[] arrOfEltypesTemp = line[2].split(",");
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
                double[] baseStats = {0, 0, 0, 0, 0, 0};
                for(int i = 0; i < 6; i++) {
                    baseStats[i] = Integer.valueOf(tempBS[i]);
                }
                Stats monStats = new Stats(baseStats[0], baseStats[1], baseStats[2], 
                                 baseStats[3], baseStats[4], baseStats[5]);
                // Create MoveMonster
                String[] moveString = ((line[4]).split(","));
                int count = moveString.length;
                int[] moves = new int[count];
                for (int idx = 0; idx < count; idx++) {
                    moves[idx] = Integer.valueOf(moveString[idx]);
                }

                // SET UP MOVE MILIK MONSTER
                String move = line[4];
                String[] arrOfMove = move.split(",");
                ArrayList<Move> monsMove = new ArrayList<Move>();
                DefaultMoves defaultMove = new DefaultMoves();
                monsMove.add(defaultMove);
                for(int i = 1; i < arrOfMove.length; i++){
                    Move originMove = movePool.get(Integer.valueOf(arrOfMove[i])-1);
                    monsMove.add(originMove);
                }
                
                // Create Monster Object
                Monster monsterTemp = new Monster(id, name, elementTypes, monStats, monsMove);
                monsterList.add(monsterTemp);
            }
        }
        catch (Exception e) {
            System.out.println("Failed to Load Monster...");
        }
        return monsterList;
    }
}
