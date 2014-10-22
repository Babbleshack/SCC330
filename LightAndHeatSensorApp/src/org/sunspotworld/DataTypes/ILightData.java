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
    double getLightData();
	String getSpotAddress();
    Timestamp getTime();
    int getZoneId();
	void setSpotAddress(String setSpotAddress);
    void setLightData(double data);
    void setTime(Timestamp time);
    void setZoneId(int zoneId);
}
