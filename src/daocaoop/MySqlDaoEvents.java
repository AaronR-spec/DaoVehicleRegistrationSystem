package daocaoop;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Aaron Reihill / D00222467
 */
public class MySqlDaoEvents extends MySqlDao implements EventDaoInterface
{

    /**
     * writes the map of event objects to the database
     *
     * @param events
     * @throws DaoException
     */
    @Override
    public void writeToDatabase(Map<String, ArrayList<Event>> events) throws DaoException
    {
        Connection con = null;
        String query;
        try
        {
            ArrayList<Event> list;
            con = this.getConnection();
            Statement sqlStatement = con.createStatement();
            for (String s : events.keySet())
            {
                list = events.get(s);
                for (Event e : list)
                {
                    query = "INSERT INTO Events(Reg,Img,timestamp)"
                            + "\nVALUES ('" + e.getReg() + "', '" + e.getImgId() + "',Timestamp('" + e.getTimestamp() + "'));\n";
                    sqlStatement.execute(query);
                }
            }
        }
        catch (SQLException e)
        {
            if (e instanceof MySQLIntegrityConstraintViolationException)
            {
            }
            else
            {
                throw new DaoException("getAllVehicleReg() " + e.getMessage());
            }
        }

        finally
        {
            try
            {

                if (con != null)
                {
                    freeConnection(con);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("getAllVehicleReg() " + e.getMessage());
            }
        }

    }

    /**
     * Gets all currently stored events in the database
     *
     * @return all the event objects in database as a list
     * @throws DaoException
     */
    @Override
    public List<Event> getAllEvents() throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Event> events = new ArrayList<>();

        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM Events";
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next())
            {
                String reg = rs.getString("Reg");
                String img = rs.getString("Img");
                String time = rs.getString("timestamp");
                events.add(new Event(reg, img, Timestamp.valueOf(time)));
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("getAllEvents() " + e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("getAllEvents() " + e.getMessage());
            }
        }
        return events;
    }
}
