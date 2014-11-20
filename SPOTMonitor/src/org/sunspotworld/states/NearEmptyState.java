/**
 * NearEmptyState Controls flow rate when glass is below 20% full.
 * @author Dominic Lindsay
 */
package org.sunspotworld.states;

import org.sunspotworld.spotMonitors.SmartCupMonitor;

public class NearEmptyState implements SmartCupState 
{

    private static final double MINIMUM_FLOW_RATE = 5;
    private static final double AVERAGE_FLOW_RATE = 10;
    private static final double MAX_FLOW_RATE = 15;

    public double pour(SmartCupMonitor context, 
            double cupAngle, double fillLevelPercentage) 
    {
        System.out.println("NEAR EMPTY STATE");
        /**
         * SUBTRACT FROM FILL LEVEL
         */
        if(cupAngle >= 45 && cupAngle <= 65)
        {
            System.out.println("=====================================");
            System.out.println("NOT POURING");
            System.out.println("=====================================");
        } else if(cupAngle >= 25 && cupAngle <= 45)
        {
            System.out.println("=====================================");
            System.out.println("Not Pouring");
            System.out.println("=====================================");
        } else if(cupAngle < 25)
        {
            System.out.println("=====================================");
            fillLevelPercentage -= (MAX_FLOW_RATE * (fillLevelPercentage/100));
            System.out.println("POURING worth %" + MAX_FLOW_RATE);
            System.out.println("=====================================");
        }
        if(fillLevelPercentage <= 10)
        {
            this.changeState(context, cupAngle);
        }
        return fillLevelPercentage; 
    }
    public void changeState(SmartCupMonitor context, double currentFillLevel) 
    {
        context.setState(new RefillState());
    }

}
