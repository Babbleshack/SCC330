/*
 * SunSpotHostApplication.java
 *
 * Created on 13-Oct-2014 23:19:07;
 */

package org.sunspotworld;

import com.sun.spot.peripheral.ota.OTACommandServer;
import org.sunspotworld.Collections.ArrayList;

import java.sql.Timestamp;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import org.sunspotworld.DB.QueryManager;
import org.sunspotworld.DataTypes.LightData;
import org.sunspotworld.DataTypes.ThermoData;

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
    private Thread discoveryThread = null;
    private Thread heatThread = null;
    private Thread lightThread = null;
    private Thread accelThread = null;

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
        heatThread = new Thread(new TReceivingHeat(),"heatService");
        lightThread = new Thread(new TReceivingLight(),"lightService");
        accelThread = new Thread(new TReceivingAccel(),"accelService");

        // generalThread.setDaemon(true);
        discoveryThread.setDaemon(true);
        heatThread.setDaemon(true);
        lightThread.setDaemon(true);
        accelThread.setDaemon(true);

        // generalThread.start();
        discoveryThread.start();
        heatThread.start();
        lightThread.start();
        accelThread.start();
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