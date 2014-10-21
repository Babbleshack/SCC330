/**
 * encapsulates readings from Accelerometer
 * Dominic Lindsay
 */
package org.sunspotworld.DataTypes;

public interface IAccelData 
{
	String getSpotAddress();
	void setSpotAddress();
    void setAccelData(double data);
    double getAccelData();
    Timestamp getTime();
    void SetTime(Timestamp time);
    int getZoneId();
    void setZoneId(int zoneId);


}