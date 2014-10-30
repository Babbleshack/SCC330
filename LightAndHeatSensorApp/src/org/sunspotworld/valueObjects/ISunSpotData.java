/*
 * Generic Super Interface for Sun Spot Data
 * Dominic Lindsay
 */
package org.sunspotworld.valueObjects;

import java.sql.Timestamp;

/**
 *
 * @author Babblebase
 */
public interface ISunSpotData {
    void setSpotAddress(String addr);
    void setTime(Timestamp time);
    Timestamp getTime();
    String getSpotAddress();  
}
