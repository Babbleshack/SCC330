/*
 * Factory for creating Database Connections
 * Dominic Lindsay
 */
package org.conditionManager.database;


/**
 *
 * @author babbleshack
 */
public class DatabaseConnectionFactory 
{
    public static IDatabaseConnectionManager createMySQLConnection()
    {
        return new MySQLConnectionManager();
    }
    
}
