/**
 * And operation command.
 * @author Dominic Lindsay
 */
package org.conditionManager.operators;
public class And implements Operator {
    public And(){};
    @Override
    public boolean operate(boolean r1, boolean r2) {
        return r1 & r2;
    }
}
