package com.stephen_browne.chess.Pieces

import android.widget.ImageView
import com.stephen_browne.chess.ChessPiece
import com.stephen_browne.chess.MainActivity
import com.stephen_browne.chess.R

/**
 * Created by steph on 9/14/2017.
 */
class WhiteKnight(mainActivity: MainActivity, row: Int, col: Int) : ChessPiece(mainActivity, row, col){
    override val color: String
        get() = "white"


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
        if (row-2 >= 0 && col-1 >= 0){
            if(gameState[row-2][col-1] == null || gameState[row-2][col-1]?.color == "black"){
                possibleMoves.add(Pair(row-2, col-1))
            }else if (gameState[row-2][col-1]?.color == "white"){
                gameState[row-2][col-1]?.isProtected = true
            }
        }
        if (row-2 >= 0 && col+1 < 8){
            if(gameState[row-2][col+1] == null || gameState[row-2][col+1]?.color == "black"){
                possibleMoves.add(Pair(row-2, col+1))
            }else if (gameState[row-2][col+1]?.color == "white"){
                gameState[row-2][col+1]?.isProtected = true
            }
        }
        if (row+2 <8  && col-1 >= 0){
            if(gameState[row+2][col-1] == null || gameState[row+2][col-1]?.color == "black"){
                possibleMoves.add(Pair(row+2, col-1))
            }else if (gameState[row+2][col-1]?.color == "white"){
                gameState[row+2][col-1]?.isProtected = true
            }
        }
        if (row+2 < 8 && col+1 < 8){
            if(gameState[row+2][col+1] == null || gameState[row+2][col+1]?.color == "black"){
                possibleMoves.add(Pair(row+2, col+1))
            }else if (gameState[row+2][col+1]?.color == "white"){
                gameState[row+2][col+1]?.isProtected = true
            }
        }
        if (row-1 >= 0 && col-2 >= 0){
            if(gameState[row-1][col-2] == null || gameState[row-1][col-2]?.color == "black"){
                possibleMoves.add(Pair(row-1, col-2))
            }else if (gameState[row-1][col-2]?.color == "white"){
                gameState[row-1][col-2]?.isProtected = true
            }
        }
        if (row-1 >= 0 && col+2 < 8){
            if(gameState[row-1][col+2] == null || gameState[row-1][col+2]?.color == "black"){
                possibleMoves.add(Pair(row-1, col+2))
            }else if (gameState[row-1][col+2]?.color == "white"){
                gameState[row-1][col+2]?.isProtected = true
            }
        }
        if (row+1 <8  && col-2 >= 0){
            if(gameState[row+1][col-2] == null || gameState[row+1][col-2]?.color == "black"){
                possibleMoves.add(Pair(row+1, col-2))
            }else if (gameState[row+1][col-2]?.color == "white"){
                gameState[row+1][col-2]?.isProtected = true
            }
        }
        if (row+1 < 8 && col+2 < 8){
            if(gameState[row+1][col+2] == null || gameState[row+1][col+2]?.color == "black"){
                possibleMoves.add(Pair(row+1, col+2))
            }else if (gameState[row+1][col+2]?.color == "white"){
                gameState[row+1][col+2]?.isProtected = true
            }
        }
        if (mainActivity.whiteInCheck || mainActivity.isBlockingWhite){
            possibleMoves = possibleMoves.intersect(mainActivity.blockSpots).toMutableSet()
        }
    }
    override fun movePiece(newRow: Int, newCol: Int) {
        super.movePiece(newRow, newCol)

        mainActivity.gameState[newRow][newCol] = this
        mainActivity.gameState[row][col] = null


        val newSpot: ImageView = mainActivity.board.findViewWithTag("space:$newRow-$newCol") as ImageView
        val oldSpot: ImageView = mainActivity.board.findViewWithTag("space:$row-$col") as ImageView

        oldSpot.setImageResource(R.drawable.blank)
        newSpot.setImageResource(R.drawable.whiteknight)
        this.row = newRow
        this.col = newCol
        mainActivity.detectCheck(mainActivity.gameState)
        mainActivity.setCheckWarning()
    }




}