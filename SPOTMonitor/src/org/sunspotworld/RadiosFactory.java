/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

import java.io.IOException;

/**
 *
 * @author adamcornforth
 */
public class RadiosFactory 
{
    public static ISendingRadio createSendingRadio()
    {
        try
        {
            return new SendingRadio();
        } catch (IOException e) {
            System.err.println("IO exception occured during radion int: " + e );
        }
        return null;
    }
    public static IReceivingRadio createReceivingRadio() 
    {
        try
        {
            return new ReceivingRadio();
        } catch (IOException e) {
            System.err.println("IO exception occured during radion int: " + e );
        }
        return null; 
    }
}
