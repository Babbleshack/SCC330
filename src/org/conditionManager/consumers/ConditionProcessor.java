/**
 *
 * @author Dominic Lindsay
 */
package org.conditionManager.consumers;

import java.util.concurrent.BlockingQueue;
import org.conditionManager.conditionContainers.ActuatorCondition;
import org.conditionManager.conditionContainers.Result;
import org.conditionManager.database.QueryManager;


public class ConditionProcessor implements Runnable {
    private final QueryManager _qm;
    private Result _res;
    private BlockingQueue _sharedQueue;
    public ConditionProcessor(BlockingQueue sharedBlockingQueue,
            final QueryManager queryManager ) {
        this._qm = queryManager;
        this._sharedQueue = sharedBlockingQueue;
    }
    public void run(){
        ActuatorCondition actCond = null;
        _res = null;
        while(true/*add a stopping flag*/){
            try {
                //take item of queue, blocking if its empty.
                actCond = (ActuatorCondition) _sharedQueue.take();
            } catch (InterruptedException ex) {
                System.err.println("Exception thrown: " + 
                        (ConditionProcessor.class.getName()));
            } finally { /*didnt get an actuator cond, should never happen*/
                if(actCond == null)
                    continue;
            }
            //create a new result object with the result of the 
            //actuator condition
            _res = new Result(
                    actCond.getOp().operate(
                        _qm.checkActuatorJob(actCond.getActJob1()),
                        _qm.checkActuatorJob(actCond.getActJob1())
                    )
            );
            //go through 'next' conditions.
            while(actCond.getNext_op() != null)
            {
                actCond = _qm.getNextCondition(actCond.getCondID());
                //reaval result with result of new condition 'Operator' 
                //old condition result. i.e. res OR new cond
                _res.setResult(actCond.getNext_op().operate(
                        _res.getResult(), actCond.getOp().operate(
                        _qm.checkActuatorJob(actCond.getActJob1()),
                        _qm.checkActuatorJob(actCond.getActJob1())
                    )));
            }
            //eval last cond ----REPEATED CODE SHOOULD BE REFACTORED-----
            actCond = _qm.getNextCondition(actCond.getCondID());
            //reaval result with result of new condition 'Operator' 
            //old condition result. i.e. res OR new cond
            _res.setResult(actCond.getNext_op().operate(
                    _res.getResult(), actCond.getOp().operate(
                    _qm.checkActuatorJob(actCond.getActJob1()),
                    _qm.checkActuatorJob(actCond.getActJob1())
                )));
            //take action on sensors
            if(_res.getResult())
                System.out.println("TURN RELAY ON");
            else 
                System.out.println("TURN RELAY OFF");
        }
    }
}
