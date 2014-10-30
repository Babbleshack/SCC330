/*
 * encapsulate Light data extend ISpotData
 */
package org.sunspotworld.valueObjects;

import java.sql.Timestamp;
/**
 *
 * @author Babblebase
 */
public interface ILightData extends ISunSpotData
{
    String getSpotAddress();
    Timestamp getTime();
    double getLightData();
    void setSpotAddress(String spotAddress);
    void setLightData(double data);
    void setTime(Timestamp time);
	
}
