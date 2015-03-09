/*
 * Thread for receiving acceleration data.
 * Dominic Lindsay
 */

package org.sunspotworld.threads;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import org.sunspotworld.basestationRadios.IReceivingRadio;
import org.sunspotworld.basestationRadios.RadiosFactory;
import org.sunspotworld.basestationRadios.SunspotPort;

import org.sunspotworld.database.QueryManager;
public class TReceivingAccelSample implements Runnable
{

    private QueryManager _qm;

    // Init receiving radio
    private IReceivingRadio accelReceivingRadio;
    private final int _port;
    private final ConcurrentHashMap<String, String> _addressMap;
    // creates an instance of SunSpotHostApplication class and initialises
    // instance variables
    public TReceivingAccelSample(ConcurrentHashMap addressMap)
    {
       _port = SunspotPort.ACCEL_SAMPLE;
        try
        {
            accelReceivingRadio = RadiosFactory.createReceivingRadio(
                    new SunspotPort(_port)
            );
            _qm = new QueryManager();
        }
        catch(Exception e)
        {
           System.out.println("Unable initiate polling");
        }
        _addressMap = addressMap;
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
                System.out.println("Message from " + accelReceivingRadio.getReceivedAddress() + " \t\t " + "Acceleration: \t\t " + accelValue);

                try
                {   
                    _qm.createAccelRecord(accelValue, 
                            accelReceivingRadio.getReceivedAddress(), 
                            System.currentTimeMillis(), _port);
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