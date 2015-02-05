/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.service;
import java.io.IOException;
import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.homePatterns.TaskObserver;
import org.sunspotworld.sensors.ISensor;
import org.sunspotworld.spotMonitors.IMonitor;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.SunspotPort;
public class LightService implements IService, TaskObserver {
    private IMonitor monitor;
    private final int _serviceId;
    private ISendingRadio sRadio;
    public LightService(IMonitor monitor, int serviceId) {
        try {
            sRadio = RadiosFactory.createSendingRadio(
                    new SunspotPort(SunspotPort.LIGHT_PORT));
        } catch (PortOutOfRangeException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.monitor = monitor;
        this.monitor.addMonitorObserver(this);
        this._serviceId = serviceId;
        System.out.println("innit Service with ID" + this._serviceId);
    }
    public void startService() {
        monitor.startMonitor();
        System.out.println("Started Light Service");
    }
    public void stopService() {
        monitor.stopMonitor();
        System.out.println("Stopped Light Service");
    }
    public boolean isScheduled() {
        return this.getMonitor().getStatus();
    }

    public void update(TaskObservable o, Object arg) {
        //send data across radio connection.
        sRadio.sendLight(((IMonitor)o).getSensorReading().getDataAsInt());
        System.out.println("Sent Light");
    }

    public void update(TaskObservable o) {
        //send data across radio connection.
        sRadio.sendLight(((IMonitor)o).getSensorReading().getDataAsInt());
        System.out.println("Sent Light");
    }
    public int getServiceId() {
        return this._serviceId;
    }
    public IMonitor getMonitor(){
        return this.monitor;
    }
    public void setData(int data) {
        this.monitor.setVariable(data);
    }
}
