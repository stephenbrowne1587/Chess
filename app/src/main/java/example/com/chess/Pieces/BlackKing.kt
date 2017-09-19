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
        get() = "white"

    override var possibleMoves: MutableSet<Pair<Int, Int>> = mutableSetOf()
    override fun highlightPossibleMoves(){
        possibleMoves.clear()
        if (row - 1 >= 0){
            if (mainActivity.gameState[row-1][col] == null || mainActivity.gameState[row-1][col]?.color == "white") {
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row - 1}-${col}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row - 1, col))
            }
        }
        if (row + 1 < 8){
            if (mainActivity.gameState[row+1][col] == null || mainActivity.gameState[row+1][col]?.color == "white") {
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row + 1}-${col}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row + 1, col))
            }
        }
        if (col + 1 < 8){
            if (mainActivity.gameState[row][col+1] == null || mainActivity.gameState[row][col+1]?.color == "white") {
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row}-${col + 1}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row, col + 1))
            }
        }
        if (col - 1 >= 0){
            if (mainActivity.gameState[row][col-1] == null || mainActivity.gameState[row][col-1]?.color == "white") {
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row}-${col - 1}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row, col - 1))
            }
        }
        if (row - 1 >= 0 && col - 1 >= 0){
            if (mainActivity.gameState[row-1][col-1] == null || mainActivity.gameState[row-1][col-1]?.color == "white") {
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row - 1}-${col - 1}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row - 1, col - 1))
            }
        }
        if (row - 1 >= 0 && col + 1 < 8){
            if (mainActivity.gameState[row-1][col+1] == null || mainActivity.gameState[row-1][col+1]?.color == "white") {
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row - 1}-${col + 1}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row - 1, col + 1))
            }
        }

        if (row + 1 < 8 && col + 1 < 8){
            if (mainActivity.gameState[row+1][col+1] == null || mainActivity.gameState[row+1][col+1]?.color == "white") {
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row + 1}-${col + 1}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row + 1, col + 1))
            }
        }

        if (row + 1 < 8 && col - 1 >=0){
            if (mainActivity.gameState[row+1][col-1] == null || mainActivity.gameState[row+1][col-1]?.color == "white") {
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row + 1}-${col - 1}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row + 1, col - 1))
            }
        }

    }

    override fun canMove(newRow: Int, newCol: Int): Boolean{
        return  possibleMoves.contains(Pair(newRow, newCol))
    }
 

    override fun movePiece(newRow: Int, newCol: Int) {
        mainActivity.gameState[newRow][newCol] = this
        mainActivity.gameState[row][col] = null


        val newSpot: ImageView = mainActivity.board.findViewWithTag("space:$newRow-$newCol") as ImageView
        val oldSpot: ImageView = mainActivity.board.findViewWithTag("space:$row-$col") as ImageView

        oldSpot.setImageResource(R.drawable.blank)
        newSpot.setImageResource(R.drawable.blackking)
        this.row = newRow
        this.col = newCol
    }


}