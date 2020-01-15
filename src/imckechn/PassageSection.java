package imckechn;

import dnd.models.Monster;
import java.util.Random;

/* Represents a 10 ft section of passageway */

/**
 * this is the class for the psg section.
 */
public final class PassageSection {

     /**
     * The description for the passage.
     */
     private String passageSectionDescription;

     /**\
     * The random var.
     */
     private Random rand = new Random();
     /**
     * The monster for this section.
     */
     private Monster machoManRandySavage = new Monster();
     /**
      * the door for this section.
      */
     private Door aDoor = new Door();

     /**
      * does this psg section have a monster.
      */
     private boolean isMonster;

     /**
     * does this psg section have a door.
      */
     private boolean isDoor;

     /**
     * sets monster exists var = true.
      */
     public PassageSection() {
         isMonster = true;
     }

     /**
      * Adds a description to the psg.
      * @param description is psg desciption being added.
      */
     public PassageSection(String description) {
        passageSectionDescription = description;

         setMonsterAndDoorFromDescription();

         if (isMonster) {   //See if this can connect to main's genMonster class;
             Monster m = genMonster();
             addMonster(m);
         }
     }

    /**
     * Helper function that is called by PassageSection and it tells the program that a monster exists or a door exists.
     */
    private void setMonsterAndDoorFromDescription() {
        isMonster = false;
        isDoor = false;

        if (passageSectionDescription.equals("Wandering Monster (passage continues straight for 10 ft)")) {
            isMonster = true;
        } else if (passageSectionDescription.equals("archway (door) to right (main passage continues straight for 10 ft)")) {
            isDoor = true;
        } else if (passageSectionDescription.equals("archway (door) to left (main passage continues straight for 10 ft)")) {
            isDoor = true;
        } else if (passageSectionDescription.equals("passage ends in archway (door) to chamber")) {
            isDoor = true;
        } else if (passageSectionDescription.equals("passage ends in Door to a Chamber")) {
            isDoor = true;
        }
    }

     /**
      * gets the door from this psg section.
      * @return tghe door for this section
      */
     public Door getDoor() {
         if (passageSectionDescription.equals("passage ends in Door to a Chamber")) {
             aDoor.doorExists(true);
             aDoor.setArchway(false);

         } else if (passageSectionDescription.equals("archway (door) to right (main passage continues straight for 10 ft)")) {

             aDoor.setArchway(false);

         } else if (passageSectionDescription.equals("archway (door) to left (main passage continues straight for 10 ft)")) {

             aDoor.setArchway(false);

         } else if (passageSectionDescription.equals("passage ends in archway (door) to chamber")) {

             aDoor.setArchway(false);
         }

         return aDoor;
     }

     /**
      * returns the monster for this section.
      * @return the monster for this section
      */
     public Monster getMonster() {

         if (isMonster) {
             return machoManRandySavage;
         }

         return null;
     }

     /**
      * returns the psg description.
      * @return the description var
      */
     public String getDescription() {
         return passageSectionDescription;
     }

     /**
      * Adds a monster to the psg section.
      * @param johnCena is the monster being inputted
      */
     public void addMonster(Monster johnCena) {
         /*If the description permits a montser it will add one*/
         if (isMonster) {
             machoManRandySavage = johnCena;
         }
     }

     /**
      * Adds a door to the psg section.
      * @param theDoor is the door for this psg section
      */
     public void addDoor(Door theDoor) {
         if (isDoor) {
             aDoor = theDoor;
         }
}

     /**
      * Sets the description of the psg section.
      * @param description is the string car holding the description
      */
     public void setDesc(String description) {
         passageSectionDescription = description;

         if (passageSectionDescription.equals("passage ends in Door to a Chamber")) {
             isDoor = true;
             isMonster = false;
         } else if (passageSectionDescription.equals("archway (door) to right (main passage continues straight for 10 ft)")) {
             isDoor = true;
             isMonster = false;
         } else if (passageSectionDescription.equals("archway (door) to left (main passage continues straight for 10 ft)")) {
             isDoor = true;
             isMonster = false;
         } else if (passageSectionDescription.equals("passage ends in archway (door) to chamber")) {
             isDoor = true;
             isMonster = false;
         } else if (passageSectionDescription.equals("passage ends in archway (door) to chamber")) {
             isMonster = false;
             isDoor = false;
         } else if (passageSectionDescription.equals("Wandering Monster (passage continues straight for 10 ft)")) {
             isMonster = true;
             isDoor = false;
         } else {
             isMonster = false;
             isDoor = false;
         }
     }

    /**
     * This returns if this pSection has a door or not.
     * @return the boolean if a door exists or not.
     */
    public boolean hasDoor() {
        return isDoor;
    }

    /**
     * This returns if this pSection has a monster or not.
     * @return the boolean if a monster exists or not.
     */
    public boolean hasMonster() {
        return isMonster;
    }

    /**
     * This generates a fully created monster object.
     * @return The monster object.
     */
    private static Monster genMonster() {
        Monster beast = new Monster();
        Random rand = new Random();

        beast.setType(rand.nextInt(100) + 1);

        return beast;
    }
}

