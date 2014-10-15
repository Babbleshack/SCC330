/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

import java.io.IOException;
/**
 *
 * @author adamcornforth
 */
public interface IReceivingRadio 
{
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
