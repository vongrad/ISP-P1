import java.util.HashMap;
import java.util.Map;

/**
 * Created by vongrad on 3/12/17.
 */
public class Evaluator2 {

    /**
     * Get score for both players in both diagonal directions
     * @param board - current state of the board
     * @param terminalCount - number of coins in a row for a winning state
     * @param playerId - our player id
     * @param oponentId - oponents player id
     * @return
     */
    public EvaluationScore diagonal(Integer[][] board, int terminalCount, int playerId, int oponentId) {

        int maxX = board.length;
        int maxY = board[0].length;

        EvaluationScore score = new EvaluationScore(playerId, oponentId);

        int startY = maxY - terminalCount;

        // Slash diagonal
        for(int x = 0; x <= maxX - terminalCount; x++) {
            for(int y = startY; y >= 0; y--) {
                checkDiagonal(board, x, y, terminalCount, score, Orientation.FORWARD_DIAGONAL);
            }
            // The initial run (X0), we start from Y top and decrement it to Y0, for the subsequent runs, we only do check Y0
            startY = 0;
        }

        // Backslash diagonal
        for(int x = maxX - 1; x >= terminalCount - 1 ; x--) {
            for (int y = startY; y >= 0; y--) {
                checkDiagonal(board, x, y, terminalCount, score, Orientation.BACKWARD_DIAGONAL);
            }
            startY = 0;
        }
        return score;
    }

    /**
     * Get all possible moves for both players in the given diagonal
     * @param board - current state of the board
     * @param startX - starting X of the diagonal
     * @param startY - starting Y of the diagonal
     * @param terminalCount - number of coins in a row for a winning state
     * @param score - score holder
     * @param orientation - SLASH / BACKSLASH DIAGONAL
     */
    public void checkDiagonal(Integer[][] board, int startX, int startY, int terminalCount, EvaluationScore score, Orientation orientation) {

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
                    player = board[x][y];
                    possibility[i] = board[x][y];
                }
            }
            score.addCount(possibility, player);
        }

        // If there are more segments to explore, recursively explore them
        if(startX + terminalCount <= maxX && startY + terminalCount <= maxX) {
            checkDiagonal(board, startX + 1, startY + 1, terminalCount, score, orientation);
        }
    }


//    public void checkDiagonal(Integer[][] board, int startX, int startY, int terminalCount, EvaluationScore score, Orientation orientation) {
//
//        int maxX = board.length;
//        int maxY = board[0].length;
//
//        Integer currentPlayer = null;
//        int counter = 0;
//        int emptyCounter = 0;
//
//        int x = startX;
//
//        // Started with empty square
//        boolean emptyStart = false;
//
//        boolean emptyPrev = false;
//
//        for(int y = startY; y < maxY; y++) {
//
//            if(x < 0 || x > maxX) break;
//
//            // Check if the sequence started with an empty square
//            if(currentPlayer == null && board[x][y] == null) {
//                emptyStart = true;
//            }
//            else if(board[x][y] != currentPlayer && board[x][y] != null && emptyPrev) {
//                emptyStart = true;
//            }
//
//            // Add to score
//            if(counter + emptyCounter >= terminalCount) {
//                if(counter == terminalCount - 1) {
//                    // This is our definite winning move
//                    score.addCount(counter + 1, currentPlayer);
//                    // TODO: maybe stop execution as we found perfect solution
//                }
//                else {
//                    // Possible winning move
//                    score.addCount(counter, currentPlayer);
//                }
//                counter = 0;
//                emptyCounter = emptyPrev ? 1 : 0;
//            }
//
//            // Square is empty, continue
//            if(board[x][y] == null) {
//                emptyPrev = true;
//                emptyCounter++;
//                continue;
//            }
//            else {
//                emptyPrev = false;
//            }
//
//            // Still the same player or no player yet
//            if(board[x][y] == currentPlayer || currentPlayer == null) {
//                counter++;
//            }
//            else {
//                counter = 1;
//                emptyCounter = 0;
//            }
//
//            // Only if current squery is not empty, change current player
//            if(board[x][y] != null) {
//                currentPlayer = board[x][y];
//            }
//
//            x += orientation == Orientation.FORWARD_DIAGONAL ? 1 : -1;
//        }
//    }

//    public void __deprecated_checkSlashDiagonal(Integer[][] board, int startX, int startY, int terminalCount, EvaluationScore score, Orientation orientation) {
//
//        int maxX = board.length;
//        int maxY = board[0].length;
//
//        Integer currentPlayer = null;
//        int counter = 0;
//        int emptyCounter = 0;
//
//        Map<Integer, Integer> us = new HashMap<>();
//        Map<Integer, Integer> oponent = new HashMap<>();
//
//        for(int x = startX; x < maxX; x++) {
//
//            for(int y = startY; y < maxY; y++) {
//
//                // Add to score
//                if(counter + emptyCounter >= terminalCount) {
//                    score.addCount(counter, currentPlayer);
//                }
//
//                // Square is empty, continue
//                if(board[x][y] == null) {
//                    emptyCounter++;
//                    continue;
//                }
//
//                // Still the same player or no player yet
//                if(board[x][y] == currentPlayer || currentPlayer == null) {
//                    counter++;
//                }
//                else {
//                    counter = 1;
//                    emptyCounter = 0;
//                }
//
//                // Only if current squery is not empty, change current player
//                if(board[x][y] != null) {
//                    currentPlayer = board[x][y];
//                }
//            }
//        }
//    }
}
