import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPI_Exception;


public class main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, YAPI_Exception {
		YAPI.RegisterHub("127.0.0.1");

		ActuatorController actuatorController = new ActuatorController();

		TActuatorFinder actuatorFinder = new TActuatorFinder(actuatorController);
		actuatorFinder.start();

		TActuatorJobFinder actuatorJobFinder = new TActuatorJobFinder(actuatorController);
		actuatorJobFinder.start();

		TActuatorStatusManager actuatorStatusManager = new TActuatorStatusManager(actuatorController);
		actuatorStatusManager.start();
	}
}
