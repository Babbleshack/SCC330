/**
 * TZoneController
 * Processes zone transitions 
 */
package org.sunspotworld.threads;

import org.sunspotworld.basestationRadios.SunspotPort;
import org.sunspotworld.basestationRadios.IReceivingRadio;
import org.sunspotworld.basestationRadios.RadiosFactory;
import org.sunspotworld.basestationRadios.PortOutOfRangeException;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import org.sunspotworld.cache.CacheFactory;
import org.sunspotworld.cache.IZoneCache;
import org.sunspotworld.database.IQueryManager;

import org.sunspotworld.database.QueryManager;

public class TZoneController implements Runnable
{
	private IReceivingRadio rRadio;
        private IZoneCache zCache;
        private IQueryManager qm;
        private final ConcurrentHashMap<String, String> _addressMap;
	public TZoneController(ConcurrentHashMap addressMap)
	{
		try 
		{
			rRadio = RadiosFactory.createReceivingRadio(new SunspotPort(SunspotPort.BASE_TOWER_PORT));
		} catch (PortOutOfRangeException e) {
			System.err.println("Port out of range");
		}
                qm = new QueryManager();
		zCache = CacheFactory.createZoneCache(qm);
                _addressMap = addressMap;
	}
	public void run()
	{
		while(true)
		{
			try {
	            
                String towerAddress = rRadio.receiveZonePacket();
                String spotAddress = rRadio.getReceivedAddress();
                if(!_addressMap.contains(spotAddress)) continue;                
               int receivedSpotZoneID = qm.getZoneIdFromSpotAddress(spotAddress);
               int receivedTowerZoneID = qm.getZoneIdFromSpotAddress(towerAddress);
                // int receivedSpotZoneID = zCache.getZoneID(spotAddress);
                // int receivedTowerZoneID = zCache.getZoneID(towerAddress);

                System.out.println("Roaming SPOT " + spotAddress + " moved from zone " + receivedSpotZoneID
                + " to zone " + receivedTowerZoneID + " because it is closest to Tower SPOT " + towerAddress);

                // no zone change
                if(receivedSpotZoneID == receivedTowerZoneID) continue;
                
                // add to cache and database
                qm.createZoneRecord(receivedTowerZoneID, spotAddress, 
                        towerAddress, System.currentTimeMillis(), SunspotPort.BASE_TOWER_PORT);
                zCache.add(spotAddress, receivedTowerZoneID);
			} catch(IOException io) {
				System.out.println("Could not receive received address: " + io);
			}
		}

	}
}

