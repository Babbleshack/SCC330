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
   /**
     * Check if a given actuator job id meets its job thresholds
     * @param  actuator_job_id  id of actuator job to check
     * @return boolean          true or false                 
     */
    public boolean checkActuatorJob(int actuator_job_id)
    {
        return false; 
    }

    /**
     * Return an operator, given a string for an operator
     * @param  operation string representing an operation
     * @return           Or / And 
     */
    private returnOperator(String operation) 
    {
        if(operation.equals('or')) {
            return new Or(); 
        } else if(operation.equals('and')) {
            return new And(); 
        } else {
            return null; 
        }
    }

    /**
     * Get the first condition for a given actuator
     * @param  actuator_id actuator id
     * @return             ActuatorCondition - first condition for a given actuator
     */
    public ActuatorCondition getFirstCondition(int actuator_id)
    {
        String getFirstCondition = "SELECT * " 
                + " FROM Condition"
                + " WHERE actuator_id = ? "
                + " ORDER BY id LIMIT 1";

        try {
            /**
             * Execute select query
             */
            PreparedStatement record =
                connection.getConnection().prepareStatement(getFirstCondition);
            record.setInt(1, actuator_id);

            /**
             * Access ResultSet for condition
             */
            ResultSet result = record.executeQuery();
            if(result.next()) {
                /**
                 * Return result
                 */
                return new ActuatorCondition(result.getInt('id'), actuator_id, result.getInt('actuator_job'), result.getInt('actuator_job'), this.returnOperator(result.getString('boolean_operator')), this.returnOperator(result.getString('next_operator')));
            } else {
                return null;
            }

        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing "
                + "getFirstCondition: " + e);
                return null;
        }
    }

    /**
     * Private method for accessing a condition directly by its ID
     * @param  condition_id condition id
     * @return              ActuatorCondition for the given id
     */
    private ActuatorCondition getCondition(int condition_id)
    {
        if(condition_id == null)
            return null;
        
        String getCondition = "SELECT * " 
                + " FROM Condition"
                + " WHERE id = ? "
                + " ORDER BY id LIMIT 1";

        try {
            /**
             * Execute select query
             */
            PreparedStatement record =
                connection.getConnection().prepareStatement(getCondition);
            record.setInt(1, actuator_id);

            /**
             * Access ResultSet for condition
             */
            ResultSet result = record.executeQuery();
            if(result.next()) {
                /**
                 * Return result
                 */
                return new ActuatorCondition(result.getInt('id'), actuator_id, result.getInt('actuator_job'), result.getInt('actuator_job'), this.returnOperator(result.getString('boolean_operator')), this.returnOperator(result.getString('next_operator')));
            } else {
                return null;
            }

        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing "
                + "getCondition: " + e);
                return null;
        }
    }

    public ActuatorCondition getNextCondition(int condition_id)
    {
        String getNextCondition = "SELECT * " 
                + " FROM Condition"
                + " WHERE id = ? "
                + " ORDER BY id LIMIT 1";

        try {
            /**
             * Execute select query
             */
            PreparedStatement record =
                connection.getConnection().prepareStatement(getNextCondition);
            record.setInt(1, actuator_id);

            /**
             * Access ResultSet for condition
             */
            ResultSet result = record.executeQuery();
            if(result.next()) {
                /**
                 * Return result
                 */
                return this.getCondition(result.getInt('next_condition')); 
            }

        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing "
                + "getNextCondition: " + e);
                return null;
        }
    }
    
    public ArrayList<Integer> getActuatorIds(){return null;}
    public void setRelayOn(int relayId){/**should check if relay is already on do nothing*/};
    public void setRelayOff(int relayId){/**same as above only turning relay off*/};
}
