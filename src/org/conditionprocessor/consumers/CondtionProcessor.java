/**
 *
 * @author Dominic Lindsay
 */
package org.conditionprocessor.consumers;

import java.util.concurrent.BlockingQueue;
import org.conditionProcessor.conditionContainers.ActuatorCondition;
import org.conditionProcessor.conditionContainers.Result;
import org.conditionprocessor.database.QueryManager;


public class CondtionProcessor extends Thread {
    private final QueryManager qm;
    Result res;
    BlockingQueue sharedQueue;
    public CondtionProcessor(final QueryManager queryManager,
            BlockingQueue sharedBlockingQueue ) {
        this.qm = queryManager;
        this.sharedQueue = sharedBlockingQueue;
    }
    public void Run(){
        ActuatorCondition actCond = null;
        Result res = null;
        while(true){
            try {
                //take item of queue, blocking if its empty.
                actCond = (ActuatorCondition) sharedQueue.take();
            } catch (InterruptedException ex) {
                System.err.println("Exception thrown: " + 
                        (CondtionProcessor.class.getName()));
            } finally { /*didnt get an actuator cond, should never happen*/
                if(actCond == null)
                    continue;
            }
            actCond.getOp().operate(
                        qm.checkActuatorJob(actCond.getActJob1()),
                        qm.checkActuatorJob(actCond.getActJob1())
                    );
        }
    }
    
}
