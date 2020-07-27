package daocaoop;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Aaron Reihill / D00222467
 */
public class VehicleTable
{

    HashSet<String> table = new HashSet<>();

    /**
     * empty constructor
     */
    public VehicleTable()
    {
    }

    /**
     * gets all the vehicles registration stored as the hash set
     *
     * @return hash set of Strings
     */
    public HashSet<String> getTable()
    {
        return table;
    }

    /**
     * adds a string to the hash set
     *
     * @param reg
     */
    public void add(String reg)
    {
        table.add(reg);
    }

    /**
     * removes a string from the hash set
     *
     * @param reg
     */
    public void remove(String reg)
    {
        table.remove(reg);
    }

    /**
     * gets all Strings stored in file and puts them in hash set
     *
     * @param fileName
     */
    public void readFromFile(String fileName)
    {
        Scanner sc = new Scanner("");
        try
        {
            sc = new Scanner(new File(fileName));
            sc.useDelimiter("[\n\r]+");

            String reg;
            while (sc.hasNext())
            {
                reg = sc.next().trim();
                this.add(reg);
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
    }

    /**
     * Reads from database and puts all currently stored strings in hash set
     */
    public void readFromDataBase()
    {
        MySqlDaoVehicle v = new MySqlDaoVehicle();
        try
        {
            for (String s : v.getAllVehicleReg())
            {
                this.add(s);
            }
        }
        catch (DaoException e)
        {
            System.err.println(e.getLocalizedMessage());

        }
    }

    /**
     * writes hash set to database
     */
    public void writeToDatabase()
    {
        MySqlDaoVehicle sql = new MySqlDaoVehicle();
        try
        {
            sql.writeToDatabase(this.table);
        }
        catch (DaoException ex)
        {
            System.err.println(ex.getLocalizedMessage());
        }
    }

    /**
     * finds string in hash set if there
     *
     * @param s
     * @return true if found, false if not
     */
    public boolean find(String s)
    {
        return table.contains(s);
    }

    /**
     * displays the hash set neatly
     */
    public void display()
    {
        System.out.println("");
        int count = 0;
        int lineSplit = 6;
        String line = "-------------------------------------------------------------------------------------";
        System.out.println("Vehicle Table");
        System.out.println(line);
        for (String s : table)
        {
            System.out.print(String.format("%-15s", s, ","));
            count++;
            if (count >= lineSplit)
            {
                System.out.println("");
                count = 0;
            }
        }
        System.out.println(line);
    }

    @Override
    public String toString()
    {
        return "VehicleTable{" + "table=" + table + '}';
    }

}
