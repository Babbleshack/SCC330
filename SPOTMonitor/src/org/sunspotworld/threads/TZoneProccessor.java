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
import java.util.Random;
import org.sunspotworld.data.TowerSpot;
import org.sunspotworld.data.ZonePowerData;
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
    private Random rGen;
    private static final long SECOND = 1000;
    private static final long SAMPLE_RATE = (2*SECOND);
    private static final int FALSE_READING = -66;
    private static final int THRESHOLD = 3;
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
        rGen = new Random();
  //      tReceiver = new Thread(new TRoamingReceiver(zpd), "roamingService");
    }

    /**
     * receives towerdata from radio, if power level is 
     * greater than closestTower power level, then 
     * send address and make tone.
     */
    public void run() {
        init(); //find a tower

        int count = 0; 
        String countTower = null;
        while(true)
        {
            TowerSpot tSpot = rRadio.receivePing();
            
            // Error did not receive
            if(tSpot == null) continue;

            // Discard packet if same tower 
            // But we should update the closest tower anyway
            if(tSpot.getAddress().equals(closestTower.getAddress())) {
                closestTower = tSpot;
                continue;
            }

            // Discard if less power than closest tower
            if(tSpot.getPowerLevel() - THRESHOLD <= closestTower.getPowerLevel() + THRESHOLD) {   
                continue;
            } else {
                if(count == 0) countTower = tSpot.getAddress(); 
                if(!countTower.equals(tSpot.getAddress())) {
                    countTower = tSpot.getAddress();
                    count = 0; 
                    System.out.println("New Count " + count + ": from tower " + tSpot.getAddress());
                } else {
                    System.out.println("Count " + count + ": from tower " + tSpot.getAddress());
                    count++;
                }
            }

            // Only switch tower if we are reasonably sure we're in the new zone (not just anomaly)
            if(count > 5) {       
                count = 0;     
                toneGen.startTone(250.0, 40);
                
                System.out.println("New closest tower. Moved from " + closestTower.getAddress() + " (" + closestTower.getPowerLevel() + ") to " + tSpot.getAddress() + "(" + tSpot.getPowerLevel() + ")");
                
                // New closest tower
                closestTower = tSpot; 

                sRadio.sendTowerAddress(closestTower.getAddress());

                long wait = (SECOND / (rGen.nextInt(5) + 1));
            }
        }
    }

    /*
    * init to any tower that pings.
    */
    private void init()
    {
        this.closestTower = rRadio.receivePing();
        System.out.println("Initial tower: [" + 
                closestTower.getAddress() + "]");
    }
}
