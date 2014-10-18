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

/**
 * Sample Sun SPOT host application
 */
public class TReceivingHeat implements Runnable
{

    private IThermoMonitor thermoMonitor;

    // Init receiving radio
    IReceivingRadio thermoReceivingRadio;

    // creates an instance of SunSpotHostApplication class and initialises
    // instance variables
    public TReceivingHeat()
    {
        try
        {
            thermoMonitor = MonitorFactory.createThermoMonitor();
            thermoReceivingRadio = RadiosFactory.createReceivingRadio(thermoMonitor.getPort());
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
            System.out.println("Waiting for heat info ...");
            try
            {
                // Read light and heat values
                double  thermoValue   = thermoReceivingRadio.receiveHeat();

                // Print out light and heat values
                System.out.println("Message from " + thermoReceivingRadio.getReceivedAddress() + " - " + "Heat: " + thermoValue);
            }
            catch (IOException io)
            {
                System.err.println("Caught " + io +  " while polling SPOT");
                io.printStackTrace();
            }
        }
    }
}