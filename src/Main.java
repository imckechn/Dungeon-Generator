import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.Monster;
import dnd.models.Trap;
import dnd.models.Treasure;
import imckechn.Passage;
import imckechn.Door;
import imckechn.PassageSection;
import imckechn.Chamber;
import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/*
* Did not have enough time to make the extra passages link to other passages so in the mean time they're
* just set to dead ends.
* To fix this I just need to add more method calls that instead of setting it to a dead end will
* loop through the linkedhashtable and find any passage at random. Not super hard just need time to do
* it which I do not have rn. Thanks.
*
* */


/**
 * The main class that calls everthing.
 */
public final class Main {

    /**
     * This is the rand function, the whole class uses it.
     */
    private static Random rand = new Random();

    /**
     * This is called to generate all the chambers in one shot.
     * @param numChambers is the number of chambers the dungeon will have.
     * @return The array list containing every chamber.
     */
    private static ArrayList<Chamber> generateChambers(int numChambers) {
        ArrayList<Chamber> chamberList = new ArrayList<Chamber>();
        int i;

        /*The first chamber is unique in that it has to have at least 2 exits (1 the player spawns in,
        * the second is the one leading out.*/
        for (i = 0; i < numChambers; i++) {
            chamberList.add(genChamber(i));
        }

        /*First chamber needs at least two doors so repeat process until that happens*/
        if (chamberList.get(0).numDoors() == 1) {
            return generateChambers(numChambers);
        }

        return chamberList;
    }


    /**
     * This generates a individual chamber.
     * @param chamberNum the exit number (0 makes it the first, anything else doesn't matter.
     * @return the chamber object fully created.
     */
    private static Chamber genChamber(int chamberNum) {
        Chamber chamber1 = new Chamber();

        /*This calls methods to generate each part of the chamber*/
        chamber1.setShape(generateShape(chamberNum));
        chamber1.setContents(setInfo());
        chamber1.addMonster(genMonster());
        chamber1.addTreasure(genTreasure());
        chamber1.addTrap(genTrap());
        chamber1.declareDoors(rand.nextInt(3));

        return chamber1;
    }

    /**
     * This returns a fully made trap object.
     * @return The trap object.
     */
    private static Trap genTrap() {
        Trap theBigJuke = new Trap();

        theBigJuke.setDescription(rand.nextInt(20) + 1);

        return theBigJuke;
    }

    /**
     * Returns a fully made treasure object.
     * @return The treasure object.
     */
    private static Treasure genTreasure() {
        Treasure loot = new Treasure();

        loot.setDescription(rand.nextInt(100) + 1);

        loot.setContainer(rand.nextInt(20) + 1);

        return loot;
    }

    /**
     * This generates a fully created monster object.
     * @return The monster object.
     */
    private static Monster genMonster() {
        Monster beast = new Monster();

        beast.setType(rand.nextInt(100) + 1);

        return beast;
    }

    /**
     * Generates the description of the chamber.
     * @return the chamberContents object fully declared.
     */
    private static ChamberContents setInfo() {
        ChamberContents chamberInfo = new ChamberContents();

        chamberInfo.setDescription(rand.nextInt(20) + 1);

        return chamberInfo;
    }

    /**
     * generates the Shape of the chamber being generated.
     * @param roomNum The roomnumber in sequence. Only number that matters is if its room 0.
     * @return the generated chamberShape.
     */
    private static ChamberShape generateShape(int roomNum) {
        ChamberShape room = generateExits(roomNum);

        room.setShape(rand.nextInt(20) + 1);
        return room;
    }

    /**
     * Sets the number of exits to random unless it's the first room in which case it needs atleast 2.
     * @param roomNum the chamber number.
     * @return the chamberShape, only has numExits decided thought.
     */
    private static ChamberShape generateExits(int roomNum) {
        ChamberShape room = new ChamberShape();

        do {
            room.setNumExits(rand.nextInt(20) + 1);
        } while (roomNum == 0 && room.getNumExits() > 1);

        return room;
    }

    /**
     * This generates all the passages for a set of chambers given.
     * @param chamberList The arrayList of all generated chambers.
     * @return The Linkedhashmap of all the passages, passages dont have passageSections tho, just connecting them to chambers.
     */
    private static LinkedHashMap<Integer, Passage> generatePassages(ArrayList<Chamber> chamberList) {
        LinkedHashMap<Integer, Passage> passageList = new LinkedHashMap<Integer, Passage>();

        generateStartingPassage(passageList, chamberList);
        generateAllPassages(passageList, chamberList);  /*This generates all chambers except first one*/

        return passageList;
    }

    /**
     * This loops through the chamber list and calls generate path on each chamber which builds all the connections for that chamber.
     * @param passageList is the list of passages that is going to get populated soon.
     * @param chamberList Is the list of chambers that will have their connections made.
     */
    private static void generateAllPassages(LinkedHashMap<Integer, Passage> passageList, ArrayList<Chamber> chamberList) {
        int listLength;
        int i;

        listLength = chamberList.size();

        /*Loops through the chamber list making connections for each chamber*/
        for (i = 0; i < listLength; i++) {
            generateConnection(i, chamberList.get(i), chamberList, passageList);
        }
    }

    /**
     * This generates a connection between to chambers.
     * @param chamberNum is the chamber in the arrList that cannot be picked again/
     * @param room Is the chamber the must be linked.
     * @param chamberList is all the chambers created.
     * @param passageList is all the passages that will be declared.
     */
    private static void generateConnection(int chamberNum, Chamber room, ArrayList<Chamber> chamberList,  LinkedHashMap<Integer, Passage> passageList) {
        int numConnectionsForChamber;
        int i;

        numConnectionsForChamber = room.numDoors();

        for (i = 0; i < numConnectionsForChamber; i++) {

            /*This will need to build a passage, connect it to a chamber that's not numbered i and has an open slot and link the two of them*/
            Passage passageWay = connect(chamberList, chamberNum, room, passageList);
            if (chamberNum == 0 && i == 0) {
                passageWay.connects(-1, 0);
            } /*Sets the starting passage to not link a starting chamber that doesn't exist*/

            /*This puts the passageway in*/
            passageList.put((chamberNum * 6) + i, passageWay);
        }
    }

    /**
     * This searches for another open chamber to call smaller methods on.
     * @param chamberList is the list of all chambers to be searched through.
     * @param chamberNum Is slot where the current chamber is kept so it's not linked to itself.
     * @param room  is the current chamber being linked.
     * @param passageList is the list of all passages that can be updated when a new passage is made.
     * @return The passage that links to the two chamebers.
     */
    private static Passage connect(ArrayList<Chamber> chamberList, int chamberNum, Chamber room,  LinkedHashMap<Integer, Passage> passageList) {
        int openChamberNum;

        openChamberNum = findChamber(chamberList, chamberNum);
        Passage passageWay;

        /*If deadEnd essentially*/
        if (openChamberNum == -1) {
            passageWay = genPassage(true);
            linkPSGandCHBR(passageWay, room);
            passageWay.connects(chamberNum, -1);

        } else {
            Chamber chamberToBeLinked = chamberList.get(openChamberNum);
            passageWay = linkChambers(room, chamberToBeLinked);
            passageWay.connects(chamberNum, openChamberNum);
            tellChamberItHasBeenLinked(passageList, chamberList, openChamberNum, passageWay);
        }

        return passageWay;
    }

    /**
     * This just links a door to one passage and one chamber because it's a deadend.
     * @param psg The passage.
     * @param chmbr the chamber.
     */
    private static void linkPSGandCHBR(Passage psg, Chamber chmbr) {
        Door connecting = new Door();
        connecting.setSpaces(chmbr, psg);
    }

    /**
     * This finds a chamber that can be linked with. If none are found it will indicate so.
     * @param chamberList Is all the chambers availible.
     * @param chamberNum Is the chamber that cannot be selected.
     * @return is the chamber number that can be picked.
     */
    private static int findChamber(ArrayList<Chamber> chamberList, int chamberNum) {
        int i;

        /*This loops through the list and finds any avaiblible chambers that can be linked with.*/
        for (i = 0; i < chamberList.size(); i++) {
            if (i != chamberNum) {

                if (chamberList.get(i).getConnectionCount() > 0) {
                    chamberList.get(i).updateConnectionCount(1);
                    return i;
                }
            }
        }

        return -1;
    }

    /**
     * When to chambers to be linked are found this runs and connects them.
     * @param room the first chamber.
     * @param linkee The second chamber.
     * @return the passage linking the two.
     */
    private static Passage linkChambers(Chamber room, Chamber linkee) {
        Passage passageWay = genPassage(false);
        Door d1 = new Door();
        Door d2 = new Door();

        d1.setSpaces(room, passageWay);
        d2.setSpaces(room, passageWay);

        passageWay.setDoor(d1);
        passageWay.setDoor(d2);

        return passageWay;
    }


    /**
     * This generates the very first passge that the troop spawns in. cant be a dead end.
     * @param passageList is added so the passage can be tracked.
     * @param chamberList is all the chambers in the dungeon.
     */
    private static void generateStartingPassage(LinkedHashMap<Integer, Passage> passageList, ArrayList<Chamber> chamberList) {
        Passage p = genPassage(false);   //generates a passage, false means not a dead end.

        Door d = genDoor();

        d.setOneSpace(chamberList.get(0));  //Links the door to the passage and the first chamber
        p.setDoor(d);

        passageList.put(0, p);  //Puts the passage in the linkedHaskMap
    }

    /**
     * This is a quick door generator that just generates if it's a regular door or a archway. Alawyas open.
     * @return The door that was generated.
     */
    private static Door genDoor() {
        Door d = new Door();
        int archwayOdds;

        archwayOdds = rand.nextInt(1);

        if (archwayOdds == 0) {
            d.setArchway(true);
        } else {
            /*Setting it to a regular door but force it to be open.*/
            d.setUpDoorNoArchway(2);
        }

        return d;

    }

    /**
     * Generates a complete passage.
     * @param isDeadEnd if false means it cannot be a head end. If true means it has to be a head end.
     * @return The passage.
     */
    private static Passage genPassage(boolean isDeadEnd) {

        Passage passageWay = generateDeadEnd(isDeadEnd);

        //Fills the passageSection list with doors for the ones that need doors.
        populateWithDoors(passageWay);
        return passageWay;
    }

    /**
     * This populates the passageWay's arraylist full of passageSections with doors.
     * @param passageWay The passage who's getting everything filled.
     */
    private static void populateWithDoors(Passage passageWay) {
        ArrayList<PassageSection> psList = passageWay.getPsList();
        int  i;

        /*Loops through the entire list*/
        for (i = 0; i < psList.size(); i++) {
            /*If the section being checked has a door*/
            if (psList.get(i).hasDoor()) {

                /*If its the final door, it must be open, otherwise can be anything*/
                if (i == psList.size() - 1) {
                    setFinalDoor(psList.get(i));
                } else {
                    setRandomDoor(psList.get(i));
                }
            }
        }
    }

    /**
     * This creates a door for a passage section that will always be open because if it's at the end it must link to a chamber.
     * @param pSection The passage section getting it added to.
     */
    private static void setFinalDoor(PassageSection pSection) {

        Door d = new Door();
        d.setArchway(true);
        pSection.addDoor(d);
    }

    /**
     * This creates a completely random door that is being added to a passageSection.
     * @param pSection The passage section getting it added to.
     */
    private static void setRandomDoor(PassageSection pSection) {
        Door d = new Door();

        d.setUpDoorNoArchway(rand.nextInt(20) + 1);

        if (rand.nextInt(1) == 0) {
            d.setTrapped(true, rand.nextInt(20) + 1);
        } else {
            d.setTrapped(false);
        }

        pSection.addDoor(d);
    }


    /**
     * This generates a dead end passage, loops creating a dead end every time.
     * @return The passage, everything is done except connecting it with doors to chambers.
     * @param isDeadEnd is if this is a dead end or not.
     */
    private static Passage generateDeadEnd(boolean isDeadEnd) {
        Passage passageWay = new Passage();
        String desc;
        int i;

        //This loops until a deadEnd is made.
        for (i = 0; i < 12; i++) {

            desc = getDesc(isDeadEnd, i);
            PassageSection passageWaySection = new PassageSection(desc);
            passageWay.addPassageSection(passageWaySection);

            if (endOfPassage(passageWaySection)) {
                break;
            }
        }

        return passageWay;
    }

    /**
     * This generates a description for a passage section.
     * @param isDeadEnd boolean saying if it's a deadend. If it is it cannot end in a doorway.
     * @param count is like roll var essentially.
     * @return the string desc.
     */
    private static String getDesc(boolean isDeadEnd, int count) {
        int roll;
        roll = rand.nextInt(20) + 1;

        //Prevents the sequence of passage sections from being greater than 10;
        if (count > 10 && isDeadEnd) {
            return "Dead End";
        } else if (count > 10) {
            return "passage ends in archway (door) to chamber";
        }

        if (roll <= 11) {
            return getDescLessThan(roll, isDeadEnd);
        } else {
            return getDescGreaterThan(roll, isDeadEnd);
        }
    }

    /**
     * helper function for method 'getDesc', for rolls < 12.
     * @param roll The roll.
     * @param isDeadEnd if it's a deadend.
     * @return the string desc.
     */
    private static String getDescLessThan(int roll, boolean isDeadEnd) {
        if (roll < 3) {
            return "passage goes straight for 10 ft";
        } else if (roll < 6 && !isDeadEnd) {
            return "passage ends in Door to a Chamber";
        } else if (roll < 8 && !isDeadEnd) {
            return "archway (door) to right (main passage continues straight for 10 ft)";
        } else if (roll < 10 && !isDeadEnd) {
            return "archway (door) to left (main passage continues straight for 10 ft)";
        } else {
            return "passage turns to left and continues for 10 ft";
        }
    }

    /**
     * helper function for method 'getDesc', for rolls < 12.
     * @param roll The roll.
     * @param isDeadEnd if it's a deadend.
     * @return the string desc.
     */
    private static String getDescGreaterThan(int roll, boolean isDeadEnd) {
        if (roll < 14) {
            return "passage turns to right and continues for 10 ft";
        } else if (roll < 17 && !isDeadEnd) {
            return "passage ends in archway (door) to chamber";
        } else if (roll == 17) {
            return "Stairs, (passage continues straight for 10 ft)";
        } else if (roll < 20) {
            return "Dead End";
        } else {
            return "Wandering Monster (passage continues straight for 10 ft)";
        }
    }

    /*---------------------------------S---------------------------------S add oto the excel doc---------------------------------S/---------------------------------S*/

    /**
     * This prints all the chambers and all the passages for each chamber.
     * @param chamberList is all the chambers.
     * @param passageList is all the passageWays.
     */
    private static void printLevel(ArrayList<Chamber> chamberList, LinkedHashMap<Integer, Passage> passageList) {
        int i;
        printStartingPassage(passageList);

        /*Loops through the whole chamber list printing each chamber and all it passageWays in order.*/
        for (i = 0; i < chamberList.size(); i++) {

            System.out.println("---------------- Chamber " + i + " --------------------");
            chamberList.get(i).getDescription();
            printPassageWays(passageList, i, chamberList.get(i).numDoors());

        }
    }

    /**
     * This prints the passage the first passage that the troop behins in.
     * @param passageList is the list of all the passages.
     */
    private static void printStartingPassage(LinkedHashMap<Integer, Passage> passageList) {
        System.out.println("*** The troop enters the Dungeon, they begin in a passage! ***");
        System.out.println();

        /*Always the first passage*/
        passageList.get(0).getDescription();
        System.out.println();
    }

    //passageList.put((chamberNum * 6) + i, passageWay);

    /**
     * Calls all the print methods on the passages(Not the first obviously).
     * @param passageList is the list of all the passages.
     * @param chamberNum is used as part of they hashKey.
     * @param numDoors is the number of passages to print.
     */
    private static void printPassageWays(LinkedHashMap<Integer, Passage> passageList, int chamberNum, int numDoors) {
        int i;

        System.out.println();
        if (chamberNum == 0) {
            printFirstChamberPassages(passageList, chamberNum, numDoors);
        } else {
            for (i = 0; i < numDoors; i++) {
                System.out.println("------- Passage " + i + " -------");
                passageList.get((chamberNum * 6) + i).getDescription();
            }
        }
    }

    /**
     * It cannot print the first passage for the first chameber so this is called when that happens.
     * @param passageList is the list of all the passages.
     * @param numDoors is the number of passages.
     * @param chamberNum is the number that the chamber is in the chamebr list.
     */
    private static void printFirstChamberPassages(LinkedHashMap<Integer, Passage> passageList, int chamberNum, int numDoors) {
        int i;

        for (i = 1; i < numDoors; i++) {
            System.out.println("------- Passage " + (i + 1) + " -------");
            passageList.get((chamberNum * 6) + i).getDescription();
            System.out.println();
        }
    }

    /**
     * This checks if it's the passageSection triggers the end of a passage.
     * @param pS Is the passageSection being checked.
     * @return is a boolean answering yes or nah.
     */
    private static boolean endOfPassage(PassageSection pS) {
        if (pS.getDescription().equals("passage ends in archway (door) to chamber")) {
            return true;
        } else if (pS.getDescription().equals("Dead End")) {
            return true;
        } else if (pS.getDescription().equals("passage ends in Door to a Chamber")) {
            return true;
        }

        return false;
    }

    /**
     * This fills the chamber that is not being updated right now with an updated passage.
     * @param passageList is the list of all the passages.
     * @param chamberList is all the passages.
     * @param openChamberNum is the chamber that will get the updated passage list.
     * @param passageWay is the passage that is getting added to the list.
     */
    private static void tellChamberItHasBeenLinked(LinkedHashMap<Integer, Passage> passageList, ArrayList<Chamber> chamberList, int openChamberNum, Passage passageWay) {

        passageList.put((openChamberNum * 6) + chamberList.get(openChamberNum).numDoors() - chamberList.get(openChamberNum).getConnectionCount(), passageWay);
    }


    /**
     * The main, it builds all the chambers, the passages between them and prints them.
     * @param args command line args I dont use.
     */
    public static void main(String[] args) {

        /*Generates five unique cahmbers*/
        ArrayList<Chamber> chamberList = generateChambers(5);
        LinkedHashMap<Integer, Passage> passageList = generatePassages(chamberList);

        System.out.println();
        System.out.println();

        printLevel(chamberList, passageList);
    }
}
