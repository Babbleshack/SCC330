/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.monitorStates;

import org.sunspotworld.spotMonitors.ThresholdMonitor;


public class BearingMonitorState implements IThresholdMonitorState {
    public void checkThresholdCondition(ThresholdMonitor context) {
        if(context.getThreshold() > context.getSensorReading().getDataAsDouble()){ 
          //if threshold has not been met an set
            context.setHasBeenMet(false);
            return;
        }
        if(!context.getHasBeenMet()){
          context.setHasBeenMet(true);
          context.notifyObservers(context.getSensorReading());
        }
    }
}
