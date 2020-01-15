package imckechn;
import dnd.models.Exit;
import dnd.models.Trap;
import java.util.ArrayList;

/**
 * The door class.
 */
public final class Door {
    /**
     * Counts total number of doors.
     */
    private static int counter = 0;

    /**
     * The var tracking if the door is trapped.
     */
    private boolean isTrap;

    /**
     * Contains all spaces.
     */
    private ArrayList<Space> myThings = new ArrayList<Space>();

    /**
     *does this door exist.
     */
    private boolean exists;

    /**
     * is this door open.
     */
    private boolean isOpen;

    /**
     * Is this door an arch.
     */
    private boolean isArchway;

    /**
     * describes the exit behind the door.
     */
    private Exit beyondTheDoor = new Exit();

    /**
     * The trap for the door.
     */
     private Trap rickJames = new Trap();

    /**
     * the passage.
     */
    private Passage doorExit = new Passage();

    /**
     * Makes it exist.
     */
    public Door() {
          exists = true;
     }

     /**
      * This is called to declare if the door even exists or not. Also sets up a bunch of the info for the door.
      * @param value The boolean value of if it exists.
     */
     public void doorExists(boolean value) {
        exists = value;
    }

     /**
      * If in the chamber class I decide to reconfigure the direction of the door by making an exit object, I can reconfigure the door with that objet.
      * @param theExit An exit object.
     */
     public void door(Exit theExit) {
          beyondTheDoor = theExit;
     }

     /**
      * Sets the trap if there is one.
      * @param flag The boolean saying there's a trap or not.
      * @param roll An int for the roll deciding which trap exists.
      */
     public void setTrapped(boolean flag, int... roll) {
          isTrap = flag;
          rickJames.setDescription(roll[0]);

     }

     /**
      * Sets if the door is open or not.
      * @param flag The boolean value of if it's open or not.
      */
     public void setOpen(boolean flag) {
        if (!isArchway) {
            isOpen = flag;
        } else {
            isOpen = true;
        }
     }

     /**
      * This sees if the exit is an archway. And sets the rest of the door parameters accordingly.
      * @param flag The boolean value on if it's an archway or not.
      */
     public void setArchway(boolean flag) {

         isArchway = flag;

         if (flag) {
             setOpen(flag);
             setTrapped(false, 0);
         }
     }

     /**
      * Returns true if a trap exists.
      * @return The boolean value on if it's true or not.
      */
     public boolean isTrapped() {
          return isTrap;
     }

     /**
      * Is asking if the doorway is open.
      * @return Boolean on if it's open or not.
      */
     public boolean isOpen() {
         return isOpen;
     }

     /**
      * Is asking if the doorway is an archway.
      * @return Boolean value for if it's open or not.
     */
     public boolean isArchway() {
          return isArchway;
     }


     /**
      * Returns the trap desc.
      * @return trap desc.
     */
     public String getTrapDescription() {
          return rickJames.getDescription();
     }

     /**
      * Gets the spaces b4 and after door.
      * @return the arraylist of spaces.
      */
     public ArrayList<Space> getSpaces() {

          return myThings;
     }

     /**
      * Sets one space at time.
      * @param spaceBeingAdded the space b4 or after the door.
      */
     public void setOneSpace(Space spaceBeingAdded) {
          myThings.add(spaceBeingAdded);
          spaceBeingAdded.setDoor(this);
     }

     /**
      * Sets the spaces b4 and after the door, both at once.
      * @param spaceOne B4 the door (how I did it)
      * @param spaceTwo after the door.
      */
     public void setSpaces(Space spaceOne, Space spaceTwo) {
          myThings.add(spaceOne);
          myThings.add(spaceTwo);
     }

     /**
      * returns the desc of the door.
      * @return the string desc.
      */
     public String getDescription() {
          if (isTrap) {
              return "trap";
          } else if (isArchway) {
              return "archway";
          } else {
              return "Normal open door with no traps";
          }
     }

     /**
      * This is a question and returns the value of the boolean.
      * @return the boolean value identifying if it exsits.
      */
     public boolean doorExists() {
         return exists;
     }

     /**
      * Sets up the doorway if its not an archway (configures if it's open and puts archway = false).
      * @param roll The roll valuse deciding the odds of if it's an archway.
      */
     public void setUpDoorNoArchway(int roll) {

         isArchway = false;
         if (roll < 11) {
             setOpen(true);
         } else {
             setOpen(false);
         }
     }

     /**
      * Called to set the exists var to true.
      */
     public void setExists() {
         exists = true;
     }

     /**
      * Prints all the information about the door being called obly if it exists.
     */
     public void printInfo() {

         if (isArchway) {
             System.out.println("This door is an archway");

         } else {
            printInfoPartTwo();
         }
     }

      /**
     * This is a question. Can it generate a path, and returns the answer. Supposed to be inverted.
     * @return a boolean if the door is open.
     */
      public boolean generateAPath() {

          if (!isOpen) {
              return true;
          }
          return false;
      }

    /**
     * Second part to printInfo to keep it in the correct length limit.
     */
    private void printInfoPartTwo() {
         if (isOpen) {
             System.out.println("The door is unlocked");
             System.out.println("Beyond the door is " + beyondTheDoor.getLocation() + " and goes " + beyondTheDoor.getDirection());
         } else {
             System.out.println("The door is locked");
         }

         if (isTrap) {
             System.out.println("Its a trap, the trap is " + rickJames.getDescription());

         } else {
             System.out.println("There is no trap");
         }
     }
}
