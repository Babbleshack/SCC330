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
public interface IThermoMonitor 
{
    /**
     * return temperature in Degrees Celsius
     * @return double temp
     */
    double getCelsiusTemp();
    /**
     * returns temperature in Fahrenheit
     * @return double temp
     */
    double getFahrenheitTemp();
}
