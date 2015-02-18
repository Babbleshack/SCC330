/**
 * encapsulate null operation, always returns true
 */
package operators;

/**
 *
 * @author babbleshack
 */
public class NullOperator implements IOperator {
    /**
     * always returns true
     * @param x
     * @param y
     * @return 
     */
    public boolean operate(double x, double y) {
        return true;
    }
}
