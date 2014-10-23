/**
 * Observer Interface
 * Dominic Lindsay
 */
package org.sunspotworld.Patterns;
public interface Observer 
{
	public void update(Observable o, Object arg);
	public void update(Observable o);
}