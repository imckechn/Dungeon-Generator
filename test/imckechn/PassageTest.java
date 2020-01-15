package imckechn;

import dnd.models.Exit;
import dnd.models.Monster;
import dnd.models.Stairs;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/*
 * Written by Ian McKechnie on Friday, November 1st, 2019.
 */
public class PassageTest {
    
    @Test
    public void testGetDoors() {
        System.out.println("Test: testGetDoors");
        PassageSection PS = new PassageSection("archway (door) to right (main passage continues straight for 10 ft)");
        PassageSection PS2 = new PassageSection("archway (door) to right (main passage continues straight for 10 ft)");
        PassageSection PS3 = new PassageSection("archway (door) to right (main passage continues straight for 10 ft)");
        PassageSection PS4 = new PassageSection("archway (door) to right (main passage continues straight for 10 ft)");

        Passage p = new Passage();
        p.addPassageSection(PS);
        p.addPassageSection(PS2);
        p.addPassageSection(PS3);
        p.addPassageSection(PS4);

        ArrayList<Door> arr = p.getDoors();

        assertEquals(4, arr.size());
    }


    @Test
    public void testIsDeadEnd() {
        System.out.println("Test: testIsDeadEnd");
        PassageSection PS = new PassageSection("Dead End");

        Passage p = new Passage();

        p.addPassageSection(PS);

        assertEquals(true, p.isDeadEnd());
    }

    @Test
    public void testCheckPassages() {
        System.out.println("Test: testIsDeadEnd");

        PassageSection PS = new PassageSection("archway (door) to right (main passage continues straight for 10 ft)");
        PassageSection PS2 = new PassageSection("archway (door) to right (main passage continues straight for 10 ft)");
        PassageSection PS3 = new PassageSection("Dead End");
        PassageSection PS4 = new PassageSection("archway (door) to right (main passage continues straight for 10 ft)");

        Passage p = new Passage();
        p.addPassageSection(PS);
        p.addPassageSection(PS2);
        p.addPassageSection(PS3);
        p.addPassageSection(PS4);

        ArrayList<PassageSection> arr = p.getPsList();

        assertEquals(3, arr.size());
    }

    @Test
    public void testCheckDoor() {
        System.out.println("Test: testCheckDoor");
        Door d = new Door();
        PassageSection ps = new PassageSection();
        Passage p = new Passage();

        ps.addDoor(d);
        p.addPassageSection(ps);

        assertEquals(d, p.getDoor(0));
    }

    @Test
    public void testDoorList() {
        System.out.println("Test: testDoorList");

        Door d = new Door();
        Door d2 = new Door();
        Door d3 = new Door();
        Door d4 = new Door();

        PassageSection PS = new PassageSection("archway (door) to right (main passage continues straight for 10 ft)");
        PassageSection PS2 = new PassageSection("archway (door) to right (main passage continues straight for 10 ft)");
        PassageSection PS3 = new PassageSection("Dead End");
        PassageSection PS4 = new PassageSection("archway (door) to right (main passage continues straight for 10 ft)");

        Passage p = new Passage();
        p.addPassageSection(PS);
        p.addPassageSection(PS2);
        p.addPassageSection(PS3);
        p.addPassageSection(PS4);

        ArrayList<Door> arr = new ArrayList<Door>();

        arr.add(d);
        arr.add(d2);
        arr.add(d3);
        arr.add(d4);

        assertEquals(arr.size(), p.getDoors().size());
    }

    @Test
    public void testGetMonster() {
        System.out.println("Test: testGetMonster");

        PassageSection PS = new PassageSection("Wandering Monster (passage continues straight for 10 ft)");
        Passage p = new Passage();

        p.addPassageSection(PS);

        Monster m = new Monster();

        p.addMonster(m, 0);

        assertEquals(m, p.getMonster(0));
    }

    @Test
    public void testConnectsMethods() {
        System.out.println("Test: testConnectsMethods");

        Passage p = new Passage();

        p.connects(2,3);

        int sum = p.getNextChamberNum() + p.getHomechamberNum();

        assertEquals(5, sum);
    }


}
