/*
 * SunSpotHostApplication.java
 *
 * Created on 13-Oct-2014 23:19:07;
 */

package org.sunspotworld.threads;

import java.io.IOException;
import org.sunspotworld.basestationRadios.IReceivingRadio;
import org.sunspotworld.basestationRadios.ISendingRadio;
import org.sunspotworld.basestationRadios.RadiosFactory;
import org.sunspotworld.basestationRadios.SunspotPort;
import java.util.ArrayList;
import org.sunspotworld.database.QueryManager;

/**
 * Sample Sun SPOT host application
 */
public class TDiscovery implements Runnable
{

    private QueryManager queryManager;

    // Init receiving radio
    IReceivingRadio discoveryRadio;
    ISendingRadio responseRadio; 

    // creates an instance of SunSpotHostApplication class and initialises
    // instance variables
    public TDiscovery()
    {
        try
        {
            discoveryRadio = RadiosFactory.createReceivingRadio(new SunspotPort(90));
            responseRadio = RadiosFactory.createSendingRadio(new SunspotPort(90));
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
                // Receive discover me 
                String  spot_address   = discoveryRadio.receiveDiscoverMe();
                System.out.println("Discovery request from: " + spot_address);

                // Query to see if this SPOT exists
                if(queryManager.isSpotExists(spot_address) == 0) {
                    // If spot does not exist: insert spot, with no user id
                    queryManager.createSpotRecord(spot_address, System.currentTimeMillis());
                } 

                // 2. Get all jobs + sensors + sensor ports attached to this SPOT
                ArrayList spotParameters = queryManager.getSensorPortsJobThresholdsFromSpotAddress(spot_address);
                System.out.println("got ports "  + spot_address.toString());
                // 3. Send list of ports back to SPOT 
                responseRadio.sendDiscoverReponse(spot_address, spotParameters);
            }
            catch (IOException io)
            {
                System.err.println("Caught " + io +  " while polling SPOT");
                io.printStackTrace();
            }
        }
    }
}
