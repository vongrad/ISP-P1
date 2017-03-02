/**
 * Created by lema on 02-03-2017.
 */
public class State {

    private int[][] _board;


    private Turn _turn;

    public Turn get_turn() {
        return _turn;
    }

    public void set_turn(Turn _turn) {
        this._turn = _turn;
    }

    public int[][] get_board() {
        return _board;
    }

    public void set_board(int[][] _board) {
        this._board = _board;
    }


}
