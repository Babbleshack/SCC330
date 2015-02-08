/*
 * SunSpotHostApplication.java
 *
 * Created on 13-Oct-2014 23:19:07;
 */

package org.sunspotworld;

import org.sunspotworld.threads.TDiscovery;
import org.sunspotworld.threads.TReceivingSwitch;
import org.sunspotworld.threads.TReceivingHeat;
import org.sunspotworld.threads.TReceivingAccel;
import org.sunspotworld.threads.TReceivingLight;
import org.sunspotworld.threads.TZoneController;
import com.sun.spot.peripheral.ota.OTACommandServer;
import org.sunspotworld.threads.TReceivingBattery;
import org.sunspotworld.threads.TReceivingWater;

import org.sunspotworld.database.QueryManager;
import org.sunspotworld.threads.TReceivingBearing;

/**
 * Host application that polls for temperature and
 * light updates from the Java Sun SPOTs
 */
public class SunSpotHostApplication implements Runnable
{
    /**
     * Threads for communicating with SPOT
     */
    private Thread discoveryThread, switchThread, 
            heatThread, lightThread, accelThread, 
            zoneThread, waterThread, batteryMonitor,
            barrometerThread = null;
    
    private QueryManager qm  = null; 

    /**
     * Starts polling threads
     */
    public SunSpotHostApplication() throws Exception
    {
        this.qm = new QueryManager(); 
        startPolling();
    }

    /**
     * Initiate threads for communicating with the sunSPOT device
     */
    public void startPolling() throws Exception
    {
        //declare and instantiate threads.
        discoveryThread = new Thread(new TDiscovery(),"discoveryService");
        switchThread = new Thread(new TReceivingSwitch(),"switchService");
        heatThread = new Thread(new TReceivingHeat(),"heatService");
        lightThread = new Thread(new TReceivingLight(),"lightService");
        accelThread = new Thread(new TReceivingAccel(),"accelService");
        zoneThread = new Thread(new TZoneController(), "zoneControllerService");
        waterThread = new Thread(new TReceivingWater(), "waterLevelService");
        batteryMonitor = new Thread(new TReceivingBattery(), "BatteryMonitorService");
        barrometerThread = new Thread(new TReceivingBearing(), "BearingService");
        
        //set Daemons
        discoveryThread.setDaemon(true);
        switchThread.setDaemon(true);
        heatThread.setDaemon(true);
        lightThread.setDaemon(true);
        accelThread.setDaemon(true);
        zoneThread.setDaemon(true);
        waterThread.setDaemon(true);
        batteryMonitor.setDaemon(true);
        barrometerThread.setDaemon(true);
        //start threads
        discoveryThread.start();
        switchThread.start();
        heatThread.start();
        lightThread.start();
        accelThread.start();
        zoneThread.start();
        waterThread.start();
        batteryMonitor.start();
        barrometerThread.start();
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
