package org.sunspotworld.monitorStates;
/**
 * PhotoMonitor implementation of checkSensorThreshold()
 * @author Dominic Lindsay
 */
import org.sunspotworld.spotMonitors.ThresholdMonitor;
public class LightThresholdState implements IThresholdMonitorState {
    public void checkThresholdCondition(final ThresholdMonitor context) {
        boolean result = context.getDirection().operate(
                context.getSensorReading().getDataAsDouble(),
                context.getThreshold()
        );
        if(context.getHasBeenMet() && !result) { //gone below threshold for first time
            context.setHasBeenMet(false);
            context.notifyObservers(context.getThresholdAsSensorReading());
        } 
        if(result && !context.getHasBeenMet() ){ //if met set flag and send reading
            context.notifyObservers(context.getSensorReading());
            context.setHasBeenMet(true);
        }
    }
}
