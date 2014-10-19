/*
 * Defines set of database quearies which must be implemented.
 * Dominic
 */
package org.sunspotworld.DB;

/**
 *
 * @author Babblebase
 */
public interface IQueryManager 
{
    public void createLightRecord(double Light, int zone_id, long time);
    public void createThermoRecord(double CelciusData, Double fahrenheitData,
            int zone_id, long time);
    public void createSpotRecord(String spot_id);
    public void createZoneRecord(String title);
    
    
}
