/**
 * Observer Interface
 * Dominic Lindsay
 */
package org.sunspotworld.homePatterns;
public interface TaskObserver
{
	public void update(TaskObservable o, Object arg);
	public void update(TaskObservable o);
}