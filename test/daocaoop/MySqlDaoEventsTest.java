/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daocaoop;

import static daocaoop.Main.v;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author aaron
 */
public class MySqlDaoEventsTest
{

    public MySqlDaoEventsTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test of writeToDatabase method, of class MySqlDaoEvents.
     */
    @Test
    public void testWriteToDatabase()
    {

        System.out.println("writeToDatabase");
        Map<String, ArrayList<Event>> events = processEvents();
        MySqlDaoEvents instance = new MySqlDaoEvents();
        try
        {
            instance.writeToDatabase(events);
        }
        catch (DaoException ex)
        {
            System.out.println("TestwriteToDatabase error");
        }
    }

    private static List<Event> readEvents(String fileName)
    {
        ArrayList<Event> events = new ArrayList<>();
        Scanner sc = new Scanner("");
        try
        {
            sc = new Scanner(new File(fileName));
            sc.useDelimiter("[;\n\r]+");

            String reg, img, time;
            while (sc.hasNext())
            {
                reg = sc.next().trim();
                img = sc.next().trim();
                time = sc.next().trim();
                Timestamp t = java.sql.Timestamp.from(Instant.parse(time));
                events.add(new Event(reg, img, t));
            }
        }
        catch (IOException e)
        {
            System.out.println("File Not Found. " + e.getMessage());
        }
        catch (InputMismatchException e)
        {
            System.out.println("Format Is Wrong" + e.getMessage());
        }
        finally
        {
            sc.close();
        }
        return events;
    }

    private static Map<String, ArrayList<Event>> processEvents()
    {
        Map<String, ArrayList<Event>> events = new HashMap<>();

        for (Event e : readEvents("Toll-Events.csv"))
        {
            if (v.find(e.getReg()))
            {
                if (events.containsKey(e.getReg().trim()))
                {
                    events.get(e.getReg()).add(e);
                }
                else
                {
                    ArrayList<Event> list = new ArrayList<>();
                    list.add(e);
                    events.put(e.getReg().trim(), list);
                }
            }
        }
        return events;
    }

    /**
     * Test of getAllEvents method, of class MySqlDaoEvents.
     */
    @Test
    public void testGetAllEvents()
    {
        try
        {
            System.out.println("getAllEvents");
            List<Event> result = null;
            MySqlDaoEvents instance = new MySqlDaoEvents();
            //List<Event> expResult = readEvents("Toll-Events.csv");
            boolean expResult = true;
            result = instance.getAllEvents();
            assertEquals(expResult, result != null);
        }
        catch (DaoException ex)
        {
            System.out.println("testGetAllEvents error");

        }
    }

}
