package org.sunspotworld.threads;

import com.sun.spot.util.Utils;
import java.io.IOException;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.SunspotPort;

/**
 * Pings Sorounding Area at SAMPLE_RATE
 * @author Dominic Lindsay
 */
public class TTower implements Runnable
{
    private ISendingRadio radio;
    private static final long SECOND = 1000;
    private static final long SAMPLE_RATE = SECOND/2;
    public TTower() 
    {
         try
        {
            radio = RadiosFactory.createSendingRadio(
                    new SunspotPort(SunspotPort.PING_PORT));	
        } catch (PortOutOfRangeException e) {
            System.err.println("error creating PING radio: " + e);
        } catch (IOException e) {
            System.err.println("error creating PING radio: " + e);
        }
    }
    public void run()
    {
	while(true)
	{
            radio.ping();
            Utils.sleep(SAMPLE_RATE); //deschedule thread	
        }
    }
}
