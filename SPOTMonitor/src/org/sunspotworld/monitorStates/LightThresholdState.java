package org.sunspotworld.monitorStates;
/**
 * PhotoMonitor implementation of checkSensorThreshold()
 * @author Dominic Lindsay
 */
import org.sunspotworld.data.SensorData;
import org.sunspotworld.spotMonitors.ThresholdMonitor;
public class LightThresholdState implements IThresholdMonitorState {
    public void checkThresholdCondition(final ThresholdMonitor context) {
        double threshold = context.getThreshold();
        boolean result = context.getDirection().operate(
                context.getSensorReading().getDataAsDouble(),
                threshold
        );
        if(context.getHasBeenMet() && !result) { //gone below threshold for first time
            context.setHasBeenMet(false);
            context.notifyObservers(context.getThresholdAsSensorReading());
            System.out.println("Not met threhsold for first time...");
        } 
        if(result && !context.getHasBeenMet() ){ //if met set flag and send reading
            System.out.println("Threshold met...");
            context.notifyObservers(context.getSensorReading());
            context.setHasBeenMet(true);
        }
    }
}
