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
    private double LightData;
    private Timestamp time;
    private int zoneID;
    
    public LightData(String spotAddress, double LightData) {
        this.spotAddress = spotAddress;
        this.LightData = LightData;
    }
    public LightData(String spotAddress, double LightData, Timestamp time) {
        this.spotAddress = spotAddress;
        this.LightData = LightData;
        this.time = time;
    }
    public LightData(String spotAddress, double LightData, Timestamp time,
            int zoneID) {
        this.spotAddress = spotAddress;
        this.LightData = LightData;
        this.time = time;
        this.zoneID = zoneID;
    }
    

    public String getSpotAddress() {
        return spotAddress;
    }

    public void setSpotAddress(String spotAddress) {
        this.spotAddress = spotAddress;
    }

    public double getLightData() {
        return LightData;
    }

    public void setLightData(double LightData) {
        this.LightData = LightData;
    }    

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }


}
