/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.basestationRadios;

import org.sunspotworld.basestationMonitors.ISendingRadio;
import com.sun.spot.resources.Resources;
import java.io.IOException;

import org.sunspotworld.Collections.ArrayList;
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

    public void sendDiscoverReponse(String spot_address, ArrayList portsThresholds) 
    {
        try {
            datagram.reset();
            // write spot_address first
            datagram.writeUTF(spot_address);
            datagram.writeInt(portsThresholds.size());

            // now write ports + thresh
            for (int i = 0; i < portsThresholds.size(); i += 2) {
                System.out.println("Port " + (Integer)portsThresholds.get(i) + " sent thresh " + (Integer)portsThresholds.get(i+1));
                datagram.writeInt((Integer)portsThresholds.get(i));
                datagram.writeInt((Integer)portsThresholds.get(i+1));
            }
            
            radioConn.send(datagram);
            System.out.println("Ports and thresholds of length (" + portsThresholds.size() + ") sent to  " + spot_address + "");
        } catch (Exception e) {
            System.err.println("IOException occured while sending ports: " + e);
        }
    }
}
