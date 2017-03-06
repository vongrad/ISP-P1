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
    private IGameLogic.Winner player;

    public Action(int column, IGameLogic.Winner player) {
        this.move = column;
        this.player = player;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int column) {
        this.move = column;
    }

    public IGameLogic.Winner getPlayer() {
        return player;
    }

    public void setPlayer(IGameLogic.Winner player) {
        this.player = player;
    }
}
