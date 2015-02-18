package org.sunspotworld.monitorStates;
/**
 * AxisMonitor implementation of checkSensorThreshold()
 * @author Dominic Lindsay
 */
import org.sunspotworld.spotMonitors.ThresholdMonitor;
public class AxisThresholdState implements IThresholdMonitorState {
    private static final double LOWER_THRESH = 0.8;
    private static final double UPPER_THRESH = 1.2;
    public void checkThresholdCondition(ThresholdMonitor context) {
        if(context.getSensorReading().getDataAsDouble() > LOWER_THRESH && 
                context.getSensorReading().getDataAsDouble() < UPPER_THRESH) 
            return;
        else
            context.notifyObservers(context.getSensorReading());
    }
}
