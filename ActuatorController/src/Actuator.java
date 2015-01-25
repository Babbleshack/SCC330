import com.yoctopuce.YoctoAPI.*;
public class Actuator
{
	//declares a relay variable
	YRelay relay1;

	//creates Actuator class and calls method to setup virtual hub on local computer
	public Actuator()
	{
	}
	
	public Actuator(String name)
	{
		setRelays(name);
	}
	
	//sets up hub API on local computer and returns relay connected to USB port
	public void setRelays(String name)
	{
		System.out.println("changing relay to: " + name);
		
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
	
	public void turnLightON()
	{
	//turns lights connected to relay1 ON by setting its state to 1
		try
		{
			relay1.isOnline();
			relay1.set_state(1);			
		}
		catch(YAPI_Exception e)
		{
			System.out.println("Can't turn light ON");
		}
	}
	
	public void turnLightOFF()
	{
	//turns lights connected to relay1 OFF by setting its state to 0
		try
		{
			relay1.isOnline();
			relay1.set_state(0);
		}
		catch(YAPI_Exception e)
		{
			System.out.println("Can't turn light OFF");
		}
	}
}