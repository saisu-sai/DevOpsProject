import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
enum GameState {
    VILLAGE,
    FOREST,
    COMBAT,
    QUIT
}

class Game {

    public static void initialize(Entity zaratul, Player user) {
//        zaratul.speak("Hello there! You're the new adventurere, " + user.name + " from the village, right?");
  //      zaratul.speak(
//                "I am Zaratul, the village chief. I heard your the last adventurer of the village, and now your leaving too. It saddens me.");
//        zaratul.speak("I hope you can do me and the village one last favor, before going on with your journey.");
//        zaratul.speak(
//                "Recently, some bandits have appeared in the grasslands. If all adventurers go out of the village, then it will be unprotected! I hope you can go exterminate them for good. ");
//        zaratul.speak("I will give you 10 bronze for it. Consider it your first quest on the path to glory.");
    }

    //public static void main(String[] args) {
        // System.out.println(args.length);
   //     PrintTextOnScreen p = new PrintTextOnScreen();
   //     System.out.print("Please enter your name: ");
   //     String userName = p.takeInput();

     //   Player user = new Player(userName, p);

     //   Entity zaratul = new Entity("zaratul", p);

  //      initialize(zaratul, user);
 //       MapControl mc = new MapControl(p, user);
  //      mc.start();
	System.out.println("Hello");
        
    }

}
class PrintTextOnScreen {
    Scanner sr = new Scanner(System.in);
    int speed = 2;
    String takeInput() {
        return sr.nextLine();
    }

    char showOptions(String a, String b, String c, String d, String f) {
        char result;
        printText("=================================================");
        printText(a);
        printText(b);
        printText(c);
        printText(d);
        printText(f);
        result = sr.next().charAt(0);
        printText("\n\nYou have choseen: "+ result);
        return result;
        // if (result != 'i') {
        //     System.out.println("Will have to do something here...");
        //     ;
        // }
    }

    char showOptions(String a[]){
        char result;
        for(int i = 0; i < a.length; i++){
            printText(a[i]);
        }
        result = sr.next().charAt(0);
        return result;
    }

    public void printText(String text){
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            try {
                // Sleep for 2 seconds (2000 milliseconds)
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                // Handle exception if the thread is interrupted during sleep
                e.printStackTrace();
            }
        }
        System.out.println("");
        
    }
    @SuppressWarnings("unused")
    public void printText(String text, int speed){
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            try {
                // Sleep for 2 seconds (2000 milliseconds)
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                // Handle exception if the thread is interrupted during sleep
                e.printStackTrace();
            }
        }
        System.out.println("");
    }
    void speech(String speaker, String text) {
        System.out.print("[" + speaker + "] : ");
        printText(text, 15);
        sr.nextLine();
        // instead of creating a speech object, what if every speakable entity will
        // have speak method, that can be called? that is much simpler? or unnecessary?
    }

   
}

// class Shop{
//     private Map<String, Integer> shopItems;
//     //Add shop items and their prices. 
// }

class Entity {
    String name;
    PrintTextOnScreen printer;

    Entity(String name, PrintTextOnScreen printer) {
        this.name = name;
        this.printer = printer;
    }

    void speak(String speech) {
        printer.speech(name, speech);
    }
}

class Player extends Entity {
    private Map<String, Integer> inventory = new HashMap<>();
    int health = 100;
    int defense = 2;
    int attack = 2;

    public Player(String name, PrintTextOnScreen printer) {
        super(name, printer);
        this.inventory = new HashMap<>();
        addItem("Bronze coins", 2);
        addItem("Old wooden sword", 1);
        addItem("Old Boots", 1);
    }

    public void addItem(String item, int quantity) {
        inventory.put(item, inventory.getOrDefault(item, 0) + quantity);
    }

    public void removeItem(String item, int quantity) {
        if (inventory.containsKey(item)) {
            int currentQuantity = inventory.get(item);
            if (currentQuantity <= quantity) {
                inventory.remove(item);
            } else {
                inventory.put(item, currentQuantity - quantity);
            }
        }
    }

    public void showStats(){
        printer.printText("=================================================");
        printer.printText("Health: " + health);
        printer.printText("Attack: " + attack);
        printer.printText("Defense: " + defense);
        printer.printText("=================================================");

    }

    public void showInventory() {
        printer.printText("=================================================");
        printer.printText("Inventory: ");
        for (String key : inventory.keySet()) {
            printer.printText(key + " : " + inventory.get(key));
        }
        printer.printText("=================================================");
    }

}

class MapControl {
    private PrintTextOnScreen printer;
    private Player user;
    private GameState currentState;

    MapControl(PrintTextOnScreen printer, Player user) {
        this.printer = printer;
        this.user = user;
        this.currentState = GameState.VILLAGE; // Initial state
    }

    public void start() {
        boolean running = true;
        while (running) {
            switch (currentState) {
                case VILLAGE:
                    handleVillage();
                    break;
                case FOREST:
                    handleForest();
                    break;
                case COMBAT:
                    handleCombat();
                    break;
                case QUIT:
                    running = false;
                    break;
            }
        }
    }

    private void handleVillage() {
        char choice = printer.showOptions(new String[]{
            "i. Show Inventory",
            "s. Check stats",
            "f. Go to Forest",
            "q. Quit Game"
        });

        switch (choice) {
            case 'i':
                user.showInventory();
                break;
            case 's':
                user.showStats();
                break;
            case 'f':
                currentState = GameState.FOREST; // Transition to forest
                break;
            case 'q':
                confirmQuit();
                break;
            default:
                printer.printText("Please choose a valid option.");
                break;
        }
    }

    private void handleForest() {
        char choice = printer.showOptions(new String[]{
            "e. Explore and Fight Monsters",
            "v. Go back to Village",
            "d. Go deeper into forest",
            "q. Quit Game"
        });

        switch (choice) {
            case 'e':
                // Handle combat or exploration
                currentState = GameState.COMBAT;
                break;
            case 'v':
                currentState = GameState.VILLAGE; // Go back to village
                break;
            case 'd':
                System.out.println("You can't do that currently. Please try again later.");
                break;
            case 'q':
                confirmQuit();
                break;
            default:
                printer.printText("Please choose a valid option.");
                break;
        }
    }

    private void handleCombat() {
        printer.printText("You encounter a wild monster! Fight or run?");
        char choice = printer.showOptions(new String[]{
            "f. Fight",
            "r. Run to Forest"
        });

        switch (choice) {
            case 'f':
                // Combat logic (simplified here)
                printer.printText("You fight bravely and win!");
                currentState = GameState.FOREST; // Return to forest
                break;
            case 'r':
                currentState = GameState.FOREST; // Escape to forest
                break;
            default:
                printer.printText("Please choose a valid option.");
                break;
        }
    }

    private void confirmQuit() {
        char result = printer.showOptions(new String[]{
            "Are you sure you want to quit? (y/n)"
        });
        if (result == 'y') {
            currentState = GameState.QUIT;
        }
    }
}


// class MapControl {
//     PrintTextOnScreen printer;  
//     Player user;

//     MapControl(PrintTextOnScreen printer, Player user){
//         this.printer = printer;
//         this.user = user;
//     }

//     void inventoryControl(){
//         user.showInventory();
        
//     }

//     void villageControl(){
//         boolean quit = false;
//         char result;
//         String options[] = {"i. Show Inventory", "a. Visit Shop","s. Check stats", "c. Continue", "q. Quit"};
//         while(!quit){
//             System.out.println("What do you want to do?");
//             result = printer.showOptions(options);
//             if(result != 'i' && result != 'a' && result != 'c' && result != 'q' && result != 's'){
//                 System.out.println("Please choose a valid option.");
//                 continue;
//             }
//             else if(result == 'i'){
//                 inventoryControl();
//             }
//             else if(result == 's'){
//                 user.showStats();
//             }
//             else if(result == 'q'){
//                 while(4<5){
//                     result = printer.showOptions(new String[]{"Quitting the game doesn't save the progress. Are you sure you want to quit?(y/n)"});
//                     if(result == 'y'){
//                         System.exit(0);
//                     }
//                     else if(result != 'n'){
//                         System.out.println("please choose a valid option");

//                     }
//                     else{
//                         break;
//                     }
//                 }   
//             }
//         }
//     }
// }

