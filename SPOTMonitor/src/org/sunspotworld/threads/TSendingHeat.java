package org.sunspotworld.threads;

import java.io.IOException;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotMonitors.IThermoMonitor;
import org.sunspotworld.spotMonitors.ThermoMonitor;
import org.sunspotworld.spotMonitors.MonitorFactory;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.homePatterns.Observer;
import org.sunspotworld.homePatterns.Observable;
import com.sun.spot.util.Utils;

// import java.util.Observer;

/**
 * Thread to send Thermo data
 */
public class TSendingHeat implements Runnable, Observer
{
    private IThermoMonitor thermoMonitor;
    private static final int SAMPLE_RATE = 60 * 1000; //60 seconds

    // Init sending radio
    ISendingRadio thermoSendingRadio;

    /**
     * Instantiates the monitor and sending radio required
     * for sending thermo data to the base station
     */
    public TSendingHeat(int threshold)
    {
        try
        {
            thermoMonitor = MonitorFactory.createThermoMonitor(threshold);
            thermoSendingRadio = RadiosFactory.createSendingRadio(thermoMonitor.getPort());
            ((ThermoMonitor)thermoMonitor).addObserver((Object)this);
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
            // thermoSendingRadio.sendHeat(thermoMonitor.getCelsiusTemp());
            Utils.sleep(SAMPLE_RATE);
        }
    }
    /**
     * Message received from monitor
     * pass to radio
     */
    public void update(Observable o, Object arg)
    {
        thermoSendingRadio.sendHeat(((IThermoMonitor)o).getDataAsDouble());
    }
    public void update(Observable o)
    {
        thermoSendingRadio.sendHeat(((IThermoMonitor)o).getDataAsDouble());
    }
}