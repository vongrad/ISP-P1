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
        //int move = GetLeastUsedColumn();
        return 1;
        //return 0;
    }

    private int minimax(int depth){
        List<Integer> nextMoves = generateMoves();

        return 0;
    }

    private List<Integer> generateMoves() {
        List<Integer> nextMoves = new ArrayList<Integer>();// allocate List

        // If gameover, i.e., no next move
        if (finished) {
            return nextMoves;   // return empty list
        }

        // Search for empty cells and add to the List
        for (int row = 0; row < x; ++row) {
                if (gameBoard[row][0]==0)
                    nextMoves.add(row);

        }
        return nextMoves;
    }

/*
    private int GetLeastUsedColumn() {
        int[] arrayCount = new int[x];
        Arrays.fill(arrayCount,0);
        for (int i=0;i<x;i++)
            for (int j=0;j<y;j++)
                if(gameBoard[i][j]!=0) arrayCount[i]+=1;
       return FindSmallest(arrayCount);
    }

    public static int FindSmallest (int [] arr1){//start method

        int index = 0;
        int min = arr1[index];
        for (int i=1; i<arr1.length; i++){

            if (arr1[i] < min ){
                min = arr1[i];
                index = i;
            }


        }
        return index ;

    }
    */
}
