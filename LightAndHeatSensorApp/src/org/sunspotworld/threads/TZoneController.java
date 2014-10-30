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
			System.out.println("About to wait on TOWER!");
			String spotAddress = rRadio.receiveZonePacket();
			System.out.println("ADDRESS FROM TOWER " + spotAddress);
			int oldZoneID = qm.getZoneIdFromSpotAddress(spotAddress);
			int newZoneID = oldZoneID;
			try {
				newZoneID = qm.getOtherTowerZone(rRadio.getReceivedAddress(), oldZoneID);  
			} catch(IOException io) {
				System.out.println("Could not receive received address: " + io);
			}
			qm.createZoneRecord(newZoneID, spotAddress, System.currentTimeMillis());
		}

	}
}

