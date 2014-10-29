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
public interface IReceivingRadio 
{
	/**
	 * Gets the latest discoverRequest requests and returns the 
	 * address of the spot that has sent the datagram
	 * @return String address
	 */
	public int[] receiveDiscoverResponse() throws IOException;

	 /**
     * Receive packets from responding SPOTS,
     * returns the current power level of received packet.
     */
    public int pingRssiReader();
    /**
     * gets the address of the last received ping packet
     */
    public String getLastPingAddress();
}
