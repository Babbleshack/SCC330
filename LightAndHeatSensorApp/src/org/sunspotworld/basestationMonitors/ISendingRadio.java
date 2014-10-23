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
public interface ISendingRadio 
{
	/**
	 * Method that broadcasts a spot_address and a list of
	 * ports so that the given spot_address sun SPOT knows 
	 * what its responsibilities are
	 */
	public void sendDiscoverReponse(String spot_address, int[] ports, int[] thresholds);
}
