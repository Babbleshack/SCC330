/*
 * SunSpotHostApplication.java
 *
 * Created on 13-Oct-2014 23:19:07;
 */

package org.sunspotworld;

import java.io.IOException;
import org.sunspotworld.basestationMonitors.IReceivingRadio;
import org.sunspotworld.basestationMonitors.IThermoMonitor;
import org.sunspotworld.basestationMonitors.MonitorFactory;
import org.sunspotworld.basestationRadios.RadiosFactory;
import org.sunspotworld.basestationRadios.SunspotPort;

import org.sunspotworld.DB.DatabaseConnectionFactory;
import org.sunspotworld.DB.MySQLConnectionManager;
import org.sunspotworld.DB.QueryManager;

/**
 * Sample Sun SPOT host application
 */
public class TDiscovery implements Runnable
{

    private IThermoMonitor thermoMonitor;
    private QueryManager queryManager;

    // Init receiving radio
    IReceivingRadio discoveryRadio;

    // creates an instance of SunSpotHostApplication class and initialises
    // instance variables
    public TDiscovery()
    {
        try
        {
            discoveryRadio = RadiosFactory.createReceivingRadio(new SunspotPort(90));
            queryManager = new QueryManager();
        }
        catch(Exception e)
        {
           System.out.println("Unable initiate polling");
        }
    }

    public void startPolling() throws Exception
    {
    }

    public void run()
    {
        // main switch reading/polling loop
        while (true)
        {
            try
            {
                // Read light and heat values
                String  spot_address   = discoveryRadio.receiveDiscoverMe();

                // Print out light and heat values
                System.out.println("Discovery request from: " + spot_address);

                // Query to see if this SPOT exists
                if(!queryManager.isSpotExists(spot_address)) {
                    System.out.println("Spot does not exist");
                    
                    // If spot does not exist: insert spot, with no user id
                    queryManager.createSpotRecord(spot_address, System.currentTimeMillis());
                } else {
                    System.out.println("Spot exists");
                }

                // 2. Get all jobs + sensors + sensor ports attached to this SPOT
                // 3. Send list of ports back to SPOT 
            }
            catch (IOException io)
            {
                System.err.println("Caught " + io +  " while polling SPOT");
                io.printStackTrace();
            }
        }
    }
}