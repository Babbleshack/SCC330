/**
 * A service for encapsulating the barometer
 * @author Dominic Lindsay
 */
package org.sunspotworld.service;

import java.io.IOException;
import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.homePatterns.TaskObserver;
import org.sunspotworld.spotMonitors.IMonitor;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.SendingRadio;
import org.sunspotworld.spotRadios.SunspotPort;


public class BarometerService implements IService, TaskObserver {
    private final IMonitor _monitor;
    private final int _serviceId; 
    private ISendingRadio _sRadio;
    public BarometerService(final IMonitor monitor, final int serviceId) {
        _monitor = monitor;
        _serviceId = serviceId;
        try {
            _sRadio = RadiosFactory.createSendingRadio(
                    new SunspotPort(SunspotPort.BAROMETER_PORT));
        } catch (PortOutOfRangeException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void startService() {
        if(_monitor.getStatus())
            return;
        _monitor.startMonitor();
    }

    public void stopService() {
        if(!_monitor.getStatus())
            return;
        _monitor.stopMonitor();
    }

    public boolean isScheduled() {
        return _monitor.getStatus();
    }

    public int getServiceId() {
        return _serviceId;
    }

    public IMonitor getMonitor() {
        return _monitor;
    }

    public void setData(int data) {
        _monitor.setVariable(data);
    }

    public void update(TaskObservable o, Object arg) {
        _sRadio.sendBarometerReadion(
                ((IMonitor)o).getSensorReading().getDataAsDouble()
        );
    }

    public void update(TaskObservable o) {
        _sRadio.sendBarometerReadion(
                ((IMonitor)o).getSensorReading().getDataAsDouble()
        
        );
    }
    
    
}
