/**
* Basestation discovery thread, polls db for list of active spots.
*/
public class BaseStationDiscoveryThread implements Runnable {
	private final IQueryManager _qm;
	public BaseStationDiscovery(IQueryManager qm) {
		_qm = qm;
	}

	public void run() {
		//get id of bs
		//get list of active spots
		//add to shared map
		//remove old spots
	}


}
