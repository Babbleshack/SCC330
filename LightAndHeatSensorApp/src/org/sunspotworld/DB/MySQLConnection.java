/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Babblebase
 */
public class MySQLConnection implements IDatabaseConnection 
{
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/SPOT";
    private static final String USERNAME = "babbleshack";
    private static final String PASSWORD = "password";
    private Connection connection = null;
    
    
    public MySQLConnection()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            System.err.println("MySQL driver not found" + e);
        }
    }
    @Override
    public void connect()
    {
        try { 
            connection  =
                DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            System.err.println("Failed to connect to DB" + ex);
        }
    }
    
    @Override
    public void disconnect()
    {
        try
        {
            connection.close();
        } catch (Exception e) {
            System.err.println("connection failed to close! " + e);
        }
    }  
}
