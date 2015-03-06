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
public class TReceivingAccelThreshold implements Runnable
{

    private QueryManager _qm;

    // Init receiving radio
    private IReceivingRadio accelReceivingRadio;
    private final int _port;
    private final ConcurrentHashMap<String, String> _addressMap;
    // creates an instance of SunSpotHostApplication class and initialises
    // instance variables
    public TReceivingAccelThreshold(ConcurrentHashMap addressMap)
    {
       _port = SunspotPort.ACCEL_THRESH;
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
                String address = accelReceivingRadio.getReceivedAddress();
                if(!_addressMap.contains(address)) continue;

                // Print out accel
                System.out.println("Message from " + accelReceivingRadio.getReceivedAddress() + " - " + "acceleration: " + accelValue);

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