/*
 * Defines set of database queries which must be implemented.
 * Dominic
 */
package org.sunspotworld.database;

import java.util.ArrayList;

/**
 *
 * @author Babblebase
 */
public interface IQueryManager 
{
	public int getZoneIdFromSpotAddress(String spot_address);
    public int getJobIdFromSpotAddressReadingFieldPortNumber(String spot_address, String column_name, int port_number);
    public int isSpotExists(String spot_address);
    public String getSpotAddressFromObjectTitle(String object_title);
    public void createSwitchRecord(String switch_id, String spot_address, long time);
    public void createLightRecord(double light, String spot_address, long time, int port_number);
    public void createAccelRecord(double accelData, String spot_address, long time, int port_number);
    public void createThermoRecord(double celciusData, String spot_address, long time, int port_number);
    public void createBarometerRecord(double bearing, String spot_address, long time, int port_number);
    public void createWaterRecord(int water_percent, String spot_address, long time, int port_number);
    public void createSpotRecord(String spot_id);
    public void createZoneRecord(String title);
    public void createSpotZoneRecord(String spot_address, int spot_id);
    public ArrayList getPastWeekLight();
    public ArrayList getPastWeekThermo();

    public int getOtherTowerZone(String towerAddress, int oldZoneID);
    
    public void updateBatteryPower(String spotAddress, int powerLevelPercentage);


    public void createZoneRecord(int newZoneID, String spotAddress, String towerAddress, long currentTimeMillis, int port_number);
    //basestation stuff
    public boolean checkIfBaseStationExists(String bsAddress);
    public void addBasestation(String bsAddress);
    public boolean doesSpotBelongToBS(String bsAddress, String spotAddress);
    public ArrayList<String> getSpots(String bsAddress);
    
}
