/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.spotMonitors;

/**
 *
 * @author babbleshack
 */
public interface IMonitor 
{
    public String getDataAsString();
    public double getDataAsDouble();
    public int getDataAsInt();
    public long getDataAsLong();
}
