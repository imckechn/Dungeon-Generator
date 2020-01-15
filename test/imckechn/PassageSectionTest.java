package imckechn;

import dnd.models.Exit;
import dnd.models.Monster;
import org.junit.Test;
import static org.junit.Assert.*;

/*
 * Written by Ian McKechnie on Friday, November 1st, 2019.
 */
public class PassageSectionTest {

    @Test
    public void testMonster() {
        System.out.println("Test: testMonster");

        PassageSection PS = new PassageSection();
        Monster m = new Monster();

        PS.addMonster(m);

        assertEquals(m, PS.getMonster());
    }

    @Test
    public void testDescription1() {
        System.out.println("Test: testDescription1");

        PassageSection PS = new PassageSection();
        String s = "Wandering Monster (passage continues straight for 10 ft)";

        PS.setDesc(s);

        assertEquals(s, PS.getDescription());
    }

    @Test
    public void testDescription2() {
        System.out.println("Test: testDescription2");

        String s = "Wandering Monster (passage continues straight for 10 ft)";
        PassageSection PS = new PassageSection(s);

        assertEquals(s, PS.getDescription());
    }

    @Test
    public void testHasDoor() {
        System.out.println("Test: testHasDoor");

        String s = "passage ends in Door to a Chamber";

        PassageSection PS = new PassageSection(s);

        assertEquals(true, PS.hasDoor());
    }

    @Test
    public void testHasMonster() {
        System.out.println("Test: testHasMonster");

        String s = "passage ends in Door to a Chamber";

        PassageSection PS = new PassageSection(s);

        assertEquals(false, PS.hasMonster());
    }


}

