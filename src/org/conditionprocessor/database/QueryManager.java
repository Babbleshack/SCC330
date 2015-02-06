/**
 *
 * @author Dominic Lindsay
 */
package org.conditionprocessor.database;


public class QueryManager {
    IDatabaseConnectionManager connection;
    public QueryManager()
    {
        connection = DatabaseConnectionFactory.createMySQLConnection();
    }
    
    
}
