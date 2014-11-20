/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.states;

import org.sunspotworld.spotMonitors.SmartCupMonitor;


public class RefillState implements SmartCupState
{
    public double pour(SmartCupMonitor context, double cupAngle, double fillLevelPercentage) {
        System.out.println("REFILL NEEDED");
        return 0;
    }
}
