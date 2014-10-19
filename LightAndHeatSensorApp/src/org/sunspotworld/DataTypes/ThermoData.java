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

    private String SpotAddress;
    private double FahrenheitData;
    private double CelciusData;
    private Timestamp time;
    
    public ThermoData(String SpotAddress, double FahrenheitData, double CelciusData) {
        this.SpotAddress = SpotAddress;
        this.FahrenheitData = FahrenheitData;
        this.CelciusData = CelciusData;
    }
    public String getSpotAddress() {
        return SpotAddress;
    }

    public void setSpotAddress(String SpotAddress) {
        this.SpotAddress = SpotAddress;
    }

    public double getFahrenheitData() {
        return FahrenheitData;
    }

    public void setFahrenheitData(double FahrenheitData) {
        this.FahrenheitData = FahrenheitData;
    }

    public double getCelciusData() {
        return CelciusData;
    }

    public void setCelciusData(double CelciusData) {
        this.CelciusData = CelciusData;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
  
}
