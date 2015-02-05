/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.spotRadios;

import java.io.IOException;

/**
 *
 * @author adamcornforth
 */
public class RadiosFactory 
{
    public static ISendingRadio createSendingRadio(SunspotPort port) throws IOException
    {
        return new SendingRadio(port);
    }
    public static IReceivingRadio createReceivingRadio(SunspotPort port) throws IOException
    {
        return new ReceivingRadio(port);   
    }
}
