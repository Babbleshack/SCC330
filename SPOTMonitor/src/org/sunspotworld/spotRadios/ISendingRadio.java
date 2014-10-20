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
public interface ISendingRadio 
{
	/**
	 * Builds a datagram that sends light intensity data to the basestation
	 * @param  value the light value to send to the basestation
	 */
	public void sendLight(int value);

	/**
	 * Builds a datagram that sends heat data to the basestation
	 * @param  value the light value to send to the basestation
	 */
	public void sendHeat(double value);
}
