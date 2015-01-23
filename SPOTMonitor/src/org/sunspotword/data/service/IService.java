package org.sunspotword.data.service;
/**
 * Service interface.
 * @author babbleshack
 */
public interface IService {
    // Identifiers for services
    public static final int THERMO_SAMPLE   = 110;
    public static final int THERMO_THRESH   = 115;
    public static final int LIGHT_SAMPLE    = 120;
    public static final int LIGHT_THRESH    = 125;
    public static final int ACCEL_SAMPLE    = 130;
    public static final int ACCEL_THRESH    = 135;
    public static final int MOTION_SAMPLE   = 140;
    void startService();
    void stopService();
}
