/*
 * Handles quearies to Database
 * Dominic Lindsay
 * Adam Cornforth
 */
package org.sunspotworld.DB;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.util.Calendar;
import org.sunspotworld.Collections.ArrayList;
import org.sunspotworld.DataTypes.LightData;
import org.sunspotworld.DataTypes.ThermoData;
import org.sunspotworld.DataTypes.AccelData;


public class QueryManager implements IQueryManager
{
    private IDatabaseConnectionManager connection;
    private static final String DB_NAME = "testing";
    private PreparedStatement stm = null;
    
    public QueryManager()
    {
        connection = DatabaseConnectionFactory.createMySQLConnection();
    }

    public Boolean isSpotExists(String spot_address) {
        String isSpotExists = "SELECT * FROM Spot WHERE spot_address = ?";

        try {
            /**
             * Execute select query
             */
            PreparedStatement record = 
                connection.getConnection().prepareStatement(isSpotExists);
            record.setString(1, spot_address);

            /**
             * Access ResultSet for zone_id
             */
            ResultSet result = record.executeQuery();
            
            /**
             * Return result
             */
            if(result.next()) 
                return true;
            else
                return false; 

        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing "
                + "isSpotExists: " + e);
                return false;
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
     * Returns a zone id, given a spot address
     * @param  spot_address [description]
     * @return              [description]
     */
    public int getZoneIdFromSpotAddress(String spot_address) {
        String getZoneId = "SELECT zone_id FROM zone_spot"
                + " WHERE spot_address = ?";
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
                return result.getInt("zone_id");

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
                System.out.println("Job id: " + job_id);
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
     * returns past 7 days of Light Data
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
    
    private Timestamp findDateRange(Timestamp from, int noOfWeeks){
        Calendar past = Calendar.getInstance();
        past.setTimeInMillis(from.getTime());
        past.add(Calendar.WEEK_OF_YEAR, noOfWeeks);
        return new Timestamp(past.getTimeInMillis());
    }   
} 
