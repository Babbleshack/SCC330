/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.service;

import java.io.IOException;
import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.homePatterns.TaskObserver;
import org.sunspotworld.spotMonitors.IMonitor;
import org.sunspotworld.spotRadios.IReceivingRadio;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.SunspotPort;


public class ThermoService implements TaskObserver, IService {
    private final IMonitor _monitor;
    private final int _serviceId;
    private ISendingRadio _sRadio;
    //add radio and methods
    public ThermoService(IMonitor monitor, int serviceId) {
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
        _sRadio.sendHeat(((IMonitor)o).getSensorReading().getDataAsDouble());
        System.out.println("Sent Heat");
    }
    public void update(TaskObservable o) {
        //send data across radio connection.
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
