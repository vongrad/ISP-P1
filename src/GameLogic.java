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

        this.x = x;
        this.y = y;

        // indicates ur id
        this.playerID = playerID;
        this.oponentID = playerID == Winner.PLAYER1.ordinal() ? Winner.PLAYER2.ordinal() : Winner.PLAYER1.ordinal();

        gameBoard = new Integer[x][y];
        //TODO Write your implementation for this method
    }



    // checks whether game is finished, called every click basically
    public Winner gameFinished() {

        if(terminalTester.isTerminal(gameBoard, lastPlayedColumn)) {
            if(lastPlayerId == Winner.PLAYER1.ordinal()){
                return Winner.PLAYER1;
            }
            return Winner.PLAYER2;
        };

        // TODO: handle tie

        return Winner.NOT_FINISHED;
    }

    //Notifies that a token/coin is put in the specified column of      the game board.

    /**
     * Insert coin at specific location
     * @param column The column where the coin is inserted.
     * @param playerID The ID of the current player.
     */
    public void insertCoin(int column, int playerID) {

        this.lastPlayedColumn = column;
        this.lastPlayerId = playerID;

        int index = 0;

        while (index < x){
            if (gameBoard[column][index] == null) {
                gameBoard[column][index] = playerID;
                break;
            }
            index++;
        }
    }

    public int decideNextMove() {

        State state = new State(gameBoard, new Action(lastPlayedColumn, getPlayer(playerID)));
        state.setPlayer(getPlayer(oponentID));

        Action action = miniMaxDecision(state, 100);

        return action.getMove();
    }

    private Action miniMaxDecision(State state, int depth) {

        List<Action> actions = Actions(state);

        int max = Integer.MIN_VALUE;
        Action nextAction = null;

        for (Action action :actions) {
            int value = minValue(Result(action, state), depth--);

            if(value > max) {
                max = value;
                nextAction = action;
            }
        }
        return nextAction;
    }

    private int maxValue(State state, int depth) {

        if(TermnialState(state)) {
            return Utility(state);
        }

        if(depth == 0) {
            return Evaluate(state);
        }

        List<Action> actions = Actions(state);

        int utility = Integer.MIN_VALUE;

        for (Action action :actions) {
            utility = Math.max(utility, minValue(Result(action, state), depth--));
        }

        return utility;
    }

    private int minValue(State state, int depth) {

        if(TermnialState(state)) {
            return Utility(state);
        }

        if (depth == 0) {
            return Evaluate(state);
        }

        List<Action> actions = Actions(state);

        int utility = Integer.MAX_VALUE;

        for (Action action :actions) {
            utility = Math.min(utility, maxValue(Result(action, state), depth--));
        }

        return utility;
    }

    /**
     * This method goes through all rows, columns, dioganals, and evaluates the chances to win
     * @param state
     * @return the Action which contains evaluated value INTEGER and move itself
     */
    private int Evaluate(State state) {
        Integer[][] array = state.getBoard();
        int columns = x,rows = y;
        List <String> linesForEvaluation = new ArrayList<>();

        String temp = "";
        if (columns > 3)
            for (int k = 0 ; k < rows ; k++) {
                for (int j = 0; j <columns; j++) {
                    temp += array[k][j];
                }
                linesForEvaluation.add(temp);
                temp = "";
            }
        if (rows > 3)
            for (int k = 0 ; k < columns ; k++) {
                for (int j = 0; j <rows; j++) {
                    temp += array[k][j];
                }
                linesForEvaluation.add(temp);
                temp = "";
            }
        //ignore dioganals if one of the dimensions is smaller than 4
        if (columns>3 && rows >3) {
            // iterate through dioganals ( +3 and -3 is because we dont care about first 3 and last 3 dioganals)
            for (int k = 0 + 3; k < (rows + columns - 1) - 3; k++) {
                for (int j = 0; j <= k; j++) {
                    int i = k - j;
                    if (i < rows && j < columns) temp += array[i][j];
                }
                linesForEvaluation.add(temp);
                temp = "";
            }
            // iterate through other one
            for (int k = (rows - 1) - 3; k > (-columns) + 3; k--) {
                for (int j = 0; j <= (rows - 1) - k; j++) {
                    int i = k + j;
                    //same as before
                    if (i < rows && j < columns && i > -1 ) temp += array[i][j];
                }

                linesForEvaluation.add(temp);
                temp = "";
            }
        }
        int evaluatedValue = EvaluateList(linesForEvaluation);

        return 0;
    }

    private int EvaluateList(List<String> linesForEvaluation) {
        HashMap<String, Integer> valuesMax = new HashMap<String, Integer>();
        HashMap<String, Integer> valuesMin = new HashMap<String, Integer>();
        for (String line: linesForEvaluation)
        {
            char[] tempArray = line.toCharArray();
            char previousChar = '0';
            int counter = 0;
            for (int i= 0; i< tempArray.length; i++)
            {
                char currentChar = tempArray[i];
                if (currentChar == previousChar && currentChar != '0' )
                {
                    counter++;
                }
                else if (currentChar != previousChar && previousChar != '0')
                {
                    switch (currentChar) {
                        case '0':
                            if (previousChar == '1'){

                            }
                            else
                            {

                            }
                            break;
                        case '1':
                            if (previousChar == '1')
                            {}
                            else
                            {}
                            break;
                        case '2':
                            if (previousChar == '1')
                            {}
                            else
                            {}
                            break;
                    }

                }
                else if (true){;}

            }
        }

        return 0;
    }


    private int Utility(State state) {
        if(state.getAction().getPlayer().ordinal() == playerID) {
            return 1;
        }
        else if(state.getAction().getPlayer().ordinal() == oponentID){
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

        Integer[][] board = state.getBoard().clone();

        while (index < x){
            if (board[action.getMove()][index] == null) {
                board[action.getMove()][index] = playerID;
                break;
            }
            index++;
        }

        State newState = new State(board, action);

        if(playerID == action.getPlayer().ordinal()) {
            newState.setPlayer(getPlayer(oponentID));
        }
        else{
            newState.setPlayer(getPlayer(playerID));
        }

        return newState;
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
        if(playerID == Winner.PLAYER1.ordinal()) {
            return Winner.PLAYER1;
        }
        else{
            return Winner.PLAYER2;
        }
    }





}


