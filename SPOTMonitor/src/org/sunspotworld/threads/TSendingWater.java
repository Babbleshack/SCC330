package org.sunspotworld.threads;

import java.io.IOException;
import org.sunspotworld.spotRadios.ISendingRadio;
// import org.sunspotworld.spotMonitors.IWaterMonitor;
// import org.sunspotworld.spotMonitors.WaterMonitor;
import org.sunspotworld.spotMonitors.MonitorFactory;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.homePatterns.Observer;
import org.sunspotworld.homePatterns.Observable;
import com.sun.spot.util.Utils;

/**
 * Thread to send Motion data
 */
public class TSendingWater implements Runnable, Observer
{
    // private IWaterMonitor waterMonitor;
    private static final int SAMPLE_RATE = 60 * 1000; //60 seconds

    // Init sending radio
    private ISendingRadio waterSendingRadio;

    /**
     * Instantiates the monitor and sending radio required
     * for sending motion data to the base station
     */
    public TSendingWater()
    {
        // waterMonitor = MonitorFactory.createWaterMonitor();

        try
        {
            // waterSendingRadio = RadiosFactory.createSendingRadio(((WaterMonitor)waterMonitor).getPort());
            
        }
        catch(Exception e)
        {
           System.out.println("Unable initiate WATER sending radio" 
             + e);
        }
        // ((WaterMonitor)waterMonitor).addObserver((Object)this);
    }

    public void startPolling() throws Exception
    {
    }

    public void run()
    {
        // main switch reading/polling loop
        while (true)
        {
            Utils.sleep(SAMPLE_RATE);
        }
    }
     /**
     * Message received from monitor
     * pass to radio
     */
    public void update(Observable o, Object arg)
    {
        // waterSendingRadio.sendWater(((IWaterMonitor)o).getDataAsInt());
    }
    public void update(Observable o)
    {
        // waterSendingRadio.sendWater(((IWaterMonitor)o).getDataAsInt());
    }
}