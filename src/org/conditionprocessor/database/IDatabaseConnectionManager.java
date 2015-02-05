/*
 * Defines a database connection interface
 * Dominic Lindsay
 */
package org.conditionprocessor.database;

import java.sql.Connection;

/**
 *
 * @author Babblebase
 */
public interface IDatabaseConnectionManager {
    /**
     * creates a JDBC database connection
     */
    public void connect();

    /**
     * disconnects JDBC database connection
     */
    public void disconnect();
    
    /**
     * returns connection
     * @return Connection
     */
    public Connection getConnection();
    
}
