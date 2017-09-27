package example.com.chess.Pieces

import android.widget.ImageView
import example.com.chess.ChessPiece
import example.com.chess.MainActivity
import example.com.chess.R

/**
 * Created by steph on 9/14/2017.
 */
class WhiteKing (mainActivity: MainActivity, row: Int, col: Int) : ChessPiece(mainActivity, row, col){
    override val color: String
        get() = "white"
    var hasMoved = false

    override var possibleMoves: MutableSet<Pair<Int, Int>> = mutableSetOf()
    override fun highlightPossibleMoves(){
        possibleMoves.clear()
            if (row - 1 >= 0){
                if (mainActivity.gameState[row-1][col] == null || mainActivity.gameState[row-1][col]?.color == "black") {
                    val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row - 1}-${col}") as ImageView
                    nextSpot.setBackgroundResource(R.drawable.circle2)
                    possibleMoves.add(Pair(row - 1, col))
                }
            }
            if (row + 1 < 8){
                if (mainActivity.gameState[row+1][col] == null || mainActivity.gameState[row+1][col]?.color == "black") {
                    val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row + 1}-${col}") as ImageView
                    nextSpot.setBackgroundResource(R.drawable.circle2)
                    possibleMoves.add(Pair(row + 1, col))
                }
            }
            if (col + 1 < 8){
                if (mainActivity.gameState[row][col+1] == null || mainActivity.gameState[row][col+1]?.color == "black") {
                    val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row}-${col + 1}") as ImageView
                    nextSpot.setBackgroundResource(R.drawable.circle2)
                    possibleMoves.add(Pair(row, col + 1))
                }
            }
            if (col - 1 >= 0){
                if (mainActivity.gameState[row][col-1] == null || mainActivity.gameState[row][col-1]?.color == "black") {
                    val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row}-${col - 1}") as ImageView
                    nextSpot.setBackgroundResource(R.drawable.circle2)
                    possibleMoves.add(Pair(row, col - 1))
                }
            }
            if (row - 1 >= 0 && col - 1 >= 0){
                if (mainActivity.gameState[row-1][col-1] == null || mainActivity.gameState[row-1][col-1]?.color == "black") {
                    val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row - 1}-${col - 1}") as ImageView
                    nextSpot.setBackgroundResource(R.drawable.circle2)
                    possibleMoves.add(Pair(row - 1, col - 1))
                }
            }
            if (row - 1 >= 0 && col + 1 < 8){
                if (mainActivity.gameState[row-1][col+1] == null || mainActivity.gameState[row-1][col+1]?.color == "black") {
                    val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row - 1}-${col + 1}") as ImageView
                    nextSpot.setBackgroundResource(R.drawable.circle2)
                    possibleMoves.add(Pair(row - 1, col + 1))
                }
            }

            if (row + 1 < 8 && col + 1 < 8){
                if (mainActivity.gameState[row+1][col+1] == null || mainActivity.gameState[row+1][col+1]?.color == "black") {
                    val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row + 1}-${col + 1}") as ImageView
                    nextSpot.setBackgroundResource(R.drawable.circle2)
                    possibleMoves.add(Pair(row + 1, col + 1))
                }
            }

            if (row + 1 < 8 && col - 1 >=0){
                if (mainActivity.gameState[row+1][col-1] == null || mainActivity.gameState[row+1][col-1]?.color == "black") {
                    val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row + 1}-${col - 1}") as ImageView
                    nextSpot.setBackgroundResource(R.drawable.circle2)
                    possibleMoves.add(Pair(row + 1, col - 1))
                }
            }
        if (canCastleShort()){
            val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${7}-${6}") as ImageView
            nextSpot.setBackgroundResource(R.drawable.circle2)
            possibleMoves.add(Pair(7, 6))
        }
        if (canCastleLong()){
            val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${7}-${2}") as ImageView
            nextSpot.setBackgroundResource(R.drawable.circle2)
            possibleMoves.add(Pair(7, 2))
        }
    }
    override fun canMove(newRow: Int, newCol: Int): Boolean{
        return  possibleMoves.contains(Pair(newRow, newCol))
    }

    override fun movePiece(newRow: Int, newCol: Int) {
        super.movePiece(newRow, newCol)
        if (!hasMoved && newRow == 7 && newCol == 6){
            moveRookShort()
        }
        if (!hasMoved && newRow == 7 && newCol == 2){
            moveRookLong()
        }
        hasMoved = true
        mainActivity.gameState[newRow][newCol] = this
        mainActivity.gameState[row][col] = null


        val newSpot: ImageView = mainActivity.board.findViewWithTag("space:$newRow-$newCol") as ImageView
        val oldSpot: ImageView = mainActivity.board.findViewWithTag("space:$row-$col") as ImageView

        oldSpot.setImageResource(R.drawable.blank)
        newSpot.setImageResource(R.drawable.whiteking)

        this.row = newRow
        this.col = newCol

    }

    fun canCastleLong(): Boolean {
        if (mainActivity.gameState[7][0] != null && mainActivity.gameState[7][0] is WhiteRook) {
            val rookLong = mainActivity.gameState[7][0] as WhiteRook
            if (!rookLong.hasMoved && !this.hasMoved) {
                if (mainActivity.gameState[7][1] == null && mainActivity.gameState[7][2] == null && mainActivity.gameState[7][3] == null) {
                    return true
                }
            }
        }
        return false
    }
    fun canCastleShort(): Boolean {
        if (mainActivity.gameState[7][7] != null && mainActivity.gameState[7][7] is WhiteRook) {
            val rookShort = mainActivity.gameState[7][7] as WhiteRook
            if (!rookShort.hasMoved && !this.hasMoved) {
                if (mainActivity.gameState[7][5] == null && mainActivity.gameState[7][6] == null) {
                    return true
                }
            }
        }
        return false
    }
    fun moveRookShort(){
        val rookShort = mainActivity.gameState[7][7] as WhiteRook
        rookShort.movePiece(7, 5)
        mainActivity.toggleActivePlayer()
    }
    fun moveRookLong(){
        val rookLong = mainActivity.gameState[7][0] as WhiteRook
        rookLong.movePiece(7, 3)
        mainActivity.toggleActivePlayer()
    }


}