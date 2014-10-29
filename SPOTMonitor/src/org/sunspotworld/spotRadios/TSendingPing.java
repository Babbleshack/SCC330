/**
 * Pings sorround tower 'teritory'
 * A ping is simply an empty packet.
 * Dominic Lindsay
 */
public class TSendingPing implements Runnable
{
	private ISendingRadio radio;
	private static final long SECOND = 1000;
	private static final long SAMPLE_RATE = SECOND/2;
	public TSendingPing()
	{
		radio = RadiosFactory.createSendingRadio();
	}
	public void Run()
	{
		while(true)
		{
			radio.ping();
			Utils.sleep(SAMPLE_RATE); //give Schedular a chance to deschedule thread	
		}
	}
}