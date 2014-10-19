/*
 * Handles quearies to Database
 * Dominic Lindsay
 * Adam Cornforth
 */
package org.sunspotworld.DB;

import java.sql.Date;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author Babblebase
 */
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
     * Returns a zone id, given a spot address
     * @param  spot_address [description]
     * @return              [description]
     */
    public int getZoneIdFromSpotAddress(String spot_address) {
        String getZoneId = "SELECT zone_id FROM Spot_Zone"
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
            result.next();

            /**
             * Return result
             */
            return result.getInt("zone_id");
        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing"
                + "createLightRecord: " + e);
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
                + "(light_intensity, spot_address, zone_id, created_at)"
                + ("VALUES (?,?,?,?)");
        try {
            PreparedStatement insert = 
                connection.getConnection().prepareStatement(insertLightRecord);
            insert.setInt(1, light);
            insert.setString(2, spot_address);
            insert.setInt(3, this.getZoneIdFromSpotAddress(spot_address));
            insert.setTimestamp(4, new Timestamp(time));
            insert.executeUpdate();
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
                + "(heat_temperature, spot_address, zone_id, created_at)"
                + ("VALUES (?,?,?,?)");
        try {
           PreparedStatement insert = 
                connection.getConnection().prepareStatement(insertThermoRecord);
            insert.setDouble(1, celsiusData);
            insert.setString(2, spot_address);
            insert.setInt(3, this.getZoneIdFromSpotAddress(spot_address));
            insert.setTimestamp(4, new Timestamp(time));
            insert.executeUpdate();
        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing"
                + "createThermoRecord: " + e);
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
        String insertSpotZoneRecord = "INSERT INTO Spot_Zone"
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
    
} 
