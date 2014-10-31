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

import org.sunspotworld.database.IQueryManager;
import org.sunspotworld.database.QueryManager;

public class TZoneController implements Runnable
{
	private IReceivingRadio rRadio;
	private QueryManager qm;
	public TZoneController()
	{
		try 
		{
			rRadio = RadiosFactory.createReceivingRadio(new SunspotPort(SunspotPort.BASE_TOWER_PORT));
		} catch (PortOutOfRangeException e) {
			System.err.println("Port out of range");
		}
		qm = new QueryManager();
	}
	public void run()
	{
		while(true)
		{
			System.out.println("Base station waiting for Tower forwarding of ping reply from SPOT");
			try {
				String spotAddress = rRadio.receiveZonePacket();
				String towerAddress = rRadio.getReceivedAddress();
				System.out.println("SPOT " + spotAddress + " has passed tower " + towerAddress);

				// Get current SPOT zone 
				int oldZoneID = qm.getZoneIdFromSpotAddress(spotAddress);
				int newZoneID;

				// Get adjacent zone for this tower
				newZoneID = qm.getOtherTowerZone(towerAddress, oldZoneID);  

				// Insert zone change to adjacent zone
				qm.createZoneRecord(newZoneID, spotAddress, towerAddress, System.currentTimeMillis());

			} catch(IOException io) {
				System.out.println("Could not receive received address: " + io);
			}
		}

	}
}

