package org.sunspotworld;

import java.io.IOException;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotMonitors.IMotionMonitor;
import org.sunspotworld.spotMonitors.MotionMonitor;
import org.sunspotworld.spotMonitors.MonitorFactory;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.Patterns.Observer;
import org.sunspotworld.Patterns.Observable;
import com.sun.spot.util.Utils;

/**
 * Thread to send Motion data
 */
public class TSendingMotion implements Runnable, Observer
{
    private IMotionMonitor MotionMonitor;
    private static final int SAMPLE_RATE = 60 * 1000; //60 seconds

    // Init sending radio
    ISendingRadio motionSendingRadio;

    /**
     * Instantiates the monitor and sending radio required
     * for sending motion data to the base station
     */
    public TSendingMotion()
    {
        try
        {
            MotionMonitor = MonitorFactory.createMotionMonitor();
            motionSendingRadio = RadiosFactory.createSendingRadio(MotionMonitor.getPort());
            ((MotionMonitor)MotionMonitor).addObserver((Object)this);
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
            motionSendingRadio.sendMotionTime(MotionMonitor.getMotionTime());
            Utils.sleep(SAMPLE_RATE);
        }
    }
     /**
     * Message received from monitor
     * pass to radio
     */
    public void update(Observable o, Object arg)
    {
        motionSendingRadio.sendMotionTime(((IMotionMonitor)o).getMotionTime());
    }
    public void update(Observable o)
    {
        motionSendingRadio.sendMotionTime(((IMotionMonitor)o).getMotionTime());
    }
}