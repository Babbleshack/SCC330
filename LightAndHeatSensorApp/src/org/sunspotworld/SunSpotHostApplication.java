/*
 * SunSpotHostApplication.java
 *
 * Created on 13-Oct-2014 23:19:07;
 */

package org.sunspotworld;

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
    private Thread pollingThread = null;

    // Init receiving radio
    IReceivingRadio receivingRadio;
   
    // creates an instance of SunSpotHostApplication class and initialises
    // instance variables
    public SunSpotHostApplication()
    {
       try
       { 
           // starts polling thread
           startPolling();
       }
       catch(Exception e)
       {
           System.out.println("Unable initiate polling");
       }
    }
     
    public void startPolling() throws Exception
    {
        //initiates thread for communication with SPOT device
        pollingThread = new Thread(this,"pollingService");
        pollingThread.setDaemon(true);
        pollingThread.start();
    }
    
    public void run()  
    {
        try 
        {
            receivingRadio = (IReceivingRadio)RadiosFactory.createReceivingRadio(); 
        } 
        catch (Exception e) 
        {
            System.err.println("setUp caught " + e.getMessage());   
        }
        
        // main switch reading/polling loop
        while (true) 
        {
            try 
            {                                
                // Read light and heat values
                int     lightValue  = receivingRadio.receiveLight();
                double  heatValue   = receivingRadio.receiveHeat();

                // Print out light and heat values
                System.out.println("Message from "+receivingRadio.getReceivedAddress()+": \t Light " + lightValue + "\t Heat: " + heatValue);   
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
        //registers the application's name with the OTA Command
      //server & starts the OTA 
        OTACommandServer.start("SunSpotHostApplication");
        SunSpotHostApplication SunSpotHostApplication = new SunSpotHostApplication(); 
        
    }    
}