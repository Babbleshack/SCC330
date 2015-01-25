/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotword.service;

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
        System.out.println("innit Service with ID" + this._serviceId);
    }
    public void startService() {
        monitor.startMonitor();
        System.out.println("Started accel Service");
    }
    public void stopService() {
        monitor.stopMonitor();
        System.out.println("Stopped accel Service");
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