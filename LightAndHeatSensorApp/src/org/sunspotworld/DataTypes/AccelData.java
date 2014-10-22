/**
 * Encapsulates accelerometer data
 * Dominic Lindsay
 */
package org.sunspotworld.DataTypes;

import java.sql.Timestamp;

public class AccelData implements IAccelData
{
	private String spotAddress;
	private double accelData;
	private Timestamp time;
	private int zoneId;


    public String getSpotAddress() {
        return spotAddress;
    }

    public void setSpotAddress(String spotAddress) {
        this.spotAddress = spotAddress;
    }

    public double getAccelData() {
        return accelData;
    }

    public void setAccelData(double accelData) {
        this.accelData = accelData;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getZoneId()
    {
        return this.zoneId;
    }

    public void setZoneId(int zoneId)
    {
        this.zoneId = zoneId;
    }

}