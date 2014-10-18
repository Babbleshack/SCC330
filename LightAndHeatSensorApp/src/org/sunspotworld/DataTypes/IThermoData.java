/*
 * encapsulate Thermonitor data extends ISpotData
 * Dominic Lindsay
 */
package org.sunspotworld.DataTypes;

/**
 *
 * @author Babblebase
 */
public interface IThermoData extends ISunSpotData
{
    double getCelciusData();
    double getFahrenheitData();
    void setCelciusData(double data);
    void setFahrenheitData(double data);
}
