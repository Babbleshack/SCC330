/**
 * condition producer
 * @author Dominic Lindsay
 */
package org.conditionManager.producers;

import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import org.conditionManager.database.QueryManager;


public class ConditionProducer implements Runnable {
    private BlockingQueue _sharedQueue;
    private final QueryManager _qm;
    public ConditionProducer(BlockingQueue sharedQueue, 
            final QueryManager quearyManager) {
        this._sharedQueue = sharedQueue;
        this._qm = quearyManager;
    }
    /**
     * builds a set of actuator ids, itterates set add condition for each
     * id to shared queue
     */
    public void run() {
        HashSet<Integer> actuatorIds = new HashSet<Integer>();
        while(true) {
            actuatorIds.addAll(_qm.getActuatorIds());
            for(Integer i : actuatorIds){
                try {
                    _sharedQueue.put(_qm.getFirstCondition(i.intValue()));
                } catch (InterruptedException ex) {
                    System.err.println(ConditionProducer.class.getName() + "Failed");
                }
            }
        }
    }
    
}
