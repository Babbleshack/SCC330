/*
 * SunSpotHostApplication.java
 *
 * Created on 13-Oct-2014 23:19:07;
 */

package org.sunspotworld;

import com.sun.spot.peripheral.ota.OTACommandServer;
import org.sunspotworld.database.QueryManager;
import org.sunspotworld.threads.*;
import java.util.concurrent.ConcurrentHashMap;
import java.io.*;
import com.sun.spot.util.*;
import com.sun.spot.peripheral.Spot;
import com.sun.spot.peripheral.radio.BasestationManager;
import com.sun.spot.peripheral.radio.BasestationManager.DiscoverResult;
/**
 * Host application that polls for temperature and
 * light updates from the Java Sun SPOTs
 */
public class SunSpotHostApplication
{
    /**
     * Threads for communicating with SPOT
     */
    private Thread discoveryThread, switchThread, 
            heatThreadThresh, lightThreadThresh, accelThreadThresh, 
            zoneThread, waterThread, batteryMonitor,
            CompassThreadThresh, heatSampleThread, lightSampleThread,
            accelSampleThread, CompassSampleThread, baseStationDiscovey= null;
    
    private QueryManager qm  = null; 

    /**
     * Starts polling threads
     */
    public SunSpotHostApplication() throws Exception
    {
        this.qm = new QueryManager(); 
    }

    /**
     * Initiate threads for communicating with the sunSPOT device
     */
    public void startPolling() throws Exception
    {	//create share map
	ConcurrentHashMap<String, String> addressMap = new ConcurrentHashMap<String, String>(); 
        //get basestation discovery started
        baseStationDiscovey = new Thread(new TBaseStationDiscovery(new QueryManager(), addressMap),"TBaseStationDiscovery");
        baseStationDiscovey.setDaemon(true);
        baseStationDiscovey.start();
        //declare and instantiate threads.
        discoveryThread = new Thread(new TDiscovery(addressMap),"discoveryService");
        switchThread = new Thread(new TReceivingSwitch(addressMap),"switchService");
        heatThreadThresh = new Thread(new TReceivingHeatThreshold(addressMap),"heatService");
        lightThreadThresh = new Thread(new TReceivingLightThreshold(addressMap),"lightService");
        accelThreadThresh = new Thread(new TReceivingAccelThreshold(addressMap),"accelService");
        CompassThreadThresh = new Thread(new TReceivingCompassThreshold(addressMap), "BearingService");
        heatSampleThread = new Thread(new TReceivingHeatSample(addressMap),"heatServiceSample");
        lightSampleThread = new Thread(new TReceivingLightSample(addressMap),"lightServiceSample");
        accelSampleThread = new Thread(new TReceivingAccelSample(addressMap),"accelServiceSample");
        CompassSampleThread = new Thread(new TReceivingCompassSample(addressMap), "BearingServiceSample");
        zoneThread = new Thread(new TZoneController(addressMap), "zoneControllerService");
        waterThread = new Thread(new TReceivingWater(addressMap), "waterLevelService");
        batteryMonitor = new Thread(new TReceivingBattery(addressMap), "BatteryMonitorService");
        
        //set Daemons
        discoveryThread.setDaemon(true);
        switchThread.setDaemon(true);
        heatThreadThresh.setDaemon(true);
        lightThreadThresh.setDaemon(true);
        accelThreadThresh.setDaemon(true);
        zoneThread.setDaemon(true);
        waterThread.setDaemon(true);
        batteryMonitor.setDaemon(true);
        CompassThreadThresh.setDaemon(true);
        heatSampleThread.setDaemon(true);
        lightSampleThread.setDaemon(true);
        accelSampleThread.setDaemon(true);
        CompassSampleThread.setDaemon(true);
        //start threads
        discoveryThread.start();
        switchThread.start();
        batteryMonitor.start();
        heatSampleThread.start();
        heatThreadThresh.start();
        lightThreadThresh.start();
        accelThreadThresh.start();
        zoneThread.start();
        waterThread.start();
        CompassThreadThresh.start();
        lightSampleThread.start();
        accelSampleThread.start();
        CompassSampleThread.start();
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
        OTACommandServer ota = (OTACommandServer) 
                OTACommandServer.getInstance();
	ota.setServiceName("HostApplication");
	ota.start();
		        //OTACommandServer.start("HostApplication");
	SunSpotHostApplication SunSpotHostApplication = new SunSpotHostApplication();
	SunSpotHostApplication.startPolling();
    }
}
