/**
 * encapsulates boolean results from condition processing.
 * @author Dominic Lindsay
 */
package org.conditionProcessor.conditionContainers;

import org.conditionprocessor.operators.Operator;

public class Result {
    private boolean result;
    public Result(boolean result)
    {
        this.result = result;
    }
    /**
     * calls op operate method on op, passing the value of this result, and 
     * passed boolean value r, sets the returned value of op.operate as the
     * instance variable 'result'
     * @param op Operation to be used to define type of operation to be performed
     * @param r boolean value to evaluate
     * @return boolean result of the revised result;
     */
    public boolean reEvaluateResult(final Operator op, final boolean r) {
        return this.result = (op.operate(this.result, r));
    }
    /**
     * returns result boolean.
     * @return boolean
     */
    public boolean getResult() {
        return this.result;
    }
    /**
     * sets the result boolean
     * @param result boolean
     */
    public void setResult(boolean result) {
        this.result = result;
    }
}
