/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.basestationRadios;

import java.io.IOException;
import java.lang.Exception;

/**
 *
 * @author adamcornforth
 */
public class RadiosFactory 
{
    public static ISendingRadio createSendingRadio(SunspotPort port)
    {
        try
        {
            return new SendingRadio(port);
        } catch (Exception e) {
            System.err.println("IO exception occured during radion int: " + e );
        }
        return null;
    }
    public static IReceivingRadio createReceivingRadio(SunspotPort port)
    {
        try
        {
            return new ReceivingRadio(port);
        } catch (Exception e) {
            System.err.println("IO exception occured during radion int: " + e );
        }
        return null;
    }
}
