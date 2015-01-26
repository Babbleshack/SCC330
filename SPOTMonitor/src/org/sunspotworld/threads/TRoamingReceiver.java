/**
 * Tower Receiver thread
 * Dominic Lindsay
 */
package org.sunspotworld.threads;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.IReceivingRadio;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.SunspotPort;
import java.lang.Exception;
import org.sunspotworld.data.TowerSpot;
import org.sunspotworld.data.ZonePowerData;

public class TRoamingReceiver extends Thread implements Runnable
{
	private ISendingRadio sRadio;
        private IReceivingRadio rRadio;
        private ZonePowerData zpd;
        private ITriColorLEDArray leds;
        private static final long SECOND = 1000;
        private static final long SAMPLE_RATE = (2*SECOND);
	public TRoamingReceiver(ZonePowerData zpd)
	{
            try
            {
                sRadio = RadiosFactory.createSendingRadio(new SunspotPort(SunspotPort.BASE_TOWER_PORT));
                rRadio = RadiosFactory.createReceivingRadio(new SunspotPort(SunspotPort.TOWER_RECIEVER_PORT));
            } catch (Exception e) {
                System.err.println("error creating Roaming radios " + e);
            }   
            zpd = zpd;
                     leds = (ITriColorLEDArray)
                             Resources.lookup(ITriColorLEDArray.class );
                     leds.setRGB(0, 255, 0);
                    leds.setOn();
	}
        /**
         * receives tower addresses every for SAMPLE_RATE
         * exits returning control to parent thread.
         */
	public void run()
	{
            long timer = (System.currentTimeMillis() + (SAMPLE_RATE));
            while(System.currentTimeMillis() >= timer)
            {
                TowerSpot tSpot = rRadio.receivePing();
                if(tSpot == null)
                {
                    continue;
                }
                zpd.AddOrUpdate(tSpot);   
            }
	}
}