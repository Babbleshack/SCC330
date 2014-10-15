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
public class RadiosFactory 
{
    public static ISendingRadio createSendingRadio()
    {
        return new SendingRadio();
    }
    public static IReceivingRadio createReceivingRadio()
    {
        return new ReceivingRadio(); 
    }
}
