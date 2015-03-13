/*
 * defines a timer
 */
package org.sunspotworld.timers;
public interface ITimer extends Runnable {
    public void setTimer(long time);
    public long getTimer();
}
