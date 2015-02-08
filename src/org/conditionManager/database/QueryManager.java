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

    public ArrayList<Integer> getActuatorIds()
    {
        String getActuatorIds = "SELECT * " 
                + " FROM Actuator";
        try {
            /**
             * Execute select query
             */
            PreparedStatement record =
                connection.getConnection().prepareStatement(getActuatorIds);

            /**
             * Return result
             */
            ArrayList output_array = new ArrayList<Integer>();

            while (result.next()) {
                output_array.add((Object)Integer.valueOf(result.getInt('id')));
            }

            return output_array;

        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing "
                + "getActuatorIds: " + e);
                return null;
        }
    }

    /**
     * Checks if actuator is on, if it isn't set it to on
     * @param actuator_id actuator id
     */
    public void setRelayOn(int actuator_id){
        if(!this.isActuatorOn(actuator_id)) 
            this.setRelayStatus(actuator_id, 1);
    }

    /**
     * Checks if actuator is on, if it is set it to off
     * @param actuator_id actuator id
     */
    public void setRelayOff(int actuator_id){
        if(this.isActuatorOn(actuator_id)) 
            this.setRelayStatus(actuator_id, 0);
    }

    /**
     * Sets relay status
     * @param actuator_id   actuator id to change
     * @param status        status to change to
     *                      1 = on
     *                      0 = off
     */
    private void setRelayStatus(int actuator_id, int status)
    {
        String setRelayOn = "UPDATE Actuator SET is_on = ? "
                + "WHERE id = ?";
        try {
            PreparedStatement putBattery;
            putBattery =
                    connection.getConnection().prepareStatement(setRelayOn);
            putBattery.setint(1, status);
            putBattery.setInt(2, actuator_id);
            putBattery.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Returns if actuator is currently on or not
     * @param  actuator_id  Actuator to check
     * @return       int    1 if actuator is on, 0 if actuator is off   
     */
    public int isActuatorOn(String actuator_id)
    {
        String isActuatorOn = "SELECT * FROM Actuator WHERE id = ?";

        try {
            /**
             * Execute select query
             */
            PreparedStatement record =
                connection.getConnection().prepareStatement(isActuatorOn);
            record.setInt(1, actuator_id);

            /**
             * Access ResultSet for actuator_address
             */
            ResultSet result = record.executeQuery();

            /**
             * Return result
             */
            if(result.next()) {
                if(result.getInt("is_on") == 1)
                    return 1;
                else
                    return 0;
            } else {
                return 0;
            }
        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing "
                + "isActuatorOn: " + e);
                return 0;
        }
    }
}
