import java.util.*;

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

        //EvaluateColumns();
        //EvaluateRows();
        diagonal(4);
        return 0;
    }

    private void EvaluateRows(){
        int y = board[0].length,x  = board.length;
        Integer[] tempArray= new Integer[4];
        boolean containsOne = false;
        boolean containsTwo = false;
        if (y > 3)
            for (int y1 = 0; y1 < y ; y1++) {
                for (int x1 = 0; x1 < x - 3; x1++) {
                    containsOne = false;
                    containsTwo = false;
                    for (int index = 0; index < 4; index++)
                    {
                        if (board[x1+index][y1]!= null) {
                            if (board[x1+index][y1]==1)
                                containsOne = true;
                            else
                                containsTwo = true;
                            tempArray[index] = board[x1+index][y1];
                        }
                        else
                        {
                            tempArray[index]=null;
                            //check if value below is null
                            if (y1!=0)
                                if(board[x1+index][y1-1]==null)
                                {
                                    containsOne = false;
                                    containsTwo = false;
                                    break;
                                }
                        }
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

    private void EvaluateColumns(){

        Integer[] tempArray = new Integer[4];
        int y = board[0].length,x  = board.length; 
        if (x > 3)
            for (int x1 = x - 1; x1 > -1  ; x1--) {
                int counter = 0;
                boolean valueSet = false;
                if(board[x1][0]!=null && board[x1][y - 1]== null) { //check if first element of a columns is zero or last
                    for (int y1 = y - 1 ; y1 > -1; y1--) {
                        if (board[x1][y1] != null) {
                            if (!valueSet)
                            {
                                tempArray[counter]=board[x1][y1];
                                valueSet = true;
                            }
                            else
                            {
                                if (board[x1][y1]!=tempArray[0]){
                                    break;
                                }
                                else
                                {
                                    counter++;
                                    tempArray[counter] = board[x1][y1];
                                }
                            }
                        }
                    }
                }
                evaluationScore.addCount(tempArray,tempArray[0]);
                tempArray = new Integer[4];
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
                checkDiagonal(x, y, terminalCount, Orientation.FORWARD_DIAGONAL);
            }
            // The initial run (X0), we start from Y top and decrement it to Y0, for the subsequent runs, we only do check Y0
            startY = 0;
        }

        // Re-initialize start Y
        startY = maxY - terminalCount;

        // Backslash diagonal
        for(int x = maxX - 1; x >= terminalCount - 1 ; x--) {
            for (int y = startY; y >= 0; y--) {
                checkDiagonal(x, y, terminalCount, Orientation.BACKWARD_DIAGONAL);
            }
            startY = 0;
        }
    }

    /**
     * Get all possible moves for both players in the given diagonal
     * @param startX - starting X of the diagonal
     * @param startY - starting Y of the diagonal
     * @param terminalCount - number of coins in a row for a winning state
     * @param orientation - SLASH / BACKSLASH DIAGONAL
     */
    public void checkDiagonal(int startX, int startY, int terminalCount, Orientation orientation) {

        int maxX = board.length;
        int maxY = board[0].length;

        // Length of the diagonal
        int segments = Math.min(Orientation.FORWARD_DIAGONAL == orientation ? maxX - startX : startX + 1, maxY - startY) / terminalCount;

        Integer player = null;

        int lastX = 0;
        int lastY = 0;

        segments:
        for(int s = 0; s < segments; s++) {

            Integer[] possibility = new Integer[terminalCount];

            for(int i = 0; i < terminalCount; i++) {

                // Calculate [x, y] relative to the segment
                int x = startX + (s * terminalCount + (orientation == Orientation.FORWARD_DIAGONAL ? i : -i));
                int y = startY + (s * terminalCount + i);

                if(x == 4 && y == 2) {
                    String test = "";
                }

                lastX = x;
                lastY = y;

                // If the coin cannot be placed on [x, y] because there is nothing at [x, y-1], break
                if(y > 0 && board[x][y - 1] == null) {
                    continue segments;
                }

                // If there are two different players in the segment, break
                if(player != board[x][y] && player != null && board[x][y] != null) {
                    continue segments;
                }
                else {
                    if(board[x][y] != null) {
                        player = board[x][y];
                    }
                    possibility[i] = board[x][y];
                }
            }

            if(player != null) {
                evaluationScore.addCount(possibility, player);
            }
        }

        // If there are more segments to explore, recursively explore them
        if(startX + terminalCount <= maxX && startY + terminalCount <= maxX) {
            checkDiagonal(startX + 1, startY + 1, terminalCount, orientation);
        }
    }




}
