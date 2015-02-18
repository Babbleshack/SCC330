/*
 * SunSpotHostApplication.java
 *
 * Created on 13-Oct-2014 23:19:07;
 */

package org.sunspotworld.threads;

import java.io.IOException;
import org.sunspotworld.basestationMonitors.ILightMonitor;
import org.sunspotworld.basestationRadios.IReceivingRadio;
import org.sunspotworld.basestationMonitors.MonitorFactory;
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
    public TReceivingLightThreshold()
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
                int  lightValue  = lightReceivingRadio.receiveLight();

                // Print out light and heat values
                System.out.println("Message from " + lightReceivingRadio.getReceivedAddress() + " - " + "Light: " + lightValue);

                try
                {
                    //add to queue.
                    _qm.createLightRecord(lightValue,
                            lightReceivingRadio.getReceivedAddress(),
                            System.currentTimeMillis(), _port);
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