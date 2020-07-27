package daocaoop;

import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Aaron Reihill / D00222467
 */
public interface VehicleDaoInterface
{

    /**
     * Gets all the vehicle registrations currently stored in the database
     *
     * @return list of vehicle registrations as Strings
     * @throws DaoException
     */
    public List<String> getAllVehicleReg() throws DaoException;

    /**
     * Writes a hash set of Strings to a database
     *
     * @param table
     * @throws DaoException
     */
    public void writeToDatabase(HashSet<String> table) throws DaoException;
}
