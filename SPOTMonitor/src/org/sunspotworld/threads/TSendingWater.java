package org.sunspotworld.threads;

import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotMonitors.MonitorFactory;
import com.sun.spot.util.Utils;
import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.homePatterns.TaskObserver;
import org.sunspotworld.spotMonitors.WaterMonitor;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.SunspotPort;

/**
 * Thread to send Water data
 * sens water when observable watermonitor calls update.
 */
public class TSendingWater implements Runnable, TaskObserver
{
    // private IWaterMonitor waterMonitor;
    private static final int SAMPLE_RATE = 250; //60 seconds

    // Init sending radio
    private ISendingRadio waterSendingRadio;
    private WaterMonitor waterMonitor;
    /**
     * Instantiates the monitor and sending radio required
     * for sending motion data to the base station
     */
    public TSendingWater()
    {
        waterMonitor = MonitorFactory.createWaterMonitor();
        try
        {
            waterSendingRadio = RadiosFactory.createSendingRadio(
                    new SunspotPort(SunspotPort.WATER_PORT)
            );
        }
        catch(Exception e)
        {
           System.out.println("Unable initiate WATER sending radio" 
             + e);
        }
        waterMonitor.addObserver((Object)this);
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
    public void update(TaskObservable o, Object arg)
    {
        waterSendingRadio.sendWater(((WaterMonitor)o).getDataAsInt());
    }
    public void update(TaskObservable o)
    {
        waterSendingRadio.sendWater(((WaterMonitor)o).getDataAsInt());
    }
}