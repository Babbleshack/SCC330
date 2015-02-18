package org.sunspotworld.monitorStates;
/**
 * PhotoMonitor implementation of checkSensorThreshold()
 * @author Dominic Lindsay
 */
import org.sunspotworld.spotMonitors.IThresholdMonitor;
import org.sunspotworld.spotMonitors.ThresholdMonitor;
public class LightThresholdState implements IThresholdMonitorState {
    public void checkThresholdCondition(ThresholdMonitor context) {
        if(context.getThreshold() > context.getSensorReading().getDataAsDouble() && 
                !context.getHasBeenMet()){
            context.setHasBeenMet(false);
            return;
        }
            context.setHasBeenMet(true);
            context.notifyObservers(context.getSensorReading());
    }
}
