/**
 * TRoaming, Blocks until a ping from tower is received
 * at which point replies with address
 * Dominic Lindsay
 */
package org.sunspotworld.threads;

import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.IReceivingRadio;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.ITriColorLED;
import org.sunspotworld.spotRadios.SunspotPort;
import com.sun.spot.util.Utils;
import java.lang.Exception;

public class TRoaming implements Runnable
{
	private ISendingRadio sRadio;
	private IReceivingRadio rRadio;
	ITriColorLEDArray leds;
	ITriColorLED waiting;
	ITriColorLED ping;

	public TRoaming()
	{
		try
		{
			rRadio = RadiosFactory.createReceivingRadio(new SunspotPort(SunspotPort.PING_PORT));
			sRadio = RadiosFactory.createSendingRadio(new SunspotPort(SunspotPort.TOWER_RECIEVER_PORT));
			leds = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
			waiting = leds.getLED(0);
			ping = leds.getLED(1);
			waiting.setRGB(0,255,0);
			ping.setRGB(0,0,255);
		} catch (Exception e)
		{
			System.err.println("Error innitializing Roaming Radios: " + e);
		}
	}
	public void run()
	{
		while(true)
		{
			waiting.setOn();
			String tower_address = rRadio.receivePing();
			waiting.setOff();
			ping.setOn();
			System.out.println("received PING");
			sRadio.sendTowerAddress(tower_address);
			ping.setOff();	
		}
	}
}