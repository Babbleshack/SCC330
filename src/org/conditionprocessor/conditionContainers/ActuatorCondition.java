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
   
}
