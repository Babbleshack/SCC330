/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.basestationMonitors;

import org.sunspotworld.basestationRadios.SunspotPort;

/**
 *
 * @author babbleshack
 */
public interface ILightMonitor
{
	/** 
     * Returns port number for this sensor type
     */
    SunspotPort getPort(); 
}
