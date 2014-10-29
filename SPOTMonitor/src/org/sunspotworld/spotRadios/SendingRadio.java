/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.spotRadios;

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
    private RadiogramConnection radioConn = null;
    private Datagram  datagram = null;
    
    private String spotAddress = System.getProperty("IEEE_ADDRESS");

    public SendingRadio(SunspotPort port) throws IOException
    {
        radioConn = (RadiogramConnection) Connector.open("radiogram://broadcast:" + port.getPort());
        System.out.println("Sending Radio created for " + spotAddress + " on port " + port.getPort()); 
        datagram = radioConn.newDatagram(50); 
    }

    public void sendLight(int value)
    {
        try {
            datagram.reset();
            datagram.writeInt(value);
            radioConn.send(datagram);
            System.out.println("Light value " + value + " sent...");
        } catch (Exception e) {
            System.err.println("IOException occured while sending light: " + e);
        }
    }

    public void sendHeat(double value)
    {
        try {
            datagram.reset();
            datagram.writeDouble(value);
            radioConn.send(datagram);
            System.out.println("Heat value " + value + " sent...");
        } catch (IOException e) {
            System.err.println("IOException occured while sending thermo: " + e);
        }
    }

    public void sendAccel(double value)
    {
        try {
            datagram.reset();
            datagram.writeDouble(value);
            radioConn.send(datagram);
            System.out.println("Accel value " + value + " sent...");
        } catch (IOException e) {
            System.err.println("IOException occured while sending accel: " + e);
        }
    }
    public void sendMotionTime(long value)
    {
        try {
            datagram.reset();
            datagram.writeLong(value);
            radioConn.send(datagram);
            System.out.println("Motion time value " + value + " sent...");
        } catch (IOException e) {
            System.err.println("IOException occured while sending Motion Time: " + e);
        
        }
    }
    /**
     * Sends empty packet to be used as a 'pinging' service
     */
    public void ping()
    {
        try {
            datagram.reset();
            radioConn.send(datagram);
        } catch (IOException e) {
            System.err.println("IOException occured while sending PING" + e);
        }
    }
    /**
     * sends SPOT address to basestation
     */
    public sendSPOTAddress(String address)
    {
        try
        {
            datagram.reset();
            datagram.writeUTF(address);
            radioConn.send(datagram);
        } catch (IOException e) {
            System.err.println("Error sending SPOT address: " + e);
        }

    }


    public void discoverMe() {
        try {
            datagram.reset();
            datagram.writeUTF(spotAddress);
            radioConn.send(datagram);
            System.out.println("DiscoverME request sent..."); 
        } catch (IOException e) {
            System.err.println("IOException occured while sending discovery request: " + e);
        } 
    }
}
