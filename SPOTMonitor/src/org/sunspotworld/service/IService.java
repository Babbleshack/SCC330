package org.sunspotworld.service;

import operators.IOperator;
import org.sunspotworld.spotMonitors.IMonitor;

/**
 * Service interface.
 * @author babbleshack
 */
public interface IService {
    // Identifiers for services
    public static final int THERMO_THRESH   = 110;
    public static final int THERMO_SAMPLE   = 115;
    public static final int LIGHT_THRESH    = 120;
    public static final int LIGHT_SAMPLE    = 125;
    public static final int ACCEL_THRESH    = 130;
    public static final int ACCEL_SAMPLE    = 135;
    public static final int MOTION_SAMPLE   = 140;
    public static final int PING            = 150;
    public static final int TOWER_RECIEVER = 160;
    public static final int BASE_TOWER     = 170;
    public static final int BAROMETER_THRESH = 200;
    public static final int BAROMETER_SAMPLE = 205;
    void startService();
    void stopService();
    boolean isScheduled();
    int getServiceId();
    IMonitor getMonitor();
    void setData(int data);
    void setDirection(int direction);
    IOperator getDirecton();
}
