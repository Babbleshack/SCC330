/*
 * encapsulate Light data extend ISpotData
 */
package org.sunspotworld.DataTypes;

import java.sql.Timestamp;
/**
 *
 * @author Babblebase
 */
public interface ILightData extends ISunSpotData
{
    String getSpotAddress();
    void setSpotAddress(String spotAddress);
    void setLightData(double data);
    double getLightData();
	String getSpotAddress();
    Timestamp getTime();
    void setTime(Timestamp time);
    int getZoneId();
	void setSpotAddress(String setSpotAddress);
    void setLightData(double data);
    void setZoneId(int zoneId);
}
