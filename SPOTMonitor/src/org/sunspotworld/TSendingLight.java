/*
 * SunSpotHostApplication.java
 *
 * Created on 13-Oct-2014 23:19:07;
 */

package org.sunspotworld;

import java.io.IOException;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotMonitors.ILightMonitor;
import org.sunspotworld.spotMonitors.MonitorFactory;
import org.sunspotworld.spotRadios.RadiosFactory;
import com.sun.spot.util.Utils;

/**
 * Sample Sun SPOT host application
 */
public class TSendingLight implements Runnable
{
    private ILightMonitor lightMonitor;
    private static final int SAMPLE_RATE = 60 * 1000; //60 seconds

    // Init sending radio
    ISendingRadio lightSendingRadio;

    /**
     * Instantiates the monitor and sending radio required
     * for sending light data to the base station
     */
    public TSendingLight()
    {
        try
        {
            lightMonitor = MonitorFactory.createLightMonitor();
            lightSendingRadio = RadiosFactory.createSendingRadio(lightMonitor.getPort());
        }
        catch(Exception e)
        {
           System.out.println("Unable initiate thermo sending radio");
        }
    }

    public void startPolling() throws Exception
    {
    }

    public void run()
    {
        // main switch reading/polling loop
        while (true)
        {
            // Send light reading
            lightSendingRadio.sendLight(lightMonitor.getLightIntensity());
            Utils.sleep(SAMPLE_RATE);
        }
    }
}