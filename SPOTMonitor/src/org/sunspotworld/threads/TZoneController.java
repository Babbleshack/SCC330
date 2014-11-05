/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.threads;

import java.io.IOException;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.SunspotPort;

/**
 *
 * @author Babblebase
 */
public class TZoneController implements Runnable
{
    private TTowerReceiver tReceiver;
    private ISendingRadio sRadio;
    public TZoneController()
    {
        try {
            sRadio = RadiosFactory.createSendingRadio
                    (new SunspotPort(SunspotPort.BASE_TOWER_PORT));
        } catch (PortOutOfRangeException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        tReceiver = new TTowerReceiver();
    }

    public void run() {
        while(true)
        {
            tReceiver.start();
            tReceiver.join();
            //synced storage return closest
            //send address to BS
        }
    }

    
}
