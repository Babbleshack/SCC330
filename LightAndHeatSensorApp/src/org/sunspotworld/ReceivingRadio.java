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
    //Connection and datagram variables
    private RadiogramConnection radioConn;
    private Datagram datagram;
   
    //thread for communicating with SPOT
    private Thread pollingThread = null;

    private String spotAddress = System.getProperty("IEEE_ADDRESS");

    public ReceivingRadio(SunspotPort port) throws IOException
    {
        radioConn = (RadiogramConnection) Connector.open("radiogram://:" + port.getPort());
        datagram = radioConn.newDatagram(radioConn.getMaximumLength());   
        System.out.println("Receiving Radio created for " + spotAddress + " on port " + port.getPort());
    }

    public String getReceivedAddress() throws IOException
    {
        return datagram.getAddress();  
    }

    public double receiveLight() throws IOException
    {
        radioConn.receive(datagram); 
        return datagram.readDouble(); 
    }

    public double receiveHeat() throws IOException
    {
        radioConn.receive(datagram); 
        return datagram.readDouble(); 
    }
}
