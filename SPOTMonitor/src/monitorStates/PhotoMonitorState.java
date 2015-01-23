package monitorStates;
/**
 * PhotoMonitor implementation of checkSensorThreshold()
 * @author Dominic Lindsay
 */
import org.sunspotworld.spotMonitors.ThresholdMonitor;
public class PhotoMonitorState {
    public void checkThresholdCondition(ThresholdMonitor context) {
        if(context.getThreshold() > context.getSensorReading().getDataAsDouble())
            return;
        else
            context.notifyObservers(context.getSensorReading());
    }
}
