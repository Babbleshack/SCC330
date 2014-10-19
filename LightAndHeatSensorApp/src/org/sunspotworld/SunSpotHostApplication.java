/*
 * SunSpotHostApplication.java
 *
 * Created on 13-Oct-2014 23:19:07;
 */

package org.sunspotworld;

import com.sun.spot.peripheral.ota.OTACommandServer;
import java.util.ArrayList;


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
        ArrayList test = new ArrayList();
        int i;
        //test adding
        for(i=0;i<10;i++)
            test.add((Object)new Integer(i));
        for(i=0;i<10;i++)
            System.out.println(i + " : " + (Integer)test.get(i));
        for(i=9;i>-1;i--)
            test.remove(i);
        System.out.println("List Size After Delete: " + test.size());        
        //test auto resize
        for(i=0;i<15;i++)
            test.add((Object)new Integer(i));
        for(i=0;i<15;i++)
            System.out.println(i + " : " + (Integer)test.get(i));
        
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