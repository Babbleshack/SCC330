/**
 * Water monitoring service,
 * measures fill level of container
 */
package org.sunspotworld.service;

import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.LEDColor;
import java.io.IOException;
import operators.IOperator;
import operators.NullOperator;
import org.sunspotworld.controllers.LEDController;
import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.homePatterns.TaskObserver;
import org.sunspotworld.spotMonitors.IMonitor;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.SunspotPort;
import org.sunspotworld.data.SensorData;

public class WaterService implements IService, TaskObserver {
	private ISendingRadio _sRadio;
	private final IMonitor _monitor;
	private final int _serviceId;
	private final ITriColorLED _feedbackLED;
	private  final LEDColor _serviceColour;
	public WaterService(IMonitor  monitor, int serviceId){
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

		_serviceColour = LEDColor.CYAN;//find colour for water monitor
		_feedbackLED = LEDController.getLED(LEDController.STATUS_LED);
		System.out.println("innit Service with ID: " + this._serviceId);
	}
	public void startService() {
		_monitor.startMonitor();
	}
	public void stopService() {
		_monitor.stopMonitor();
	}
	public boolean isScheduled(){
		return _monitor.getStatus();
	}
	public int getServiceId(){
		return _serviceId;
	}
	public IMonitor getMonitor() {
		return _monitor;
	}
	public void setData(int data) {
		_monitor.setVariable(data);
	}
	public void setDirection(int direction){	
	}
	public IOperator getDirecton(){
		return new NullOperator();
	}
	public void update(TaskObservable o, Object arg) {
		//send data across radio connection.
		LEDController.flashLED(_feedbackLED, _serviceColour);
		_sRadio.sendWater(((SensorData)arg).getDataAsInt());

		System.out.println("Sent Heat");
	}
	public void update(TaskObservable o) {
		//send data across radio connection.
		LEDController.flashLED(_feedbackLED, _serviceColour);
		_sRadio.sendWater(((IMonitor)o).getSensorReading().getDataAsInt());
		System.out.println("Sent Heat");
	}
	//ADD OBSERVER UPDATE METHOD
}
