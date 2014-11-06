package org.sunspotworld.threads;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
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
    private ITriColorLEDArray leds;
    private ITriColorLED led;
    private static final long SECOND = 1000;
    private static final long SAMPLE_RATE = SECOND;
    
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
         leds = (ITriColorLEDArray)Resources.lookup(ITriColorLEDArray.class );
         leds.setRGB(0, 0, 255);
         leds.setOn();
         led = leds.getLED(7);
         led.setRGB(255, 0, 0);
         System.out.println("STARTING TOWER");
    }
    public void run()
    {
	while(true)
	{
            
            led.setOn();
            radio.ping();
            led.setOff();
            Utils.sleep(SAMPLE_RATE); //deschedule thread	
        }
    }
}
