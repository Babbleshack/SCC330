package org.sunspotworld.threads;

import java.io.IOException;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotMonitors.IThermoMonitor;
import org.sunspotworld.spotMonitors.ThermoMonitor;
import org.sunspotworld.spotMonitors.MonitorFactory;
import org.sunspotworld.spotRadios.SunspotPort;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.homePatterns.Observer;
import org.sunspotworld.homePatterns.Observable;
import com.sun.spot.util.Utils;

// import java.util.Observer;

/**
 * Thread to send Thermo data
 */
public class TDiscoverMe implements Runnable
{
    ISendingRadio discoverMeRadio;
    private static final int SAMPLE_RATE = 15 * 1000; // 15 seconds

    /**
     * Instantiates the monitor and sending radio required
     * for sending thermo data to the base station
     */
    public TDiscoverMe()
    {
        try
        {
            discoverMeRadio = RadiosFactory.createSendingRadio(new SunspotPort(90));
        }
        catch(Exception e)
        {
           System.out.println("Unable initiate discover me sending radio");
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
            discoverMeRadio.discoverMe(); 
            Utils.sleep(SAMPLE_RATE);
        }
    }

}