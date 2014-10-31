/*
 * Interface defining functionality of Receiving Radio
 * Dominic Lindsay + Adam Cornforth
 */
package org.sunspotworld.spotRadios;

import java.io.IOException;
public interface IReceivingRadio 
{
	/**
	 * Gets the latest discoverRequest requests and returns the 
	 * address of the spot that has sent the datagram
	 * @return String address
	 */
	public int[] receiveDiscoverResponse() throws IOException;

	 /**
     * Receive packets from responding SPOTS that are addressed to 
     * this radio's spot address, returns the current power level 
     * of received packet.
     * @return  int signal strength
     */
    public int receivePingReply();
    /**
     * gets the address of the last received ping packet
     * @return  String address
     */
    public String getLastPingAddress();
    
    /**
     * receives a ping packet to roaming spot and 
     * returns address of tower that sent it 
     */
    public String receivePing();
}
