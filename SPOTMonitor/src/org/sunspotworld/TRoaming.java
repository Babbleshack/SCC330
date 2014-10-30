/**
 * TRoaming, Blocks until a ping from tower is received
 * at which point replies with address
 * Dominic Lindsay
 */
package org.sunspotworld;

import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.IReceivingRadio;
import org.sunspotworld.spotRadios.SunspotPort;
import com.sun.spot.util.Utils;
import java.lang.Exception;

public class TRoaming implements Runnable
{
	private ISendingRadio sRadio;
	private IReceivingRadio rRadio;
	public TRoaming()
	{
		try
		{
			rRadio = RadiosFactory.createReceivingRadio(new SunspotPort(SunspotPort.PING_PORT));
			sRadio = RadiosFactory.createSendingRadio(new SunspotPort(SunspotPort.TOWER_RECIEVER_PORT));
		} catch (Exception e)
		{
			System.err.println("Error innitializing Roaming Radios: " + e);
		}
	}
	public void run()
	{
		while(true)
		{
			rRadio.receiveAndDoNothing();
			sRadio.sendSPOTAddress(System.getProperty("IEEE_ADDRESS"));	
		}
	}
}