/*
 * SunSpotApplication.java
 *
 * Created on 13-Oct-2014 23:18:39;
 */

package org.sunspotworld;

import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotMonitors.ILightMonitor;
import org.sunspotworld.spotMonitors.IThermoMonitor;
import org.sunspotworld.spotMonitors.MonitorFactory;
import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.service.BootloaderListenerService;
import com.sun.spot.util.IEEEAddress;
import com.sun.spot.util.Utils;

import java.io.IOException;
import java.lang.SecurityException;

import java.lang.Thread;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * The startApp method of this class is called by the VM to start the
 * application.
 * 
 * The manifest specifies this class as MIDlet-1, which means it will
 * be selected for execution.
 */
public class SunSpotApplication extends MIDlet implements Runnable {

    /**
     * Threads for communicating with Basestation
     */
    private Thread heatThread = null;
    private Thread lightThread = null;
    private Thread switchThread = null;

    public SunSpotApplication() {
        
    }

    /**
     * Initiate threads for communicating with the basestation
     */
    public void startPolling() throws SecurityException
    {
        heatThread = new Thread(new TSendingHeat(),"heatService");
        lightThread = new Thread(new TSendingLight(),"lightService");
        // TSendingAccel

        try {
            switchThread = new Thread(new TDemandSwitch(),"switchService");
        } catch (IOException io) {
            System.out.println("Error starting switch listener thread: " + io);
        }

        heatThread.start();
        lightThread.start();
        switchThread.start();
    }

    public void run()  
    {    
    }

    /** 
     * Starts polling threads
     * @throws MIDletStateChangeException [description]
     */
    protected void startApp() throws MIDletStateChangeException {
        BootloaderListenerService.getInstance().start();   // monitor the USB (if connected) and recognize commands from host
        long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
        System.out.println("Our radio address = " + IEEEAddress.toDottedHex(ourAddr));
        try {
            startPolling();
        } catch(SecurityException se) {
            System.out.println("The current thread cannot create a thread in the specified thread group: " + se);
        }

        //notifyDestroyed();                      // cause the MIDlet to exit
    }

    protected void pauseApp() {
        // This is not currently called by the Squawk VM
    }

    /**
     * Called if the MIDlet is terminated by the system.
     * It is not called if MIDlet.notifyDestroyed() was called.
     *
     * @param unconditional If true the MIDlet must cleanup and release all resources.
     */
    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    }
}
  
