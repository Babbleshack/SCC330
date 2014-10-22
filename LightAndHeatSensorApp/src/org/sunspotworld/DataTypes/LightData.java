/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.DataTypes;

import java.sql.Timestamp;

/**
 *
 * @author Babblebase
 */
public class LightData implements ILightData 
{   
    private String spotAddress;
    private double lightData;
    private Timestamp time;
    private int zoneId;
    
    public LightData(String spotAddress, double lightData) {
        this.spotAddress = spotAddress;
        this.lightData = lightData;
    }
    public LightData(String spotAddress, double lightData, Timestamp time) {
        this.spotAddress = spotAddress;
        this.lightData = lightData;
        this.time = time;
    }
    public LightData(String spotAddress, double lightData, Timestamp time,
            int zoneId) {
        this.spotAddress = spotAddress;
        this.lightData = lightData;
        this.time = time;
        this.zoneId = zoneId;
    }
    
    public String getSpotAddress() {
        return spotAddress;
    }

    public void setSpotAddress(String spotAddress) {
        this.spotAddress = spotAddress;
    }

    public double getLightData() {
        return lightData;
    }

    public void setLightData(double lightData) {
        this.lightData = lightData;
    }    

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getZoneId(){
        return this.zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }


}
