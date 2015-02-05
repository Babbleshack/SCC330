/*
 * SunSpotHostApplication.java
 *
 * Created on 13-Oct-2014 23:19:07;
 */

package org.sunspotworld.threads;

import java.io.IOException;
import org.sunspotworld.basestationRadios.IReceivingRadio;
import org.sunspotworld.basestationMonitors.IThermoMonitor;
import org.sunspotworld.basestationMonitors.MonitorFactory;
import org.sunspotworld.basestationRadios.RadiosFactory;

import org.sunspotworld.database.QueryManager;

/**
 * Sample Sun SPOT host application
 */
public class TReceivingHeat implements Runnable
{

    private IThermoMonitor thermoMonitor;
    private QueryManager queryManager;

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
                double  thermoValue   = thermoReceivingRadio.receiveHeat();

                // Print out light and heat values
                System.out.println("Message from " + thermoReceivingRadio.getReceivedAddress() + " - " + "Heat: " + thermoValue);

                try
                {
                    queryManager.createThermoRecord(thermoValue, thermoReceivingRadio.getReceivedAddress(), System.currentTimeMillis());
                } catch (NullPointerException npe) {
                    System.out.println("heatService: queryManager - NullPointerException");
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