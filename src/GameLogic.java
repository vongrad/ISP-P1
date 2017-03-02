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

    private int Minimax(State state, int depth)
    {


        if (TermnialState(state)){
            return Utility(state);
        }
        if (depth == 0) {
            return Evaluate(state);
        }

        List<Integer> actions = Actions(state);
        if (state.get_turn()== Turn.MAX) {

            int max = Integer.MIN_VALUE;
            for (Integer action: actions) {
                State newState = Result(action,state);
                max = Math.max(max, Minimax(newState,depth--));
            }


        }
        else
        {
            int min = Integer.MAX_VALUE;

            for (Integer action: actions) {
                State newState = Result(action,state);
                min = Math.max(min, Minimax(newState, depth--));
            }
        }




        return 0;
    }

    private int Evaluate(State state) {
        return 0;
    }

    private int Utility(State state) {
        return 0;
    }

    /**
     * Return new state by applying the action to the state. Change the board and change turn ( Max and Min )
     * @param action
     * @param state
     * @return new state
     */
    private State Result(Integer action, State state) {

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
    private List<Integer> Actions(State state)
    {
        List<Integer> actions = new ArrayList<Integer>();
        int[] row = state.get_board()[0];
        for (int i=0;i<row.length;i++) {
            if(row[i]==0)actions.add(i);
        }
        return actions;
    }

    private int Evaluation(State state)
    {
        return 0;
    }





}


