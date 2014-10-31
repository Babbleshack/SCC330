/**
 * Encapsulates reception of datagrams.
 * Dominic Lindsay + Adam Cornfourth
 */
package org.sunspotworld.spotRadios;

import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.peripheral.radio.IRadioPolicyManager;
import com.sun.spot.io.j2me.radiostream.*;
import com.sun.spot.io.j2me.radiogram.*;
import com.sun.spot.io.j2me.radiogram.Radiogram;
import com.sun.spot.peripheral.ota.OTACommandServer;
import com.sun.spot.util.IEEEAddress;

import java.io.*;
import javax.microedition.io.*;

public class ReceivingRadio implements IReceivingRadio
{
    private static final int HOST_PORT = 96;
    //sample period in milliseconds
    private RadiogramConnection radioConn = null;
    private Datagram  datagram = null;
    
    private String spotAddress = System.getProperty("IEEE_ADDRESS");
    private String lastPingAddress = null;

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
        
        for (int i = 0;i < datagramLength; i += 2) {
            portsThresholds[i] = datagram.readInt();    
            portsThresholds[i+1] = datagram.readInt();  
        }

        return portsThresholds;
    }

    /**
     * Receive packets from responding SPOTS,
     * returns the current power level of received packet.
     */
    public int receivePingReply()
    {
        int powerLevel = -9999;
        try {
            String tower_address = ""; 

            while(!tower_address.equals(spotAddress)) {
                datagram.reset();
                radioConn.receive(datagram);
                tower_address = datagram.readUTF();
                System.out.println("Ping reply recipient: " + tower_address + ", This tower address: " + spotAddress);
            }
            System.out.println("Tower received ping reply addressed to itself...");

            powerLevel = ((Radiogram)datagram).getRssi();
            lastPingAddress = datagram.getAddress();
        } catch (IOException e) {
            System.err.println("Error reading RSSI: " + e);
        }
        return powerLevel;
    }

    /**
     * gets the address of the last received ping packet
     * @return address String
     */
    public String getLastPingAddress()
    {
        return lastPingAddress;
    }

    /**
     * receives a ping packet to roaming spot and 
     * returns address of tower that sent it 
     */
    public String receivePing()
    {
        try
        {
            datagram.reset();
            radioConn.receive(datagram);
            return this.getReceivedAddress();
        } catch (IOException e) {
            System.err.println("Error while waiting on packet");
        }
        return null;
    }   
}
