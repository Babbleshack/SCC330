/*
 * SunSpotHostApplication.java
 *
 * Created on 13-Oct-2014 23:19:07;
 */

package org.sunspotworld.threads;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import org.sunspotworld.basestationRadios.IReceivingRadio;
import org.sunspotworld.basestationRadios.RadiosFactory;
import org.sunspotworld.basestationRadios.SunspotPort;

import org.sunspotworld.database.QueryManager;

/**
 * Sample Sun SPOT host application
 */
public class TReceivingLightThreshold implements Runnable
{

    private final QueryManager _qm;

    // Init receiving radio
    private IReceivingRadio lightReceivingRadio;
    private final int _port;
    // creates an instance of SunSpotHostApplication class and initialises
    // instance variables
    private final ConcurrentHashMap<String, String> _addressMap;
    public TReceivingLightThreshold(ConcurrentHashMap addressMap)
    {
        _port = SunspotPort.LIGHT_THRESH;
        try
        {
            lightReceivingRadio = RadiosFactory.createReceivingRadio(
                    new SunspotPort(_port));
        }
        catch(Exception e)
        {
           System.out.println("Unable initiate polling");
        }
       _qm = new QueryManager();
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
                // Read light and heat values
                double  lightValue  = lightReceivingRadio.receiveLight();
                String address = lightReceivingRadio.getReceivedAddress();
                if(!_addressMap.contains(address)) continue; 
                // Print out light and heat values
                System.out.println("Message from " + lightReceivingRadio.getReceivedAddress() + " - " + "Light: " + lightValue);

                try
                {
                    //add to queue.
                    _qm.createLightRecord(lightValue,
                            lightReceivingRadio.getReceivedAddress(),
                            System.currentTimeMillis(), _port);
                    System.out.println("GOT LIGHT THRESHOLD VAL: " + lightValue);
                } catch (NullPointerException npe) {
                    System.out.println("lightService: queryManager - NullPointerException");
                }
            }
            catch (IOException io)
            {
                System.err.println("Caught " + io +  " while polling SPOT");
                io.printStackTrace();
            }
        }
    }
}
