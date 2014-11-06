/*
 * Interface defining functionality of Receiving Radio
 * Dominic Lindsay + Adam Cornforth
 */
package org.sunspotworld.spotRadios;

import java.io.IOException;
import org.sunspotword.data.TowerSpot;
public interface IReceivingRadio 
{
    /**
     * Gets the latest discoverRequest requests and returns the 
     * address of the spot that has sent the datagram
     * @return String address
     */
    public int[] receiveDiscoverResponse() throws IOException;
    /**
     * receives a ping packet to roaming spot and 
     * returns address of tower that sent it 
     */
    public TowerSpot receivePing();
    /**
     * gets the address of the last received ping packet
     * @return  String address
     */
    public String getLastPingAddress();
}
