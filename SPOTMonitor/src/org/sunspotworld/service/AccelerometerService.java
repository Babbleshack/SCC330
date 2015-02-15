/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.service;

import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.LEDColor;
import java.io.IOException;
import org.sunspotworld.controllers.LEDController;
import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.homePatterns.TaskObserver;
import org.sunspotworld.spotMonitors.IMonitor;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.SunspotPort;


public class AccelerometerService implements IService, TaskObserver {
    private IMonitor _monitor;
    private final int _serviceId;
    private ISendingRadio _sRadio;
    private final ITriColorLED _feedbackLED;
    private final LEDColor serviceColour;
    public AccelerometerService(IMonitor monitor, int serviceId) {
        this._monitor = monitor;
        this._monitor.addMonitorObserver(this);
        this._serviceId = serviceId;
        try {
            _sRadio = RadiosFactory.createSendingRadio(new SunspotPort(SunspotPort.ACCEL_PORT));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (PortOutOfRangeException ex) {
            ex.printStackTrace();
        }
        serviceColour = LEDColor.GREEN;
        //LED CONTROLLER TIME
        LEDController.turnLEDOn(
                LEDController.getLED(LEDController.ACCEL_LED),
                serviceColour
        );
        _feedbackLED = LEDController.getLED(LEDController.STATUS_LED);
        System.out.println("innit Service with ID" + this._serviceId);
    }
    public void startService() {
        _monitor.startMonitor();
        System.out.println("Started accel Service");
    }
    public void stopService() {
        _monitor.stopMonitor();
        System.out.println("Stopped accel Service");
    }
    public boolean isScheduled() {
        return this._monitor.getStatus();
    }
    public void update(TaskObservable o, Object arg) {
        //send data on radio
        LEDController.flashLED(_feedbackLED, serviceColour);
        _sRadio.sendAccel(((IService)o).getMonitor().getSensorReading().getDataAsDouble());
    }
    public void update(TaskObservable o) {
        //sends data on radio
        LEDController.flashLED(_feedbackLED, serviceColour);
        _sRadio.sendAccel(((IService)o).getMonitor().getSensorReading().getDataAsDouble());
    }
    public int getServiceId() {
        return this._serviceId;
    }
    public IMonitor getMonitor(){
        return this._monitor;
    }
    public void setData(int data) {
        this._monitor.setVariable(data);
    }
}