/**
 * SmartCupState represents the current fill state of the 
 * smart cup.
 * states include full, half full and empty.
 * Dominic Lindsay
 */
package org.sunspotworld.states;

import org.sunspotworld.spotMonitors.SmartCupMonitor;

public interface SmartCupState 
{
    /**
     * Define fill level values
     */
    public static final double MAX_FILL = 100;
    public static final double HALF_FILL = 50;
    public static final double NEAR_EMPTY_FILL = 15;
    public double pour(SmartCupMonitor context,
            double cupAngle, double fillLevelPercentage);
}
