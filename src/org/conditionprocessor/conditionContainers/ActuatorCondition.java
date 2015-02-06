/**
 *
 * @author Dominic Lindsay
 */
package org.conditionProcessor.conditionContainers;

import org.conditionprocessor.operators.Operator;


public class ActuatorCondition {
    private final int condID;
    private final int actID;
    private final int actJob1;
    private final int actJob2;
    private final Operator op;
    private final Operator next_op;
    /* brace yourself, heavy constructor incoming! */
    public ActuatorCondition(
            final int conditionID,
            final int actuatorID,
            final int actuatorJob1,
            final int actuatorJob2,
            final Operator operation,
            final Operator next_operation
        ) {
            this.condID = conditionID;
            this.actID = actuatorID;
            this.actJob1   = actuatorJob1;
            this.actJob2   = actuatorJob2;
            this.op = operation;
            this.next_op = next_operation;
    }

    /**
     * @return the condition ID
     */
    public int getCondID() {
        return condID;
    }

    /**
     * @return the actuator ID
     */
    public int getActID() {
        return actID;
    }

    /**
     * @return the actuator Job 1
     */
    public int getActJob1() {
        return actJob1;
    }

    /**
     * @return the actuator Job 2
     */
    public int getActJob2() {
        return actJob2;
    }

    /**
     * @return the operation for this condition
     */
    public Operator getOp() {
        return op;
    }

    /**
     * @return the next_operation
     */
    public Operator getNext_op() {
        return next_op;
    }
   
}
