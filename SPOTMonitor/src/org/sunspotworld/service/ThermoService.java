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


public class ThermoService implements TaskObserver, IService {
    private final IMonitor _monitor;
    private final int _serviceId;
    private ISendingRadio _sRadio;
    private final ITriColorLED _feedbackLED;
    private  final LEDColor _serviceColour;
    public ThermoService(final IMonitor monitor, final int serviceId) {
        this._monitor = monitor;
        this._monitor.addMonitorObserver(this);
        this._serviceId = serviceId;
        System.out.println("innit Service with ID" + this._serviceId);
        try {
            _sRadio = RadiosFactory.createSendingRadio(
                    new SunspotPort(SunspotPort.THERMO_PORT));
        } catch (PortOutOfRangeException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        _serviceColour = LEDColor.RED;
        _feedbackLED = LEDController.getLED(LEDController.STATUS_LED);
        LEDController.turnLEDOn(
                LEDController.getLED(LEDController.THERMAL_LED), _serviceColour);
    }
    public void startService() {
        _monitor.startMonitor();
        System.out.println("Started Thermo Service");
    }
    public void stopService() {
        _monitor.stopMonitor();
        System.out.println("Stopped Thermo Service");
    }
    public boolean isScheduled() {
        return this.getMonitor().getStatus();
    }
    public int getServiceId() {
        return this._serviceId;
    }
    public void update(TaskObservable o, Object arg) {
        //send data across radio connection.
        LEDController.flashLED(_feedbackLED, _serviceColour);
        _sRadio.sendHeat(((IMonitor)o).getSensorReading().getDataAsDouble());
        System.out.println("Sent Heat");
    }
    public void update(TaskObservable o) {
        //send data across radio connection.
        LEDController.flashLED(_feedbackLED, _serviceColour);
        _sRadio.sendHeat(((IMonitor)o).getSensorReading().getDataAsDouble());
        System.out.println("Sent Heat");
    }
    public IMonitor getMonitor(){
        return this._monitor;
    }
    public void setData(int data) {
        this._monitor.setVariable(data);
    }
}
