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
            accelSampleThread, CompassSampleThread= null;
    
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
    {
	//create share map
	ConcurrentHashMap<String, String> addressMap = new ConcurrentHashMap<String, String>(); 
        //declare and instantiate threads.
        discoveryThread = new Thread(new TDiscovery(addressMap),"discoveryService");
        switchThread = new Thread(new TReceivingSwitch(),"switchService");
        heatThreadThresh = new Thread(new TReceivingHeatThreshold(),"heatService");
        lightThreadThresh = new Thread(new TReceivingLightThreshold(),"lightService");
        accelThreadThresh = new Thread(new TReceivingAccelThreshold(),"accelService");
        CompassThreadThresh = new Thread(new TReceivingCompassThreshold(), "BearingService");
        heatSampleThread = new Thread(new TReceivingHeatSample(),"heatServiceSample");
        lightSampleThread = new Thread(new TReceivingLightSample(),"lightServiceSample");
        accelSampleThread = new Thread(new TReceivingAccelSample(),"accelServiceSample");
        CompassSampleThread = new Thread(new TReceivingCompassSample(), "BearingServiceSample");
        zoneThread = new Thread(new TZoneController(), "zoneControllerService");
        waterThread = new Thread(new TReceivingWater(), "waterLevelService");
        batteryMonitor = new Thread(new TReceivingBattery(), "BatteryMonitorService");
        
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
        heatThreadThresh.start();
        lightThreadThresh.start();
        accelThreadThresh.start();
        zoneThread.start();
        waterThread.start();
        batteryMonitor.start();
        CompassThreadThresh.start();
        heatSampleThread.start();
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
