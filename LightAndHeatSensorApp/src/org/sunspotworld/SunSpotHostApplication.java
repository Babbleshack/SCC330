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
    // Broadcast port on which we listen for tilt data
    private static final int HOST_PORT = 96;
    
    //Connection and datagram variables
    private RadiogramConnection radioConn;
    private Datagram datagram;
   
    //thread for communicating with SPOT
    private Thread pollingThread = null;
   
   //creates an instance of SunSpotHostApplication class and initialises
    //instance variables
    public SunSpotHostApplication()
    {
       try
       { 
           //starts polling thread
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
        //starts application
        try 
        {
            // opens a server-side broadcast radiogram connection
            // to listen for switch readings being sent by SPOT devices
            radioConn = (RadiogramConnection) Connector.open("radiogram://:" + HOST_PORT);
            datagram = radioConn.newDatagram(radioConn.getMaximumLength());   
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
                // reads accelerometer state data received over the radio
                radioConn.receive(datagram);
                
                //reads sender ID
                String addr = datagram.getAddress();  
                
                //read a value
                double value = datagram.readDouble(); 

                //prints ID od switch pressed, senders ID's and date
                System.out.println("Message from "+addr+": \t " + value);   
            } 
            catch (Exception e) 
            {
                System.err.println("Caught " + e +  " while polling SPOT");   
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