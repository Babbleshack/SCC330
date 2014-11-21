/**
 * NearEmptyState Controls flow rate when glass is below 20% full.
 * @author Dominic Lindsay
 */
package org.sunspotworld.states;

import org.sunspotworld.spotMonitors.WaterMonitor;

public class NearEmptyState implements SmartCupState 
{

    private static final double MINIMUM_FLOW_RATE = 5;
    private static final double AVERAGE_FLOW_RATE = 10;
    private static final double MAX_FLOW_RATE = 15;

    public double pour(WaterMonitor context, 
            double cupAngle, double fillLevelPercentage) 
    {
        System.out.println("NEAR EMPTY STATE");
        /**
         * SUBTRACT FROM FILL LEVEL
         */
        if(cupAngle < 25)
        {
            fillLevelPercentage -= (MAX_FLOW_RATE * (fillLevelPercentage/100));
        }
        if(fillLevelPercentage <= 10)
        {
            this.changeState(context, cupAngle);
        }
        return fillLevelPercentage; 
    }
    public void changeState(WaterMonitor context, double currentFillLevel) 
    {
        context.setState(new RefillState());
    }
}
