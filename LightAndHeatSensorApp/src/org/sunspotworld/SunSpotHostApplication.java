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

    private ILightMonitor lightMonitor; 
    private IThermoMonitor thermoMonitor;
        
    /**
     * thread for communicating with SPOT
     * Init receiving radio
     */
    private Thread pollingThread = null;
    IReceivingRadio lightReceivingRadio, thermoReceivingRadio;
   
    /**
     * creates an instance of SunSpotHostApplication class and initialises
     * instance variables
     */
    public SunSpotHostApplication()
    {
       try
       { 
            lightMonitor = MonitorFactory.createLightMonitor(); 
            thermoMonitor = MonitorFactory.createThermoMonitor(); 

            lightReceivingRadio = RadiosFactory.createReceivingRadio(lightMonitor.getPort());
            thermoReceivingRadio = RadiosFactory.createReceivingRadio(thermoMonitor.getPort());

            /**
             * Starts polling thread
             */
            startPolling();
       }
       catch(Exception e)
       {
           System.out.println("Unable initiate polling");
       }
    }
     
    /**
     * Initiates thread for communication with SPOT device
     */ 
    public void startPolling() throws Exception
    {
        pollingThread = new Thread(this,"pollingService");
        pollingThread.setDaemon(true);
        pollingThread.start();
    }
    
    public void run()  
    {    
        /**
         * Poll for reading updates
         */
        while (true) 
        {
            try 
            {                                
                /**
                 * Read values using radios
                 */
                double  lightValue  = lightReceivingRadio.receiveLight();
                double  thermoValue   = thermoReceivingRadio.receiveHeat();

                /**
                 * Output received values
                 */
                System.out.println("Message from "+lightReceivingRadio.getReceivedAddress()+": \t Light " + lightValue + "\t Heat: " + thermoValue);   
            } 
            catch (IOException io) 
            {
                System.err.println("Caught " + io +  " while polling SPOT");   
                io.printStackTrace();
            }
        }
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