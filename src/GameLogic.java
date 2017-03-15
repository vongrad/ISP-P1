import com.sun.xml.internal.bind.v2.model.core.WildcardMode;
import com.sun.xml.internal.fastinfoset.util.CharArray;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GameLogic implements IGameLogic {

    private TerminalTester terminalTester;

    private int x = 0;
    private int y = 0;

    private int playerID;
    private int oponentID;

    private Integer[][] gameBoard;

    private int lastPlayedColumn = 0;
    private int lastPlayerId;

    public GameLogic() {

    }

    // Gives the board size and your id
    public void initializeGame(int x, int y, int playerID) {

        terminalTester = new TerminalTester(4);
        // indicate board size
        this.x = x;
        this.y = y;

        // indicates ur id
        this.playerID = playerID;
        this.oponentID = playerID == 1 ? 2 : 1;

        // initialize empty board
        gameBoard = new Integer[x][y];
    }



    // checks whether game is finished, called every click basically
    public Winner gameFinished() {
        if(terminalTester.isWin(gameBoard, lastPlayedColumn)) {
            return getPlayer(lastPlayerId);
        }
        else if(terminalTester.isTie(gameBoard)) {
            return Winner.TIE;
        }
        return Winner.NOT_FINISHED;
    }

    //Notifies that a token/coin is put in the specified column of      the game board.

    /**
     * Insert coin at specific location
     * @param x The column where the coin is inserted.
     * @param playerID The ID of the current player.
     */
    public void insertCoin(int x, int playerID) {

        this.lastPlayedColumn = x;
        this.lastPlayerId = playerID;
        int index = 0;
        while (index < y){
            if (gameBoard[x][index] == null) {
                gameBoard[x][index] = playerID;
                break;
            }
            index++;
        }
    }

    public int decideNextMove() {
        State state = new State(gameBoard, new Action(lastPlayedColumn, playerID));
        state.setPlayer(playerID);
        Action action = miniMaxDecision(state, 9);
        return action.getMove();

    }

    private Action miniMaxDecision(State state, int depth) {

        List<Action> actions = Actions(state);
        double max = -2;
        Action nextAction = null;

        for (Action action :actions) {
            double value = minValue(Result(action, state), depth - 1,-2,2, depth);

            if(value > max) {
                max = value;
                nextAction = action;
            }
        }
        return nextAction;
    }

    private double maxValue(State state, int depth, double alfa, double beta, int initialDepth) {

        // Terminal state check
        if(stateWin(state)) {
            double value = Utility(state, false);
            // Moves that occur at higher depths are preferred as they will happen sooner in the future
            if (value>0) return value - ((initialDepth - depth) * 0.0000001);
            else return value + ((initialDepth - depth) * 0.0000001);
        }
        else if(stateTie(state)) {
            return Utility(state, true);
        }

        if(depth == 0) {
            return Evaluate(state);
        }

        List<Action> actions = Actions(state);
        double utility = -2;
        for (Action action :actions) {
            utility = Math.max(utility, minValue(Result(action, state), depth - 1,alfa,beta, initialDepth));
            if (utility>= beta)
                return utility;
            alfa = Math.max(alfa,utility);
        }
        return utility;
    }

    private double minValue(State state, int depth, double alfa, double beta, int initialDepth) {
        // Terminal state check
        if(stateWin(state)) {
            double value = Utility(state, false);
            // Moves that occur at higher depths are preferred as they will happen sooner in the future
            if (value>0) return value - ((initialDepth - depth) * 0.0000001);
            else return value + ((initialDepth - depth) * 0.0000001);
        }
        else if(stateTie(state)) {
            return Utility(state, true);
        }
        // Cut-off test
        if (depth == 0) {
            return Evaluate(state);
        }

        List<Action> actions = Actions(state);

        double utility = 2;

        for (Action action :actions) {
            utility = Math.min(utility, maxValue(Result(action, state), depth - 1,alfa,beta, initialDepth));
            if (utility<= alfa)
                return utility;
            beta = Math.min(beta,utility);
        }
        return utility;
    }

    /**
     * This method goes through all rows, columns, dioganals, and evaluates the chances to win
     * @param state
     * @return the Action which contains evaluated value INTEGER and move itself
     */
    private double Evaluate(State state) {
        Evaluator evaluator = new Evaluator(state, playerID);
        return evaluator.Evaluate();
    }

    private double Utility(State state, boolean tie) {

        if(tie) {
            return 0;
        }

        if(state.getAction().getPlayer() == playerID) {
            return 1.0;
        }
        else {
            return -1.0;
        }
    }

    /**
     * Return new state by applying the action to the state. Change the board and change turn ( Max and Min )
     * @param action
     * @param state
     * @return new state
     */
    private State Result(Action action, State state) {

        int index = 0;
        Integer [][] board = CopyBoard(state.getBoard());

        while (index < y){
            if (board[action.getMove()][index] == null) {
                board[action.getMove()][index] = action.getPlayer();
                break;
            }
            index++;
        }

        State newState = new State(board, action);

        if(playerID == action.getPlayer()) {
            newState.setPlayer(oponentID);
        }
        else{
            newState.setPlayer(playerID);
        }

        return newState;
    }

    private Integer[][] CopyBoard(Integer[][] board) {
        Integer [][] myBoard = new Integer[board.length][];
        for(int i = 0; i < board.length; i++)
            myBoard[i] = board[i].clone();
        return myBoard;
    }

    private boolean stateWin(State state) {
        return terminalTester.isWin(state.getBoard(), state.getAction().getMove());
    }

    private boolean stateTie(State state) {
        return terminalTester.isTie(state.getBoard());
    }

    /**
     * return the list of available action at current state of game
     * @param state
     * @return List<Integer>
     */

    private List<Action> Actions(State state)
    {
        List<Action> actions = new ArrayList<>();
        Integer[][] board = state.getBoard();

        int x = board.length;
        int maxY = board[0].length - 1;

        for (int i=0; i < x; i++) {

            if(board[i][maxY] == null){
                actions.add(new Action(i,  state.getPlayer()));
            }
        }
        return actions;
    }

    private int Evaluation(State state)
    {
        return 0;
    }

    private Winner getPlayer(int playerID) {
        if(playerID - 1 == Winner.PLAYER1.ordinal()) {
            return Winner.PLAYER1;
        }
        else{
            return Winner.PLAYER2;
        }
    }





}


