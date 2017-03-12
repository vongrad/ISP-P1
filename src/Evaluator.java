import java.util.ArrayList;
import java.util.List;

/**
 * Created by lema on 11-03-2017.
 */
public class Evaluator {

    private Integer currentPlayersMoveId;
    private Integer maxPlayerId;
    private Integer minPlayerId;

    public Evaluator(){

    }

    public double Evaluate(State state,boolean isMax)
    {
        //preparation
        currentPlayersMoveId = state.getPlayer();

        if (isMax) {
            maxPlayerId = state.getPlayer();
            minPlayerId = 3 - maxPlayerId;
        }
        else {
            minPlayerId = state.getPlayer();
            maxPlayerId = 3 - minPlayerId;
        }

        Integer[][] board = state.getBoard();
        List<String> columns = GetColumns(board);
        double columnEvaluationValue = EvaluateColumns(columns);
        List<String> rows = GetRows(board);
        List<String> dioganals = GetDioganals(board);
        double rowEvaluationValue = EvaluateRows(rows);
        double dioganalEvaluatrionValue = EvaluateDioganals(dioganals);
        return 0;
    }

    private double EvaluateColumns(List<String> columns)
    {
        double evaluateValue = 0;
        for(String line: columns)
        {
             evaluateValue += EvaluateColumn(line);
        }
        return evaluateValue;
    }
    private double EvaluateColumn(String column)
    {
        char[] columnArray = column.toCharArray();
        int counter = 0;
        char foundValue = '0';
        boolean valueSet = false;
        for (char coin: columnArray)
        {
            if (!valueSet)
            {
                foundValue=coin;
                valueSet = true;
                counter++;
            }
            else
            {
                if (coin!=foundValue)
                    return counter;
                else counter++;
            }
        }
        return counter;
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
    private List<String> GetRows(Integer[][] board){
        List<String> rowList = new ArrayList<String>();
        int rowSize  = board.length,columnSize = board[0].length;

        String temp = "";
        if (columnSize > 3)
            for (int k = 0 ; k < rowSize ; k++) {
                for (int j = 0; j <columnSize; j++) {
                    if (board[k][j]!= null) {
                        temp += '0';
                    }
                    else {
                        temp += board[k][j];
                    }
                }
                rowList.add(temp);
                temp = "";
            }
        return rowList;
    }

    private List<String> GetColumns(Integer[][] board){
        List<String> columnList = new ArrayList<>();
        int rowSize = board.length,columnSize  = board[0].length;

        String temp = "";
        if (rowSize > 3)
            for (int k = 0 ; k < columnSize ; k++) {
                if(board[0][k]==null) { //check if first element of a columns is zero or lasat
                    for (int j = 0; j < rowSize; j++) {
                        if (board[j][k] != null) {
                            temp += board[j][k];
                        }
                    }
                    columnList.add(temp);
                    temp = "";
                }
            }
        return columnList;
    }

    private List<String> GetDioganals(Integer[][] board){
        List<String> diogonalList = new ArrayList<>();
        int rowSize = board.length, columnSize  = board[0].length;
        String temp = "";
        if (columnSize>3 && rowSize >3) {
            // iterate through dioganals ( +3 and -3 is because we dont care about first 3 and last 3 dioganals)
            for (int k = 0 + 3; k < (rowSize + columnSize - 1) - 3; k++) {
                for (int j = 0; j <= k; j++) {
                    int i = k - j;
                    if (i < rowSize && j < columnSize)
                        if (board[i][j]== null) {
                            temp += '0';
                        }
                        else {
                            temp += board[i][j];
                        }
                }
                diogonalList.add(temp);
                temp = "";
            }
            // iterate through other one
            for (int k = (rowSize - 1) - 3; k > (-columnSize) + 3; k--) {
                for (int j = 0; j <= (rowSize - 1) - k; j++) {
                    int i = k + j;
                    //same as before
                    if (i < rowSize && j < columnSize && i > -1 && j > -1)
                        if (board[i][j]== null) {
                            temp += '0';
                        }
                        else {
                            temp += board[i][j];
                        }
                }

                diogonalList.add(temp);
                temp = "";
            }
        }

        return diogonalList;
    }


}
