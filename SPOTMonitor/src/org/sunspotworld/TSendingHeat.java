/*
 * SunSpotHostApplication.java
 *
 * Created on 13-Oct-2014 23:19:07;
 */

package org.sunspotworld;

import java.io.IOException;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotMonitors.IThermoMonitor;
import org.sunspotworld.spotMonitors.MonitorFactory;
import org.sunspotworld.spotRadios.RadiosFactory;
import com.sun.spot.util.Utils;

/**
 * Sample Sun SPOT host application
 */
public class TSendingHeat implements Runnable
{
    private IThermoMonitor thermoMonitor;
    private static final int SAMPLE_RATE = 60 * 1000; //60 seconds

    // Init sending radio
    ISendingRadio thermoSendingRadio;

    /**
     * Instantiates the monitor and sending radio required
     * for sending thermo data to the base station
     */
    public TSendingHeat()
    {
        try
        {
            thermoMonitor = MonitorFactory.createThermoMonitor();
            thermoSendingRadio = RadiosFactory.createSendingRadio(thermoMonitor.getPort());
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
            // Send thermo reading
            thermoSendingRadio.sendHeat(thermoMonitor.getCelsiusTemp());
            Utils.sleep(SAMPLE_RATE);
        }
    }
}