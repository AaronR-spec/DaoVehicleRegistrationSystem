package daocaoop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Aaron Reihill / D00222467
 */
public interface EventDaoInterface
{

    /**
     *
     * @return a list filled with event objects
     * @throws DaoException
     */
    public List<Event> getAllEvents() throws DaoException;

    /**
     * writes to a database with map of event objects
     *
     * @param events
     * @throws DaoException
     */
    public void writeToDatabase(Map<String, ArrayList<Event>> events) throws DaoException;
}
