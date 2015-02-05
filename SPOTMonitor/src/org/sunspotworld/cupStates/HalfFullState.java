/**
 * Half Full state controls the pour rate when glass is 50% or less
 * full.
 * @author Dominic Lindsay
 */
package org.sunspotworld.cupStates;
import org.sunspotworld.spotMonitors.WaterMonitor;
public class HalfFullState implements SmartCupState 
{
    private static final double MINIMUM_FLOW_RATE = 5;
    private static final double AVERAGE_FLOW_RATE = 8;
    private static final double MAX_FLOW_RATE = 12;

    public double pour(WaterMonitor context, 
            double cupAngle, double fillLevelPercentage) 
    {
        System.out.println("HALF FULL STATE");
        /**
         * SUBTRACT FROM FILL LEVEL
         */
        if(cupAngle >= 35 && cupAngle <= 55)
        {
            fillLevelPercentage -= 
                    (MINIMUM_FLOW_RATE * (fillLevelPercentage/100));
        } else if(cupAngle >= 15 && cupAngle <= 35)
        {
            fillLevelPercentage -= 
                    (AVERAGE_FLOW_RATE * (fillLevelPercentage/100));
        } else if(cupAngle < 10)
        {
            fillLevelPercentage -= (MAX_FLOW_RATE * (fillLevelPercentage/100));
        }
        if(fillLevelPercentage <= SmartCupState.NEAR_EMPTY_FILL)
        {
            this.changeState(context, cupAngle);
        }
        return fillLevelPercentage;
    }
    public void changeState(WaterMonitor context, double currentFillLevel) 
    {
        context.setState(new NearEmptyState());
    }

}
