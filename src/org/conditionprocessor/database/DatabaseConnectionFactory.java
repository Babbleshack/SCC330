/*
 * Factory for creating Database Connections
 * Dominic Lindsay
 */
package org.conditionprocessor.database;


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
