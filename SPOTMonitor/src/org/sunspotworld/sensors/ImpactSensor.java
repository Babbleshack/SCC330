//The digital I/O pins (_d0-D3) can be accessed using the the getIOPins
//method as shown below. _d0 represents the variable to which one of the pins
//will be assigned (in this case pin _d0). The noise sensor value will be read
//through _d0.

//Pins _d0-D3 of the Sun SPOT accept digital values 0 or 1 (5v or 0v). In the
//program an InputPinListener is attached to pin _d0 to monitor when the pin
//turns HIGH or LOW. When the _d0 turns HIGH led(0) turns green briefly else
//the led reverts to red
public class ImpactSensor implements ISensor {
    private IIOPin _d0;
    private EDemoBoard _dboard;    
    private static final int TRUE = 1;
    private static final int FALSE = 0;
    public ImpactSensor() {
        _dboard = EDemoBoard.getInstance();
        _d0 = dboard.getIOPins()[EDemoBoad.d0];
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
