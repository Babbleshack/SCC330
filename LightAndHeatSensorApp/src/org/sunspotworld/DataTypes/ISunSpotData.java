/*
 * Generic Super Interface for Sun Spot Data
 * Dominic Lindsay
 */
package org.sunspotworld.DataTypes;

import java.util.Date;

/**
 *
 * @author Babblebase
 */
public interface ISunSpotData {
    void setSpotAddress(String addr);
    void setTime(long time);
    long getTime();
    String getSpotAddress();  
}
