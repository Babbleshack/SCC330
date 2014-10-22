/*
 * Thread for receiving acceleration data.
 * Dominic Lindsay
 */

package org.sunspotworld;

import java.io.IOException;
import org.sunspotworld.basestationMonitors.IReceivingRadio;
import org.sunspotworld.basestationMonitors.IAccelMonitor;
import org.sunspotworld.basestationMonitors.MonitorFactory;
import org.sunspotworld.basestationRadios.RadiosFactory;

import org.sunspotworld.DB.DatabaseConnectionFactory;
import org.sunspotworld.DB.MySQLConnectionManager;
import org.sunspotworld.DB.QueryManager;
public class TReceivingAccel implements Runnable
{

    private IAccelMonitor accelMonitor;
    private QueryManager queryManager;

    // Init receiving radio
    IReceivingRadio accelReceivingRadio;

    // creates an instance of SunSpotHostApplication class and initialises
    // instance variables
    public TReceivingAccel()
    {
        try
        {
            accelMonitor = MonitorFactory.createAccelMonitor();
            accelReceivingRadio = RadiosFactory.createReceivingRadio(accelMonitor.getPort());
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
            System.out.println("Waiting for accel info ...");
            try
            {
                // Read accel
                double  accelValue   = accelReceivingRadio.receiveAccel();

                queryManager.createAccelRecord(accelValue, accelReceivingRadio.getReceivedAddress(), System.currentTimeMillis());

                // Print out accel
                System.out.println("Message from " + accelReceivingRadio.getReceivedAddress() + " - " + "acceleration: " + accelValue);
            }
            catch (IOException io)
            {
                System.err.println("Caught " + io +  " while polling SPOT");
                io.printStackTrace();
            }
        }
    }
}