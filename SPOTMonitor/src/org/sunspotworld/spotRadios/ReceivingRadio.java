/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.spotRadios;

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
    private static final int HOST_PORT = 96;
    //sample period in milliseconds
    private RadiogramConnection radioConn = null;
    private Datagram  datagram = null;
    
    private String spotAddress = System.getProperty("IEEE_ADDRESS");

    public ReceivingRadio(SunspotPort port) throws IOException
    {
        radioConn = (RadiogramConnection) Connector.open("radiogram://:" + port.getPort());
        datagram = radioConn.newDatagram(radioConn.getMaximumLength());   
        System.out.println("Receiving Radio created for " + spotAddress + " on port " + port.getPort());
    }

    /**
     * Receives the discovey response and returns an array of integers, 
     * containing the port numbers and sensor thresholds
     */
    public int[] receiveDiscoverResponse() throws IOException
    {
        String spot_address = "";
        while(!spot_address.equals(spotAddress)) {
            radioConn.receive(datagram); 
            spot_address = datagram.readUTF();
            System.out.println("Sun SPOT address " + spotAddress + " has received a discover response with the recipient of " + spot_address);
        }

        int datagramLength = datagram.readInt(); 
        int[] portsThresholds = new int[datagramLength];
        
        for (int i = 0;i < datagram.readInt(); i += 2) {
            portsThresholds[i] = datagram.readInt();    
            portsThresholds[i+1] = datagram.readInt();  
        }

        return portsThresholds;
    }
}
