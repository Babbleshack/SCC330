/*
 * Encapsulates caching operations by making good use of HashTable.
 * I kinda mucked this one up, i wanted it to cache a bit more data,
 * this will do for now.
 * data should be added after insertion to db, 
 * when data is requested if it is not present in cache db will be queried
 * I just found out this saves about 400ms!
 * Dominic Lindsay
 */
package org.sunspotworld.cache;

import java.util.Hashtable;
import org.sunspotworld.database.IQueryManager;

/**
 *
 * @author babbleshack
 */
public class ZoneCache implements IZoneCache {
    private Hashtable zoneCache;
    private IQueryManager qm;
    private static final int NO_ZONE = -1;
    public ZoneCache(IQueryManager qm)
    {   
        zoneCache = new Hashtable();
        this.qm = qm;
    }
    /**
     * wrapper for hashtable, if key already exists, replaces previous
     * entry, adds record to database.
     */
    public void add(String address, int zoneId)
    {
        zoneCache.put(address, Integer.valueOf(zoneId));
    }
    /**
     * wrapper for get, if the zone address is not in cache, 
     * the cache will retreive it from the db.
     */
    public int getZoneID(String address)
    {
        int zone = NO_ZONE;
        zone = ((Integer)(zoneCache.get(address))).intValue();
        if(zone == NO_ZONE) //then get it and add it to cache
        {
            System.out.println("DID NOT FIND ZONE IN CACHE");
            zone = ((Integer)(zoneCache.get(address))).intValue();
           
            this.add(address, zone);
        }
        return zone;
    }
    
        
}
