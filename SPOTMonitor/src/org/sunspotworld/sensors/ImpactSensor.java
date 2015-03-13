package org.sunspotworld.sensors;

import com.sun.spot.sensorboard.EDemoBoard;
import com.sun.spot.resources.transducers.IIOPin;
import org.sunspotworld.data.SensorData;
public class ImpactSensor implements ISensor {
    private IIOPin _d0;
    private EDemoBoard _dboard;    
    private static final int TRUE = 1;
    private static final int FALSE = 0;
    public ImpactSensor() {
        _dboard = EDemoBoard.getInstance();
        _d0 = _dboard.getIOPins()[EDemoBoard.D0];
        _d0.setAsOutput(false);
    }
    /*
     * get impact sensor status
     * return 1 for true, 0 for false
     */
    public SensorData getData(){
       if(_d0.isHigh())
           return new SensorData(TRUE);
       else 
           return new SensorData(FALSE);
    }
}
