/*
 * Defines set of database queries which must be implemented.
 * Dominic
 */
package org.sunspotworld.DB;

import org.sunspotworld.Collections.ArrayList;

/**
 *
 * @author Babblebase
 */
public interface IQueryManager 
{
	public int getZoneIdFromSpotAddress(String spot_address);
    public void createLightRecord(int light, String spot_address,
    	 long time);
    public void createAccelRecord(int accelData, String spot_address,
    		long time)
    public void createThermoRecord(double celciusData,
        String spot_address, long time);
    public void createSpotRecord(String spot_id);
    public void createZoneRecord(String title);
    public void createSpotZoneRecord(String spot_address, int spot_id);
    public ArrayList getPastWeekLight();
    public ArrayList getPastWeekThermo();
}
