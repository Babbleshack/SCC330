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
import java.util.Vector;

public class TTowerReceiver implements Runnable
{
	private ISendingRadio sRadio;

	public TTowerReceiver()
	{
		try
		{
			sRadio = RadiosFactory.createSendingRadio(new SunspotPort(SunspotPort.BASE_TOWER_PORT));
			rRadio = RadiosFactory.createReceivingRadio(new SunspotPort(SunspotPort.TOWER_RECIEVER_PORT));
		} catch (Exception e) {
			System.err.println("error creating TOWER radios " + e);
		}
                towers = new Vector(NUMBER_OF_ZONES);
	}
	public void run()
	{
            long timer = (System.currentTimeMillis() + (5*SECOND));
            while(System.currentTimeMillis() != timer)
            {
                rRadio.re
            }
                int powerLevel;
		boolean inRange = false;
		while(true)
		{
			powerLevel = rRadio.receivePingReply(); 
			System.out.println("Power level: " + powerLevel);
			if(powerLevel > INNER_THRESHOLD && !inRange) //within proximity of tower
			{
				inRange = true;
				System.out.println("Within territory with power level: " + powerLevel);
			}
			if(powerLevel < OUTER_THRESHOLD && inRange) //leaving proximity
			{
				// REPORT ADDRESS TO BASESTATION
				inRange = false;
				sRadio.sendSPOTAddress(rRadio.getLastPingAddress());
				System.out.println("Leaving territory with power level: " + powerLevel);
			}
		}
	}
}