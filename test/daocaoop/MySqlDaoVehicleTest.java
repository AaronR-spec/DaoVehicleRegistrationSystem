/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daocaoop;

import java.util.HashSet;
import java.util.List;
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
public class MySqlDaoVehicleTest
{

    public MySqlDaoVehicleTest()
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
     * Test of getAllVehicleReg method, of class MySqlDaoVehicle.
     */
    @Test
    public void testGetAllVehicleReg()
    {
        System.out.println("getAllVehicleReg");
        MySqlDaoVehicle instance = new MySqlDaoVehicle();
        boolean expResult = true;
        List<String> result = null;
        try
        {
            result = instance.getAllVehicleReg();
        }
        catch (DaoException ex)
        {
            System.out.println("testGetAllVehicleReg error");
        }
        assertEquals(expResult, result != null);
    }

    /**
     * Test of writeToDatabase method, of class MySqlDaoVehicle.
     */
    @Test
    public void testWriteToDatabase()
    {
        VehicleTable v = new VehicleTable();
        v.readFromFile("Vehicles.csv");
        System.out.println("writeToDatabase");
        HashSet<String> table = v.getTable();
        boolean expResult = true;
        List<String> result = null;
        MySqlDaoVehicle instance = new MySqlDaoVehicle();
        try
        {
            instance.writeToDatabase(table);
            result = instance.getAllVehicleReg();

        }
        catch (DaoException ex)
        {
            System.out.println("testWriteToDatabase error");

        }
        assertEquals(expResult, result != null);
    }

}
