/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.monitorStates;

import org.sunspotworld.spotMonitors.ThresholdMonitor;


public class BearingMonitorState implements IThresholdMonitorState {
    public void checkThresholdCondition(ThresholdMonitor context) {
        if(!context.getDirection().operate(context.getSensorReading().getDataAsDouble(),
                context.getThreshold())){
            //threshold has not been met
            context.setHasBeenMet(false);
            return;
        }
        if(!context.getHasBeenMet()){
          context.setHasBeenMet(true);
          context.notifyObservers(context.getSensorReading());
        }
    }
}
