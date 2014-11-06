/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.threads;

import java.io.IOException;
import org.sunspotword.data.ZonePowerData;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.SunspotPort;

/**
 *
 * @author Babblebase
 */
public class TZoneProccessor implements Runnable
{
    private Thread tReceiver;
    private ISendingRadio sRadio;
    private ZonePowerData zpd;
    public TZoneProccessor()
    {
        try {
            sRadio = RadiosFactory.createSendingRadio
                    (new SunspotPort(SunspotPort.BASE_TOWER_PORT));
        } catch (PortOutOfRangeException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        zpd = new ZonePowerData();
        tReceiver = new Thread(new TRoamingReceiver(zpd), "roamingService");
    }

    /**
     * starts receiver thread, and waits for it to return,
     * where then calls ZonePowerData for the closest tower.
     */
    public void run() {
        while(true)
        {
            try {
                tReceiver.run();
                //tReceiver.start();
                tReceiver.join();
                sRadio.sendTowerAddress(zpd.closestTowerAddress());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    
}
