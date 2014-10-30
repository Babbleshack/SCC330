/*
 * SunSpotHostApplication.java
 *
 * Created on 13-Oct-2014 23:19:07;
 */

package org.sunspotworld.threads;

import java.io.IOException;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotMonitors.IThermoMonitor;
import org.sunspotworld.spotMonitors.ILightMonitor;
import org.sunspotworld.spotMonitors.IAccelMonitor;
import org.sunspotworld.spotMonitors.MonitorFactory;
import org.sunspotworld.spotRadios.RadiosFactory;
import com.sun.spot.util.Utils;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.resources.transducers.ISwitchListener;
import com.sun.spot.resources.transducers.SwitchEvent;

/**
 * Sample Sun SPOT host application
 */
public class TDemandSwitch implements Runnable, ISwitchListener
{
    private ISwitch sw1;
    private static final int DEFAULT_THRESHOLD = 0;
    // Init sending radio
    IThermoMonitor thermoMonitor;
    ILightMonitor lightMonitor;
    IAccelMonitor accelMonitor;


    ISendingRadio thermoSendingRadio, lightSendingRadio, accelSendingRadio;

    // creates an instance of SunSpotHostApplication class and initialises
    // instance variables
    public TDemandSwitch(int heat_threshold) throws IOException
    {
        sw1 = (ISwitch) Resources.lookup(ISwitch.class, "SW1");
        sw1.addISwitchListener(this);       // enable automatic notification of switches
        try
        {
            thermoMonitor = MonitorFactory.createThermoMonitor(DEFAULT_THRESHOLD);
            lightMonitor = MonitorFactory.createLightMonitor(DEFAULT_THRESHOLD);
            accelMonitor = MonitorFactory.createAccelMonitor();

            thermoSendingRadio = RadiosFactory.createSendingRadio(thermoMonitor.getPort());
            lightSendingRadio = RadiosFactory.createSendingRadio(lightMonitor.getPort());
            accelSendingRadio = RadiosFactory.createSendingRadio(accelMonitor.getPort());
        }
        catch(Exception e)
        {
           System.out.println("Unable initiate thermo sending radio");
        }
    }

    /**
     * These methods are the "call backs" that are invoked whenever the
     * switch is pressed or released. They are run in a new thread.
     *
     * @param sw the switch that was pressed/released.
     */
    public void switchPressed(SwitchEvent evt) {
    }
    
    public void switchReleased(SwitchEvent evt) {
        thermoSendingRadio.sendHeat(thermoMonitor.getCelsiusTemp());
        lightSendingRadio.sendLight(lightMonitor.getLightIntensity());
        accelSendingRadio.sendAccel(accelMonitor.getAccel());
    }

    public void startPolling() throws Exception
    {
    }

    public void run()
    {
        Utils.sleep(30000); // sleep for 30 seconds
    }
}