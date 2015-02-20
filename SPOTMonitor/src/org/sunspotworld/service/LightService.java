/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.service;
import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.LEDColor;
import java.io.IOException;
import operators.GreaterThan;
import operators.IOperator;
import operators.LessThan;
import operators.NullOperator;
import org.sunspotworld.controllers.LEDController;
import org.sunspotworld.data.SensorData;
import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.homePatterns.TaskObserver;
import org.sunspotworld.spotMonitors.IMonitor;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.SunspotPort;
public class LightService implements IService, TaskObserver {
    private final IMonitor _monitor;
    private final int _serviceId;
    private ISendingRadio _sRadio;
    private final ITriColorLED _feedbackLED;
    private  final LEDColor _serviceColour;
    private IOperator _direction;
    public LightService(IMonitor monitor, int serviceId) {
        try {
            _sRadio = RadiosFactory.createSendingRadio(
                    new SunspotPort(serviceId));
        } catch (PortOutOfRangeException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this._monitor = monitor;
        this._monitor.addMonitorObserver(this);
        this._serviceId = serviceId;
        
        _serviceColour = LEDColor.CYAN;
        _feedbackLED = LEDController.getLED(LEDController.STATUS_LED);
        System.out.println("innit Service with ID" + this._serviceId);
    }
    public void startService() {
        _monitor.startMonitor();
        LEDController.turnLEDOn(
                LEDController.getLED(LEDController.LIGHT_LED), _serviceColour);
        System.out.println("Started Light Service");
    }
    public void stopService() {
        _monitor.stopMonitor();
        LEDController.turnLEDOff(
                LEDController.getLED(LEDController.LIGHT_LED));
        System.out.println("Stopped Light Service");
    }
    public boolean isScheduled() {
        return this.getMonitor().getStatus();
    }

    public void update(TaskObservable o, Object arg) {
        //send data across radio connection.
        LEDController.flashLED(_feedbackLED, _serviceColour);
        _sRadio.sendLight(((SensorData)arg).getDataAsDouble());
        System.out.println("Sent Light");
    }

    public void update(TaskObservable o) {
        //send data across radio connection.
        LEDController.flashLED(_feedbackLED, _serviceColour);
        _sRadio.sendLight(((IMonitor)o).getSensorReading().getDataAsDouble());
        System.out.println("Sent Light");
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
    public void setDirection(int dir) {
        if(dir == IOperator.ABOVE)
            _monitor.setDirection(new GreaterThan());
        else if(dir == IOperator.BELOW)
            _monitor.setDirection(new LessThan());
        else 
            _monitor.setDirection(new NullOperator());
    }
    public IOperator getDirecton() {
        return _direction;
    }
}
