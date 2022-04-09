public class Interface {
    public static void loadingGame(){
        try {
            Thread.sleep(1000);
            System.out.println("");
            System.out.println("=========== WELCOME TRAINERS ===========\n");
            Thread.sleep(2000);
            System.out.println("Welcome to Monster Saku");
            Thread.sleep(2000);
            System.out.println("Hello, I'm Professor Ash, Your Monster Saku Master");
            Thread.sleep(2000);
            System.out.println("Please Wait A Few Moments For System To Load The Game");
            Thread.sleep(2000);
            System.out.printf("Loading Game Info");
            for (int i = 0; i < 5; i++){
                Thread.sleep(1000);
                System.out.printf(".");
            }
            System.out.println("");
            Thread.sleep(2000);
            System.out.printf("Game Info Loaded");
            System.out.println("\n");
            Thread.sleep(2000);
            System.out.println("=========== ENJOY THE GAME ===========");
            Thread.sleep(2000);
            System.out.print("\033[H\033[2J");
            System.out.flush(); 
        }
        catch(InterruptedException e) {

        }                                                                     
    }

    public static void newGame(){
        try{
            Thread.sleep(1000);
            System.out.println("============================================================================================================\n");
            System.out.println("    ███    ███  ██████  ███    ██ ███████ ████████ ███████ ██████      ███████  █████  ██   ██ ██    ██ ");
            System.out.println("    ████  ████ ██    ██ ████   ██ ██         ██    ██      ██   ██     ██      ██   ██ ██  ██  ██    ██ ");
            System.out.println("    ██ ████ ██ ██    ██ ██ ██  ██ ███████    ██    █████   ██████      ███████ ███████ █████   ██    ██ ");
            System.out.println("    ██  ██  ██ ██    ██ ██  ██ ██      ██    ██    ██      ██   ██          ██ ██   ██ ██  ██  ██    ██ ");
            System.out.println("    ██      ██  ██████  ██   ████ ███████    ██    ███████ ██   ██     ███████ ██   ██ ██   ██  ██████  ");
            System.out.println("");
            System.out.println("============================================================================================================");
        }
        catch(InterruptedException e){

        }
    }
    public static void main(String[] args){
        loadingGame();
        newGame();
    }
}

