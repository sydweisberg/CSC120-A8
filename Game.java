import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner; // Import statements

public class Game implements Contract {

    static ArrayList<Item> userItems = new ArrayList<Item>(); // ArrayList representing a user's inventory
    static Boolean dragonDefeated = false;
    static Scanner input = new Scanner(System.in); // Creates a new scanner
    static Boolean itemHeld = false;
    static int sideCoordinate = 0;  // Default starting values
    static int upCoordinate = 0;
    static int userHealth = 30;
    static int dragonHealth = 70;
    static int indexItemEquipped = 0;
    static Item droppedItem;

    public static final String RESET = "\033[0m";
    public static final String WHITE_BACKGROUND = "\033[47m";
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m";
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // Different ANSI colors that can be used in the terminal

    /**
     * Default Constructor
     */
    public Game() {

    }

    /**
     * Selects a random item from a list and creates an object (Item) based off of the item and adds the item to the user's inventory
     */
    public void obtain() {
            String[] itemList = {"Sword", "Polearm", "Claymore", "Bow", "Potion", "Ultra Potion", "Trash"};
            String[] itemDescription = {"An object with a blade and a hilt.", "A long staff with a pointed end.", "A large, bulky object with a blade and hilt.", "A curved weapon joined by a string.", "A consumable that heals.", "A consumable that heals extra.", "Nothing."};
            Random random = new Random();
            int itemKey = random.nextInt(itemList.length - 0) + 0; // Randomly selects a number that is used to determine the item and item's description
            String item = itemList[itemKey];
            String descriptor = itemDescription[itemKey];
            Item newItem; // Creates a new Item
            if(item.equals("Sword")) {
                newItem = new Item(item, random.nextInt(10 - 5) + 5, random.nextInt(20 - 10) + 10, descriptor);
                userItems.add(newItem);
            }
            else if(item.equals("Polearm")) {
                newItem = new Item(item, random.nextInt(7 - 3) + 3, random.nextInt(25 - 15) + 15, descriptor);
                userItems.add(newItem);
            }
            else if(item.equals("Claymore")) {
                newItem = new Item(item, random.nextInt(15 - 10) + 10, random.nextInt(10 - 5) + 5, descriptor);
                userItems.add(newItem);
            }
            else if(item.equals("Bow")) {
                newItem = new Item(item, random.nextInt(8 - 5) + 5, random.nextInt(25 - 20) + 20, descriptor);
                userItems.add(newItem);
            }
            else if(item.equals("Potion")) {
                newItem = new Item(item, random.nextInt(15 - 8) + 8, descriptor);
                userItems.add(newItem);
            }
            else if(item.equals("Ultra Potion")) {
                newItem = new Item(item, random.nextInt(20 - 15) + 15, descriptor);
                userItems.add(newItem);
            }
            else {
                newItem = new Item(item, descriptor);
                userItems.add(newItem);
            }
            System.out.println("You have obtained " + WHITE_BOLD_BRIGHT + newItem.getName() + RESET + "!");
    }

    /**
    * Allows the user to currently hold an item by setting the itemHeld variable to true
    * @param item An Item object that represents an item the user has
    */
    public void grab(Item item) {
        System.out.println("The user is holding " + item.getName() + "!");
        itemHeld = true;
    }

    /**
    * Either increase' the user's health, decreases the dragon's health, or does nothing based on the item
    * Represents a user attacking the dragon or healing themself
    * @param item An Item object that represents an item the user has
    */
    public void use(Item item) {
        if((item.getName().equals("Sword") || item.getName().equals("Polearm") || item.getName().equals("Claymore") || item.getName().equals("Bow")) && item.getStrength() > 0) {
            dragonHealth -= item.getStrength();
            Random random = new Random();
            int damageDealt = random.nextInt(10 - 5) + 5;
            item.setHealth(damageDealt);
        }
        else if(item.getName().equals("Potion") || item.getName().equals("Ultra Potion") && item.getHealAmount() > 0) {
            userHealth += item.getHealAmount();
            item.setStatus(item.getHealAmount());
        }
        else {
            System.out.println("This item is not useful in battle!");
        }

    }

    /**
    * Removes an item from the user's inventory
    * @param item An Item object that represents an item the user has
    */
    public String drop(Item item) {
        System.out.println("You have dropped " + item.getName() + ".");
        return item.getName();
    }

    /**
    * Prints the item's description, strength, and health if applicable
    * @param item An Item object that represents an item the user has
    */
    public void examine(Item item) {
        System.out.println("Description: " + item.getDescription());
        if(item.getName().equals("Sword") || item.getName().equals("Polearm") || item.getName().equals("Claymore") || item.getName().equals("Bow")) {
            System.out.println("Health: " + item.getHealth());
            System.out.println("Strength: " + item.getStrength());
        }
        else if(item.getName().equals("Potion") || item.getName().equals("Ultra Potion")) {
            System.out.println("Heal Amount: " + item.getHealAmount());
        }
    }
 
    /**
    * Changes the user's location on a map
    * @param x An integer representing the "x" or vertical value on a coordinate plane
    * @param y An integer representing the "y" or horizontal value on a coordinate plane
    * @return     A boolean of whether or not the user moved spaces
    */
    public boolean fly(int x, int y) {
        System.out.println("You have moved to space (" + x + "," + y + ")!");
        lootBox();
        return true;
    }

    /**
    * Restores the users health
    */
    public void rest() {
        userHealth += 7;
    }

    /**
    * Moves the user to a different point on the coordinate grid based on the direction inputted
    * @param direction A string of either "Up", "Down", "Left", or "Right" representing directional movement
    */
    public boolean walk(String direction) {
        if(direction.toLowerCase().equals("left") && sideCoordinate < 5) {
            sideCoordinate += 1;
            System.out.println("You are at coordinate: (" + sideCoordinate + "," + upCoordinate + ")!");
            lootBox();
            return true;
        }
        else if(direction.toLowerCase().equals("right") && sideCoordinate > 0) {
            sideCoordinate -= 1;
            System.out.println("You are at coordinate: (" + sideCoordinate + "," + upCoordinate + ")!");
            lootBox();
            return true;
        }
        else if(direction.toLowerCase().equals("up") && upCoordinate < 5) {
            upCoordinate += 1;
            System.out.println("You are at coordinate: (" + sideCoordinate + "," + upCoordinate + ")!");
            lootBox();
            return true;
        }
        else if(direction.toLowerCase().equals("down") && upCoordinate > 0) {
            upCoordinate -= 1;
            System.out.println("You are at coordinate: (" + sideCoordinate + "," + upCoordinate + ")!");
            lootBox();
            return true;
        }
        else {
            System.out.println("You at the edge of the map and cannot move further in this direction.");
            System.out.println("You are at coordinate: (" + sideCoordinate + "," + upCoordinate + ")!");
            return false;
        }
    }

    /**
    * Adds the dropped Item back to the inventory if the user wants to undo their drop
    */
    public void undo() {
        System.out.println(YELLOW_BOLD_BRIGHT + "Are you sure you meant to drop the " + droppedItem.getName() + "?" + RESET + " \nType " + GREEN_BOLD_BRIGHT + "Y" + RESET + " for yes or " + RED_BOLD_BRIGHT + "N" + RESET + " for no -");
        String result = input.next();
        if(result.toLowerCase().equals("n")) {
            userItems.add(droppedItem);
            System.out.println("The dropped item was readded to your inventory!");
        }
    }

    /**
    * Decreases the dragon's health by a random amount
    */
    public Number shrink() {
        Random random = new Random();
        int healthShrunk = random.nextInt(30 - 10) + 10;
        return healthShrunk;
    }

    /**
    * Increases the dragon's health by a random amount
    */
    public Number grow() {
        Random random = new Random();
        int healthGrown = random.nextInt(40 - 10) + 10;
        return healthGrown;
    }

    /**
    * Randomly determines whether or not the user can get a loot box (2/3 chance) which contains an item and allows the user to accept or decline it
    */
    public void lootBox() {
        Random random = new Random();
        int zeroOrOne = random.nextInt(2 - 0) + 0;
        if(zeroOrOne > 0) { // FIX THIS
            System.out.println("There's a lootbox here! \n"+ YELLOW_BOLD_BRIGHT + "Would you like to pick it up?" + RESET + " \nType " + GREEN_BOLD_BRIGHT + "Y" + RESET + " for yes or " + RED_BOLD_BRIGHT + "N" + RESET + " for no -");
            String pickUp = input.next();
            if(pickUp.toLowerCase().equals("y")) {
                obtain();
            }
            else {
                System.out.println("Item was not picked up!");
            }
        }
    }

    /**
    * Either prints out a list of items and asks the user to choose an item to grab or tells the user that they do not have an item
    */
    public void selectGrabItem() {
        if(userItems.size() > 0) {
            for (int i = 0; i < userItems.size(); i++) {
                int number = i + 1;
                System.out.println(number + ". " + (userItems.get(i)).getName());      
            }
            System.out.println(YELLOW_BOLD_BRIGHT + "Which item would you like to grab? - " + RESET);
            int itemIndex = input.nextInt();
            indexItemEquipped = itemIndex - 1;
            grab(userItems.get(itemIndex - 1)); // Enables the user to "hold" and item
        }
        else {
            System.out.println("You cannot grab an item that you do not have!");
        }
    }

    /**
    * Either prints out a list of items and asks the user to choose an item to drop or tells the user that they do not have an item
    */
    public void selectDropItem() {
        if(userItems.size() > 0) {
            for(int i = 0; i < userItems.size(); i++){
                int number = i + 1;
                System.out.println(number + ". " + (userItems.get(i)).getName());
            }
            System.out.println(YELLOW_BOLD_BRIGHT + "Which item would you like to drop? Please use the item's number -" + RESET);
            int index = input.nextInt();
            droppedItem = userItems.get(index-1);
            drop(userItems.get(index-1)); // Removes the Item from the user's inventory
            userItems.remove(index-1);
            undo();
        }
        else {
            System.out.println("You cannot drop an item that you do not have!");
        }
    }

    /**
    * Either prints out a list of items and asks the user to choose an item to examine or tells the user that they do not have an item
    */
    public void selectExamineItem() {
        if(userItems.size() > 0) {
            for(int i = 0; i < userItems.size(); i++){
                int number = i + 1;
                System.out.println(number + ". " + (userItems.get(i)).getName());
            }
            System.out.println(YELLOW_BOLD_BRIGHT + "Which item would you like to examine? Please use the item's number -" + RESET);
            int index = input.nextInt(); 
            examine(userItems.get(index-1));
        }
        else {
            System.out.println("You do not currently have any items!");
        }
    }

    /**
    * Asks the user which vertical and horizontal coordinate they would like to go to
    */
    public void selectCoordinates() {
        System.out.println(YELLOW_BOLD_BRIGHT + "Where would you like to go? Enter the horizontal coordinate 0-5 -" + RESET);
        sideCoordinate = input.nextInt();
        while(sideCoordinate < 0 || sideCoordinate > 5) {
            System.out.println("Please enter a different number -");
            sideCoordinate = input.nextInt();
        }
        System.out.println(YELLOW_BOLD_BRIGHT + "Where would you like to go? Enter the vertical coordinate 0-5 -" + RESET);
        upCoordinate = input.nextInt();
        while(upCoordinate < 0 || upCoordinate > 5) {
            System.out.println("Please enter a different number - ");
            upCoordinate = input.nextInt();
        }
        fly(sideCoordinate, upCoordinate);
    }

    /**
    * Allows the user to set difficult and take inputs from the command line to fight a dragon
    */
    public void fightDragon() {
        System.out.println("The dragon's health is currently set to: " + dragonHealth);
        System.out.println(YELLOW_BOLD_BRIGHT + "Would you like to increase or decrease his health? Type Increase, Decrease, or Nothing - " + RESET);
        String userResponse = input.next();
        if(userResponse.toLowerCase().equals("increase")) {
            dragonHealth += grow().intValue();
            System.out.println("The dragon's new health is: " + dragonHealth);
        }
        else if(userResponse.toLowerCase().equals("decrease")) {
            dragonHealth -= shrink().intValue();
            System.out.println("The dragon's new health is: " + dragonHealth);
        }
        while(dragonDefeated == false) {
            System.out.println("Your Current Health: " + userHealth);
            System.out.println("The Dragon's Current Health: " + dragonHealth);
            System.out.println(WHITE_BACKGROUND + BLUE_BOLD_BRIGHT + "| Dragon Fight Commands:             |" + RESET);
            System.out.println(WHITE_BACKGROUND + BLUE_BOLD_BRIGHT + "| 1. Grab      2. Examine     3. Use |" + RESET);
            System.out.println(WHITE_BACKGROUND + BLUE_BOLD_BRIGHT + "| 4. Rest                            |" + RESET);
            System.out.println(WHITE_BACKGROUND + BLUE_BOLD_BRIGHT + "|" + RESET + WHITE_BACKGROUND  + "           Select a Number          " + BLUE_BOLD_BRIGHT + "|" + RESET);
            int action = input.nextInt();
            if(action == 1) {
                selectGrabItem();
            }
            if(action == 2) {
                selectExamineItem();
            }
            if(action == 3) {
                if(itemHeld == true) {
                    use(userItems.get(indexItemEquipped));
                }
                else {
                    dragonHealth -= 1;
                }
                if(dragonHealth <= 0) {
                    System.out.println(GREEN_BOLD_BRIGHT + "You Won!" + RESET);
                    dragonDefeated = true;
                    break;
                }
            }
            if(action == 4) {
                rest();
            }
            userHealth -= 5;
            if(userHealth <= 0) {
                System.out.println(RED_BOLD_BRIGHT + "You Died!" + RESET);
                dragonDefeated = true;
            }
        }
    }

    /**
    * Gives game instructions, prints out avaliable commands, and sends the user information based on their inputs
    */
    public void play() {
        System.out.println("Welcome to Dragon Defeater!\nThe purpose of this game is to move around the board to get items and ultimately defeat the dragon! \nTo play, enter a number that coorelates with the command.\nPlease enter all information exactly as shown!"); // Game instructions
        while(dragonDefeated == false) {
            System.out.println(WHITE_BACKGROUND + BLUE_BOLD_BRIGHT + "| Commands Avaliable:                 |" + RESET);
            System.out.println(WHITE_BACKGROUND + BLUE_BOLD_BRIGHT + "| 1. Grab      2. Drop     3. Examine |" + RESET);
            System.out.println(WHITE_BACKGROUND + BLUE_BOLD_BRIGHT + "| 4. Walk      5. Fly      6. Fight   |" + RESET);
            System.out.println(WHITE_BACKGROUND + BLUE_BOLD_BRIGHT + "|" + RESET + WHITE_BACKGROUND  + "           Select a Number           " + BLUE_BOLD_BRIGHT + "|" + RESET); // Command instructions
            System.out.println(YELLOW_BOLD_BRIGHT + "What would you like to do? - " + RESET);
            int action = input.nextInt(); // Takes the integer provided by the user and stores it
            if(action == 1) {
                selectGrabItem();
            }
            if(action == 2) {
                selectDropItem();
            }
            if(action == 3) {
                selectExamineItem();
            }
            if(action == 4) {
                System.out.println("Please enter Left, Right, Up, or Down to move!");
                String direction = input.next();
                walk(direction);
            }
            if(action == 5) {
                selectCoordinates();
            }
            if(action == 6) {
                fightDragon();
            }

        }
        System.out.println("Thanks for playing!");
        input.close();
    }
    
    /**
    * Creates a Game and initiates gameplay
    * @param args[] An empty array of Strings
    */
    public static void main(String[] args) {
        Game newGame = new Game(); 
        newGame.play();
    }
}
