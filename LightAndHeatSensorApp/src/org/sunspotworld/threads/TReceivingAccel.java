/*
 * Thread for receiving acceleration data.
 * Dominic Lindsay
 */

package org.sunspotworld.threads;

import java.io.IOException;
import org.sunspotworld.basestationRadios.IReceivingRadio;
import org.sunspotworld.basestationMonitors.IAccelMonitor;
import org.sunspotworld.basestationMonitors.MonitorFactory;
import org.sunspotworld.basestationRadios.RadiosFactory;

import org.sunspotworld.database.QueryManager;
public class TReceivingAccel implements Runnable
{

    private IAccelMonitor accelMonitor;
    private QueryManager queryManager;

    // Init receiving radio
    private IReceivingRadio accelReceivingRadio;

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
            try
            {
                // Read accel
                double  accelValue   = accelReceivingRadio.receiveAccel();

                // Print out accel
                System.out.println("Message from " + accelReceivingRadio.getReceivedAddress() + " - " + "acceleration: " + accelValue);

                try
                {
                    queryManager.createAccelRecord(accelValue, accelReceivingRadio.getReceivedAddress(), System.currentTimeMillis());
                } catch (NullPointerException npe) {
                    System.out.println("accelService: queryManager - NullPointerException");
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