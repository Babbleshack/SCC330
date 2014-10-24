/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.spotMonitors;

import com.sun.spot.resources.Resources;

/**
 *
 * @author babbleshack
 */
public class MonitorFactory 
{
    public static ILightMonitor createLightMonitor(int threshold)
    {
        return new LightMonitor(threshold);
    }
    public static IThermoMonitor createThermoMonitor(int threshold)
    {
        return new ThermoMonitor(threshold);
    }
    public static IAccelMonitor createAccelMonitor()
    {
    	return new AccelMonitor();
    }
    public static IMotionMonitor createMotionMonitor()
    {
        return new MotionMonitor();
    }
}
