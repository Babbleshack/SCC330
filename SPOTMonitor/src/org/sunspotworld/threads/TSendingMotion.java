package org.sunspotworld.threads;

import java.io.IOException;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotMonitors.IMotionMonitor;
import org.sunspotworld.spotMonitors.MotionMonitor;
import org.sunspotworld.spotMonitors.MonitorFactory;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.homePatterns.Observer;
import org.sunspotworld.homePatterns.Observable;
import com.sun.spot.util.Utils;

/**
 * Thread to send Motion data
 */
public class TSendingMotion implements Runnable, Observer
{
    private IMotionMonitor motionMonitor;
    private static final int SAMPLE_RATE = 60 * 1000; //60 seconds

    // Init sending radio
    private ISendingRadio motionSendingRadio;

    /**
     * Instantiates the monitor and sending radio required
     * for sending motion data to the base station
     */
    public TSendingMotion()
    {
        motionMonitor = MonitorFactory.createMotionMonitor();

        try
        {
          // motionMonitor = MonitorFactory.createMotionMonitor();
            motionSendingRadio = RadiosFactory.createSendingRadio(((MotionMonitor)motionMonitor).getPort());
            
        }
        catch(Exception e)
        {
           System.out.println("Unable initiate MOTION sending radio" 
             + e);
        }
        ((MotionMonitor)motionMonitor).addObserver((Object)this);
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
            //motionSendingRadio.sendMotionTime(motionMonitor.getMotionTime());
            Utils.sleep(SAMPLE_RATE);
        }
    }
     /**
     * Message received from monitor
     * pass to radio
     */
    public void update(Observable o, Object arg)
    {
        motionSendingRadio.sendMotionTime(((IMotionMonitor)o).getDataAsLong());
    }
    public void update(Observable o)
    {
        motionSendingRadio.sendMotionTime(((IMotionMonitor)o).getDataAsLong());
    }
}