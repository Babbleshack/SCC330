/*
 * Defines a database connection interface
 * Dominic Lindsay
 */
package org.sunspotworld.DB;

/**
 *
 * @author Babblebase
 */
public interface IDatabaseConnection {
    /**
     * creates a JDBC database connection
     */
    void connect();

    /**
     * disconnects JDBC database connection
     */
    void disconnect();
    
}
