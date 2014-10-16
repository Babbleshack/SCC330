/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

/**
 *
 * @author babbleshack
 */
public interface ILightMonitor
{
    /**
     * basically a wrapper
     * returns the average value of
     * 17 '1 msec' readings.
     * @return 
     */
    int getLightIntensity();
    
}
