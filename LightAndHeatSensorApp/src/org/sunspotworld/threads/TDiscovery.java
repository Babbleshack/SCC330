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
import java.util.concurrent.ConcurrentHashMap;

/**
 * Sample Sun SPOT host application
 */
public class TDiscovery implements Runnable
{

    private QueryManager queryManager;
    // Init receiving radio
    private IReceivingRadio discoveryRadio;
    private ISendingRadio responseRadio; 
    private final ConcurrentHashMap<String, String> _addressMap;

    // creates an instance of SunSpotHostApplication class and initialises
    // instance variables
    public TDiscovery(final ConcurrentHashMap addressMap)
    {
        try
        {
            discoveryRadio = RadiosFactory.createReceivingRadio(new SunspotPort(90));
            responseRadio = RadiosFactory.createSendingRadio(new SunspotPort(90));
            queryManager = new QueryManager();

        }
        catch(Exception e)
        {
           System.err.println("Unable initiate polling");
	   e.printStackTrace();
        }
        _addressMap = addressMap;
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
                System.out.println("\nDiscovery request from: " + spot_address);
		//check if spot belongs to bs
		// if(!_addressMap.contains(spot_address)) continue;
		// System.out.println(spot_address + "was not dropped");
                // Query to see if this SPOT exists
                if(queryManager.isSpotExists(spot_address) == 0) {
                    // If spot does not exist: insert spot, with no user id
                    queryManager.createSpotRecord(spot_address, System.currentTimeMillis());
                } 

                // 2. Get all jobs + sensors + sensor ports attached to this SPOT
                ArrayList spotParameters = queryManager.getSensorPortsJobThresholdsFromSpotAddress(spot_address);
                // System.out.println("got ports "  + spot_address.toString());
                // 3. Send list of ports back to SPOT 
                responseRadio.sendDiscoverReponse(spot_address, spotParameters);
                System.out.println();
            }
            catch (IOException io)
            {
                System.err.println("Caught " + io +  " while polling SPOT");
                io.printStackTrace();
            }
        }
    }
}
