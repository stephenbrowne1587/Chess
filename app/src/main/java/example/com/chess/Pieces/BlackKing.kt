package example.com.chess.Pieces

import android.widget.ImageView
import example.com.chess.ChessPiece
import example.com.chess.MainActivity
import example.com.chess.R

/**
 * Created by steph on 9/14/2017.
 */
class BlackKing(mainActivity: MainActivity, row: Int, col: Int) : ChessPiece(mainActivity, row, col){
    override val color: String
        get() = "black"
    var hasMoved = false

    override var possibleMoves: MutableSet<Pair<Int, Int>> = mutableSetOf()
    override fun highlightPossibleMoves(){

        refreshPossibleMoves(mainActivity.gameState)
        for (move in possibleMoves){
            val rowj = move.first
            val colj = move.second
            val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${rowj}-${colj}") as ImageView
            nextSpot.setBackgroundResource(R.drawable.circle2)
        }

    }

    override fun canMove(newRow: Int, newCol: Int): Boolean{
        return  possibleMoves.contains(Pair(newRow, newCol))
    }
    override fun refreshPossibleMoves(gameState: Array<Array<ChessPiece?>>){
        possibleMoves.clear()
        if (row - 1 >= 0){
            if (gameState[row-1][col] == null || gameState[row-1][col]?.color == "white") {
                possibleMoves.add(Pair(row - 1, col))
            }
        }
        if (row + 1 < 8){
            if (gameState[row+1][col] == null || gameState[row+1][col]?.color == "white") {
                possibleMoves.add(Pair(row + 1, col))
            }
        }
        if (col + 1 < 8){
            if (gameState[row][col+1] == null || gameState[row][col+1]?.color == "white") {
                possibleMoves.add(Pair(row, col + 1))
            }
        }
        if (col - 1 >= 0){
            if (gameState[row][col-1] == null || gameState[row][col-1]?.color == "white") {
                possibleMoves.add(Pair(row, col - 1))
            }
        }
        if (row - 1 >= 0 && col - 1 >= 0){
            if (gameState[row-1][col-1] == null || gameState[row-1][col-1]?.color == "white") {
                possibleMoves.add(Pair(row - 1, col - 1))
            }
        }
        if (row - 1 >= 0 && col + 1 < 8){
            if (gameState[row-1][col+1] == null || gameState[row-1][col+1]?.color == "white") {
                possibleMoves.add(Pair(row - 1, col + 1))
            }
        }

        if (row + 1 < 8 && col + 1 < 8){
            if (gameState[row+1][col+1] == null || gameState[row+1][col+1]?.color == "white") {
                possibleMoves.add(Pair(row + 1, col + 1))
            }
        }

        if (row + 1 < 8 && col - 1 >=0){
            if (gameState[row+1][col-1] == null || gameState[row+1][col-1]?.color == "white") {
                possibleMoves.add(Pair(row + 1, col - 1))
            }
        }
        if (canCastleShort()){
            possibleMoves.add(Pair(0, 6))
        }
        if (canCastleLong()){
            possibleMoves.add(Pair(0, 2))
        }

    }
 

    override fun movePiece(newRow: Int, newCol: Int) {
        super.movePiece(newRow, newCol)
        if (!hasMoved && newRow == 0 && newCol == 6){
            moveRookShort()
        }
        if (!hasMoved && newRow == 0 && newCol == 2){
            moveRookLong()
        }
        hasMoved = true
        mainActivity.gameState[newRow][newCol] = this
        mainActivity.gameState[row][col] = null


        val newSpot: ImageView = mainActivity.board.findViewWithTag("space:$newRow-$newCol") as ImageView
        val oldSpot: ImageView = mainActivity.board.findViewWithTag("space:$row-$col") as ImageView

        oldSpot.setImageResource(R.drawable.blank)
        newSpot.setImageResource(R.drawable.blackking)
        this.row = newRow
        this.col = newCol
        mainActivity.detectCheck(mainActivity.gameState)
        mainActivity.setCheckWarning()
    }
    fun canCastleLong(): Boolean {
        if (mainActivity.gameState[0][0] != null && mainActivity.gameState[0][0] is BlackRook) {
            val rookLong = mainActivity.gameState[0][0] as BlackRook
            if (!rookLong.hasMoved && !this.hasMoved) {
                if (mainActivity.gameState[0][1] == null && mainActivity.gameState[0][2] == null && mainActivity.gameState[0][3] == null) {
                    return true
                }
            }
        }
        return false
    }
    fun canCastleShort(): Boolean {
        if (mainActivity.gameState[0][7] != null && mainActivity.gameState[0][7] is BlackRook) {
            val rookShort = mainActivity.gameState[0][7] as BlackRook
            if (!rookShort.hasMoved && !this.hasMoved) {
                if (mainActivity.gameState[0][5] == null && mainActivity.gameState[0][6] == null) {
                    return true
                }
            }
        }
        return false
    }

    fun moveRookShort(){
        val rookShort = mainActivity.gameState[0][7] as BlackRook
        rookShort.movePiece(0, 5)
        mainActivity.toggleActivePlayer()
    }
    fun moveRookLong(){
        val rookLong = mainActivity.gameState[0][0] as BlackRook
        rookLong.movePiece(0, 3)
        mainActivity.toggleActivePlayer()
    }


}