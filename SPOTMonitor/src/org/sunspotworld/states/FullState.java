/**
 * Full state controls the pour rate when glass is near full,
 * or over half full.
 * @author Dominic Lindsay
 */
package org.sunspotworld.states;

import org.sunspotworld.spotMonitors.WaterMonitor;

public class FullState implements SmartCupState 
{
    private static final double MINIMUM_FLOW_RATE = 5;
    private static final double AVERAGE_FLOW_RATE = 15;
    private static final double MAX_FLOW_RATE = 18;

    public double pour(WaterMonitor context, 
            double cupAngle, double fillLevelPercentage) 
    {
        System.out.println("FULL STATE");
        /**
         * SUBTRACT FROM FILL LEVEL
         */
        if(cupAngle >= 45 && cupAngle <= 65)
        {
            fillLevelPercentage -= 
                    (MINIMUM_FLOW_RATE * (fillLevelPercentage/100));
        } else if(cupAngle >= 25 && cupAngle <= 45)
        {
            fillLevelPercentage -= 
                    (AVERAGE_FLOW_RATE * (fillLevelPercentage/100));
        } else if(cupAngle < 25)
        {
            fillLevelPercentage -= (MAX_FLOW_RATE * (fillLevelPercentage/100));
        }
        if(fillLevelPercentage <= SmartCupState.HALF_FILL)
        {
            this.changeState(context, cupAngle);
        }
        
        return fillLevelPercentage;
        
        
    }
    public void changeState(WaterMonitor context, double currentFillLevel) 
    {
        context.setState(new HalfFullState());
    }
}
