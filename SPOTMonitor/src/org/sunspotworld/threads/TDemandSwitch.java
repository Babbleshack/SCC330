/*
 * SunSpotHostApplication.java
 *
 * Created on 13-Oct-2014 23:19:07;
 */

package org.sunspotworld.threads;

import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotMonitors.ISwitchMonitor;
import org.sunspotworld.spotMonitors.MonitorFactory;
import org.sunspotworld.spotRadios.RadiosFactory;
import com.sun.spot.util.Utils;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.resources.transducers.ISwitchListener;
import com.sun.spot.resources.transducers.SwitchEvent;
import org.sunspotworld.spotMonitors.IBatteryMonitor;

/**
 * Sample Sun SPOT host application
 */
public class TDemandSwitch implements Runnable, ISwitchListener
{
    private ISwitch sw1, sw2;
    private static final int DEFAULT_THRESHOLD = 0;

    ISwitchMonitor switchMonitor;
    IBatteryMonitor batteryMonitor;

    ISendingRadio switchSendingRadio;

    // creates an instance of SunSpotHostApplication class and initialises
    // instance variables
    public TDemandSwitch(int heat_threshold)
    {
        
        //start battery monitor
       // batteryMonitor = MonitorFactory.createBatteryMonitor();
        sw1 = (ISwitch) Resources.lookup(ISwitch.class, "SW1");
        sw1.addISwitchListener(this);       // enable automatic notification of switches

        sw2 = (ISwitch) Resources.lookup(ISwitch.class, "SW2");
        sw2.addISwitchListener(this);       // enable automatic notification of switches

        try
        {
            switchMonitor = MonitorFactory.createSwitchMonitor();

            switchMonitor.addSwitch(sw1, "SW1");
            switchMonitor.addSwitch(sw2, "SW2"); 

            switchSendingRadio = RadiosFactory.createSendingRadio(switchMonitor.getPort());
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
        //call battery monitor
        System.out.println("Switch " + switchMonitor.getPressedId(evt) + " pressed!");
        
    }
    
    public void switchReleased(SwitchEvent evt) {
        switchSendingRadio.sendSwitch(evt.getSwitch().getTagValue("id")); 
    }

    public void startPolling() throws Exception
    {
    }
    public void run()
    {
        Utils.sleep(30000); // sleep for 30 seconds
    }
}