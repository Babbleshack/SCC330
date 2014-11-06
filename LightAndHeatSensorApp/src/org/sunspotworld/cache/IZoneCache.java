/*
 * Interface defininig  Zone Cache.
 * Dominic Lindsay
 */
package org.sunspotworld.cache;

/**
 *
 * @author babbleshack
 */
public interface IZoneCache {

    /**
     * wrapper for hashtable, if key already exists, replaces previous
     * entry
     */
    void add(String address, int zoneId);

    /**
     * wrapper for get, if the zone address is not in cache,
     * the cache will retreive it from the db.
     */
    int getZoneID(String address);
    
}
