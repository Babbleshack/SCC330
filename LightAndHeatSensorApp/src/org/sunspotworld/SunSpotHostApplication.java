/*
 * SunSpotHostApplication.java
 *
 * Created on 13-Oct-2014 23:19:07;
 */

package org.sunspotworld;

import org.sunspotworld.basestationRadios.RadiosFactory;
import org.sunspotworld.basestationMonitors.IThermoMonitor;
import org.sunspotworld.basestationMonitors.ILightMonitor;
import org.sunspotworld.basestationMonitors.MonitorFactory;
import org.sunspotworld.basestationMonitors.IReceivingRadio;
import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.peripheral.radio.IRadioPolicyManager;
import com.sun.spot.io.j2me.radiostream.*;
import com.sun.spot.io.j2me.radiogram.*;
import com.sun.spot.peripheral.ota.OTACommandServer;
import com.sun.spot.util.IEEEAddress;

import java.io.*;
import javax.microedition.io.*;


/**
 * Sample Sun SPOT host application
 */
public class SunSpotHostApplication implements Runnable
{

    //thread for communicating with SPOT
    // private Thread pollingThread = null;
    private Thread heatThread = null;
    private Thread lightThread = null;

    // creates an instance of SunSpotHostApplication class and initialises
    // instance variables
    public SunSpotHostApplication() throws Exception
    {
        // starts polling thread
        startPolling();

    }

    public void startPolling() throws Exception
    {
        //initiates thread for communication with SPOT device
        // pollingThread   = new Thread(this,"pollingService");
        heatThread      = new Thread(new TReceivingHeat(),"heatService");
        lightThread     = new Thread(new TReceivingLight(),"lightService");

        // pollingThread.setDaemon(true);
        heatThread.setDaemon(true);
        lightThread.setDaemon(true);

        // pollingThread.start();
        heatThread.start();
        lightThread.start();
    }

    public void run()
    {
    }

    public static void main(String[] args) throws Exception
    {
        System.setProperty("java.net.preferIPv4Stack" , "true");
        //registers the application's name with the OTA Command
        //server & starts the OTA
        OTACommandServer.start("HostApplication");
        SunSpotHostApplication SunSpotHostApplication = new SunSpotHostApplication();

    }
}