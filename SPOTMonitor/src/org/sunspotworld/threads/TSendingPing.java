/**
 * Pings sorround tower 'teritory'
 * A ping is simply an empty packet.
 * Dominic Lindsay
 */

package org.sunspotworld.threads;

import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.SunspotPort;
import com.sun.spot.util.Utils;
import java.lang.Exception;

public class TSendingPing implements Runnable
{
	private ISendingRadio radio;
	private static final long SECOND = 1000;
	private static final long SAMPLE_RATE = SECOND/2;
	public TSendingPing()
	{
		try
		{
			radio = RadiosFactory.createSendingRadio(new SunspotPort(SunspotPort.PING_PORT));	
		} catch (Exception e) {
			System.err.println("error creating PING radio: " + e);
		} 
		
	}
	public void run()
	{
		while(true)
		{
			radio.ping();
			Utils.sleep(SAMPLE_RATE); //give Schedular a chance to deschedule thread	
		}
	}
}