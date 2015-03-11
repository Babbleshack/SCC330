package org.sunspotworld.monitorStates;

import org.sunspotworld.data.SensorData;

/**
 * AxisMonitor implementation of checkSensorThreshold()
 * @author Dominic Lindsay
 */
import org.sunspotworld.spotMonitors.ThresholdMonitor;
public class AxisThresholdState implements IThresholdMonitorState {
    private static final double LOWER_THRESH = 0.8;
    private static final double UPPER_THRESH = 1.18;
    public void checkThresholdCondition(ThresholdMonitor context) {
    	SensorData sensorReading = context.getSensorReading(); 
    	System.out.println("Acceleration: " + sensorReading.getDataAsDouble());
        if(sensorReading.getDataAsDouble() != 0 && (sensorReading.getDataAsDouble() < LOWER_THRESH || 
                sensorReading.getDataAsDouble() > UPPER_THRESH)) 
            context.notifyObservers(sensorReading);
    }
}
