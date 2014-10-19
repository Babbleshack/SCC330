/*
 * Factory for creating Database Connections
 * Dominic Lindsay
 */
package org.sunspotworld.DB;

import javax.microedition.io.Connection;

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
