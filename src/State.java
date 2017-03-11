/**
 * Created by lema on 02-03-2017.
 */

public class State {

    private Integer[][] board;

    private Action action;

    /**
     * Player who will perform an action from this state
     */
    private int player;

    public State(Integer[][] board, Action action) {
        this.board = board;
        this.action = action;
    }

    public Integer[][] getBoard() {
        return board;
    }

    public void setBoard(Integer[][] board) {
        this.board = board;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }
}
