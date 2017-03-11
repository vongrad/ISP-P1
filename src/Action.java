/**
 * Created by lema on 03-03-2017.
 */

public class Action {

    /**
     * Column to put the coin to
     */
    private int move;

    /**
     * Player who performed this action
     */
    private int player;

    public Action(int column, int player) {
        this.move = column;
        this.player = player;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int column) {
        this.move = column;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }
}
