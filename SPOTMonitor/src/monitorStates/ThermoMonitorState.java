/**
 * ThermoMonitor implementation of checkSensorThreshold
 * @author Dominic Lindsay
 */
package monitorStates;
import org.sunspotworld.spotMonitors.ThresholdMonitor;
public class ThermoMonitorState implements IThresholdMonitorState {
    public void checkThresholdCondition(ThresholdMonitor context) {
        if(context.getThreshold() > context.getSensorReading().getDataAsDouble())
            return;
        else
            context.notifyObservers(context.getSensorReading());
    }
}