/**
 * Tower Receiver thread
 * Dominic Lindsay
 */
package org.sunspotworld.threads;

import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.IReceivingRadio;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.SunspotPort;
import java.lang.Exception;

public class TTowerReceiver implements Runnable
{
	private ISendingRadio sRadio;
	private IReceivingRadio rRadio;
	private static final int INNER_THRESHOLD = -7;
	private static final int OUTER_THRESHOLD = -20;
	public TTowerReceiver()
	{
		try
		{
			sRadio = RadiosFactory.createSendingRadio(new SunspotPort(SunspotPort.TOWER_RECIEVER_PORT));
			rRadio = RadiosFactory.createReceivingRadio(new SunspotPort(SunspotPort.TOWER_RECIEVER_PORT));
		} catch (Exception e) {
			System.err.println("error creating TOWER radios " + e);
		}
	}
	public void run()
	{
		int powerLevel;
		boolean inRange = false;
		while(true)
		{
			powerLevel = rRadio.pingRssiReader(); 
			if(powerLevel > INNER_THRESHOLD && !inRange) //within proximity of tower
			{
				inRange = true;
				System.out.println("within territory");
			}
			if(powerLevel < OUTER_THRESHOLD && inRange) //leaving proximity
			{
				//REPORT ADDRESS TO BASESTATION
				inRange = false;
				sRadio.sendSPOTAddress(rRadio.getLastPingAddress());
				System.out.println("leaving territory");
			}
		}
	}
}