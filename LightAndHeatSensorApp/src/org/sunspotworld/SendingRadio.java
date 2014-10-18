/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

import com.sun.spot.resources.Resources;
import java.io.IOException;

import com.sun.spot.util.Utils;
import com.sun.spot.io.j2me.radiogram.*;

import javax.microedition.io.*;

/**
 *
 * @author adamcornforth
 */
public class SendingRadio implements ISendingRadio
{
	private static final int HOST_PORT = 96;
    //sample period in milliseconds
    private RadiogramConnection radioConn = null;
    private Datagram  datagram = null;
    
    private String spotAddress = System.getProperty("IEEE_ADDRESS");

    public SendingRadio(SunspotPort port) throws IOException
    {
        radioConn = (RadiogramConnection) Connector.open("radiogram://broadcast:" + port.getPort());
		System.out.println("Sending Radio created for " + spotAddress + " on port " + port.getPort()); 
        datagram = radioConn.newDatagram(50); 
    }
}
