/**
 * Created by lema on 03-03-2017.
 */
public class Action {

    private int _coinAction;

    private int _evaluatedValue;

    public Action(int coinAction, int evaluatedValue) {
        set_coinAction(coinAction);
        set_evaluatedValue(evaluatedValue);
    }

    public int get_coinAction() {
        return _coinAction;
    }

    public void set_coinAction(int _coinAction) {
        this._coinAction = _coinAction;
    }


    public int get_evaluatedValue() {
        return _evaluatedValue;
    }

    public void set_evaluatedValue(int _evaluatedValue) {
        this._evaluatedValue = _evaluatedValue;
    }
}
