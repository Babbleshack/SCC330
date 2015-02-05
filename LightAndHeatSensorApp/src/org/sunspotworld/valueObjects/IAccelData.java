/**
 * encapsulates readings from Accelerometer
 * Dominic Lindsay
 */
package org.sunspotworld.valueObjects;

import java.sql.Timestamp;

public interface IAccelData 
{
	String getSpotAddress();
    double getAccelData();
    Timestamp getTime();
    int getZoneId();
	void setSpotAddress(String setSpotAddress);
    void setAccelData(double data);
    void setTime(Timestamp time);
    void setZoneId(int zoneId);
}