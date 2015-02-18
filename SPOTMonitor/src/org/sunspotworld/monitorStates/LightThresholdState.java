package org.sunspotworld.monitorStates;
/**
 * PhotoMonitor implementation of checkSensorThreshold()
 * @author Dominic Lindsay
 */
import org.sunspotworld.spotMonitors.IThresholdMonitor;
import org.sunspotworld.spotMonitors.ThresholdMonitor;
public class LightThresholdState implements IThresholdMonitorState {
    public void checkThresholdCondition(ThresholdMonitor context) {
        System.out.println("HAS BEEN MET: " + context.getHasBeenMet());
        if(!context.getDirection().operate(context.getSensorReading().getDataAsDouble(),
                context.getThreshold())){
            //threshold has not been met
            context.setHasBeenMet(false);
            return;
        }
        if(!context.getHasBeenMet() && context.getDirection().operate(
                context.getSensorReading().getDataAsDouble(),
                context.getThreshold())){ //if not met set flag and send reading
          context.notifyObservers(context.getSensorReading());
          context.setHasBeenMet(true);
        }
    }
}
