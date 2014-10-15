/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.peripheral.radio.IRadioPolicyManager;
import com.sun.spot.io.j2me.radiostream.*;
import com.sun.spot.io.j2me.radiogram.*;
import com.sun.spot.peripheral.ota.OTACommandServer;
import com.sun.spot.util.IEEEAddress;

import java.io.*;
import javax.microedition.io.*;
/**
 *
 * @author adamcornforth
 */
public class ReceivingRadio implements IReceivingRadio
{
    // Broadcast port on which we listen for tilt data
    private static final int HOST_PORT = 96;
    
    //Connection and datagram variables
    private RadiogramConnection radioConn;
    private Datagram datagram;
   
    //thread for communicating with SPOT
    private Thread pollingThread = null;

    public ReceivingRadio() throws IOException
    {
        radioConn = (RadiogramConnection) Connector.open("radiogram://:" + HOST_PORT);
        datagram = radioConn.newDatagram(radioConn.getMaximumLength());   
        System.out.println("Receiving Radio created");
    }

    public int receiveLight() throws IOException
    {
        radioConn.receive(datagram); 
        String addr = datagram.getAddress();  
        return datagram.readInt(); 
    }

    public double receiveHeat() throws IOException
    {
        radioConn.receive(datagram); 
        String addr = datagram.getAddress();  
        return datagram.readDouble(); 
    }
}
