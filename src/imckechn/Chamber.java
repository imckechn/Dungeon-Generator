/**
 * The package that holds all the given java files.
 */
package imckechn;
import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.Exit;
import dnd.models.Monster;
import dnd.models.Trap;
import dnd.models.Treasure;
import java.util.ArrayList;
import java.util.Random;

/**
 * The chamber class.
 */
public class Chamber extends Space {

    /**
     * Holds the number of pathways that havent been connected to anything yet.
     */
    private int openConnections;

    /**
     *  holds chamber contents.
     */
     private ChamberContents myContents = new ChamberContents();

    /**
     * holds the shape.
     */
     private ChamberShape mySize = new ChamberShape();

    /**
     * holds all the treasures.
     */
     private ArrayList<Treasure> treasureArrayList = new ArrayList<Treasure>();

    /**
     * holds all the monsters.
     */
    private ArrayList<Monster> monsterArrayList = new ArrayList<Monster>();

    /**
     * holds all the doors.
     */
    private ArrayList<Door> doors = new ArrayList<Door>();

    /**
     * the random var.
     */
     private Random rand = new Random();

    /**
     * keeps track of total number of chambers.
     */
     private static int chamberCount = 0;

    /**
     * first exit.
     */
     private Door exit1 = new Door();

    /**
     * second exit.
     */
    private Door exit2 = new Door();

    /**
     * third exit.
     */
    private Door exit3 = new Door();

    /**
     * fourth exit.
     */
    private Door exit4 = new Door();

    /**
     * the first rooms shape.
     */
    private ChamberShape firstRoom = new ChamberShape();

    /**
     * info on the chamber.
     */
    private ChamberContents chamberInfo = new ChamberContents();

    /**
     * the treasure.
     */
    private Treasure loot = new Treasure();

    /**
     * the monster.
     */
    private Monster theBeast = new Monster(); /*pray you never meet the beast*/

    /**
     * the trap.
     */
    private Trap theBigJuke = new Trap();


    /**
     * When chamber is instantiated the count goes up one.
     */
    public Chamber() {
    chamberCount++;
}


    /**
     * Sets the defaults.
     * @param theShape shape of chamber.
     * @param theContents whats in the cahmber.
     */
    public Chamber(ChamberShape theShape, ChamberContents theContents) {
        chamberCount++;
        firstRoom = theShape;
        chamberInfo = theContents;

        setExitExists(theShape);


    }

    /**
     * Tells the exits if they exist or not.
     * @param theShape the shape of the room (determinate).
     */
    private void setExitExists(ChamberShape theShape) {
        exit1.doorExists(true);

         if (theShape.getNumExits() == 2) {
            exit2.doorExists(true);
        } else if (theShape.getNumExits() == 3) {
            exit2.doorExists(true);
            exit3.doorExists(true);
        } else {
            exit2.doorExists(true);
            exit3.doorExists(true);
            exit4.doorExists(true);
        }

         updateArray();
    }

    /**
     * sets the chamber shape.
     * @param theShape shape of the chamber.
     */
    public void setShape(ChamberShape theShape) {
        firstRoom = theShape;
    }

    /**
     * Sets the contents of the chamber.
     * @param theContents the contents.
     */
    public void setContents(ChamberContents theContents) {
        chamberInfo = theContents;
    }

    /**
     * This creates an arraylist to store all the door objects.
     * @return The arrayList that contains all the door objects.
     */
    public ArrayList<Door> getDoors() {

        return doors;
    }

    /**
     * Adds a monster to the chamber.
     * @param theMonster the monster obj.
     */
    public void addMonster(Monster theMonster) {
        theBeast = theMonster;
        monsterArrayList.add(theMonster);
    }

    /**
     * creates an array list that holds all the monsters in that room.
     * @return The arraylist containing all the monsters in the chamber.
     */
    public ArrayList<Monster> getMonsterList() {
        return monsterArrayList;
    }

    /**
     * This gets the treasure object and sets it equal to the treasure object that the chamber object contains.
     * @param theTreasure The treasure object to be stored.
     */
    public void addTreasure(Treasure theTreasure) {
        loot = theTreasure;
        treasureArrayList.add(theTreasure);
    }

    /**
     * returns the list of treasures.
     * @return the arrlist.
     */
    public ArrayList<Treasure> getTreasureList() {
        return treasureArrayList;
    }

    /**
     * prints the description.
     * @return the description.
     */
    @Override
    public String getDescription() {

        printChamberContents(firstRoom);

        printChamberInfo(chamberInfo);

        printTrapInfo(theBigJuke);

        printMonsterInfo(theBeast);

        printTreasureInfo(loot);

        System.out.println("Doors:");
        doesDoorExist(exit1, 1);
        doesDoorExist(exit2, 2);
        doesDoorExist(exit3, 3);
        doesDoorExist(exit4, 4);

        return chamberInfo.getDescription();
    }

    /**
     * Prints the info of the treasure.
     * @param loot the treausre.
     */
    private static void printTreasureInfo(Treasure loot) {
        System.out.println("Treasure:");
        try {

            if (loot.getProtection() == null) {
                System.out.println("Your treasure is a " + loot.getDescription() + " in a " + loot.getContainer() + " that is protected by nothing");
            } else {
                System.out.println("Your treasure is a " + loot.getDescription() + " in a " + loot.getContainer() + " that is protected by " + loot.getProtection());
            }

        } catch (Exception e) {
            System.out.println("Your treasure is a " + loot.getDescription() + " in a " + loot.getContainer() + " that is protected by nothing.");
        }

        System.out.println();
    }

    /**
     * Prints the info of the monster.
     * @param theBeast the monster.
     */
    private static void printMonsterInfo(Monster theBeast) {
        System.out.println();
        System.out.println("Monster:");
        System.out.println("You have a " + theBeast.getDescription() + " to face.");
        System.out.println("Roll and get a number between " + theBeast.getMinNum() + " and " + theBeast.getMaxNum() + " to defeat this monster.");
        System.out.println();
    }

    /**
     * Prints the info of the trap.
     * @param theBigJuke the trap.
     */
    private static void printTrapInfo(Trap theBigJuke) {
        System.out.println();
        System.out.println("Trap:");
        System.out.println("Your trap" + theBigJuke.getDescription());
    }

    /**
     * Prints the info of the chamber shape.
     * @param c the chamber shape.
     */
    private static void printChamberContents(ChamberShape c) {
        System.out.println();
        System.out.println();
        System.out.println("Chamber:");

        System.out.println("The chamber is " + c.getShape() + " and is " + c.getArea() + " feet squared.");

        try {
            System.out.println("The room is " + c.getLength() + " feet long.");
            System.out.println("The room is " + c.getWidth() + " feed wide.");
        } catch (Exception e) {
            System.out.println("The room is of unusual shape");
        }
    }

    /**
     * Prints the info of the chamber contents.
     * @param c the chamber contents.
     */
    private static void printChamberInfo(ChamberContents c) {
        System.out.println();
        System.out.println("Chamber contents:");

        System.out.println(c.getDescription());
    }

    /**
     * Prints the door if it exists.
     * @param d the door.
     * @param num the door number in the sequence.
     */
    private static void doesDoorExist(Door d, int num) {
        if (d.doorExists()) {
            System.out.println("Exit " + num + " exists");
        }
    }


    /**
     * Connects the chamber ot the door.
     * @param newDoor the door that leads to the chamber.
     */
    @Override
    public void setDoor(Door newDoor) {
        doors.add(newDoor);
    }

    /**
     * This method gets the trap generated in main and attaches it to the object.
     * @param userTrap The trap that has been generated in the main method.
     */
    public void addTrap(Trap userTrap) {
        theBigJuke = userTrap;
    }

    /**
     * generates all the paths stemming from the chamber.
     */
    public void generatePaths() {

        genPath(exit1);
        genPath(exit2);
        genPath(exit3);
        genPath(exit4);
    }

    /**
     * This does the actual generation for generatePath method.
     * @param d is the door getting it's atributes set up.
     */
    private void genPath(Door d) {
        boolean goToNextPath;

        if (d.doorExists()) {
            Exit theExit = new Exit();
            /*Setting up if the physical doorway (arch, door, open, locked*/
            setExit(d);

            /*Setting up the direction it goes in*/
            d.door(theExit);

            /*Setting the doors trap*/
            d.setTrapped(true, rand.nextInt(20) + 1);

            goToNextPath = d.generateAPath();
        }
    }

    /**
     * Adds generates the actual door stats and what not.
     * @param d is the door beig checked if it's an archway or not.
     */
    private void setExit(Door d) {
        int randomNumber;

        randomNumber = rand.nextInt(20) + 1;

        if (randomNumber < 16) {
            d.setUpDoorNoArchway(randomNumber);
        } else {
            d.setArchway(true);
        }
    }

    /**
     * This gets the dice roll for the room area and uses that to find the number of doors, equal chance of any number of exits.
     * @param roll the dice roll that decideds the room size.
     */
    public void declareDoors(int roll) {
        exit1.doorExists(true); /*Always at least 1 exit*/
        exit2.doorExists(false);
        exit3.doorExists(false);
        exit4.doorExists(false);

        roll = (roll % 4);

        setDoorStates(roll);
    }

    /**
     * Helper function for declareDoors that does the actual setting of the doors.
     * @param roll the roll which determins which doors exist.
     */
    private void setDoorStates(int roll) {
        if (roll == 1) {
            exit2.doorExists(true);
        } else if (roll == 2) {
            exit2.doorExists(true);
            exit3.doorExists(true);

        } else if (roll == 3) {
            exit2.doorExists(true);
            exit3.doorExists(true);
            exit4.doorExists(true);
        }

        setConnectionCount(roll);
    }

    /**
     * This sets the number of availible connections to the number of doors.
     * @param roll is the roll that chooses how many doors exist.
     */
    private void setConnectionCount(int roll) {
        openConnections = roll++;
    }

    /**
     * Returns the openConnections amount.
     * @return the var openConnections.
     */
    public int getConnectionCount() {
        return openConnections;
    }

    /**
     * This prints the contents of the doors and the paths beyond them by calling them.
     */
    public void doorInfoAndPath() {

        System.out.println();
        System.out.println();
        System.out.println();

        printDoorInfo(exit1, 1);
        printDoorInfo(exit2, 2);
        printDoorInfo(exit3, 3);
        printDoorInfo(exit4, 4);
}

    /**
     * Prints the information on a specific door.
     * @param d the door whomst info is being printed.
     * @param num that doors number in the sequence.
     */
    private static void printDoorInfo(Door d, int num) {
        System.out.println("Door " + num + ":");
        d.printInfo();
        System.out.println();
    }

    /**
     * returns the total num of chambers.
     * @return teh total chamber count.
     */
    public int chamberCount() {
        return chamberCount;
    }

    /**
     * Returns the num door count.
     * @return teh number of doors attached to the chamber.
     */
    public int numDoors() {
        int counter;
        counter = 0;

        counter += checkDoorExists(exit1);
        counter += checkDoorExists(exit2);
        counter += checkDoorExists(exit3);
        counter += checkDoorExists(exit4);

        return counter;
    }

    /**
     * This checks if the door exists.
     * @param d The door being checked.
     * @return 1 if it exists, 0 if not.
     */
    private static int checkDoorExists(Door d) {
        if (d.doorExists()) {
            return 1;
        }

        return 0;
    }

    /**
     * This updates the amount of openConnections availible.
     * @param amountToBeSub is the amount taken of the openConnection amount.
     */
    public void updateConnectionCount(int amountToBeSub) {
        openConnections -= amountToBeSub;
    }

    /**
     * This returns the shape.
     */
    public ChamberContents getShape() {
        return chamberInfo;
    }

    /**
     * This updates the doors arrray.
     */
    private void updateArray() {
        doors.add(exit1);
        if (firstRoom.getNumExits() == 2){
            doors.add(exit2);
        }
        if (firstRoom.getNumExits() == 3){
            doors.add(exit3);
        }
        if (firstRoom.getNumExits() == 4){
            doors.add(exit4);
        }
    }
}
