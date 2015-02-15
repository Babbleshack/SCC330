/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.monitorStates;

import org.sunspotworld.spotMonitors.ThresholdMonitor;


public class BearingMonitorState implements IThresholdMonitorState {
    public void checkThresholdCondition(ThresholdMonitor context) {
        if(context.getThreshold() > context.getSensorReading().getDataAsDouble())
            return;
        else
            context.notifyObservers(context.getSensorReading());
    }
}
