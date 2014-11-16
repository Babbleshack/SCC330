package org.sunspotworld.threads;

import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotMonitors.ILightMonitor;
import org.sunspotworld.spotMonitors.LightMonitor;
import org.sunspotworld.spotMonitors.MonitorFactory;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.homePatterns.Observer;
import org.sunspotworld.homePatterns.Observable;
import com.sun.spot.util.Utils;
import org.sunspotworld.spotMonitors.IMonitor;

/**
 * Thread to send Light data
 */
public class TSendingLight implements Runnable, Observer
{
    private ILightMonitor lightMonitor;
    private int SAMPLE_RATE = 30 * 1000; //60 seconds
    private int monitorThreshold;
    private boolean thresholdMet;
    // Init sending radio
    private ISendingRadio lightSendingRadio;

    /**
     * Instantiates the monitor and sending radio required
     * for sending light data to the base station
     */
    public TSendingLight(int threshold)
    {
        this.monitorThreshold = threshold;
        this.thresholdMet = false;
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
            lightSendingRadio.sendLight(lightMonitor.getDataAsInt());
            Utils.sleep(SAMPLE_RATE);
        }
    }
     /**
     * Message received from monitor
     * pass to radio
     * @param o
     * @param arg
     */
    public void update(Observable o, Object arg)
    {
        lightSendingRadio.sendLight(((ILightMonitor)o).getDataAsInt());
    }
    public void update(Observable o)
    {
        lightSendingRadio.sendLight(((ILightMonitor)o).getDataAsInt());
    }
}