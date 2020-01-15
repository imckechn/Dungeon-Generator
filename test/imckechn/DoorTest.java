package imckechn;

import dnd.models.Trap;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/*
 * Written by Ian McKechnie on Friday, November 1st, 2019.
 */
public class DoorTest {

    @Test
    public void testIsArchway() {
        System.out.println("Test: testIsArchway");
        Door d = new Door();

        d.setArchway(true);

        assertEquals(true, d.isArchway());
    }

    @Test
    public void testIsOpen() {
        System.out.println("Test: testIsOpen");

        Door d = new Door();

        d.setOpen(false);

        assertEquals(false, d.isOpen());
    }

    @Test
    public void testGetTrapDescription() {
        System.out.println("Test: testGetTrapDescription");

        Door d = new Door();
        Trap t = new Trap();
        t.setDescription(4);
        d.setTrapped(true, 4);

        assertEquals(t.getDescription(), d.getTrapDescription());

    }

    @Test
    public void testDoorExists() {
        System.out.println("Test: testDoorExists");

        Door d = new Door();

        d.doorExists(true);

        assertEquals(true, d.doorExists());
    }

    @Test
    public void testGenerateAPath() {
        System.out.println("Test: testGenerateAPath");

        Door d = new Door();
        d.setArchway(false);
        d.setOpen(false);

        assertEquals(true, d.generateAPath());
    }

    @Test
    public void testGetSpaces() {
        System.out.println("Test: testGetSpaces");

        Door d = new Door();
        Chamber c = new Chamber();
        Chamber c2 = new Chamber();

        d.setSpaces(c, c2);

        ArrayList<Space> arr = new ArrayList<Space>();
        arr.add(c);
        arr.add(c);

        assertEquals(arr.size(), d.getSpaces().size());
    }

    @Test
    public void testIsTrapped() {
        System.out.println("Test: testIsTrapped");

        Door d = new Door();

        d.setTrapped(false);

        assertEquals(false, d.isTrapped());
    }
}
