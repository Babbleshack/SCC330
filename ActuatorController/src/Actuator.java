import com.yoctopuce.YoctoAPI.*;
public class Actuator
{
	//declares a relay variable
	YRelay relay1;
	Boolean isOn = false;

	//creates Actuator class and calls method to setup virtual hub on local computer
	public Actuator(String name)
	{
		setRelays(name);
	}

	// public boolean getStatus()
	// {
	// 	return isOn;
	// }

	public void setStatus(boolean isOn)
	{
		if(this.isOn != isOn)
		{
			this.isOn = isOn;

			if(isOn)
				turnLightON();
			else
				turnLightOFF();
		}
	}

	//sets up hub API on local computer and returns relay connected to USB port
	private void setRelays(String name)
	{
		System.out.println("Setting relay address to: " + name);

		try
		{
			YAPI.RegisterHub("127.0.0.1");
			//finds & returns relay with specified address if connected to USB port
			relay1 = YRelay.FindRelay(name);
		}
		catch(YAPI_Exception e)
		{
			System.out.println("cannot contact virtual hub");
		}
	}

	private void turnLightON()
	{
	//turns lights connected to relay1 ON by setting its state to 1
		try
		{
			System.out.println("is Online: " + relay1.isOnline());
			relay1.set_state(1);
		}
		catch(YAPI_Exception e)
		{
			System.out.println("Can't turn light ON");
		}
	}

	private void turnLightOFF()
	{
	//turns lights connected to relay1 OFF by setting its state to 0
		try
		{
			System.out.println("is Online: " + relay1.isOnline());
			relay1.set_state(0);
		}
		catch(YAPI_Exception e)
		{
			System.out.println("Can't turn light OFF");
		}
	}
}