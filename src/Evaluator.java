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

    public Evaluator(){

    }

    public double Evaluate(State state)
    {
        //preparation
        maxPlayerId = state.getPlayer();
        minPlayerId = 3 - maxPlayerId;


        Integer[][] board = state.getBoard();

        double rowEvaluationValue = EvaluateRows(board);
        double columnEvaluationValue = EvaluateColumns(board);
        List<String> dioganals = GetDioganals(board);
        double dioganalEvaluatrionValue = EvaluateDioganals(dioganals);
        return 0;
    }

    private double EvaluateRows(List<String> rows)
    {
        return 0;
    }
    private double EvaluateRow(String row)
    {
        return 0;
    }
    private double EvaluateDioganals(List<String> dioganals)
    {
        return 0;
    }
    private double EvaluateDioganal(String dioganal)
    {
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
                        //AmasMethod(tempArray,1);
                        }
                        else{
                        //AdamsMethod(tempArray,2);
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

        return 0;
    }

    private List<String> GetDioganals(Integer[][] board){
        List<String> diogonalList = new ArrayList<>();
        int rowSize = board.length, columnSize  = board[0].length;
        String temp = "";
        if (columnSize>3 && rowSize >3) {
            // iterate through dioganals ( +3 and -3 is because we dont care about first 3 and last 3 dioganals)
            for (int x = 0 + 3; x < (rowSize + columnSize - 1) - 3; x++) {
                for (int y = 0; y <= x; y++) {
                    int normalizer = x - y;
                    if (normalizer < rowSize && y < columnSize)
                        if (board[normalizer][y]== null) {
                            temp += '0';
                        }
                        else {
                            temp += board[normalizer][y];
                        }
                }
                diogonalList.add(temp);
                temp = "";
            }
            // iterate through other one
            for (int x = (rowSize - 1) - 3; x > (-columnSize) + 3; x--) {
                for (int y = 0; y <= (rowSize - 1) - x; y++) {
                    int normalizer = x + y;
                    //same as before
                    if (normalizer < rowSize && y < columnSize && normalizer > -1 && y > -1)
                        if (board[normalizer][y]== null) {
                            temp += '0';
                        }
                        else {
                            temp += board[normalizer][y];
                        }
                }

                diogonalList.add(temp);
                temp = "";
            }
        }

        return diogonalList;
    }



}
