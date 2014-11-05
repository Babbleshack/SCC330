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
import org.sunspotworld.homeCollections.ArrayList;

import java.sql.Timestamp;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import org.sunspotworld.database.QueryManager;
import org.sunspotworld.valueObjects.LightData;
import org.sunspotworld.valueObjects.ThermoData;

/**
 * Host application that polls for temperature and
 * light updates from the Java Sun SPOTs
 */
public class SunSpotHostApplication implements Runnable
{

    /**
     * Threads for communicating with SPOT
     */
    // private Thread generalThread = null;
    private Thread discoveryThread, switchThread, heatThread, lightThread, accelThread, zoneThread = null;

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
        // generalThread = new Thread(new TGeneral(),"generalService");
        discoveryThread = new Thread(new TDiscovery(),"discoveryService");
        switchThread = new Thread(new TReceivingSwitch(),"switchService");
        heatThread = new Thread(new TReceivingHeat(),"heatService");
        lightThread = new Thread(new TReceivingLight(),"lightService");
        accelThread = new Thread(new TReceivingAccel(),"accelService");
        zoneThread = new Thread(new TZoneController(), "zoneControllerService");

        // generalThread.setDaemon(true);
        discoveryThread.setDaemon(true);
        switchThread.setDaemon(true);
        heatThread.setDaemon(true);
        lightThread.setDaemon(true);
        accelThread.setDaemon(true);
        zoneThread.setDaemon(true);

        // generalThread.start();
        discoveryThread.start();
        switchThread.start();
        heatThread.start();
        lightThread.start();
        accelThread.start();
        zoneThread.start();
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