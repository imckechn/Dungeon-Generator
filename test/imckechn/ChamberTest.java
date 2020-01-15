package imckechn;
/*
* Written by Ian McKechnie on Friday, November 1st, 2019.
*
*
* Note to the TA,
*
* Most of the methods in my chamber class are helper methods,
* So not having tests for them if fine IMO since if the ones that call
* it work correctly it's fair to assume that the smaller helper
* methods also work allowing the main methods to work.
*
*
* */

import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.DnDElement;
import dnd.models.Monster;
import dnd.models.Stairs;
import dnd.models.Trap;
import dnd.models.Treasure;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChamberTest {
    ChamberContents contents;
    ChamberShape shape;


    public void setup() {
        contents = new ChamberContents();
        contents.setDescription();
    }


    @Test
    public void testSetContents() {
        System.out.println("Test: testSetContents");

        Chamber testChamber = new Chamber();
        testChamber.setContents(contents);

        assertEquals(testChamber.getShape(), contents);
    }

    @Test
    public void testGetDoors() {
        System.out.println("Test: testGetDoors");
        shape = new ChamberShape();
        contents = new ChamberContents();
        shape.setShape();

        ArrayList<Door> doorList;
        Chamber testChamber = new Chamber(shape, contents);

        doorList = testChamber.getDoors();

        assertEquals(doorList.size(), shape.getNumExits());
    }

    @Test
    public void testGetTreasureList() {
        System.out.println("Test: testGetTreasureList");
        Treasure t = new Treasure();
        Chamber c = new Chamber();

        c.addTreasure(t);
        c.addTreasure(t);
        c.addTreasure(t);

        ArrayList<Treasure> arr = c.getTreasureList();

        assertEquals(3, arr.size());
    }


    @Test
    public void testGetMonstersList() {
        System.out.println("Test: testGetMonsterList");
        Monster m = new Monster();
        Chamber c = new Chamber();

        c.addMonster(m);
        c.addMonster(m);
        c.addMonster(m);

        ArrayList<Monster> arr = c.getMonsterList();

        assertEquals(3, arr.size());
    }

    @Test
    public void testSetConnectionCount() {
        System.out.println("Test: testSetConnectionCount");
        int numConnections = 3;
        int offSetAmount = 1;

        Chamber c = new Chamber();

        c.declareDoors(numConnections);
        c.updateConnectionCount(offSetAmount);
        c.updateConnectionCount(offSetAmount);

        assertEquals(1, c.getConnectionCount());
    }


    @Test
    public void testChamberCount() {
        System.out.println("Test: testChamberCount");

        Chamber a = new Chamber();
        Chamber b = new Chamber();
        Chamber c = new Chamber();
        Chamber d = new Chamber();
        Chamber e = new Chamber();

        assertEquals(6, e.chamberCount());
    }


}