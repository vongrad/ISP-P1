import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameLogic implements IGameLogic {
    private int x = 0;
    private int y = 0;
    private int playerID;
    private int lastPlayedID;
    private boolean finished = false;
    private int[][] gameBoard;

    public GameLogic() {
        //TODO Write your implementation for this method
    }

    // Gives the board size and your id
    public void initializeGame(int x, int y, int playerID) {
        this.x = x;
        this.y = y;
        // indicates ur id
        this.playerID = playerID;
        // fill board with 0os
        gameBoard = new int[x][y];
        for (int i=0;i<x;i++)
            for (int j=0;j<y;j++)
                gameBoard[i][j] = 0;
        //TODO Write your implementation for this method

    }



    // checks whether game is finished, called every click basically
    public Winner gameFinished() {
        //TODO Write your implementation for this method
        return Winner.NOT_FINISHED;
    }

    //Notifies that a token/coin is put in the specified column of      the game board.

    public void insertCoin(int column, int playerID) {
        //TODO Write your implementation for this method
        //Implementation is done in a way where
        this.lastPlayedID = playerID;
        int index = y-1;
        while (index >= 0){
            if (gameBoard[column][index] == 0)
            {
                gameBoard[column][index] = playerID;
                break;
            }
            index--;
        }
    }

    // puts the token
    public int decideNextMove() {
        //TODO Write your implementation for this method
        return 1;
        //return 0;
    }

    private Action Minimax(State state, int depth)
    {


        if (TermnialState(state)){
            return Utility(state);
        }
        if (depth == 0) {
            return Evaluate(state);
        }

        List<Action> actions = Actions(state);
        if (state.get_turn()== Turn.MAX) {

            int max = Integer.MIN_VALUE;
            for (Action action: actions) {
                State newState = Result(action,state);
                max = Math.max(max, Minimax(newState,depth--).get_evaluatedValue());
            }


        }
        else
        {
            int min = Integer.MAX_VALUE;

            for (Action action: actions) {
                State newState = Result(action,state);
                min = Math.max(min, Minimax(newState, depth--).get_evaluatedValue());
            }
        }

        return new Action(0,0);
    }

    private Action Evaluate(State state) {
        int[][] array = state.get_board();

        int columns = 10,rows = 6;

        // iterate through dioganals
        for (int k = 0; k < rows + columns - 1 ; k++)
        {
            for (int j = 0; j <= k ; j++)
            {
                int i = k - j;
                //get one sumbol
                //if (i < rows && j < columns) array[i,j]+ " ";
            }
            // row of dioganal is finished. Here I can return an array you apply a method immediately

        }
        // iterate through other one
        for (int k = rows -1  ; k > -columns; k--)
        {
            for (int j = 0; j <= (rows - 1) - k; j++)
            {
                int i = k + j;
                //same as before
                //if (i < rows && j < columns && i > -1 ) array[i,j];
            }
        }
        return new Action(0,0);
    }

    private Action Utility(State state) {

        return new Action(0,0);
    }

    /**
     * Return new state by applying the action to the state. Change the board and change turn ( Max and Min )
     * @param action
     * @param state
     * @return new state
     */
    private State Result(Action action, State state) {

        return null;
    }

    // Adam Won or Board Full
    private boolean TermnialState(State state) {

        return true;
    }

    /**
     * return the list of avalable action at current state of game
     * @param state
     * @return List<Integer>
     */
    private List<Action> Actions(State state)
    {
        List<Action> actions = new ArrayList<Action>();
        int[] row = state.get_board()[0];
        for (int i=0;i<row.length;i++) {
            if(row[i]==0)actions.add(new Action(i,0));
        }
        return actions;
    }

    private int Evaluation(State state)
    {
        return 0;
    }





}


