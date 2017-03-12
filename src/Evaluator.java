import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by lema on 11-03-2017.
 */
public class Evaluator {

    private Integer maxPlayerId;
    private Integer minPlayerId;
    private Integer[][] board;
    private EvaluationScore evaluationScore;

    public Evaluator(State state){
        maxPlayerId = state.getPlayer();
        minPlayerId = 3 - maxPlayerId;
        board = state.getBoard();
        evaluationScore = new EvaluationScore(maxPlayerId,minPlayerId);
    }

    public double Evaluate()
    {
        //preparation

        EvaluateColumns(board);
        EvaluateRows(board);


        diagonal(4);

        return 0;
    }

    private void EvaluateRows(Integer[][] board){
        int rowSize  = board[0].length,columnSize = board.length;
        Integer[] tempArray= new Integer[4];
        boolean containsOne = false;
        boolean containsTwo = false;
        if (columnSize > 3)
            for (int y = 0 ; y < rowSize - 3 ; y++) {
                for (int x = 0; x < columnSize; x++) {
                    containsOne = false;
                    containsTwo = false;
                    for (int index = 0; index <4;index++)
                    {
                        if (board[x][y+index]!= null) {
                            if (board[x][y + index]==1)
                                containsOne = true;
                            else
                                containsTwo = true;
                            tempArray[index] = board[x][y+index];
                        }
                        else
                            tempArray[index]=0;
                    }
                    if (containsOne ^ containsTwo)
                        if (containsOne){
                            evaluationScore.addCount(tempArray,1);
                        }
                        else{
                            evaluationScore.addCount(tempArray,2);
                        }
                }
            }
    }

    private void EvaluateColumns(Integer[][] board){
        boolean isPlayerOne = false;
        boolean isPlayerTwo = false;
        Integer[] tempArray = new Integer[]{0,0,0,0};
        int rowSize = board.length,columnSize  = board[0].length;
        if (rowSize > 3)
            for (int y = 0 ; y < columnSize ; y++) {
                int counter = 0;
                boolean valueSet = false;
                if(board[0][y]==null && board[rowSize-1][y]!= null) { //check if first element of a columns is zero or last
                    for (int x = 0; x < rowSize; x++) {
                        if (board[x][y] != null) {
                            if (!valueSet)
                            {
                                tempArray[counter]=board[x][y];
                                valueSet = true;
                            }
                            else
                            {
                                if (board[x][y]!=tempArray[0]){
                                    break;
                                }
                                else
                                {
                                    counter++;
                                    tempArray[counter] = board[x][y];
                                }
                            }
                        }
                    }
                }

                evaluationScore.addCount(tempArray,tempArray[0]);
            }

    }

    /**
     * Get score for both players in both diagonal directions
     * @param terminalCount - number of coins in a row for a winning state
     * @return
     */
    public void diagonal(int terminalCount) {

        int maxX = board.length;
        int maxY = board[0].length;

        int startY = maxY - terminalCount;

        // Slash diagonal
        for(int x = 0; x <= maxX - terminalCount; x++) {
            for(int y = startY; y >= 0; y--) {
                checkDiagonal(x, y, terminalCount, evaluationScore, Orientation.FORWARD_DIAGONAL);
            }
            // The initial run (X0), we start from Y top and decrement it to Y0, for the subsequent runs, we only do check Y0
            startY = 0;
        }

        // Backslash diagonal
        for(int x = maxX - 1; x >= terminalCount - 1 ; x--) {
            for (int y = startY; y >= 0; y--) {
                checkDiagonal(x, y, terminalCount, evaluationScore, Orientation.BACKWARD_DIAGONAL);
            }
            startY = 0;
        }
    }

    /**
     * Get all possible moves for both players in the given diagonal
     * @param startX - starting X of the diagonal
     * @param startY - starting Y of the diagonal
     * @param terminalCount - number of coins in a row for a winning state
     * @param score - score holder
     * @param orientation - SLASH / BACKSLASH DIAGONAL
     */
    public void checkDiagonal(int startX, int startY, int terminalCount, EvaluationScore score, Orientation orientation) {

        int maxX = board.length;
        int maxY = board[0].length;

        // Length of the diagonal
        int segments = Math.min(Orientation.FORWARD_DIAGONAL == orientation ? maxX - startX : startX + 1, maxY - startY) / terminalCount;

        Integer player = null;

        for(int s = 0; s < segments; s++) {

            Integer[] possibility = new Integer[terminalCount];

            for(int i = 0; i < terminalCount; i++) {

                // Calculate [x, y] relative to the segment
                int x = startX + (s * terminalCount + (orientation == Orientation.FORWARD_DIAGONAL ? i : -i));
                int y = startY + (s * terminalCount + i);

                // If the coin cannot be placed on [x, y] because there is nothing at [x, y-1], break
                if(y > 0 && board[x][y - 1] == null) {
                    break;
                }

                // If there are two different players in the segment, break
                if(player != board[x][y] && player != null) {
                    break;
                }
                else {
                    if(board[x][y] != null) {
                        player = board[x][y];
                    }
                    possibility[i] = board[x][y];
                }
            }
            score.addCount(possibility, player);
        }

        // If there are more segments to explore, recursively explore them
        if(startX + terminalCount <= maxX && startY + terminalCount <= maxX) {
            checkDiagonal(startX + 1, startY + 1, terminalCount, score, orientation);
        }
    }




}
