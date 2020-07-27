package daocaoop;

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

/**
 *
 * @author Aaron Reihill / D00222467
 */
public class Main
{
    
    static VehicleTable v = new VehicleTable();
    static Scanner keyboard = new Scanner(System.in);

    /**
     * program starts here
     *
     * @param args
     */
    public static void main(String[] args)
    {
        v.readFromFile("Vehicles.csv");
        v.writeToDatabase();
        v.readFromDataBase();
        boolean exit = false;
        int option;
        System.out.println("Java Database Toll Ca Stage 1");
        while (!exit)
        {
            
            System.out.println("\n(0) Exit");
            System.out.println("(1) Display Vehicle Table");
            System.out.println("(2) Process Recent Events");
            System.out.println("(3) Display Recent Events");
            System.out.println("(4) Display Events Database");
            
            try
            {
                System.out.print("Select: ");
                option = keyboard.nextInt();
                switch (option)
                {
                    case 0:
                        System.out.println("Goodbye...");
                        exit = true;
                        break;
                    case 1:
                        v.display();
                        enterToReturn();
                        break;
                    case 2:
                        processEvents();
                        break;
                    case 3:
                        display(readEvents("Toll-Events.csv"));
                        enterToReturn();
                        break;
                    case 4:
                        displayEventsFromDatabase();
                        enterToReturn();
                        
                        break;
                    default:
                        System.err.println("Not An Option");
                }
            }
            catch (InputMismatchException e)
            {
                System.err.println("Not An Option");
                keyboard.next();
            }
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
    
    private static void display(Map<String, ArrayList<Event>> events)
    {
        for (String s : events.keySet())
        {
            System.out.println(events.get(s));
        }
    }
    
    private static void display(List<Event> events)
    {
        System.out.println(String.format("\n%-15s%-15s%-15s", "Registration", "Image", "Timestamp"));
        System.out.println("-------------------------------------------------------");
        for (Event e : events)
        {
            System.out.println(String.format("%-15s%-15s%-15s", e.getReg(), e.getImgId(), e.getTimestamp()));
        }
    }
    
    private static void processEvents()
    {
        Map<String, ArrayList<Event>> events = new HashMap<>();
        ArrayList<Event> errors = new ArrayList<>();
        
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
            else
            {
                errors.add(e);
            }
        }
        MySqlDaoEvents sql = new MySqlDaoEvents();
        try
        {
            sql.writeToDatabase(events);
            System.out.println("Events Processed Successfully...");
            if (errors.size() > 0)
            {
                System.out.println(errors.size() + " Events Could Not Be Processed");
                System.out.print("Display(Y/N): ");
                String choice = keyboard.next();
                if (choice.equalsIgnoreCase("y"))
                {
                    display(errors);
                }
                else
                {
                    System.out.println("Returning...");
                }
            }
        }
        catch (DaoException ex)
        {
            System.err.println(ex.getLocalizedMessage());
        }
    }
    
    private static void displayEventsFromDatabase()
    {
        MySqlDaoEvents sql = new MySqlDaoEvents();
        try
        {
            display(sql.getAllEvents());
        }
        catch (DaoException ex)
        {
            System.err.println("Dispaly Events From Database: " + ex.getLocalizedMessage());
        }
    }
    
    private static void enterToReturn()
    {
        keyboard.nextLine();
        System.out.print("\n\nPress Enter To Return: ");
        keyboard.nextLine();
    }
    
}
