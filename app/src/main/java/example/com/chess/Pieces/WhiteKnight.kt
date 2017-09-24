package example.com.chess.Pieces

import android.widget.ImageView
import example.com.chess.ChessPiece
import example.com.chess.MainActivity
import example.com.chess.R

/**
 * Created by steph on 9/14/2017.
 */
class WhiteKnight(mainActivity: MainActivity, row: Int, col: Int) : ChessPiece(mainActivity, row, col){
    override val color: String
        get() = "white"

    override var possibleMoves: MutableSet<Pair<Int, Int>> = mutableSetOf()
    override fun highlightPossibleMoves(){
        possibleMoves.clear()
        if (row-2 >= 0 && col-1 >= 0){
            if(mainActivity.gameState[row-2][col-1] == null || mainActivity.gameState[row-2][col-1]?.color == "black"){
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row-2}-${col-1}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row-2, col-1))
            }
        }
        if (row-2 >= 0 && col+1 < 8){
            if(mainActivity.gameState[row-2][col+1] == null || mainActivity.gameState[row-2][col+1]?.color == "black"){
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row-2}-${col+1}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row-2, col+1))
            }
        }
        if (row+2 <8  && col-1 >= 0){
            if(mainActivity.gameState[row+2][col-1] == null || mainActivity.gameState[row+2][col-1]?.color == "black"){
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row+2}-${col-1}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row+2, col-1))
            }
        }
        if (row+2 < 8 && col+1 < 8){
            if(mainActivity.gameState[row+2][col+1] == null || mainActivity.gameState[row+2][col+1]?.color == "black"){
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row+2}-${col+1}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row+2, col+1))
            }
        }
        if (row-1 >= 0 && col-2 >= 0){
            if(mainActivity.gameState[row-1][col-2] == null || mainActivity.gameState[row-1][col-2]?.color == "black"){
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row-1}-${col-2}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row-1, col-2))
            }
        }
        if (row-1 >= 0 && col+2 < 8){
            if(mainActivity.gameState[row-1][col+2] == null || mainActivity.gameState[row-1][col+2]?.color == "black"){
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row-1}-${col+2}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row-1, col+2))
            }
        }
        if (row+1 <8  && col-2 >= 0){
            if(mainActivity.gameState[row+1][col-2] == null || mainActivity.gameState[row+1][col-2]?.color == "black"){
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row+1}-${col-2}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row+1, col-2))
            }
        }
        if (row+1 < 8 && col+2 < 8){
            if(mainActivity.gameState[row+1][col+2] == null || mainActivity.gameState[row+1][col+2]?.color == "black"){
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row+1}-${col+2}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row+1, col+2))
            }
        }
    }
    override fun canMove(newRow: Int, newCol: Int): Boolean{
        return  possibleMoves.contains(Pair(newRow, newCol))
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
    }


}