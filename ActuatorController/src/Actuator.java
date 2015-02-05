import com.yoctopuce.YoctoAPI.*;

public class Actuator
{
	YRelay relay1;
	String relayAddress;
	int isOn = -1; // means status unknown yet
	private ActuatorJob job;
	private static final int ON = 1;
	private static final int OFF = 0;

	public Actuator(String name, YRelay relay1)
	{
		this.relay1 = relay1;
		this.relayAddress = name;
	}

	private void reconnect()
	{
		relay1 = YRelay.FindRelay(relayAddress);
	}

	public void setStatus(int i)
	{
//		reconnect();
		System.out.println("status: " + i);
		if(this.isOn != i)
		{
//			this.isOn = i;

			if(i == 1)
			{
				System.out.println("turning on ...");
				turnRelayOn();
			}
			else
			{
				System.out.println("turning off ...");
				turnRelayOff();
			}
		}
	}

	/**
	 * @return the actuatorAddress
	 */
	public String getActuatorAddress()
	{
		return relayAddress;
	}

	/**
	 * @return the job
	 */
	public ActuatorJob getJob()
	{
		return job;
	}

	/**
	 * @param job the job to set
	 */
	public void setJob(ActuatorJob job)
	{
		System.out.println("JOB SET: " + job);
		this.job = job;
	}

	void turnRelayOn() //turns lights connected to relay1 ON by setting its state to 1
	{
			try {
//				System.out.println("RESULT: " + relay1.get_state());
				relay1.set_state(1);
			} catch (YAPI_Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Can't turn light ON");
				System.out.println(relay1);
				e.printStackTrace();
			}
	}

	void turnRelayOff() //turns lights connected to relay1 OFF by setting its state to 0
	{
		//turns lights connected to relay1 OFF by setting its state to 0
		try
		{
//			System.out.println("RESULT: " + relay1.get_state());
			relay1.set_state(0);
		}
		catch(YAPI_Exception e)
		{
			System.out.println("Can't turn light OFF");
			System.out.println(relay1);
			e.printStackTrace();
		}
	}
}