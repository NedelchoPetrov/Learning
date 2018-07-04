// This file contains a SUGGESTION for the structure of your program.  You
// may change any of it, or add additional files to this directory (package),
// as long as you conform to the project specification.  Do not, however,
// modify the contents of the 'gui' subpackage.

// We have indicated parts of the file that you might especially want to
// fill in with "// FIXME"  or "// REPLACE..." comments.  But again,
// you can change just about anything.

// Comments that start with "//" are intended to be removed from your
// solutions.

package game2048;

import ucb.util.CommandArgs;

import game2048.gui.Game;
import static game2048.Main.Side.*;

/** The main class for the 2048 game.
 *  @author
 */
public class Main {

    /** Size of the board: number of rows and of columns. */
    static final int SIZE = 4;
    /** Number of squares on the board. */
    static final int SQUARES = SIZE * SIZE;

    /** Symbolic names for the four sides of a board. */
    static enum Side { NORTH, EAST, SOUTH, WEST };

    /** The main program.  ARGS may contain the options --seed=NUM,
     *  (random seed); --log (record moves and random tiles
     *  selected.); --testing (take random tiles and moves from
     *  standard input); and --no-display. */
    public static void main(String... args) {
        CommandArgs options =
            new CommandArgs("--seed=(\\d+) --log --testing --no-display",
                            args);
        if (!options.ok()) {
            System.err.println("Usage: java game2048.Main [ --seed=NUM ] "
                               + "[ --log ] [ --testing ] [ --no-display ]");
            System.exit(1);
        }

        Main game = new Main(options);

        while (game.play()) {
            /* No action */
        }
        System.exit(0);
    }

    /** A new Main object using OPTIONS as options (as for main). */
    Main(CommandArgs options) {
        boolean log = options.contains("--log"),
            display = !options.contains("--no-display");
        long seed = !options.contains("--seed") ? 0 : options.getLong("--seed");
        _testing = options.contains("--testing");
        _game = new Game("2048", SIZE, seed, log, display, _testing);
    }

    /** Reset the score for the current game to 0 and clear the board. */
    void clear() {
        _score = 0;
        _count = 0;
        _game.clear();
        _game.setScore(_score, _maxScore);
        for (int r = 0; r < SIZE; r += 1) {
            for (int c = 0; c < SIZE; c += 1) {
                _board[r][c] = 0;
            }
        }
    }

    /** Play one game of 2048, updating the maximum score. Return true
     *  iff play should continue with another game, or false to exit. */
    boolean play() {
        // FIXME?

        while (true) {
            // FIXME?
            //System.out.println("Main loop");
            setRandomPiece();
            setRandomPiece();
            /**Set two random tiles at the beginning of the game**/

            /* Code from the skeleton that I didn't use
            if (gameOver()) {
                // FIXME?
            }
            */
        GetMove:
            while (true) {
                String key = _game.readKey();

                switch (key) {
                case "Up": case "Down": case "Left": case "Right":
                    if (!gameOver() && tiltBoard(keyToSide(key))) {
                        setRandomPiece();
                    }else if(gameOver()){
                        _game.endGame();
                    }

                    break;
                // FIXME?

                case "New Game":
                    _game.endGame();
                    _game.clear();
                    clear();
                    break GetMove;

                case "Quit":
                    return false;

                default:
                    break;
                }
            }
            // FIXME?
        }
    }

    /**Executes all possible moves in direction Up.**/
    boolean moveUp(int[][] board, Side side){
        boolean result = false;
        for (int col = 0; col < SIZE; col ++){
            mainloop:
            for (int row = 0; row < SIZE; row ++){
                int mainValue = board[row][col];
                if(mainValue == 0){
                    for (int i = row + 1; i < SIZE; i++){
                        int value = board[i][col];
                        if (value != 0){
                            _game.moveTile(value, tiltRow(side, i, col), tiltCol(side, i, col), tiltRow(side, row, col), tiltCol(side, row, col));
                            result = true;
                            board[i][col] = 0;
                            board[row][col] = value;
                            row--;
                            continue mainloop;
                        }
                    }
                }else{
                    for (int i = row + 1; i<SIZE; i++){
                        int value = board[i][col];
                        if(value == 0){
                            continue;
                        }
                        if(value == mainValue){
                            _game.mergeTile(value, value*2,tiltRow(side, i, col), tiltCol(side, i, col), tiltRow(side, row, col), tiltCol(side, row, col));
                            result = true;
                            _score += value*2;
                            if(_score > _maxScore){
                                _maxScore = _score;
                            }
                            if(value == 1024 ){
                                _game.endGame();
                            }
                            _game.setScore(_score,_maxScore);
                            board[i][col] = 0;
                            board[row][col] = value*2;
                            _count--;
                            continue mainloop;
                        }else{
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

    /** Return true iff the current game is over (no more moves
     *  possible). */
    boolean gameOver() {
        if (_count == SIZE*SIZE) {
            int s = SIZE-1;
            for(int row = 0 ; row < SIZE-1; row ++){
                for(int col = 0; col < SIZE-1; col++){

                    int target = _board[row][col];
                    int rightOfTarget = _board[row][col+1];
                    int belowTarget = _board[row+1][col];

                    if(target == rightOfTarget || target == belowTarget){
                        return false;
                    }
                }
                if (_board[s][row]== _board[s][row+1] || _board[row][s] == _board[row+1][s]){
                    return false;
                }
            }
        return true;

        }
        return false;
    }

    /** Add a tile to a random, empty position, choosing a value (2 or
     *  4) at random.  Has no effect if the board is currently full. */
    void setRandomPiece() {
        if (_count == SQUARES) {
            return;
        }
        /** Add a Tile to an empty slot. **/
        boolean added = false;
        while(!added) {
            try {
                int[] randomTile = _game.getRandomTile();
                _game.addTile(randomTile[0], randomTile[1], randomTile[2]);
                added = true;
                _count++;
                _board[randomTile[1]][randomTile[2]] = randomTile[0];
            }catch(IllegalArgumentException badArg){
                continue;
            }
        }
    }

    /** Perform the result of tilting the board toward SIDE.
     *  Returns true iff the tilt changes the board. **/
    boolean tiltBoard(Side side) {
        /* As a suggestion (see the project text), you might try copying
         * the board to a local array, turning it so that edge SIDE faces
         * north.  That way, you can re-use the same logic for all
         * directions.  (As usual, you don't have to). */
        int[][] board = new int[SIZE][SIZE];

        for (int r = 0; r < SIZE; r += 1) {
            for (int c = 0; c < SIZE; c += 1) {
                board[r][c] =
                    _board[tiltRow(side, r, c)][tiltCol(side, r, c)];
            }
        }
/* Print the board with these lines
        for (int r = 0; r < SIZE; r++){
            for (int c = 0; c < SIZE; c ++){
                System.out.print(board[r][c] + " | ");
            }
            System.out.println("");
        }
*/
        boolean result = moveUp(board, side);
        _game.setScore(_score,_maxScore);
        _game.displayMoves();

        for (int r = 0; r < SIZE; r += 1) {
            for (int c = 0; c < SIZE; c += 1) {
                _board[tiltRow(side, r, c)][tiltCol(side, r, c)]
                    = board[r][c];
            }
        }
        return result;
    }

    /** Return the row number on a playing board that corresponds to row R
     *  and column C of a board turned so that row 0 is in direction SIDE (as
     *  specified by the definitions of NORTH, EAST, etc.).  So, if SIDE
     *  is NORTH, then tiltRow simply returns R (since in that case, the
     *  board is not turned).  If SIDE is WEST, then column 0 of the tilted
     *  board corresponds to row SIZE - 1 of the untilted board, and
     *  tiltRow returns SIZE - 1 - C. */
    int tiltRow(Side side, int r, int c) {
        switch (side) {
        case NORTH:
            return r;
        case EAST:
            return c;
        case SOUTH:
            return SIZE - 1 - r;
        case WEST:
            return SIZE - 1 - c;
        default:
            throw new IllegalArgumentException("Unknown direction");
        }
    }

    /** Return the column number on a playing board that corresponds to row
     *  R and column C of a board turned so that row 0 is in direction SIDE
     *  (as specified by the definitions of NORTH, EAST, etc.). So, if SIDE
     *  is NORTH, then tiltCol simply returns C (since in that case, the
     *  board is not turned).  If SIDE is WEST, then row 0 of the tilted
     *  board corresponds to column 0 of the untilted board, and tiltCol
     *  returns R. */
    int tiltCol(Side side, int r, int c) {
        switch (side) {
        case NORTH:
            return c;
        case EAST:
            return SIZE - 1 - r;
        case SOUTH:
            return SIZE - 1 - c;
        case WEST:
            return r;
        default:
            throw new IllegalArgumentException("Unknown direction");
        }
    }

    /** Return the side indicated by KEY ("Up", "Down", "Left",
     *  or "Right"). */
    Side keyToSide(String key) {
        switch (key) {
        case "Up":
            return NORTH;
        case "Down":
            return SOUTH;
        case "Left":
            return WEST;
        case "Right":
            return EAST;
        default:
            throw new IllegalArgumentException("unknown key designation");
        }
    }

    /** Represents the board: _board[r][c] is the tile value at row R,
     *  column C, or 0 if there is no tile there. */
    private final int[][] _board = new int[SIZE][SIZE];

    /** True iff --testing option selected. */
    private boolean _testing;
    /** THe current input source and output sink. */
    private Game _game;
    /** The score of the current game, and the maximum final score
     *  over all games in this session. */
    private int _score, _maxScore;
    /** Number of tiles on the board. */
    private int _count;
}
