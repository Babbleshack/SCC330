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
 * Host application that polls for temperature and 
 * light updates from the Java Sun SPOTs
 */
public class SunSpotHostApplication implements Runnable
{
        
    /**
     * Threads for communicating with SPOT
     */
    private Thread heatThread = null;
    private Thread lightThread = null;

    /**
     * Starts polling threads
     */
    public SunSpotHostApplication() throws Exception
    {
        startPolling();
    }

    /**
     * Initiate threads for communicating with the sunSPOT device
     */
    public void startPolling() throws Exception
    {
        heatThread = new Thread(new TReceivingHeat(),"heatService");
        lightThread = new Thread(new TReceivingLight(),"lightService");

        heatThread.setDaemon(true);
        lightThread.setDaemon(true);

        heatThread.start();
        lightThread.start();
    }

    public void run()  
    {    
    }

    public static void main(String[] args) throws Exception
    {
        /**
         * Fixes a strange issue with the SunSpot library not playing nice with 
         * IPv6 address strings when initialising the shared basestation
         */
        System.setProperty("java.net.preferIPv4Stack" , "true");

        /**
         * registers the application's name with the OTA Command
         * server & starts the OTA 
         */
        OTACommandServer.start("HostApplication");
        SunSpotHostApplication SunSpotHostApplication = new SunSpotHostApplication();

    }
}