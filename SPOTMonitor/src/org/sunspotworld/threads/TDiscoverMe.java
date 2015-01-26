package org.sunspotworld.threads;

import com.sun.spot.util.Utils;
import java.io.IOException;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.SunspotPort;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.PortOutOfRangeException;

// import java.util.Observer;

/**
 * Thread to send DiscoverMe requests
 */
public class TDiscoverMe implements Runnable
{
    ISendingRadio discoverMeRadio;
    private static final int SAMPLE_RATE = 5 * 1000; // 30 seconds

    /**
     * Instantiates the sending radio required
     * for sending discover me requests
     */
    public TDiscoverMe()
    {
        try
        {
            discoverMeRadio = RadiosFactory.createSendingRadio(new SunspotPort(90));
        }
        catch(PortOutOfRangeException e)
        {
           System.out.println("Unable initiate discover me sending radio");
        } catch (IOException e) {
            System.out.println("Unable initiate discover me sending radio");
        }
    }
    public void run()
    {
        while (true)
        {
            // Send thermo reading
            discoverMeRadio.discoverMe(); 
            Utils.sleep(SAMPLE_RATE);
        }
    }
}