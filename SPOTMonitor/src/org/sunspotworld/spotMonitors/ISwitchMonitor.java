/*
 * Thermomonitor Interface
 * Dominic Lindsay 
 */
package org.sunspotworld.spotMonitors;

import org.sunspotworld.spotRadios.SunspotPort;

import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.resources.transducers.SwitchEvent;

/**
 *
 * @author babbleshack
 */
public interface ISwitchMonitor
{
    /** 
     * Returns port number for this sensor type
     */
    SunspotPort getPort(); 

    /**
     * Adds a switch to this monitor that we might want to get the ID of later
     * @param sw  switch to add to monitor
     * @param id  ID that we might want to return later
     */
    void addSwitch(ISwitch sw, String id);

    /**
     * returns ID of currently depressed button found from switch event
     * @param  evt Switch Event
     * @return String button ID
     */
    String getPressedId(SwitchEvent evt);
}
