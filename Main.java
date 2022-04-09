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
            "configs/movepool.csv"));

    public static void main(String[] args) {
        for (String fileName : CSV_FILE_PATHS) {
            try {
                System.out.printf("Filename: %s\n", fileName);
                CSVReader reader = new CSVReader(new File(Main.class.getResource(fileName).toURI()), ";");
                reader.setSkipHeader(true);
                List<String[]> lines = reader.read();
                System.out.println("=========== CONTENT START ===========\n");
                for (String[] line : lines) {
                    for (String word : line) {
                        System.out.printf("%s ", word);
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println("=========== CONTENT END ===========");
                System.out.println();
            } catch (Exception e) {
                // do nothing
            }
        }

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
}
