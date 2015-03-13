/**
 * Interface defining sending SPOT radio
 * Dominic Lindsay + Adam Cornfourth
 */
package org.sunspotworld.spotRadios;
public interface ISendingRadio 
{
    /**
     * Builds a datagram that sends light intensity data to the basestation
     * @param  value the light value to send to the basestation
     */
    public void sendLight(double value);

    /**
     * Builds a datagram that sends heat data to the basestation
     * @param  value the light value to send to the basestation
     */
    public void sendHeat(double value);
        
    /**
    * Builds a datagram that sends accel data to the basestation
    * @param  value the accel value to send to the basestation
    */
   public void sendAccel(double value);

   /**
    * Builds a datagram that send last Motion time event to the basestation
    * @param time of last motion, long
    */
   public void sendMotionTime(long value);
   /**
    * Builds a datagram containing int representing water level fill
    * percentage, passes this data to base station via radio.
    * @param waterLevelPercentage int
    */
   public void sendWater(int waterLevelPercentage);

   /**
    * Builds a datagram that sends a switch event to the basestation
    * @param id of button press
    */
   public void sendSwitch(String value);

   /**
    * Method that sends a "discover me" message to the base station
    */
   public void discoverMe();
   
   public void sendBarometerReadion(double angle);

   /**
    * Sends empty packet to be used as a 'pinging' service
    */
    public void ping();

    /**
     * sends SPOT address.
     */
    public void sendSPOTAddress(String address);

    /**
     * sends Tower address.
     */
    public void sendTowerAddress(String address);
    
    /**
     * Sends battery information
     */
    public void sendBatteryPower(int powerLevel);
    /**
     * sends impact sensor flag
     */
    public void sendImpactFlag(int val) {
}
