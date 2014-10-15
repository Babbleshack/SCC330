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
    public static ISendingRadio createSendingRadio() throws IOException
    {
        return new SendingRadio();
    }
    public static IReceivingRadio createReceivingRadio() throws IOException
    {
        return new ReceivingRadio(); 
    }
}
