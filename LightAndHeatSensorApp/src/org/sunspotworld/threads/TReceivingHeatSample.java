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
public class TReceivingHeatSample implements Runnable
{

    private final QueryManager _qm;

    // Init receiving radio
    private IReceivingRadio thermoReceivingRadio;
    private final int _port;
    private final ConcurrentHashMap<String, String> _addressMap;
    // creates an instance of SunSpotHostApplication class and initialises
    // instance variables
    public TReceivingHeatSample(ConcurrentHashMap addressMap)
    {
        _port = SunspotPort.THERMO_SAMPLE;
        try
        {
            thermoReceivingRadio = RadiosFactory.createReceivingRadio(
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
                double  thermoValue   = thermoReceivingRadio.receiveHeat();
                String address = thermoReceivingRadio.getReceivedAddress();
                if(!_addressMap.contains(address)) continue;    
                // Print out light and heat values
                System.out.println("Message from " + thermoReceivingRadio.getReceivedAddress() + " \t\t " + "Temperature: \t\t " + thermoValue);

                try
                {
                    _qm.createThermoRecord(thermoValue,
                            thermoReceivingRadio.getReceivedAddress(),
                            System.currentTimeMillis(), _port);
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