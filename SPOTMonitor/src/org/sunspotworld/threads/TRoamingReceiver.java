
package org.sunspotworld.threads;

import java.util.Vector;
import org.sunspotworld.spotRadios.IReceivingRadio;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.SunspotPort;

/**
 *
 * @author Dominic Lindsay
 */
public class TRoamingReceiver implements Runnable 
{
    private IReceivingRadio rRadio;
    private Vector towers;
    private static final int NUMBER_OF_ZONES = 3;
    public TRoamingReceiver()
    {
        try
        {
            rRadio = RadiosFactory.createReceivingRadio(new SunspotPort(SunspotPort.PING_PORT)); 
        } catch (Exception e) {
            System.err.println("error creating TOWER radios " + e);
	}
    }

    public void run() 
    {
        towers = new Vector(NUMBER_OF_ZONES);
        
        
    }

                

}
