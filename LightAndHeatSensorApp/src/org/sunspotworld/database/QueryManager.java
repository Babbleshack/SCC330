/*
 * Handles quearies to Database
 * Dominic Lindsay
 * Adam Cornforth
 */
package org.sunspotworld.database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.util.Calendar;
import org.sunspotworld.homeCollections.ArrayList;
import org.sunspotworld.valueObjects.LightData;
import org.sunspotworld.valueObjects.ThermoData;
import org.sunspotworld.valueObjects.AccelData;
import org.sunspotworld.actuator.Actuator;
import org.sunspotworld.actuator.ActuatorJob;


public class QueryManager implements IQueryManager
{
    private IDatabaseConnectionManager connection;
    private static final String DB_NAME = "testing";
    private PreparedStatement stm = null;

    public QueryManager()
    {
        connection = DatabaseConnectionFactory.createMySQLConnection();
    }

    /**
     *
     * @param spot_address
     * @return
     */
    public int isSpotExists(String spot_address) {
        String isSpotExists = "SELECT * FROM Spot WHERE spot_address = ?";

        try {
            /**
             * Execute select query
             */
            PreparedStatement record =
                connection.getConnection().prepareStatement(isSpotExists);
            record.setString(1, spot_address);

            /**
             * Access ResultSet for spot_address
             */
            ResultSet result = record.executeQuery();

            /**
             * Return result
             */
            if(result.next())
                return 1;
            else
                return 0;

        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing "
                + "isSpotExists: " + e);
                return 0;
        }
    }

    /**
     * Insert spot record into db
     * @param spot_address
     * @param time long
     */
    public void createSpotRecord(String spot_address, long time) {
        String insertSpotRecord = "INSERT INTO Spot"
                + "(spot_address, created_at, updated_at)"
                + ("VALUES (?,?,?)");
        try {
            PreparedStatement insert =
                connection.getConnection().prepareStatement(insertSpotRecord);
            insert.setString(1, spot_address);
            insert.setTimestamp(2, new Timestamp(time));
            insert.setTimestamp(3, new Timestamp(time));
            insert.executeUpdate();
        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing"
                + "insertSpotRecord: " + e);
        }
    }

    /**
     *
     * @param actuator_address
     * @return
     */
    public int isActuatorExists(String actuator_address) {
        String isActuatorExists = "SELECT * FROM Actuator WHERE Actuator.actuator_address LIKE ? ";

        try {
            /**
             * Execute select query
             */
            PreparedStatement record =
                connection.getConnection().prepareStatement(isActuatorExists);
            record.setString(1, "%" + actuator_address.replace(".relay1", "") + "%");

            /**
             * Access ResultSet for actuator_address
             */
            ResultSet result = record.executeQuery();

            /**
             * Return result
             */
            if(result.next())
                return 1;
            else
                return 0;

        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing "
                + "isActuatorExists: " + e);
                return 0;
        }
    }

    /**
     * Insert spot record into db
     * @param actuator_address
     * @param time long
     */
    public void createActuatorRecord(String actuator_address, long time) {
        String insertActuatorRecord = "INSERT INTO Actuator"
                + "(actuator_address, created_at, updated_at)"
                + ("VALUES (?,?,?)");
        try {
            PreparedStatement insert =
                connection.getConnection().prepareStatement(insertActuatorRecord);
            insert.setString(1, actuator_address);
            insert.setTimestamp(2, new Timestamp(time));
            insert.setTimestamp(3, new Timestamp(time));
            insert.executeUpdate();
        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing"
                + "insertActuatorRecord: " + e);
        }
    }

    public Actuator getActuator(String actuator_address) {
        String getActuator = "SELECT * " 
                + " FROM Actuator"
                + " WHERE Actuator.actuator_address LIKE ? ";

        try {
            /**
             * Execute select query
             */
            PreparedStatement record =
                connection.getConnection().prepareStatement(getActuator);
            record.setString(1, "%" + actuator_address.replace(".relay1", "") + "%");

            /**
             * Access ResultSet for zone_id
             */
            ResultSet result = record.executeQuery();
            if(result.next()) {
                /**
                 * Return result
                 */
                return new Actuator(result.getString("Actuator.actuator_address"), this.getActuatorJob(actuator_address));
            } else {
                return null;
            }

        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing "
                + "getActuator: " + e);
                return null;
        }
    }
    
    public int isActuatorOn(String actuator_address)
    {
        String isActuatorOn = "SELECT * FROM Actuator WHERE actuator_address LIKE ?";

        try {
            /**
             * Execute select query
             */
            PreparedStatement record =
                connection.getConnection().prepareStatement(isActuatorOn);
            record.setString(1, "%" + actuator_address.replace(".relay1", "") + "%");

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

    public ActuatorJob getActuatorJob(String actuator_address) {
        String getActuator = "SELECT * " 
                + " FROM actuator_job, Actuator"
                + " WHERE actuator_job.actuator_id = Actuator.id "
                + " AND Actuator.actuator_address LIKE ? "
                + " LIMIT 1";

        try {
            /**
             * Execute select query
             */
            PreparedStatement record =
                connection.getConnection().prepareStatement(getActuator);
            record.setString(1, "%" + actuator_address.replace(".relay1", "") + "%");

            /**
             * Access ResultSet for zone_id
             */
            ResultSet result = record.executeQuery();
            if(result.next()) {
                /**
                 * Return result
                 */
                return new ActuatorJob(result.getInt("actuator_job.job_id"), result.getString("actuator_job.direction"), result.getDouble("actuator_job.threshold"));
            } else {
                return null;
            }

        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing "
                + "getActuator: " + e);
                return null;
        }
    }

    public double getLatestReadingFromJobId(int job_id) {
        String reading_table = this.getReadingTableFromJobId(job_id); 
        String reading_field = this.getReadingFieldFromJobId(job_id);

        if(reading_table == null || reading_field == null) return -1;

        String getReading = "SELECT * " 
                + " FROM " + reading_table
                + " WHERE job_id = ? "
                + " ORDER BY id DESC " 
                + " LIMIT 1";

        try {
            /**
             * Execute select query
             */
            PreparedStatement record =
                connection.getConnection().prepareStatement(getReading);
            record.setInt(1, job_id);

            /**
             * Access ResultSet for zone_id
             */
            ResultSet result = record.executeQuery();
            if(result.next()) {
                /**
                 * Return result
                 */
                return result.getDouble(reading_table + "." + reading_field);
            } else {
                return -1;
            }

        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing "
                + "getReading: " + e);
                return -1;
        }
    }

    public String getReadingTableFromJobId(int job_id) {
        String getReadingTableFromJobId = "SELECT * " 
                + " FROM Job, Sensor"
                + " WHERE Sensor.id = Job.sensor_id "
                + " AND Job.id = ? "
                + " LIMIT 1";

        try {
            /**
             * Execute select query
             */
            PreparedStatement record =
                connection.getConnection().prepareStatement(getReadingTableFromJobId);
            record.setInt(1, job_id);

            /**
             * Access ResultSet for zone_id
             */
            ResultSet result = record.executeQuery();
            if(result.next()) {
                /**
                 * Return result
                 */
                return result.getString("Sensor.table");
            } else {
                return null;
            }

        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing "
                + "getReadingTableFromJobId: " + e);
                return null;
        }
    }

    public String getReadingFieldFromJobId(int job_id) {
        String getReadingTableFromJobId = "SELECT * " 
                + " FROM Job, Sensor"
                + " WHERE Sensor.id = Job.sensor_id "
                + " AND Job.id = ? "
                + " LIMIT 1";

        try {
            /**
             * Execute select query
             */
            PreparedStatement record =
                connection.getConnection().prepareStatement(getReadingTableFromJobId);
            record.setInt(1, job_id);

            /**
             * Access ResultSet for zone_id
             */
            ResultSet result = record.executeQuery();
            if(result.next()) {
                /**
                 * Return result
                 */
                return result.getString("Sensor.field");
            } else {
                return null;
            }

        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing "
                + "getReadingTableFromJobId: " + e);
                return null;
        }
    }


    /**
     * Gets spot_id of a SPOT given a spot_address
     * @param  spot_address spot_address of SPOT to get ID of 
     * @return spot_id
     */
    public int getSpotIdFromSpotAddress(String spot_address) {
        String getSpotId = "SELECT Spot.id " 
                + " FROM Spot"
                + " WHERE Spot.spot_address = ? ";

        try {
            /**
             * Execute select query
             */
            PreparedStatement record =
                connection.getConnection().prepareStatement(getSpotId);
            record.setString(1, spot_address);

            /**
             * Access ResultSet for zone_id
             */
            ResultSet result = record.executeQuery();
            if(result.next()) {
                /**
                 * Return result
                 */
                return result.getInt("Spot.id");
            } else {
                return -1;
            }

        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing "
                + "getSpotId: " + e);
                return -1;
        }
    }

    /**
     * Gets spot_address of SPOT from a search on what object it is tracking
     * 
     * Search is done using LIKE %object_title%, so object_title = "center" would return the 
     * first spot_address for objects with "center" in their name
     * 
     * @param  object_title object_title to search for related SPOT on  
     * @return spot_address
     */
    public String getSpotAddressFromObjectTitle(String object_title) {
        String getSpotAddress = "SELECT Spot.spot_address " 
                + " FROM Spot, Object"
                + " WHERE Spot.id = Object.spot_id "
                + " AND Object.title LIKE %?%";

        try {
            /**
             * Execute select query
             */
            PreparedStatement record =
                connection.getConnection().prepareStatement(getSpotAddress);
            record.setString(1, object_title);

            /**
             * Access ResultSet for zone_id
             */
            ResultSet result = record.executeQuery();
            if(result.next()) {
                /**
                 * Return result
                 */
                return result.getString("Spot.spot_address");
            } else {
                return null;
            }

        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing "
                + "getSpotAddress: " + e);
                return null;
        }
    }

    /**
     * Returns a spot's most recent zone id, given a spot address
     * @param  spot_address spot address to get the most recent zone for
     * @return int zone id
     */
    public int getZoneIdFromSpotAddress(String spot_address) {
        String getZoneId = "SELECT zone_spot.zone_id " 
                + " FROM Spot, zone_spot"
                + " WHERE Spot.spot_address = ? "
                + " AND zone_spot.spot_id = Spot.id "
                + " ORDER BY zone_spot.id DESC " 
                + " LIMIT 1";

        try {
            /**
             * Execute select query
             */
            PreparedStatement record =
                connection.getConnection().prepareStatement(getZoneId);
            record.setString(1, spot_address);

            /**
             * Access ResultSet for zone_id
             */
            ResultSet result = record.executeQuery();
            if(result.next()) {
                /**
                 * Return result
                 */
                return result.getInt("zone_spot.zone_id");
            } else {
                return -1;
            }

        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing "
                + "getZoneId: " + e);
                return -1;
        }
    }

    /**
     * Returns a tower's adjacent zone_id, given a spot address and a current zone_id
     * @param spot_address address of tower zone
     * @parem zone_id current zone_id of SPOT
     * @return int
     */
    public int getOtherTowerZone(String spot_address, int zone_id) {
        String getZoneIdTowerZone = "SELECT zone_object.zone_id "
         + "FROM Spot, Object, zone_object "
         + "WHERE Spot.spot_address = ? "
         + "AND Object.spot_id = Spot.id "
         + "AND zone_object.object_id = Object.id "
         + "AND zone_object.zone_id != ? "
         + "ORDER BY zone_object.id DESC "
         + "LIMIT 1";

        try {
            /**
             * Execute select query
             */
            PreparedStatement record =
                connection.getConnection().prepareStatement(getZoneIdTowerZone);

            record.setString(1, spot_address);
            record.setInt(2, zone_id);

            /**
             * Access ResultSet for zone_id
             */
            ResultSet result = record.executeQuery();
            if(result.next()) {
                /**
                 * Return result
                 */
                int other_zone_id = result.getInt("zone_object.zone_id");
                System.out.println("Zone id: " + other_zone_id);
                return other_zone_id;
            } else {
                System.out.println("No results for get other tower zone id ");
                return -1;
            }

        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing "
                + "getZoneIdTowerZone: " + e);
                return -1;
        }
    }

    /**
     * Returns a job_id, given a spot address and field column
     */
    public int getJobIdFromSpotAddressReadingField(String spot_address, String column_name) {
        String getJobId = "SELECT Job.id "
         + "FROM Job, Spot, Object, Sensor "
         + "WHERE Spot.spot_address = ? "
         + "AND Object.spot_id = Spot.id "
         + "AND Job.object_id = Object.id "
         + "AND Sensor.field = ? "
         + "AND Sensor.id = Job.sensor_id";

        try {
            /**
             * Execute select query
             */
            PreparedStatement record =
                connection.getConnection().prepareStatement(getJobId);

               //  System.out.println("Looking for job_id for: " + spot_address + " and column: " + column_name);

            record.setString(1, spot_address);
            record.setString(2, column_name);

            /**
             * Access ResultSet for job_id
             */
            ResultSet result = record.executeQuery();
            if(result.next()) {
                /**
                 * Return result
                 */
                int job_id = result.getInt("Job.id");
                // System.out.println("Job id: " + job_id);
                return job_id;
            } else {
                System.out.println("No results to get job_id ");
                return -1;
            }

        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing "
                + "getJobId: " + e);
                return -1;
        }
    }

    /**
     * Returns all sensor ports and thresholds for a given a spot address
     */
    public ArrayList getSensorPortsJobThresholdsFromSpotAddress(String spot_address) {
        String query = "SELECT `Sensor`.`port_number`, `Job`.`threshold` "
         + "FROM Job "
         + "LEFT JOIN Sensor "
         + "ON Sensor.id = Job.sensor_id "
         + "LEFT JOIN Object "
         + "ON Object.id = Job.object_id "
         + "LEFT JOIN Spot "
         + "ON Spot.id = Object.spot_id "
         + "WHERE Spot.spot_address = ? ";

        try {
            /**
             * Execute select query
             */
            PreparedStatement record =
                connection.getConnection().prepareStatement(query);

            record.setString(1, spot_address);

            /**
             * Access ResultSet for job_id
             */
            ResultSet result = record.executeQuery();

            /**
             * Return result
             */
            ArrayList output_array = new ArrayList();

            while (result.next()) {
                output_array.add((Object)
                        Integer.valueOf(result.getInt("Sensor.port_number")));
                output_array.add((Object)
                        Integer.valueOf(result.getInt("Job.threshold")));
            }

            if(output_array.size() == 0) 
                System.out.println("No results to get Sensor port number ");
                
            return output_array;

        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing "
                + "getJobId: " + e);
                return new ArrayList();
        }
    }

    /**
     * Returns all sensor thresholds for a given a spot address
     */
    public ArrayList getSensorThresholdsFromSpotAddress(String spot_address) {
        String query = "SELECT Job.threshold "
         + "FROM Job "
         + "LEFT JOIN Sensor "
         + "ON Sensor.id = Job.sensor_id "
         + "LEFT JOIN Object "
         + "ON Object.id = Job.object_id "
         + "LEFT JOIN Spot "
         + "ON Spot.id = Object.spot_id "
         + "WHERE Spot.spot_address = ? ";

        try {
            /**
             * Execute select query
             */
            PreparedStatement record =
                connection.getConnection().prepareStatement(query);

            record.setString(1, spot_address);

            /**
             * Access ResultSet for job_id
             */
            ResultSet result = record.executeQuery();

            /**
             * Return result
             */
            ArrayList output_array = new ArrayList();

            while (result.next()) {
                output_array.add((Object)Integer.valueOf(
                        result.getInt("Job.id")));
                System.out.println("Job id: " + result.getInt("Job.id"));
            }

            if(output_array.size() == 0) 
                System.out.println("No results to get job_id ");
                
            return output_array;
                
        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing "
                + "getJobId: " + e);
                return new ArrayList();
        }
    }

    /**
     * Insert Switch record to db
     * @param switch_id String
     * @param spot_address string
     * @param time long
     */
    public void createSwitchRecord(String switch_id, String spot_address, long time) {
        String insertSwitchRecord = "INSERT INTO Switch"
                + "(switch_id, spot_address, zone_id, created_at)"
                + ("VALUES (?,?,?,?)");
        try {
            PreparedStatement insert =
                connection.getConnection().prepareStatement(insertSwitchRecord);
            insert.setString(1, switch_id);
            insert.setString(2, spot_address);
            insert.setInt(3, this.getZoneIdFromSpotAddress(spot_address));
            insert.setTimestamp(4, new Timestamp(time));
            insert.executeUpdate();
        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing"
                + "createSwitchRecord: " + e);
        }
    }

    /**
     * Insert Zone record to db
     * @param zone_id int
     * @param spot_address int
     * @param time long
     */
    public void createZoneRecord(int zone_id, String spot_address, String tower_address, long time) {
        String insertZoneRecord = "INSERT INTO zone_spot"
                + "(zone_id, spot_id, job_id, created_at)"
                + ("VALUES (?,?,?,?)");
        try {
            int job_id = this.getJobIdFromSpotAddressReadingField(tower_address, "zone_id");
            if(job_id > 0) {
                PreparedStatement insert =
                    connection.getConnection().prepareStatement(insertZoneRecord);
                insert.setInt(1, zone_id);
                insert.setInt(2, this.getSpotIdFromSpotAddress(spot_address));
                insert.setInt(3, job_id);
                insert.setTimestamp(4, new Timestamp(time));
                insert.executeUpdate();
            } else {
                System.out.println("No job_id for this zone change reading!");
            }
        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing"
                + "createZoneRecord: " + e);
        }
    }


    /**
     * Insert motion Data record to db
     * @param motion int
     * @param zone_id int
     * @param time long
     */
    public void createMotionRecord(int motion, String spot_address, long time) {
        String insertMotionRecord = "INSERT INTO Motion"
                + "(motion, spot_address, zone_id, job_id, created_at)"
                + ("VALUES (?,?,?,?,?)");
        try {
            int job_id = this.getJobIdFromSpotAddressReadingField(spot_address, "motion");
            if(job_id > 0) {
                PreparedStatement insert =
                    connection.getConnection().prepareStatement(insertMotionRecord);
                insert.setInt(1, motion);
                insert.setString(2, spot_address);
                insert.setInt(3, this.getZoneIdFromSpotAddress(spot_address));
                insert.setInt(4, job_id);
                insert.setTimestamp(5, new Timestamp(time));
                insert.executeUpdate();
            } else {
                System.out.println("No job_id for this motion reading!");
            }
        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing"
                + "createMotionRecord: " + e);
        }
    }

    /**
     * Insert light Data record to db
     * @param light double
     * @param zone_id int
     * @param time long
     */
    public void createLightRecord(int light, String spot_address, long time) {
        String insertLightRecord = "INSERT INTO Light"
                + "(light_intensity, spot_address, zone_id, job_id, created_at)"
                + ("VALUES (?,?,?,?,?)");
        try {
            int job_id = this.getJobIdFromSpotAddressReadingField(spot_address, "light_intensity");
            if(job_id > 0) {
                PreparedStatement insert =
                    connection.getConnection().prepareStatement(insertLightRecord);
                insert.setInt(1, light);
                insert.setString(2, spot_address);
                insert.setInt(3, this.getZoneIdFromSpotAddress(spot_address));
                insert.setInt(4, job_id);
                insert.setTimestamp(5, new Timestamp(time));
                insert.executeUpdate();
            } else {
                System.out.println("No job_id for this light reading!");
            }
        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing"
                + "createLightRecord: " + e);
        }
    }

    /**
     * insert Thermonitor record to db
     * @param celsiusData double
     * @param fahrenheitData double
     * @param zone_id int
     * @param time long
     */
    public void createThermoRecord(double celsiusData, String spot_address, long time) {
        String insertThermoRecord = "INSERT INTO Heat"
                + "(heat_temperature, spot_address, zone_id, job_id, created_at)"
                + ("VALUES (?,?,?,?,?)");
        try {
            int job_id = this.getJobIdFromSpotAddressReadingField(spot_address, "heat_temperature");
            if(job_id > 0) {
               PreparedStatement insert =
                    connection.getConnection().prepareStatement(insertThermoRecord);
                insert.setDouble(1, celsiusData);
                insert.setString(2, spot_address);
                insert.setInt(3, this.getZoneIdFromSpotAddress(spot_address));
                insert.setInt(4, job_id);
                insert.setTimestamp(5, new Timestamp(time));
                insert.executeUpdate();
            } else {
                System.out.println("No job_id for this heat reading!");
            }
        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing"
                + "createThermoRecord: " + e);
        }
    }
    /**
     * Insert Accel Data record to db
     * @param accel double
     * @param zone_id int
     * @param time long
     */
    public void createAccelRecord(double accelData, String spot_address, long time) {
        String insertAccelRecord = "INSERT INTO Acceleration"
                + "(acceleration, spot_address, zone_id, job_id, created_at)"
                + ("VALUES (?,?,?,?,?)");
        try {
            int job_id = this.getJobIdFromSpotAddressReadingField(spot_address, "acceleration");
            if(job_id > 0) {
                PreparedStatement insert =
                    connection.getConnection().prepareStatement(insertAccelRecord);
                insert.setDouble(1, accelData);
                insert.setString(2, spot_address);
                insert.setInt(3, this.getZoneIdFromSpotAddress(spot_address));
                insert.setInt(4, job_id);
                insert.setTimestamp(5, new Timestamp(time));
                insert.executeUpdate();
            } else {
                System.out.println("No job_id for this acceleration reading!");
            }
        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing"
                + "createAccelRecord: " + e);
        }
    }

    /**
     * Insert water Data record to db
     * @param water_percent percent that the cup has been drank to 
     * @param zone_id int
     * @param time long
     */
    public void createWaterRecord(int water_percent, String spot_address, long time) {
        String insertWaterRecord = "INSERT INTO Water"
                + "(water_percent, spot_address, zone_id, job_id, created_at)"
                + ("VALUES (?,?,?,?,?)");
        try {
            int job_id = this.getJobIdFromSpotAddressReadingField(spot_address, "water_percent");
            if(job_id > 0) {
                PreparedStatement insert =
                    connection.getConnection().prepareStatement(insertWaterRecord);
                insert.setInt(1, water_percent);
                insert.setString(2, spot_address);
                insert.setInt(3, this.getZoneIdFromSpotAddress(spot_address));
                insert.setInt(4, job_id);
                insert.setTimestamp(5, new Timestamp(time));
                insert.executeUpdate();
            } else {
                System.out.println("No job_id for this water reading!");
            }
        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing"
                + "createWaterRecord: " + e);
        }
    }

    /**
     * insert Spot Record into DB
     * @param spot_address String
     */
    public void createSpotRecord(String spot_address) {
        String insertSpotRecord = "INSERT INTO Spot"
                + "(spot_id, created_at)"
                + "VALUES (?,?)";
        try {
           PreparedStatement insert =
                connection.getConnection().prepareStatement(insertSpotRecord);
            insert.setString(1, spot_address);
            insert.setDate(2, new Date(System.currentTimeMillis()));
            insert.executeUpdate();
        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing"
                + "createLightRecord: " + e);
        }
    }

    /**
     * create  and insert a new zone record to DB
     * @param title String
     */
    public void createZoneRecord(String title) {
        String insertZoneRecord = "INSERT INTO Zone"
                + "(title, created_at)"
                + "VALUES (?,?)";
        try {
           PreparedStatement insert =
                connection.getConnection().prepareStatement(insertZoneRecord);
            insert.setString(1, title);
            insert.setDate(2, new Date(System.currentTimeMillis()));
            insert.executeUpdate();
        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing"
                + "createLightRecord: " + e);
        }
    }

    /**
     * Relates a spot address, to a zone_id
     * @param spot_address string
     * @param spot_id int
     */
    public void createSpotZoneRecord(String spot_address, int spot_id) {
        String insertSpotZoneRecord = "INSERT INTO zone_spot"
                + "(spot_address, spot_id, created_at)"
                + "VALUES (?,?,?)";
        try {
           PreparedStatement insert =
                connection.getConnection().prepareStatement(insertSpotZoneRecord);
            insert.setString(1, spot_address);
            insert.setInt(2, spot_id);
            insert.setDate(3, new Date(System.currentTimeMillis()));
            insert.executeUpdate();
        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing"
                + "createLightRecord: " + e);
        }
    }

    /**
     * Returns past 7 days of light data
     */
    public ArrayList getPastWeekLight() {
        //ArrayList for collection data
        ArrayList lightDatums = new ArrayList();
        //find timestamps
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Timestamp lastWeekTimestamp = findDateRange(currentTimestamp, -1);
        //queary db for data
        String getLastWeek = "SELECT * FROM Light WHERE "
                + "created_at >= ? "//is greater than last week
                + "AND "
                + "created_at <= ? "; //is less than today
        PreparedStatement getData;
        try {
            getData =
            connection.getConnection().prepareStatement(getLastWeek);
            getData.setTimestamp(1, lastWeekTimestamp);
            getData.setTimestamp(2, currentTimestamp);
            ResultSet rs = (ResultSet)getData.executeQuery();
            //ArrayList for collection data
            while(rs.next())
            {
                lightDatums.add((Object)new LightData(
                    rs.getString("spot_address"),
                    rs.getDouble("light_intensity"),
                    rs.getTimestamp("created_at"),
                    rs.getInt("zone_id")
                    ));
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception getting Past Week Light" + e);
        }
            return lightDatums;
    }

    /**
     * Returns past 7 days of thermo data
     */
    public ArrayList getPastWeekThermo() {
        //ArrayList for collection data
        ArrayList thermoDatums = new ArrayList();
        //find timestamps
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Timestamp lastWeekTimestamp = findDateRange(currentTimestamp, -1);
        //queary db for data
        String getLastWeek = "SELECT * FROM Heat WHERE "
            + "created_at >= ? "//is greater than last week
            + "AND "
            + "created_at <= ? "; //is less than today
        PreparedStatement getData;
        try {
            getData =
                connection.getConnection().prepareStatement(getLastWeek);
            getData.setTimestamp(1, lastWeekTimestamp);
            getData.setTimestamp(2, currentTimestamp);
            ResultSet rs = (ResultSet)getData.executeQuery();
        //ArrayList for collection data
        while(rs.next())
        {
        thermoDatums.add((Object)new ThermoData(
            rs.getString("spot_address"),
            rs.getDouble("heat_temperature"),
            rs.getTimestamp("created_at"),
            rs.getInt("zone_id")
            ));
        }
        } catch (SQLException e) {
            System.err.println("SQL Exception getting Past Week Heat" + e);
        }
        return thermoDatums;
    }

    /**
     * Returns past 7 days of accelleration and thermo data
     */
    public ArrayList getPastAccelThermo() {
        //ArrayList for collection data
        ArrayList accelDatums = new ArrayList();
        //find timestamps
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Timestamp lastWeekTimestamp = findDateRange(currentTimestamp, -1);
        //queary db for data
        String getLastWeek = "SELECT * FROM Acceleration WHERE "
            + "created_at >= ? "//is greater than last week
            + "AND "
            + "created_at <= ? "; //is less than today
        PreparedStatement getData;
        try {
            getData =
                connection.getConnection().prepareStatement(getLastWeek);
            getData.setTimestamp(1, lastWeekTimestamp);
            getData.setTimestamp(2, currentTimestamp);
            ResultSet rs = (ResultSet)getData.executeQuery();
        //ArrayList for collection data
        while(rs.next())
        {
        accelDatums.add((Object)new AccelData(
            rs.getString("spot_address"),
            rs.getDouble("acceleration"),
            rs.getTimestamp("created_at"),
            rs.getInt("zone_id")
            ));
        }
        } catch (SQLException e) {
            System.err.println("SQL Exception getting Past Week Heat" + e);
        }
        return accelDatums;
    }

    public void updateBatteryPower(String spotAddress, int powerLevelPercentage)
    {
        String updatePower = "UPDATE Spot SET battery_percent = ? "
                + "where spot_address = ?";
        try{
            PreparedStatement putBattery;
            putBattery =
                    connection.getConnection().prepareStatement(updatePower);
            putBattery.setInt(1, powerLevelPercentage);
            putBattery.setString(2, spotAddress);
            putBattery.executeUpdate();
        }catch (Exception e) {
            System.out.println(e);
        }
    }
    private Timestamp findDateRange(Timestamp from, int noOfWeeks){
        Calendar past = Calendar.getInstance();
        past.setTimeInMillis(from.getTime());
        past.add(Calendar.WEEK_OF_YEAR, noOfWeeks);
        return new Timestamp(past.getTimeInMillis());
    }
}
