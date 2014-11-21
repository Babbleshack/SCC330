/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.states;
import org.sunspotworld.spotMonitors.WaterMonitor;
public class RefillState implements SmartCupState
{
    public double pour(WaterMonitor context, double cupAngle, double fillLevelPercentage) {
        System.out.println("REFILL NEEDED");
        return 0;
    }
}
