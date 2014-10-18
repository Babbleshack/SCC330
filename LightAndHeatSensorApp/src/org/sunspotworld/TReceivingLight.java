/*
 * SunSpotHostApplication.java
 *
 * Created on 13-Oct-2014 23:19:07;
 */

package org.sunspotworld;

import java.io.IOException;
import org.sunspotworld.basestationMonitors.ILightMonitor;
import org.sunspotworld.basestationMonitors.IReceivingRadio;
import org.sunspotworld.basestationMonitors.MonitorFactory;
import org.sunspotworld.basestationRadios.RadiosFactory;


/**
 * Sample Sun SPOT host application
 */
public class TReceivingLight implements Runnable
{

    private ILightMonitor lightMonitor;

    // Init receiving radio
    IReceivingRadio lightReceivingRadio;

    // creates an instance of SunSpotHostApplication class and initialises
    // instance variables
    public TReceivingLight()
    {
        try
        {
            lightMonitor = MonitorFactory.createLightMonitor();
            lightReceivingRadio = RadiosFactory.createReceivingRadio(lightMonitor.getPort());
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
            System.out.println("Waiting for light info ...");
            try
            {
                // Read light and heat values
                double  lightValue  = lightReceivingRadio.receiveLight();

                // Print out light and heat values
                System.out.println("Message from " + lightReceivingRadio.getReceivedAddress() + " - " + "Light: " + lightValue);
            }
            catch (IOException io)
            {
                System.err.println("Caught " + io +  " while polling SPOT");
                io.printStackTrace();
            }
        }
    }
}