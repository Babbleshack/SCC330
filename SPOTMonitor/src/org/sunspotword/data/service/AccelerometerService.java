/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotword.data.service;

import java.io.IOException;
import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.homePatterns.TaskObserver;
import org.sunspotworld.spotMonitors.IMonitor;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.SunspotPort;


public class AccelerometerService implements IService, TaskObserver {
    IMonitor monitor;
    private final int _serviceId;
    private ISendingRadio sRadio;
    public AccelerometerService(IMonitor monitor, int serviceId) {
        this.monitor = monitor;
        this._serviceId = serviceId;
        try {
            sRadio = RadiosFactory.createSendingRadio(new SunspotPort(SunspotPort.ACCEL_PORT));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (PortOutOfRangeException ex) {
            ex.printStackTrace();
        }
    }
    public void startService() {
        this.monitor.startMonitor();
    }
    public void stopService() {
        this.monitor.stopMonitor();
    }
    public boolean isScheduled() {
        return this.monitor.getStatus();
    }
    public void update(TaskObservable o, Object arg) {
        //send data on radio
        sRadio.sendAccel(((IService)o).getMonitor().getSensorReading().getDataAsDouble());
    }
    public void update(TaskObservable o) {
        //sends data on radio
        sRadio.sendAccel(((IService)o).getMonitor().getSensorReading().getDataAsDouble());
    }
    public int getServiceId() {
        return this._serviceId;
    }
    public IMonitor getMonitor(){
        return this.monitor;
    }
}