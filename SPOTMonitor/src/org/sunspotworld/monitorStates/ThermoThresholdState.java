/**
 * ThermoMonitor implementation of checkSensorThreshold
 * @author Dominic Lindsay
 */
package org.sunspotworld.monitorStates;
import org.sunspotworld.spotMonitors.ThresholdMonitor;
public class ThermoThresholdState implements IThresholdMonitorState {
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