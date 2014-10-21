/*
 * encapsulate Thermonitor data extends ISpotData
 * Dominic Lindsay
 */
package org.sunspotworld.DataTypes;

import java.sql.Timestamp;
/**
 *
 * @author Babblebase
 */
public interface IThermoData extends ISunSpotData
{
	String getSpotAddress();
	void setSpotAddress();
    double getCelciusData();
    void setCelciusData(double data);
    Timestamp getTime();
    void SetTime(Timestamp time);
    int getZoneId();
    void setZoneId(int zoneId);
}
