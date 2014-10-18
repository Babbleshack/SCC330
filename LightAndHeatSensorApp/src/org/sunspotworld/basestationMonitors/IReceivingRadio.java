/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.basestationMonitors;

import java.io.IOException;
/**
 *
 * @author adamcornforth
 */
public interface IReceivingRadio 
{
	/**
	 * Gets the address of the spot that has sent the datagram
	 * @return String address
	 * @throws IOException   If address cannot be found (maybe the datagram hasn't been received by receiveLight or receiveHeat)
	 */
	public String getReceivedAddress() throws IOException;

	/**
	 * Receives a datagram sent by the SendingRadio containing light data
	 * @return light data
	 */
	public double receiveLight() throws IOException;

	/**
	 * Receives a datagram sent by the SendingRadio containing heat data
	 * @return heat data
	 */
	public double receiveHeat() throws IOException;
}
