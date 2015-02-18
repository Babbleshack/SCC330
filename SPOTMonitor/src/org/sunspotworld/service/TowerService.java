/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.service;

import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.LEDColor;
import com.sun.spot.service.Task;
import java.io.IOException;
import operators.IOperator;
import operators.NullOperator;
import org.sunspotworld.controllers.LEDController;
import org.sunspotworld.spotMonitors.IMonitor;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.SunspotPort;


public class TowerService extends Task implements IService {

    private ISendingRadio _sRadio;
    private static final long SECOND = 1000;
    private static final long SAMPLE_RATE = SECOND;
    private int _serviceId;
    private  final ITriColorLED _feedbackLED;
    private  final LEDColor _serviceColour;
    public TowerService(int serviceID) 
    {
        super(SAMPLE_RATE);
        this._serviceId = serviceID;
         try
        {
            _sRadio = RadiosFactory.createSendingRadio(
                    new SunspotPort(SunspotPort.PING_PORT));	
        } catch (PortOutOfRangeException e) {
            System.err.println("error creating PING radio: " + e);
        } catch (IOException e) {
            System.err.println("error creating PING radio: " + e);
        }
         _serviceColour = LEDColor.ORANGE;
        _feedbackLED = LEDController.getLED(LEDController.STATUS_LED);
         System.out.println("STARTING TOWER");
    }
    public void doTask() {
        System.out.println("Running TOWER");
        LEDController.flashLED(_feedbackLED, _serviceColour);
        _sRadio.ping();
    }
    public void startService() {
        this.start();
        LEDController.turnLEDOn(
                LEDController.getLED(LEDController.TOWER_LED), _serviceColour);
        System.out.println("Tower Service Started");
    }

    public void stopService() {
        this.stop();
        LEDController.turnLEDOff(
                LEDController.getLED(LEDController.TOWER_LED));
        System.out.println("Tower Service Stopped");
    }

    public boolean isScheduled() {
        return this.isActive();
    }

    public int getServiceId() {
        return this._serviceId;
    }

    public IMonitor getMonitor() {
        return null;
    }
    public void setData(int data) {
        return;
    }

    public void setDirection(int direction) {
        return;
    }

    public IOperator getDirecton() {
        return new NullOperator();
    }
    

}
