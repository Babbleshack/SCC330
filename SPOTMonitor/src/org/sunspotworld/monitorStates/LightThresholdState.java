package org.sunspotworld.monitorStates;
/**
 * PhotoMonitor implementation of checkSensorThreshold()
 * @author Dominic Lindsay
 */
import org.sunspotworld.spotMonitors.IThresholdMonitor;
import org.sunspotworld.spotMonitors.ThresholdMonitor;
public class LightThresholdState implements IThresholdMonitorState {
    public void checkThresholdCondition(ThresholdMonitor context) {
        if(context.getThreshold() > context.getSensorReading().getDataAsDouble()){ 
          //if threshold has not been met an set
            context.setHasBeenMet(false);
            System.out.println("Threshold as been unment");
            return;
        }
        if(!context.getHasBeenMet()){
          System.out.println("THRESHOLD MET");
          context.setHasBeenMet(true);
          context.notifyObservers(context.getSensorReading());
        }
    }
}
