// - TTowerReceiver (on port 160): blocks until response from roaming on 160... 
                //                  - wait until response falls into threshold
                //                  - wait until RSI falls out of threshold again: then update basestation with zone change
public class TTowerReceiver implements Runnable
{
	private ISendingRadio sRadio;
	private IReceivingRadio rRadio;
	private static final int INNER_THRESHOLD = -7;
	private static final int OUTER_THRESHOLD = -20;
	public TTowerReceiver()
	{
		sRadio = RadiosFactory.createSendingRadio(SunspotPort.TOWER_RECIEVER_PORT);
		rRadio = RadiosFactory.createReceivingRadio(new SunspotPort(SunspotPort.TOWER_RECIEVER_PORT));
	}
	public void Run()
	{
		int powerLevel;
		boolean inRange = false;
		while true()
		{
			powerLevel = rRadio.pingRssiReader(); 
			if(powerLevel > INNER_THRESHOLD && !inRange) //within proximity of tower
			{
				inRange = true;
				System.out.pringln("within territory")
			}
			if(powerLevel < OUTER_THRESHOLD && inRange) //leaving proximity
			{
				//REPORT ADDRESS TO BASESTATION
				inRange = false;
				sRadio.sendSPOTAddress(rRadio.getLastPingAddress());
				System.out.println("leaving territory");
			}
		}
	}
}