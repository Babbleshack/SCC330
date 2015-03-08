/**
 * Water monitoring service,
 * measures fill level of container
 */

public class WaterService implements IService, TaskObserver {
	

	public WaterService(){

	}

	public void startMonitor();
	public void stopMonitor();

	public SensorData getSensorReading();

	public boolean getStatus();

	public String getType();
	/**
	* used for setting the variable of the monitor 
	* i.e. threshold or sample rate
	*/
	public void setVariable(int data);
	public void addMonitorObserver(TaskObserver to);

	//Direction stuff
	public void setDirection(IOperator direction);
	public IOperator getDirection();
}
