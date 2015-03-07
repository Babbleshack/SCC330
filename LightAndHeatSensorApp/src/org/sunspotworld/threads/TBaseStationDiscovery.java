/**
 * Basestation discovery thread, polls db for list of active spots.
 * manages shared map of shared actuators.
*/
package org.sunspotworld.threads;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import org.sunspotworld.database.IQueryManager;
import com.sun.spot.peripheral.radio.BasestationManager;
import com.sun.spot.peripheral.radio.BasestationManager.DiscoverResult;

public class TBaseStationDiscovery implements Runnable {
	private final IQueryManager _qm;
	private String ADDRESS;
	private final ConcurrentHashMap<String, String> _addressMap;
	public TBaseStationDiscovery(IQueryManager qm, ConcurrentHashMap addressMap) {
		_qm = qm;
		//get BS address
		try {
			BasestationManager bsm = new BasestationManager();
			DiscoverResult[] dr = bsm.discover();
			ADDRESS = dr[0].getAddress().asDottedHex();
			System.out.println("Address is: " + ADDRESS);
		}catch(IOException e) {
			System.err.println("ERROR GETTING SYSTEM ADDRESS");
			e.printStackTrace();
		} 
		_addressMap = addressMap;
	}
	/**
	 * checks if bs is in db.
	 * adds all spot for bs to map
	 * call sanitization method 'removeStaleSpots'
	 */
	public void run() {
		while(true) {
			//check if basestation exists
			if(!_qm.checkIfBaseStationExists(ADDRESS))
				_qm.addBasestation((String)ADDRESS); 
			_updateAddressMap();
			_removeStaleSpots();
		}
	}
	/*
	 * remove spots which do not belong to this bs, from map.
	 */
	private void _removeStaleSpots() {
		for(String spotAddress : _addressMap.keySet()) {
			if(_qm.doesSpotBelongToBS(spotAddress,ADDRESS))
				continue;
			else
				_addressMap.remove(spotAddress);		
		}			
	}
	/*
	 * gets a list of spot addresses from DB and adds them 
	 * to shared address map
	 */
	private void _updateAddressMap(){
		for(String address : _qm.getSpots(ADDRESS))
		      _addressMap.put(address, address);
	}
}
