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

        //double columnEvaluationValue = EvaluateColumns(board);
        //EvaluateRows(board);

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
                            tempArray[index] = 1;
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

    private double EvaluateColumns(Integer[][] board){
        HashMap<Integer, Integer> maxValues = new HashMap();
        maxValues.put(1, 0);
        maxValues.put(2, 0);
        maxValues.put(3, 0);
        HashMap<Integer, Integer> minValues= new HashMap();;
        minValues.put(1, 0);
        minValues.put(2, 0);
        minValues.put(3, 0);
        int rowSize = board.length,columnSize  = board[0].length;
        if (rowSize > 3)
            for (int y = 0 ; y < columnSize ; y++) {
                int counter = 1;
                Integer foundValue = 0;
                boolean valueSet = false;
                if(board[0][y]==null && board[rowSize-1][y]!= null) { //check if first element of a columns is zero or lasat
                    for (int x = 0; x < rowSize; x++) {
                        if (board[x][y] != null) {
                            if (!valueSet)
                            {
                                foundValue=board[x][y];
                                valueSet = true;
                            }
                            else
                            {
                                counter++;
                                if (board[x][y]!=foundValue){
                                    counter--;
                                    break;
                                }
                            }
                        }
                    }
                    if(foundValue == maxPlayerId)
                        maxValues.put(counter, maxValues.get(counter) + 1);
                    else
                        minValues.put(counter, minValues.get(counter) + 1);
                }
            }
        //Evaluate rows
        if (maxValues.get(3)>0) return 1;
        if (minValues.get(3)>1) return -1;
        double maxValue = maxValues.get(2) * 2 + maxValues.get(1) * 1;
        double minValue = minValues.get(3) * 3 + minValues.get(2) * 2  + minValues.get(1);
        if (maxValue>minValue)
            return minValue/maxValue;
        else
            return -(maxValue/minValue);
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
