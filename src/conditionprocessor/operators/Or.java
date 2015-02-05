/**
 * 'Or' Command class
 * @author Dominic Lindsay
 */
package conditionprocessor.operators;

public class Or implements Operator {
    public Or(){}
    @Override
    public boolean operate(boolean r1, boolean r2) {
        return r1 | r2;
    }

}
