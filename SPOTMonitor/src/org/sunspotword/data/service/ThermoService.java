/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotword.data.service;

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
    private IMonitor monitor;
    private int _serviceId;
    private ISendingRadio sRadio;
    //add radio and methods
    public ThermoService(IMonitor monitor, int serviceId) {
        this.monitor = monitor;
        this._serviceId = serviceId;
        try {
            sRadio = RadiosFactory.createSendingRadio(
                    new SunspotPort(SunspotPort.THERMO_PORT));
        } catch (PortOutOfRangeException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void startService() {
        monitor.startMonitor();
    }
    public void stopService() {
        monitor.stopMonitor();
    }
    public boolean isScheduled() {
        return this.isScheduled();
    }
    public int getServiceId() {
        return this._serviceId;
    }
    public void update(TaskObservable o, Object arg) {
        //send data across radio connection.
        sRadio.sendHeat(((IMonitor)o).getSensorReading().getDataAsDouble());
    }
    public void update(TaskObservable o) {
        //send data across radio connection.
        sRadio.sendHeat(((IMonitor)o).getSensorReading().getDataAsDouble());
    }
    public IMonitor getMonitor(){
        return this.monitor;
    }
}
