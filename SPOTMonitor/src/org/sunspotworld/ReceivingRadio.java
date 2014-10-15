/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

import com.sun.spot.resources.Resources;
import com.sun.spot.sensorboard.peripheral.LightSensor;
import java.io.IOException;
/**
 *
 * @author adamcornforth
 */
public class ReceivingRadio implements IReceivingRadio
{
    public ReceivingRadio() throws IOException
    {
        System.out.println("Receiving Radio created");
    }
}
