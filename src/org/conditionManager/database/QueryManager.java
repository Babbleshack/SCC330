/**
 *
 * @author Dominic Lindsay
 */
package org.conditionManager.database;

import java.util.ArrayList;
import org.conditionManager.conditionContainers.ActuatorCondition;


public class QueryManager {
    IDatabaseConnectionManager connection;
    public QueryManager()
    {
        connection = DatabaseConnectionFactory.createMySQLConnection();
    }
    public boolean checkActuatorJob(int actuatorJobID){return true;}
    public ActuatorCondition getNextCondition(int conditionID){return null;}
    public ActuatorCondition getFirstCondition(int actuatirId){return null;}
    public ArrayList<Integer> getActuatorIds(){return null;}
    public void setRelayOn(int relayId){/**should check if relay is already on do nothing*/};
    public void setRelayOff(int relayId){/**same as above only turning relay off*/};
}
