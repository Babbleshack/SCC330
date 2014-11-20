/**
 * Half Full state controls the pour rate when glass is 50% or less
 * full.
 * @author Dominic Lindsay
 */
package org.sunspotworld.states;

import org.sunspotworld.spotMonitors.SmartCupMonitor;


public class HalfFullState implements SmartCupState 
{
    private static final double MINIMUM_FLOW_RATE = 5;
    private static final double AVERAGE_FLOW_RATE = 8;
    private static final double MAX_FLOW_RATE = 12;

    public double pour(SmartCupMonitor context, 
            double cupAngle, double fillLevelPercentage) 
    {
        System.out.println("HALF FULL STATE");
        /**
         * SUBTRACT FROM FILL LEVEL
         */
        if(cupAngle >= 35 && cupAngle <= 55)
        {
            System.out.println("=====================================");
            fillLevelPercentage = fillLevelPercentage - (MINIMUM_FLOW_RATE * (fillLevelPercentage/100));
            System.out.println("Slight Tilt worth %" + MINIMUM_FLOW_RATE);
            System.out.println("=====================================");
        } else if(cupAngle >= 15 && cupAngle <= 35)
        {
            System.out.println("=====================================");
            fillLevelPercentage -= (AVERAGE_FLOW_RATE * (fillLevelPercentage/100));
            System.out.println("Heavy Tilt worth %" + AVERAGE_FLOW_RATE);
            System.out.println("=====================================");
        } else if(cupAngle < 10)
        {
            System.out.println("=====================================");
            fillLevelPercentage -= (MAX_FLOW_RATE * (fillLevelPercentage/100));
            System.out.println("POURING worth %" + MAX_FLOW_RATE);
            System.out.println("=====================================");
        }
        if(fillLevelPercentage <= SmartCupState.NEAR_EMPTY_FILL)
        {
            this.changeState(context, cupAngle);
        }
        
        return fillLevelPercentage;
        
        
    }
    public void changeState(SmartCupMonitor context, double currentFillLevel) 
    {
        context.setState(new NearEmptyState());
    }

}
