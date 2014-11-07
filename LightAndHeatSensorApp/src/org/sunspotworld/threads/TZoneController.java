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
import org.sunspotworld.cache.CacheFactory;
import org.sunspotworld.cache.IZoneCache;
import org.sunspotworld.database.DatabaseConnectionFactory;

import org.sunspotworld.database.IQueryManager;
import org.sunspotworld.database.QueryManager;

public class TZoneController implements Runnable
{
	private IReceivingRadio rRadio;
        private IZoneCache zCache;
        private IQueryManager qm;
	public TZoneController()
	{
		try 
		{
			rRadio = RadiosFactory.createReceivingRadio(new SunspotPort(SunspotPort.BASE_TOWER_PORT));
		} catch (PortOutOfRangeException e) {
			System.err.println("Port out of range");
		}
                qm = new QueryManager();
		zCache = CacheFactory.createZoneCache(qm);
	}
	public void run()
	{
		while(true)
		{
			System.out.println("Base station waiting for Tower forwarding of ping reply from SPOT");

			try {
                                //receive address.
                                //get location from db
                                //go nuts
                                //still basically the same only with 
                                //Chocolate Brownies
				String towerAddress = rRadio.receiveZonePacket();
				String spotAddress = rRadio.getReceivedAddress();
				System.out.println("SPOT " + spotAddress + " is closest to " + towerAddress);
				// Get current SPOT zon
                                
                                int receivedSpotZoneID = qm.getZoneIdFromSpotAddress(spotAddress);
                                int receivedTowerZoneID = qm.getZoneIdFromSpotAddress(towerAddress);
                                System.out.println("RECEIVED SPOT ZONE: " + receivedSpotZoneID
                                + "RECEIVED TOWER ZONE ID: " + receivedTowerZoneID );
                                if(receivedSpotZoneID == receivedTowerZoneID)
                                { //no zone change
                                    continue;
                                }
                                //add to cache and database
                                qm.createZoneRecord(receivedTowerZoneID, spotAddress, 
                                        towerAddress, System.currentTimeMillis());
                                System.out.println("[" + spotAddress + "]" + "was added to " + "[" +
                                receivedTowerZoneID  + "]" + "[" + towerAddress + "]");
                                //zCache.add(spotAddress, receivedTowerZoneID);
			} catch(IOException io) {
				System.out.println("Could not receive received address: " + io);
			}
		}

	}
}

