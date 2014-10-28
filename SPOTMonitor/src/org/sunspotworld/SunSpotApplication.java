/*
 * SunSpotApplication.java
 *
 * Created on 13-Oct-2014 23:18:39;
 */

package org.sunspotworld;

import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.IReceivingRadio;
import org.sunspotworld.spotRadios.SunspotPort;
import org.sunspotworld.spotRadios.PortOutOfRangeException;

import org.sunspotworld.spotMonitors.LightMonitor;
import org.sunspotworld.spotMonitors.ThermoMonitor;
import org.sunspotworld.spotMonitors.AccelMonitor;
import org.sunspotworld.spotMonitors.MotionMonitor;

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
    private Thread accelThread = null;
    private Thread switchThread = null;
    private Thread motionThread = null;
    private Thread discoverMeThread = null;

    private static final int MOCK_HEAT_THRESHOLD = 30;
    private static final int MOCK_LIGHT_THRESHOLD = 20;


    private ISendingRadio discoverMeRadio;
    private IReceivingRadio discoverRequestRadio;

    public SunSpotApplication() {

    }

    public void startSensor(int port, int threshold) 
    {
        switch(port) {
            case 110:
                heatThread = new Thread(new TSendingHeat(threshold),"heatService");
                System.out.println("Thread started for sensing heat (thresh " + threshold + ")");
                heatThread.start();
                break;
            case 120:
                lightThread = new Thread(new TSendingLight(threshold),"lightService");
                System.out.println("Thread started for sensing light (thresh " + threshold + ")");
                lightThread.start();
                break;
            case 130:
                accelThread = new Thread(new TSendingAccel(),"accelService");
                System.out.println("Thread started for sensing accelleration (thresh " + threshold + ")"); 
                accelThread.start();
                break;
            case 140:
                motionThread = new Thread(new TSendingMotion(), "motionService");
                System.out.println("Thread started for sensing motion (thresh " + threshold + ")");
                motionThread.start();
                break;
        }
    }

    /**
     * Initiate threads for communicating with the basestation
     */
    public void startPolling() throws SecurityException
    {
        try {
            discoverRequestRadio = RadiosFactory.createReceivingRadio(new SunspotPort(90));
        } catch (PortOutOfRangeException p) {
            System.out.println("Discover me radio port out of range: " + p);
        } catch (IOException io) {
            System.out.println("Exception while creating discover me radio: " + io);
        }

        discoverMeThread = new Thread(new TDiscoverMe(),"discoverMeService");
        discoverMeThread.start();

        int[] portsThresholds = null;
        try {
            portsThresholds = discoverRequestRadio.receiveDiscoverResponse(); 
        } catch (IOException io) {
            System.out.println("Exception while receiving discover response: " + io);
        }

        // Dispatch threads
        if(portsThresholds != null) {
            for (int i = 0;i < portsThresholds.length; i += 2) {
                System.out.println("Port " + portsThresholds[i] + " threshold: " + portsThresholds[i+1]);
                this.startSensor(portsThresholds[i], portsThresholds[i+1]);
            }
        } else {
            System.out.println("No ports and thresholds");
        }

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

