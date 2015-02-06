/**
 *
 * @author Dominic Lindsay
 */
package org.conditionprocessor.database;

import org.conditionProcessor.conditionContainers.ActuatorCondition;


public class QueryManager {
    IDatabaseConnectionManager connection;
    public QueryManager()
    {
        connection = DatabaseConnectionFactory.createMySQLConnection();
    }
    public boolean checkActuatorJob(int actuatorJobID){return true;}
    public ActuatorCondition getNextCondition(int conditionID){return null;}
}
