/*
 * JDBC connection handler for MySQL
 * Dominic Lindsay
 */
package org.sunspotworld.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    /**
     * returns connection, if connection has not been set, a null 
     * pointer exception is thrown.
     * @return 
     */
    public Connection getConnection() {
        if(connection == null)
            throw new NullPointerException();
        return connection;
    }
}
