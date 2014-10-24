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

    private static final int MOCK_HEAT_THRESHOLD = 20;
    private static final int MOCK_LIGHT_THRESHOLD = 0;


    private ISendingRadio discoverMeRadio;

    public SunSpotApplication() {

    }

    // public startSensor(int port) 
    // {
    //     switch(port) {
    //         case LightMonitor.getStaticPort():
    //             lightThread = new Thread(new TSendingLight(),"lightService");
    //             break;
    //         case HeatMonitor.getStaticPort():
    //             heatThread = new Thread(new TSendingHeat(),"heatService");
    //             break;
    //         case AccelMonitor.getStaticPort():
    //             accelThread = new Thread(new TSendingAccel(),"accelService"); 
    //             break;
    //     }
    // }

    /**
     * Initiate threads for communicating with the basestation
     */
    public void startPolling() throws SecurityException
    {
        try {
            discoverMeRadio = RadiosFactory.createSendingRadio(new SunspotPort(90));
        } catch (PortOutOfRangeException p) {
            System.out.println("Discover me radio port out of range: " + p);
        } catch (IOException io) {
            System.out.println("Exception while creating discover me radio: " + io);
        }

        int[] ports = discoverMeRadio.discoverMe(); 

        for (int i = 0;i < ports.length; i++) 
            System.out.println("Port " + i + ":" + ports[i]);

        heatThread = new Thread(new TSendingHeat(MOCK_HEAT_THRESHOLD),"heatService");
        lightThread = new Thread(new TSendingLight(MOCK_LIGHT_THRESHOLD),"lightService");
        accelThread = new Thread(new TSendingAccel(),"accelService");

        try {
            switchThread = new Thread(new TDemandSwitch(20),"switchService");
        } catch (IOException io) {
            System.out.println("Error starting switch listener thread: " + io);
        }

        heatThread.start();
        lightThread.start();
        accelThread.start();
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

