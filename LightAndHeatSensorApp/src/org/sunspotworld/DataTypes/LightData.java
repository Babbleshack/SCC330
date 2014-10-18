/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.DataTypes;

/**
 *
 * @author Babblebase
 */
public class LightData implements ILightData 
{   
    private String spotAddress;
    private double LightData;
    
    public LightData(String spotAddress, double LightData) {
        this.spotAddress = spotAddress;
        this.LightData = LightData;
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
}
