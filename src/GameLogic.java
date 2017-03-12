import com.sun.xml.internal.bind.v2.model.core.WildcardMode;
import com.sun.xml.internal.fastinfoset.util.CharArray;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GameLogic implements IGameLogic {

    private TerminalTester terminalTester;
    private Evaluator evaluator;

    private int x = 0;
    private int y = 0;

    private int playerID;
    private int oponentID;

    private boolean finished = false;
    private Integer[][] gameBoard;

    private int lastPlayedColumn = 0;
    private int lastPlayerId;

    public GameLogic() {
        //TODO Write your implementation for this method
    }

    // Gives the board size and your id
    public void initializeGame(int x, int y, int playerID) {

        terminalTester = new TerminalTester(4);

        //test evaluation
        Integer[][] board = new Integer[][]
               {       {null, null, null, null, null, null, null, null},
                        {1,    1,   1,     1,    1,    null, null, null},
                        {1,    2,   1,     2,    2,    null, null, null},
                        {1,    2,   1,     1,    1,    null, null, null},
                        {2,    1,   2,     2,    1,    2,    1,    null}};



        State state = new State(board, new Action(2, 2));
        state.setPlayer(1);

        evaluator = new Evaluator(state);
        evaluator.Evaluate();

        this.x = x;
        this.y = y;

        // indicates ur id
        this.playerID = playerID;
        this.oponentID = playerID == 1 ? 2 : 1;

        gameBoard = new Integer[x][y];
        //TODO Write your implementation for this method
    }



    // checks whether game is finished, called every click basically
    public Winner gameFinished() {
        if(terminalTester.isTerminal(gameBoard, lastPlayedColumn)) {
            getPlayer(lastPlayerId);
        };

        // TODO: handle tie

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
        state.setPlayer(oponentID);

        Action action = miniMaxDecision(state, 5);

        return action.getMove();

    }

    private Action miniMaxDecision(State state, int depth) {

        List<Action> actions = Actions(state);

        double max = Double.MAX_VALUE;
        Action nextAction = null;

        for (Action action :actions) {
            double value = minValue(Result(action, state), depth--);

            if(value > max) {
                max = value;
                nextAction = action;
            }
        }
        return nextAction;
    }

    private double maxValue(State state, int depth) {

        if(TermnialState(state)) {
            return Utility(state);
        }

        if(depth == 0) {
            return Evaluate(state);
        }

        List<Action> actions = Actions(state);

        double utility = Double.MIN_VALUE;

        for (Action action :actions) {
            utility = Math.max(utility, minValue(Result(action, state), depth - 1));
        }

        return utility;
    }

    private double minValue(State state, int depth) {

        if(TermnialState(state)) {
            return Utility(state);
        }

        if (depth == 0) {
            return Evaluate(state);
        }

        List<Action> actions = Actions(state);

        double utility = Integer.MAX_VALUE;

        for (Action action :actions) {
            utility = Math.min(utility, maxValue(Result(action, state), depth - 1));
        }

        return utility;
    }

    /**
     * This method goes through all rows, columns, dioganals, and evaluates the chances to win
     * @param state
     * @return the Action which contains evaluated value INTEGER and move itself
     */
    private double Evaluate(State state) {
        Evaluator evaluator = new Evaluator(state);
        return evaluator.Evaluate();
    }





    private int Utility(State state) {
        if(state.getAction().getPlayer() == playerID) {
            return 1;
        }
        else if(state.getAction().getPlayer() == oponentID){
            return -1;
        }
        else {
            return 0;
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

        //Integer[][] board = state.getBoard().clone();
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

    // Adam Won or Board Full
    private boolean TermnialState(State state) {
        if(terminalTester.isTerminal(state.getBoard(), state.getAction().getMove())) {
            return true;
        };
        return false;
    }

    /**
     * return the list of available action at current state of game
     * @param state
     * @return List<Integer>
     */

    private List<Action> Actions(State state)
    {
        List<Action> actions = new ArrayList<>();
        Integer[] row = state.getBoard()[0];

        for (int i=0;i<row.length;i++) {
            if(row[i]== null){
                actions.add(new Action(i, state.getPlayer()));
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


