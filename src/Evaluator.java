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

        double columnEvaluationValue = EvaluateColumns(board);
        double rowEvaluationValue = EvaluateRows(board);
        return 0;
    }

    private double EvaluateRows(Integer[][] board){
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
        return 0;
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





}
