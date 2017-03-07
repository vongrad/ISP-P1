/**
 * Created by Adam Vongrej on 3/6/17.
 */

/**
 * Class used for checking a terminal (winning) state of the connect-n game
 */
public class TerminalTester {

    /**
     * Number of consecutive coins for a player to win
     */
    private int connectCount = 4;

    public TerminalTester(int connectCount) {
        this.connectCount = connectCount;
    }

    /**
     * Check if the last turn was terminal
     * @param grid - Game board
     * @param x - Column
     * @return
     */
    public boolean isTerminal(Integer[][] grid, int x) {
        return horizontal(grid, x) || vertical(grid, x) || slashDiagonal(grid, x) || backslashDiagonal(grid, x);
    }

    /**
     * Check HORIZONTALLY if the last move was a winning move
     * @param grid - Game board
     * @param x - Column
     * @return
     */
    public boolean horizontal(Integer[][] grid, int x) {
        return linear(grid, x, Orientation.HORIZONTAL);
    }

    /**
     * Check VERTICALLY if the last move was a winning move
     * @param grid - Game board
     * @param x - Column
     * @return
     */
    public boolean vertical(Integer[][] grid, int x) {
        return linear(grid, x, Orientation.VERTICAL);
    }

    /**
     * Generic method for checking HORIZONTAL & VERTICAL winning moves
     * @param grid - Game Board
     * @param x - Column
     * @param orientation - Orientation
     * @return
     */
    private boolean linear(Integer[][] grid, int x, Orientation orientation) {

        int count = 0;
        Integer player = null;

        int max = orientation == Orientation.HORIZONTAL ? grid.length : grid[x].length;

        int y = 0;

        for(int i = 0; i < max; i++){

            if(orientation == Orientation.HORIZONTAL){
                x = i;
            }
            else{
                y = i;
            }

            if(grid[x][y] == player) {
                count++;
            }
            else{
                count = 1;
                player = grid[x][y];
            }
            if(count == this.connectCount) return true;
        }
        return false;
    }

    /**
     * Check SLASH-DIAGONALLY if the last move was a winning move
     * @param grid - Game Board
     * @param x - Column
     * @return
     */
    public boolean slashDiagonal(Integer[][] grid, int x) {
        return diagonal(grid, x, Orientation.FORWARD_DIAGONAL);
    }

    /**
     * Check BACKSLASH-DIAGONALLY if the last move was a winning move
     * @param grid - Game board
     * @param x - Column
     * @return
     */
    public boolean backslashDiagonal(Integer[][] grid, int x) {
        return diagonal(grid, x, Orientation.BACKWARD_DIAGONAL);
    }

    /**
     * Generic method for checking SLASH-DIAGONAL & BACKSLASH-DIAGONAL winning moves
     * @param grid - Game board
     * @param x - Column
     * @param orientation - Orientation
     * @return
     */
    private boolean diagonal(Integer[][] grid, int x, Orientation orientation){

        int lastY = getLastY(grid, x);
        int maxX = grid.length -1;
        int maxY = grid[maxX].length - 1;

        int normalizer = orientation == Orientation.FORWARD_DIAGONAL ? Math.min(lastY, x) : Math.min(maxX - x, lastY);

        x -= normalizer;
        if (x<0) {
            System.out.println("X: " + x + " Y: " );
        }
        int firstY = lastY - normalizer;

        Integer player = null;
        int count = 0;

        int terminalX = orientation == Orientation.FORWARD_DIAGONAL ? maxX : -1;

        for(int y = firstY; y <= maxY; y++){

            if(x == terminalX || count == this.connectCount) return count == this.connectCount;
            if (x<0) {
                System.out.println("X: " + x + " Y: " + y);
            }
            if(grid[x][y] == player) {
                count++;
            }
            else{
                player = grid[x][y];
                count = 1;
            }
            x += orientation == Orientation.FORWARD_DIAGONAL ? 1 : -1;
        }
        return false;
    }

    /**
     * For a specific column, get Y position of top-most coin
     * @param grid - Game board
     * @param x - Column
     * @return
     */
    private int getLastY(Integer[][] grid, int x) {
        for(int y = 0; y < grid[x].length; y++){
            if(grid[x][y] == null){
                return y - 1;
            }
        }
        return 0;
    }
}
