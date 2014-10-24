package org.sunspotworld;

import java.io.IOException;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotMonitors.IAccelMonitor;
import org.sunspotworld.spotMonitors.AccelMonitor;
import org.sunspotworld.spotMonitors.MonitorFactory;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.Patterns.Observer;
import org.sunspotworld.Patterns.Observable;
import com.sun.spot.util.Utils;

/**
 * Thread to send Acceleration data
 */
public class TSendingAccel implements Runnable, Observer
{
    private IAccelMonitor accelMonitor;
    private static final int SAMPLE_RATE = 60 * 1000; //60 seconds

    // Init sending radio
    ISendingRadio accelSendingRadio;

    /**
     * Instantiates the monitor and sending radio required
     * for sending accel data to the base station
     */
    public TSendingAccel()
    {
        try
        {
            accelMonitor = MonitorFactory.createAccelMonitor();
            accelSendingRadio = RadiosFactory.createSendingRadio(accelMonitor.getPort());
            ((AccelMonitor)accelMonitor).addObserver((Object)this);
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
            accelSendingRadio.sendAccel(accelMonitor.getAccel());
            Utils.sleep(SAMPLE_RATE);
        }
    }
     /**
     * Message received from monitor
     * pass to radio
     */
    public void update(Observable o, Object arg)
    {
        System.out.println("ACCELERATION UPDATE: " + ((IAccelMonitor)o).getAccel());
        accelSendingRadio.sendAccel(((IAccelMonitor)o).getAccel());
    }
    public void update(Observable o)
    {
        System.out.println("ACCELERATION UPDATE: " + ((IAccelMonitor)o).getAccel());
        accelSendingRadio.sendAccel(((IAccelMonitor)o).getAccel());
    }
}