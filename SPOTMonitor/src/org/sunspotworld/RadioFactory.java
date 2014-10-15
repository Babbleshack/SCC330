/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

/**
 *
 * @author adamcornforth
 */
public class RadioFactory 
{
    public static IRadioMonitor createRadioMonitor()
    {
        return new RadioMonitor();
    }
}
