package example.com.chess.Pieces

import android.media.Image
import android.util.Log
import android.widget.ImageView
import example.com.chess.ChessPiece
import example.com.chess.MainActivity
import example.com.chess.R

/**
 * Created by steph on 9/14/2017.
 */
class WhitePawn(mainActivity: MainActivity, row: Int, col: Int) : ChessPiece(mainActivity, row, col){

    override var possibleMoves: MutableSet<Pair<Int, Int>> = mutableSetOf()
    override val color: String
        get() = "white"

    override fun highlightPossibleMoves(){
        possibleMoves.clear()
        for (i in 1..2){
            val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row-i}-${col}") as ImageView

            nextSpot.setBackgroundResource(R.drawable.circle2)
            possibleMoves.add(Pair(row-i, col))
        }
    }



    override fun canMove(newRow: Int, newCol: Int): Boolean{
        return  possibleMoves.contains(Pair(newRow, newCol))
    }

    override fun movePiece(newRow: Int, newCol: Int) {
        Log.i("kkkkkkk", "KKKKKKKK")
        mainActivity.gameState[newRow][newCol] = this
        mainActivity.gameState[row][col] = null


        val newSpot: ImageView = mainActivity.board.findViewWithTag("space:$newRow-$newCol") as ImageView
        val oldSpot: ImageView = mainActivity.board.findViewWithTag("space:$row-$col") as ImageView

        oldSpot.setImageResource(R.drawable.blank)
        newSpot.setImageResource(R.drawable.whitepawn)
        this.row = newRow
        this.col = newCol
    }







}