package com.stephen_browne.chess.Pieces

import android.widget.ImageView
import com.stephen_browne.chess.ChessPiece
import com.stephen_browne.chess.MainActivity
import com.stephen_browne.chess.R

/**
 * Created by steph on 9/14/2017.
 */
class WhiteKing (mainActivity: MainActivity, row: Int, col: Int) : ChessPiece(mainActivity, row, col){
    override val color: String
        get() = "white"
    var hasMoved = false

    override var possibleMoves: MutableSet<Pair<Int, Int>> = mutableSetOf()
    override fun highlightPossibleMoves(){
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
            if (gameState[row-1][col] == null || (gameState[row-1][col]?.color == "black" && gameState[row-1][col]?.isProtected == false)) {
                possibleMoves.add(Pair(row - 1, col))
            }else if (gameState[row-1][col]?.color == "white"){
                gameState[row-1][col]?.isProtected = true
            }
        }
        if (row + 1 < 8){
            if (gameState[row+1][col] == null || (gameState[row+1][col]?.color == "black" && gameState[row+1][col]?.isProtected == false)) {
                possibleMoves.add(Pair(row + 1, col))
            }else if (gameState[row+1][col]?.color == "white"){
                gameState[row+1][col]?.isProtected = true
            }
        }
        if (col + 1 < 8){
            if (gameState[row][col+1] == null || (gameState[row][col+1]?.color == "black" && gameState[row][col+1]?.isProtected == false)) {
                possibleMoves.add(Pair(row, col + 1))
            }else if (gameState[row][col+1]?.color == "white"){
                gameState[row][col+1]?.isProtected = true
            }
        }
        if (col - 1 >= 0){
            if (gameState[row][col-1] == null || (gameState[row][col-1]?.color == "black" && gameState[row][col-1]?.isProtected == false)) {
                possibleMoves.add(Pair(row, col - 1))
            }else if (gameState[row][col-1]?.color == "white"){
                gameState[row][col-1]?.isProtected = true
            }
        }
        if (row - 1 >= 0 && col - 1 >= 0){
            if (gameState[row-1][col-1] == null || (gameState[row-1][col-1]?.color == "black" && gameState[row-1][col-1]?.isProtected == false)) {
                possibleMoves.add(Pair(row - 1, col - 1))
            }else if (gameState[row-1][col-1]?.color == "white"){
                gameState[row-1][col-1]?.isProtected = true
            }
        }
        if (row - 1 >= 0 && col + 1 < 8){
            if (gameState[row-1][col+1] == null || (gameState[row-1][col+1]?.color == "black" && gameState[row-1][col+1]?.isProtected == false)) {
                possibleMoves.add(Pair(row - 1, col + 1))
            }else if (gameState[row-1][col+1]?.color == "white"){
                gameState[row-1][col+1]?.isProtected = true
            }
        }
        if (row + 1 < 8 && col + 1 < 8){
            if (gameState[row+1][col+1] == null || (gameState[row+1][col+1]?.color == "black" && gameState[row+1][col+1]?.isProtected == false)) {
                possibleMoves.add(Pair(row + 1, col + 1))
            }else if (gameState[row+1][col+1]?.color == "white"){
                gameState[row+1][col+1]?.isProtected = true
            }
        }
        if (row + 1 < 8 && col - 1 >=0){
            if (gameState[row+1][col-1] == null || (gameState[row+1][col-1]?.color == "black" && gameState[row+1][col-1]?.isProtected == false)) {
                possibleMoves.add(Pair(row + 1, col - 1))
            }else if (gameState[row+1][col-1]?.color == "white"){
                gameState[row+1][col-1]?.isProtected = true
            }
        }
        if (canCastleShort()){
            possibleMoves.add(Pair(7, 6))
        }
        if (canCastleLong()){
            possibleMoves.add(Pair(7, 2))
        }
        if (true){
//            possibleMoves = possibleMoves.intersect(mainActivity.blockSpots).toMutableSet()
            mainActivity.filterKingInCheckMoves(Pair(this.row, this.col))
        }

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
        mainActivity.detectCheck(mainActivity.gameState)
        mainActivity.setCheckWarning()

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