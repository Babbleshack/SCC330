/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.threads;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.IToneGenerator;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import java.io.IOException;
import java.util.Vector;
import org.sunspotword.data.TowerSpot;
import org.sunspotword.data.ZonePowerData;
import org.sunspotworld.spotRadios.IReceivingRadio;
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
    private IReceivingRadio rRadio;
    private ZonePowerData zpd;
    private ITriColorLEDArray leds;
    private IToneGenerator toneGen;
    private TowerSpot closestTower;
    private static final long SECOND = 1000;
    private static final long SAMPLE_RATE = (2*SECOND);
    private static final int FALSE_READING = -66;
    public TZoneProccessor()
    {
        try {
            sRadio = RadiosFactory.createSendingRadio
                    (new SunspotPort(SunspotPort.BASE_TOWER_PORT));
            rRadio = RadiosFactory.createReceivingRadio
                    (new SunspotPort(SunspotPort.PING_PORT));
            toneGen = (IToneGenerator) Resources.lookup(IToneGenerator.class);
        } catch (PortOutOfRangeException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        zpd = new ZonePowerData();
        leds = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
        leds.setRGB(0, 255, 0);
        leds.setOn();
        closestTower = new TowerSpot("Temp", FALSE_READING);
  //      tReceiver = new Thread(new TRoamingReceiver(zpd), "roamingService");
    }

    /**
     * receives towerdata from radio, if power level is 
     * greater than closestTower power level, then 
     * send address and make tone.
     */
    public void run() {
        while(true)
        {
            System.out.println("current closest: " + closestTower.getAddress());
            TowerSpot tSpot = rRadio.receivePing();
            System.out.println("just received: [" +
                    closestTower.getAddress() +"]");
            if(tSpot == null)
            { //ERROR DID NOT RECEIVE
                continue;
            }
            if(tSpot.getPowerLevel() < closestTower.getPowerLevel())
            {   //LESS THAN CLOSEST TOWER
                continue;
            }
            toneGen.startTone(250.0, 500);
            closestTower = tSpot; //NEW CLOSEST TOWER
            System.out.println("Closet tower: " + closestTower.getAddress()
            +" " + "Power Level: " + closestTower.getPowerLevel());
            sRadio.sendTowerAddress(closestTower.getAddress());
        }
    }   
}
