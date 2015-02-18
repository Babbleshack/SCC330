/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.service;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.IToneGenerator;
import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.LEDColor;
import operators.IOperator;
import operators.NullOperator;
import org.sunspotworld.controllers.LEDController;
import org.sunspotworld.data.TowerSpot;
import org.sunspotworld.data.ZonePowerData;
import org.sunspotworld.spotMonitors.IMonitor;
import org.sunspotworld.spotRadios.IReceivingRadio;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.SunspotPort;


public class ZoneProcessorService extends Thread implements IService {

    private ISendingRadio _sRadio;
    private IReceivingRadio _rRadio;
    private ZonePowerData _zpd;
    private final IToneGenerator _toneGen;
    private TowerSpot _closestTower;
    private final int _serviceID;
    private boolean _running = false;
    private  final ITriColorLED _feedbackLED;
    private  final LEDColor _serviceColour;
    private static final int THRESHOLD = 3;
    public ZoneProcessorService(int serviceID) {
        try
        {
            _sRadio = RadiosFactory.createSendingRadio(new SunspotPort(SunspotPort.BASE_TOWER_PORT));
            _rRadio = RadiosFactory.createReceivingRadio(new SunspotPort(SunspotPort.PING_PORT));
        } catch (Exception e) {
            System.err.println("error creating Roaming radios " + e);
        }   
        _zpd = _zpd;
        _serviceColour = LEDColor.YELLOW;
        _feedbackLED = LEDController.getLED(LEDController.STATUS_LED);
        _toneGen = (IToneGenerator) Resources.lookup(IToneGenerator.class);
        this._serviceID = serviceID;
    }
    public void run(){
        System.out.println("Running Roaming");
        this.findAnyTower(); //find a tower
        System.out.println("FOUND CLOSET TOWER");
        int count = 0; 
        String countTower = null;
        while(_running)
        {
            
            TowerSpot tSpot = _rRadio.receivePing();
            
            // Error did not receive
            if(tSpot == null) continue;

            // Discard packet if same tower 
            // But we should update the closest tower anyway
            if(tSpot.getAddress().equals(_closestTower.getAddress())) {
                _closestTower = tSpot;
                continue;
            }

            // Discard if less power than closest tower
            if(tSpot.getPowerLevel() - THRESHOLD <= _closestTower.getPowerLevel() + THRESHOLD) {   
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
            if(count > 3) {       
                count = 0;     
                _toneGen.startTone(250.0, 40);
                
                System.out.println("New closest tower. Moved from " + _closestTower.getAddress() + " (" + _closestTower.getPowerLevel() + ") to " + tSpot.getAddress() + "(" + tSpot.getPowerLevel() + ")");
                
                // New closest tower
                _closestTower = tSpot; 
                LEDController.flashLED(_feedbackLED, _serviceColour);
                _sRadio.sendTowerAddress(_closestTower.getAddress());
            }
        }
    }

    /*
    * init to any tower that pings.
    */
    private void findAnyTower(){
        System.out.println("WAITING FOR A PING FROM ANY TOWER");
        this._closestTower = _rRadio.receivePing();
        System.out.println("Initial tower: [" + 
                _closestTower.getAddress() + "]");
    }
    public void startService() {
        this._running = true;
        if(this.isAlive())
        {
            System.out.println("zone processor already started......");
            return;
        }
        this.start();
        LEDController.turnLEDOn(
                LEDController.getLED(LEDController.ROAMING_LED), _serviceColour);
        System.out.println("Zone Proccessor Started");
    }
    public void setData(int data) {
    }  

    public void stopService() {
        this._running = false;
        LEDController.turnLEDOff(
                LEDController.getLED(LEDController.ROAMING_LED));
        Thread.yield();
        System.out.println("------------STOPPING ZONE PROCESSOR-------------");
    }

    public boolean isScheduled() {
        return this._running;
    }

    public int getServiceId() {
        return this._serviceID;
    }

    public IMonitor getMonitor() {
        return null;
    }

    public void setDirection(int direction) {
        return;
    }

    public IOperator getDirecton() {
        return new NullOperator();
    }

}
