/*
 * encapsulate thermo data
 * Dominic Lidnsay
 */
package org.sunspotworld.DataTypes;

import java.sql.Timestamp;

/**
 *
 * @author Babblebase
 */
public class ThermoData implements IThermoData {

    private String spotAddress;
    private double celciusData;
    private Timestamp time;
    private int zoneId;

    public ThermoData(String spotAddress, double celciusData, 
        Timestamp time, int zoneId) {
        this.spotAddress = spotAddress;
        this.celciusData = celciusData;
        this.time = time;
        this.zoneId = zoneId;
    }
    public ThermoData(String spotAddress, double FahrenheitData, double celciusData) {
        this.spotAddress = spotAddress;
        this.celciusData = celciusData;
    }
    
    public String getSpotAddress() {
        return spotAddress;
    }

    public void setSpotAddress(String spotAddress) {
        this.spotAddress = spotAddress;
    }

    public double getCelciusData() {
        return celciusData;
    }

    public void setCelciusData(double celciusData) {
        this.celciusData = celciusData;
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
