package org.sunspotworld;

import java.io.IOException;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotMonitors.ILightMonitor;
import org.sunspotworld.spotMonitors.LightMonitor;
import org.sunspotworld.spotMonitors.MonitorFactory;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.Patterns.Observer;
import org.sunspotworld.Patterns.Observable;
import com.sun.spot.util.Utils;

/**
 * Thread to send Light data
 */
public class TSendingLight implements Runnable, Observer
{
    private ILightMonitor lightMonitor;
    private static final int SAMPLE_RATE = 30 * 1000; //60 seconds

    // Init sending radio
    ISendingRadio lightSendingRadio;

    /**
     * Instantiates the monitor and sending radio required
     * for sending light data to the base station
     */
    public TSendingLight(int threshold)
    {
        try
        {
            lightMonitor = MonitorFactory.createLightMonitor(threshold);
            lightSendingRadio = RadiosFactory.createSendingRadio(lightMonitor.getPort());
            ((LightMonitor)lightMonitor).addObserver((Object)this);
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
     /**
     * Message received from monitor
     * pass to radio
     */
    public void update(Observable o, Object arg)
    {
        System.out.println(((ILightMonitor)o).getLightIntensity());
        lightSendingRadio.sendLight(((ILightMonitor)o).getLightIntensity());
    }
    public void update(Observable o)
    {
        lightSendingRadio.sendLight(((ILightMonitor)o).getLightIntensity());
    }
}