/*
 * Implements IOperator. Encalpsulate 'if x is greater than y' expresions.
 */
package operators;

/**
 *
 * @author babbleshack
 */
public class GreaterThan implements IOperator {
    /**
     * returns true if x is greater than y
     * @param x 
     * @param y
     * @return boolean
     */
    public boolean operate(double x, double y) {
        return x > y;
    }
}