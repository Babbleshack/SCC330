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
            System.out.println("=====================================");
            fillLevelPercentage = fillLevelPercentage - (MINIMUM_FLOW_RATE * (fillLevelPercentage/100));
            System.out.println("Slight Tilt worth %" + MINIMUM_FLOW_RATE);
            System.out.println("=====================================");
        } else if(cupAngle >= 25 && cupAngle <= 45)
        {
            System.out.println("=====================================");
            fillLevelPercentage -= (AVERAGE_FLOW_RATE * (fillLevelPercentage/100));
            System.out.println("Heavy Tilt worth %" + AVERAGE_FLOW_RATE);
            System.out.println("=====================================");
        } else if(cupAngle < 25)
        {
            System.out.println("=====================================");
            fillLevelPercentage -= (MAX_FLOW_RATE * (fillLevelPercentage/100));
            System.out.println("POURING worth %" + MAX_FLOW_RATE);
            System.out.println("=====================================");
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
