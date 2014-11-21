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
    private static final int SAMPLE_RATE = 30 * 1000; // 30 seconds

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

    public void startPolling() throws Exception
    {
    }

    public void run()
    {
        
        while (true)
        {
            while (true)
            {
                // Send thermo reading
                discoverMeRadio.discoverMe(); 
                Utils.sleep(SAMPLE_RATE);
            }
            /*Vector jobs;
            try {
                jobs = discoverMeRadio.discoverMe(30 * 1000);
            } catch (TimeoutException ex) {
                ex.printStackTrace();
                continue;
            }
            */
            
            ///define what is going into jobs.
        }
        /*Send Discover me
         * wait 30 seconds for reply
         * When received go through list of services,
         * Starting ones that correspond.
         * if no reply send a second discover me. 
        */
        
        
        // main switch reading/polling loop
/**        while (true)
        {
            discoverMeRadio.discoverMe(); 
            Utils.sleep(SAMPLE_RATE);
        }*/
    }

}