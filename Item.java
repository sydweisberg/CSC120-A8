import java.util.Random;

public class Item {

    private String itemName;
    private int itemStrength;
    private int itemHealth;
    private int itemHealAmount;
    private String itemDescription;

    /* Overloaded constructor without health */
    public Item(String name, String description) {
        this.itemName = name;
        this.itemDescription = description;
    }

    /* Overloaded constructor without strength */
    public Item(String name, int healNumber, String description) {
        this.itemName = name;
        this.itemHealAmount = healNumber;
        this.itemDescription = description;
    }

    /* Full Constructor */
    public Item(String name, int strength, int health, String description) {
        this.itemName = name;
        this.itemStrength = strength;
        this.itemHealth = health;
        this.itemDescription = description;
    }

    /* Accessors */
    public String getName() {
        return this.itemName;
    }

    public String getDescription() {
        return this.itemDescription;
    }

    public int getStrength() {
        return this.itemStrength;
    }

    public int getHealth() {
        return this.itemHealth;
    }

    public int getHealAmount() {
        return this.itemHealAmount;
    }

    /* Manipulators */
    public void setHealth(int damage) {
        this.itemHealth -= damage;
    }

    public void setStatus(int damage) {
        this.itemHealAmount -= damage;
    }

    /**
    * Creates an Item based off of a random array of items
    * @param args[] An empty array of Strings
    */
    public static void main(String[] args) {
        String[] itemList = {"Sword", "Polearm", "Claymore", "Bow", "Potion", "Ultra Potion", "Trash", "Repairer"};
        String[] itemDescription = {"An object with a blade and a hilt.", "A long staff with a pointed end.", "A large, bulky object with a blade and hilt.", "A curved weapon joined by a string.", "A consumable that heals.", "A consumable that heals extra.", "Nothing.", "Allows for the repair of a weapon."};
        Random random = new Random();
        int itemKey = random.nextInt(itemList.length - 0) + 0;
        String item = itemList[itemKey];
        String descriptor = itemDescription[itemKey];
        if(item.equals("Sword")) {
            new Item(item, random.nextInt(10 - 5) + 5, random.nextInt(20 - 10) + 10, descriptor);
        }
        else if(item.equals("Polearm")) {
            new Item(item, random.nextInt(7 - 3) + 3, random.nextInt(25 - 15) + 15, descriptor);
        }
        else if(item.equals("Claymore")) {
            new Item(item, random.nextInt(15 - 10) + 10, random.nextInt(10 - 5) + 5, descriptor);
        }
        else if(item.equals("Bow")) {
            new Item(item, random.nextInt(8 - 5) + 5, random.nextInt(25 - 20) + 20, descriptor);
        }
        else if(item.equals("Potion")) {
            new Item(item, random.nextInt(10 - 5) + 5, descriptor);
        }
        else if(item.equals("Ultra Potion")) {
            new Item(item, random.nextInt(20 - 15) + 15, descriptor);
        }
        else {
            new Item(item, descriptor);
        }
    }
}
