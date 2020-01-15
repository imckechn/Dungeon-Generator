package imckechn;

import dnd.models.Monster;
import java.util.ArrayList;

/**
 * A passage begins at a door and ends at a door.  It may have many other doors along
 * the way
 *
 * You will need to keep track of which door is the "beginning" of the passage
 * so that you know how to
 */
public class Passage extends Space {
    /**
     * Is this a dead end.
     */
    private boolean hasDeadEnd = false;
    /**
     * ArrList that holds all sections.
    */
    private ArrayList<PassageSection> thePassage = new ArrayList<PassageSection>();

    /**
     * holds all doors.
     */
    private ArrayList<Door> theDoorList = new ArrayList<Door>();

    /**
     * Tells which chamber the passage connects to.
     */
    private int homeChamberNum;

    /**
     * Tells which chamber the passage connects to.
     */
    private int nextChamberNum;

    /**
     * gets arrlist of doors.
     * @return the arrlist of doors.
     */
    public ArrayList<Door> getDoors() {

        theDoorList = new ArrayList<Door>();
        Door doorFromPassage = new Door();

        for (int i = 0; i < thePassage.size(); i++) {
            doorFromPassage = thePassage.get(i).getDoor();

            if (doorFromPassage != null) {
                theDoorList.add(doorFromPassage);
            }
        }

        return theDoorList;
    }

    /**
     * gets the door at spot i in the arrList.
     * @param i is the index.
     * @return the door object.
     */
    public Door getDoor(int i) {
    //returns the door in section 'i'. If there is no door, returns null
    return thePassage.get(i).getDoor();
}

    /**
     * Adds a monsters to a psg section.
     * @param theMonster is the monster being added.
     * @param i at this index.
     */
    public void addMonster(Monster theMonster, int i) {

        // adds a monster to section 'i' of the passage
        if (i > 0) {
            thePassage.get(i - 1).addMonster(theMonster);
        } else if (i == 0) {
            thePassage.get(i).addMonster(theMonster);
        }
    }

    /**
     * gets a monster from a psg section at index i.
     * @param i the index in the arrlist.
     * @return the monster object.
     */
    public Monster getMonster(int i) {
    Monster m;

    if (i > 0) {
        m = (thePassage.get(i - 1)).getMonster();
    } else if (i == 0) {
        m = (thePassage.get(i)).getMonster();
    } else {
        m = new Monster();
    }

    return m;
}

    /**
     * adds a psg section to the arrlist.
     * @param toAdd the psg section being added.
     */
    public void addPassageSection(PassageSection toAdd) {
        hasDeadEnd = false;

        if (toAdd.getDescription().equals("Dead End") || hasDeadEnd) {
            hasDeadEnd = true;
        }
        thePassage.add(toAdd);
    }

    /**
     * add a door connection to the current Passage Section. I assmued
     * the door was being attached to the last passage section created because
     * they can have doors to.
     * @param newDoor teh door obj.
     */
    @Override
    public void setDoor(Door newDoor) {

        theDoorList.add(newDoor);
    }

    /**
     * gets the desc of the psg.
     * @return the string description of the psg.
     */
    @Override
    public String getDescription() {
        int i;

        printWelcomeWagon();
        for (i = 0; i < thePassage.size(); i++) {

            System.out.println("The " + thePassage.get(i).getDescription());

            if ((thePassage.get(i).getDescription()).equals("Wandering Monster (passage continues straight for 10 ft)")) {
                printMonsterInfo(i);

                /*Have to if elses here so the line isnt super long, instead it's two shorter ones.*/
            } else if ((thePassage.get(i).getDescription()).equals("archway (door) to right (main passage continues straight for 10 ft)")) {
                printDoorInfo(i);

            } else if ((thePassage.get(i).getDescription()).equals("archway (door) to left (main passage continues straight for 10 ft)")) {
                printDoorInfo(i);
            }
        }


        return thePassage.get(i - 1).getDescription();
    }

    /**
     * Prints all the info about the monster in the passageSection.
     * @param i is the slot int he list where the passageSection that the monster is being addded to.
     */
    private void printMonsterInfo(int i) {
        System.out.println("The monster is " + thePassage.get(i).getMonster().getDescription());
    }

    /**
     * Prints the info about the door in the passage.
     * @param i is the slot int he list where the passageSection that the door is being printed.
     */
    private void printDoorInfo(int i) {

        System.out.println();
        System.out.println("**The door information**");
        (thePassage.get(i).getDoor()).printInfo();
        System.out.println("**End of door information**");
        System.out.println();
    }

    /**
     * returns the bool which knows if this is a deadend.
     * @return the deadEnd bool.
     */
    public boolean isDeadEnd() {
    return hasDeadEnd;
}

    /**
     * returns the arraylist of passages to the user.
     * @return The passageList to the user.
     */
    public ArrayList<PassageSection> getPsList() {
        return thePassage;
    }


    /*--------------------------------------------------------------------------------------------------*/

    /**
     * This is an easy way to tell which chambers this passage is connected to.
     * @param homeNum is the chamber number it was first connected to.
     * @param nextNum is the chamber it's linked to.
     */
    public void connects(int homeNum, int nextNum) {
        homeChamberNum = homeNum;
        nextChamberNum = nextNum;
    }

    /**
     * Call this to get the number of the chamber that this connects to.
     * @return the next chamber num.
     */
    public int getNextChamberNum() {
        return nextChamberNum;
    }

    /**
     * Call this to find the chamber it's connect to first.
     * @return the home chamber num.
     */
    public int getHomechamberNum() {
        return homeChamberNum;
    }

    /**
     * This prints to screen which chambers it's connected to and the description.
     */
    private void printWelcomeWagon() {   /*make sure to add this to the excel doc and update the getDescription method it calls.*/

        if (getNextChamberNum() == -1) {
            System.out.println("This psssageway links chamber " + getHomechamberNum() + " and leads to a dead end.");

        } else if (getHomechamberNum() == -1) {
            System.out.println("This passageway leads the troop to their first chamber.");
        } else {
            System.out.println("This passageway links chamber " + getHomechamberNum() + " and chamber " + getNextChamberNum() + ".");
        }
    }

}
