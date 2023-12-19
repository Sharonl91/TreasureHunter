/**
 * The Town Class is where it all happens.
 * The Town is designed to manage all of the things a Hunter can do in town.
 */
import java.util.Random;
public class Town
{
    //instance variables
    private Hunter hunter;
    private Shop shop;
    private Terrain terrain;
    private String printMessage;
    private boolean toughTown;
    private String treasureOne = "Dragon Skull";
    private String treasureTwo = "Magic Wand";
    private String treasureThree = "Egyptian Sword";
    private int amountOfTreasures = 0;
    private boolean t1, t2, t3;
    private boolean t1Found, t2Found, t3Found;

    //Constructor
    /**
     * The Town Constructor takes in a shop and the surrounding terrain, but leaves the hunter as null until one arrives.
     * @param s The town's shoppe.
     * @param t The surrounding terrain.
     */
    public Town(Shop shop, double toughness)
    {
        this.shop = shop;
        this.terrain = getNewTerrain();

        // the hunter gets set using the hunterArrives method, which
        // gets called from a client class
        hunter = null;

        printMessage = "";

        // higher toughness = more likely to be a tough town
        toughTown = (Math.random() < toughness);
    }
    public String getLatestNews()
    {
        return printMessage;
    }

    /**
     * Assigns an object to the Hunter in town.
     * @param h The arriving Hunter.
     */
    public void hunterArrives(Hunter hunter)
    {
        this.hunter = hunter;
        printMessage = "Welcome to town, " + hunter.getHunterName() + ".";

        if (toughTown)
        {
            printMessage += "\nIt's pretty rough around here, so watch yourself.";
        }
        else
        {
            printMessage += "\nWe're just a sleepy little town with mild mannered folk.";
        }
        Random treasureGen = new Random();
        int newTreasure = treasureGen.nextInt((amountOfTreasures - 1)) + 1;
        if (newTreasure == 1) {
            if (!t1Found) {
                amountOfTreasures++;
                t1Found = true;
            }
            if (newTreasure == 2) {
                if (!t2Found) {
                    amountOfTreasures++;
                    t2Found = true;
                }
            }
            if (newTreasure == 3) {
                if (!t3Found) {
                    amountOfTreasures++;
                    t3Found = true;
                }
            }
        }


    }

    /**
     * Handles the action of the Hunter leaving the town.
     * @return true if the Hunter was able to leave town.
     */
    public boolean leaveTown()
    {
        boolean canLeaveTown = terrain.canCrossTerrain(hunter);
        if (canLeaveTown)
        {
            String item = terrain.getNeededItem();
            printMessage = "You used your " + item + " to cross the " + terrain.getTerrainName() + ".";
            if (checkItemBreak())
            {
                hunter.removeItemFromKit(item);
                printMessage += "\nUnfortunately, your " + item + " broke.";
            }

            return true;
        }

        printMessage = "You can't leave town, " + hunter.getHunterName() + ". You don't have a " + terrain.getNeededItem() + ".";
        return false;
    }

    public void enterShop(String choice)
    {
        shop.enter(hunter, choice);
    }

    /**
     * Gives the hunter a chance to fight for some gold.<p>
     * The chances of finding a fight and winning the gold are based on the toughness of the town.<p>
     * The tougher the town, the easier it is to find a fight, and the harder it is to win one.
     */
    public void lookForTrouble()
    {
        double noTroubleChance;
        if (toughTown)
        {
            noTroubleChance = 0.66;
        }
        else
        {
            noTroubleChance = 0.33;
        }

        if (Math.random() > noTroubleChance)
        {
            printMessage = "You couldn't find any trouble";
        }
        else
        {
            printMessage = "You want trouble, stranger!  You got it!\nOof! Umph! Ow!\n";
            int goldDiff = (int)(Math.random() * 10) + 1;
            if (Math.random() > noTroubleChance)
            {
                printMessage += "Okay, stranger! You proved yer mettle. Here, take my gold.";
                printMessage += "\nYou won the brawl and receive " +  goldDiff + " gold.";
                hunter.changeGold(goldDiff);
            }
            else
            {
                printMessage += "That'll teach you to go lookin' fer trouble in MY town! Now pay up!";
                printMessage += "\nYou lost the brawl and pay " +  goldDiff + " gold.";
                hunter.changeGold(-1 * goldDiff);
            }
        }
    }

    public String toString()
    {
        return "This nice little town is surrounded by " + terrain.getTerrainName() + ".";
    }

    /**
     * Determines the surrounding terrain for a town, and the item needed in order to cross that terrain.
     *
     * @return A Terrain object.
     */
    public void findTheTreasure(){
        Random rand = new Random();
        int randomNum = rand.nextInt((amountOfTreasures - 1))+ 1;
        if (randomNum == 4) {
            if (t1 = true & t1Found == true) {
                System.out.println("You have found a duplicate! Discard it");
            } else {
                System.out.println("You have found the " + treasureOne + "!");
                t1 = true;
            }
        }
        if (randomNum == 2){
            if (t2 = true & t2Found == true) {
                System.out.println("You have found a duplicate! Discard it");
            }
            else {
                System.out.println("You have found the " + treasureTwo + "!");
                t2 = true;
            }
        }
        if (randomNum == 3){
            if (t3 = true & t3Found == true) {
                System.out.println("You have found a duplicate! Discard it");
            }
            else {
                System.out.println("You have found the " + treasureThree + "!");
                t3 = true;
            }
        }
        if (randomNum == 1){
            System.out.println("You have found nothing! Try again");
        }
        if (t1 & t2 & t3){
            System.out.println("You have collected all the treasures! Congratulations!");
            System.exit(0);
        }
    }
    private Terrain getNewTerrain()
    {
        double rnd = Math.random();
        if (rnd < .2)
        {
            return new Terrain("Mountains", "Rope");
        }
        else if (rnd < .4)
        {
            return new Terrain("Ocean", "Boat");
        }
        else if (rnd < .6)
        {
            return new Terrain("Plains", "Horse");
        }
        else if (rnd < .8)
        {
            return new Terrain("Desert", "Water");
        }
        else
        {
            return new Terrain("Jungle", "Machete");
        }
    }

    /**
     * Determines whether or not a used item has broken.
     * @return true if the item broke.
     */
    private boolean checkItemBreak()
    {
        double rand = Math.random();
        return (rand < 0.5);
    }

}