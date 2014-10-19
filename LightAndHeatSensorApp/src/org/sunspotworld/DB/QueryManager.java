/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.DB;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    public void createLightRecord(double Light, int zone_id, long time) {
        String insertLightRecord = "INSET INTO light"
                + "(light_intensity, zone_id, created_at)"
                + ("?,?,?,?");
        try {
            PreparedStatement insert = 
                connection.getConnection().prepareStatement(insertLightRecord);
            insert.setDouble(1, Light);
            insert.setInt(2, zone_id);
            insert.setDate(3, new Date(time));
        } catch (SQLException e) {
                System.err.println("SQL Exception while preparing/Executing"
                + "createLightRecord: " + e);
        }
   
    }

    public void createThermoRecord(double CelciusData, Double fahrenheitData, int zone_id) {
    }

    public void createSpotRecord(String spot_id) {
    }

    public void createZoneRecord(String title) {
    }
    
} 
    
