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
    private Thread heatThread = null;
    private Thread lightThread = null;

    /**
     * Starts polling threads
     */
    public SunSpotHostApplication() throws Exception
    {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.out.println("com.mysql.jdbc.Driver could not be instantiated");
        }
        QueryManager qm = new QueryManager();
        //ArrayList heatData = qm.getPastWeekThermo();
        ArrayList lightData = qm.getPastWeekLight();
        ArrayList heatData = qm.getPastWeekThermo();
        SimpleDateFormat dateFormatter =
        new SimpleDateFormat("E dd/MM/yyyy'@'hh:mm:ss a zzz");
        LightData light;
        System.out.println("----------------------------------");
        System.out.println("Last Light Readings");
        for(int i = 0; i<lightData.size();i++)
        {
            light = (LightData) lightData.get(i);
            System.out.println("----------------------------------");
            System.out.println("Heat Data: \n"
            + "\t" + "Address: " + light.getSpotAddress() + "\n"
            + "\t" + "Lumen Value: " + light.getLightData() + "\n"
            + "\t" + "Time: "
            + dateFormatter.format(light.getTime()) + "\n"
            + "\t" + "ZoneID: " + light.getZoneId() + "\n");
            System.out.println("----------------------------------");
        }
        ThermoData data;
        System.out.println("----------------------------------");
        System.out.println("Last Thermonitor Readings");
        for(int i = 0; i<heatData.size();i++)
        {
            data =(ThermoData)heatData.get(i);
            System.out.println("----------------------------------");
            System.out.println("Heat Data: \n"
            + "\t" + "Address: " + data.getSpotAddress() + "\n"
            + "\t" + "Celcius Value: " + data.getCelciusData() + "\n"
            + "\t" + "Time: "
            + dateFormatter.format(data.getTime()) + "\n"
            + "\t" + "ZoneID: " + data.getZoneId() + "\n");
            System.out.println("----------------------------------");
        }
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