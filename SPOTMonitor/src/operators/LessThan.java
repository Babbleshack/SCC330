/*
 * Implements IOperator. Encalpsulate 'if x is less than y' expresions.
 */
package operators;

/**
 *
 * @author babbleshack
 */
public class LessThan implements IOperator {
    /**
     * returns true if x is less than y
     * @param x 
     * @param y
     * @return boolean
     */
    public boolean operate(double x, double y) {
        return x < y;
    }
}
