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
}
