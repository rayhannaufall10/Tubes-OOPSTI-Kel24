import util.CSVReader;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final List<String> CSV_FILE_PATHS = Collections.unmodifiableList(Arrays.asList(
            "configs/monsterpool.csv",
            "configs/movepool.csv",
            "configs/element-type-effectivity-chart.csv"));

    public static void main(String[] args) {
        // Interface Awal
        readConfig(CSV_FILE_PATHS.get(0));
        Interface.loadingGame();
        
        boolean isActive = true;
        while (isActive) {
            System.out.println("Welcome to ^o^");
            System.out.println("Menus :");
            System.out.println("1. Start Game");
            System.out.println("2. How to Play");
            System.out.println("3. Exit Game");

            Scanner scan = new Scanner(System.in);
            System.out.println("select option :");

            int select = scan.nextInt();
            if (select == 1) {
                System.out.printf("Enter player 1 name: ");
                String playerName1 = scan.next();
                Player playerOne = new Player(playerName1);
                System.out.printf("Enter player 2 name: ");
                String playername2 = scan.next();
                Player playerTwo = new Player(playername2);
                int player = 1;
                boolean gameGoing = true;
                Player currPlayer;
                while (gameGoing = true) {
                    currPlayer = player == 1 ? playerOne : playerTwo;
                    System.out.println("Menu :");

                    if (player == 1) {
                        player = 2;
                    } else if (player == 2) {
                        // TODO : Do action such as using both player moves
                        player = 3;

                    } else {
                        player = 1;
                    }
                }
            }
        }
    }

    public static List<Move> readMoovPool(String file){
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
        movePool = readMoovPool(CSV_FILE_PATHS.get(1));
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
            System.out.println("Failed to Load Moves...");
        }
        return monsterList;
    }
}
