/*
 * Cache factory used to encapsulate cache creation.
 * Dominic Lindsay
 */
package org.sunspotworld.cache;

import org.sunspotworld.database.IQueryManager;

/**
 *
 * @author babbleshack
 */
public class CacheFactory 
{
    public static IZoneCache createZoneCache(IQueryManager qm)
    {
        return new ZoneCache(qm);
    }
    
}
