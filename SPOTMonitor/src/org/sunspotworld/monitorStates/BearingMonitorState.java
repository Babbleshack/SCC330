/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.monitorStates;

import org.sunspotworld.spotMonitors.ThresholdMonitor;


public class BearingMonitorState implements IThresholdMonitorState {
    public void checkThresholdCondition(ThresholdMonitor context) {
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
