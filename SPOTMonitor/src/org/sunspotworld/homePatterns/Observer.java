/**
 * Observer Interface
 * Dominic Lindsay
 */
package org.sunspotworld.homePatterns;
public interface Observer 
{
	public void update(Observable o, Object arg);
	public void update(Observable o);
}