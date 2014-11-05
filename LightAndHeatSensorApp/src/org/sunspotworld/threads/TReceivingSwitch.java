package org.sunspotworld.threads;

import java.io.IOException;
import org.sunspotworld.basestationRadios.IReceivingRadio;
import org.sunspotworld.basestationMonitors.ISwitchMonitor;
import org.sunspotworld.basestationMonitors.MonitorFactory;
import org.sunspotworld.basestationRadios.RadiosFactory;

import org.sunspotworld.database.DatabaseConnectionFactory;
import org.sunspotworld.database.MySQLConnectionManager;
import org.sunspotworld.database.QueryManager;

/**
 * Sample Sun SPOT host application
 */
public class TReceivingSwitch implements Runnable
{

    private ISwitchMonitor switchMonitor;
    private QueryManager queryManager;

    // Init receiving radio
    IReceivingRadio switchReceivingRadio;

    // creates an instance of SunSpotHostApplication class and initialises
    // instance variables
    public TReceivingSwitch()
    {
        try
        {
            switchMonitor = MonitorFactory.createSwitchMonitor();
            switchReceivingRadio = RadiosFactory.createReceivingRadio(switchMonitor.getPort());
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
                String  switchId   = switchReceivingRadio.receiveSwitch();

                // Print out light and heat values
                System.out.println("Message from " + switchReceivingRadio.getReceivedAddress() + " - " + "Switch ID: " + switchId);

                try
                {
                    queryManager.createSwitchRecord(switchId, switchReceivingRadio.getReceivedAddress(), System.currentTimeMillis());
                } catch (NullPointerException npe) {
                    System.out.println("switchService: queryManager - NullPointerException");
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