/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.service;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.IToneGenerator;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import java.util.Random;
import org.sunspotworld.data.TowerSpot;
import org.sunspotworld.data.ZonePowerData;
import org.sunspotworld.spotMonitors.IMonitor;
import org.sunspotworld.spotRadios.IReceivingRadio;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.SunspotPort;


public class ZoneProcessorService extends Thread implements IService {

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
    private final int serviceID;
    private boolean running = false;
    public ZoneProcessorService(int serviceID) {
        try
        {
            sRadio = RadiosFactory.createSendingRadio(new SunspotPort(SunspotPort.BASE_TOWER_PORT));
            rRadio = RadiosFactory.createReceivingRadio(new SunspotPort(SunspotPort.TOWER_RECIEVER_PORT));
        } catch (Exception e) {
            System.err.println("error creating Roaming radios " + e);
        }   
        zpd = zpd;
        leds = (ITriColorLEDArray)
        Resources.lookup(ITriColorLEDArray.class );
        
        this.serviceID = serviceID;
    }
    public void run(){
       findAnyTower(); //find a tower

        int count = 0; 
        String countTower = null;
        while(running)
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
    private void findAnyTower(){
        this.closestTower = rRadio.receivePing();
        System.out.println("Initial tower: [" + 
                closestTower.getAddress() + "]");
    }
    public void startService() {
        this.running = true;
        leds.setRGB(0, 255, 0);
        leds.setOn();
        if(this.isAlive())
        {
            System.out.println("zone processor already started......");
            return;
        }
        this.start();
        System.out.println("Zone Proccessor Started");
    }
    public void setData(int data) {
    }  

    public void stopService() {
        this.running = false;
        leds.setOff();
        Thread.yield();
        System.out.println("------------STOPPING ZONE PROCESSOR-------------");
    }

    public boolean isScheduled() {
        return this.running;
    }

    public int getServiceId() {
        return this.serviceID;
    }

    public IMonitor getMonitor() {
        return null;
    }

}
