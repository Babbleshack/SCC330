/*
 * Sunspot Operators used for 
 */
package operators;

/**
 *
 * @author babbleshack
 */
public interface IOperator {
    public final static int ABOVE = 1; 
    public final static int BELOW = 0;
    public final static int NULL  = -1;
    boolean operate(double x, double y);
}
