/**
 * IGame class
 * Modified from Professor Ruby code
 * @author jubck
 * @date 1/26/2024
 */
interface IGame {
    /**
     * clear the board of all discs by setting all spots to EMPTY
     */
    fun clearBoard()

    /** Sets the given player at the given location on the game board.
     * The location must be available, or the board will not be changed
     * @param player - GameConstants.BLUE or GameConstants.RED
     * @param row - The row (0-5) to place the move
     * @param col - The column (0-5) to place the move
     */
    fun setMove(player: Int, row: Int, col: Int)

    /** Returns the best move for the computer to make. You must call setMove()
     * to actually make the computer move to the location
     * start with a dumb implementation that returns a the first non-occupied cell
     * @return the best move for the computer to make (0-35)
     */
    val computerMove: Int

    /**
     * Check for a winner and return a status value indicating who has won.
     * @return GameConstants.PLAYING if still playing, GameConstants.TIE if it's a tie,
     * GameConstants.BLUE_WON if GameConstants.BLUE won, or GameConstants.RED_WON if GameConstants.RED won
     */
    fun checkForWinner(): Int
}

object GameConstants{
    // Name-constants to represent the seeds and cell contents
    const val EMPTY = 0
    const val BLUE = 1
    const val RED = 2

    // Name-constants to represent the various states of the game
    const val PLAYING = 0

    const val ROWS = 6
    const val COLS = 6 // number of rows and columns
}