/*
 * Encapsulates caching operations by making good use of HashTable.
 * I kinda mucked this one up, i wanted it to cache a bit more data,
 * this will do for now.
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
        Integer zone = (Integer)zoneCache.get((Object)address);
        if(zone == null)
        {
            this.add(address, qm.getZoneIdFromSpotAddress(address));
        }
        return 0;   
    }
    
        
}
