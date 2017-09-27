package example.com.chess.Pieces

import android.widget.ImageView
import example.com.chess.ChessPiece
import example.com.chess.MainActivity
import example.com.chess.R

/**
 * Created by steph on 9/14/2017.
 */
class WhiteRook(mainActivity: MainActivity, row: Int, col: Int) : ChessPiece(mainActivity, row, col){

    override val color: String
        get() = "white"
    var hasMoved = false
    override var possibleMoves: MutableSet<Pair<Int, Int>> = mutableSetOf()

    override fun highlightPossibleMoves(){
        possibleMoves.clear()

        for (j in 1.until(8)){//handle forward
            if (row - j >= 0){
                if (mainActivity.gameState[row-j][col] != null && mainActivity.gameState[row-j][col]?.color == "black"){
                    val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row-j}-${col}") as ImageView
                    nextSpot.setBackgroundResource(R.drawable.circle2)
                    possibleMoves.add(Pair(row-j, col))
                    break
                }else if (mainActivity.gameState[row-j][col] != null && mainActivity.gameState[row-j][col]?.color == "white"){
                    break
                }
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row-j}-${col}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row-j, col))
            }
        }
        for (j in 1.until(8)){//handle back
            if (row + j < 8){
                if (mainActivity.gameState[row+j][col] != null && mainActivity.gameState[row+j][col]?.color == "black"){
                    val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row+j}-${col}") as ImageView
                    nextSpot.setBackgroundResource(R.drawable.circle2)
                    possibleMoves.add(Pair(row+j, col))
                    break
                }else if (mainActivity.gameState[row+j][col] != null && mainActivity.gameState[row+j][col]?.color == "white"){
                    break
                }
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row+j}-${col}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row+j, col))
            }
        }
        for (j in 1.until(8)){//handle right
            if (col + j < 8){
                if (mainActivity.gameState[row][col+j] != null && mainActivity.gameState[row][col+j]?.color == "black"){
                    val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row}-${col+j}") as ImageView
                    nextSpot.setBackgroundResource(R.drawable.circle2)
                    possibleMoves.add(Pair(row, col+j))
                    break
                }else if (mainActivity.gameState[row][col+j] != null && mainActivity.gameState[row][col+j]?.color == "white"){
                    break
                }
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row}-${col+j}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row, col+j))
            }
        }
        for (j in 1.until(8)){//handle left
            if (col - j >= 0){
                if (mainActivity.gameState[row][col-j] != null && mainActivity.gameState[row][col-j]?.color == "black"){
                    val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row}-${col-j}") as ImageView
                    nextSpot.setBackgroundResource(R.drawable.circle2)
                    possibleMoves.add(Pair(row, col-j))
                    break
                }else if (mainActivity.gameState[row][col-j] != null && mainActivity.gameState[row][col-j]?.color == "white"){
                    break
                }
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row}-${col-j}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row, col-j))
            }
        }
    }
    override fun canMove(newRow: Int, newCol: Int): Boolean{
        return  possibleMoves.contains(Pair(newRow, newCol))
    }


    override fun movePiece(newRow: Int, newCol: Int) {
        super.movePiece(newRow, newCol)
        hasMoved = true
        mainActivity.gameState[newRow][newCol] = this
        mainActivity.gameState[row][col] = null


        val newSpot: ImageView = mainActivity.board.findViewWithTag("space:$newRow-$newCol") as ImageView
        val oldSpot: ImageView = mainActivity.board.findViewWithTag("space:$row-$col") as ImageView

        oldSpot.setImageResource(R.drawable.blank)
        newSpot.setImageResource(R.drawable.whiterook)
        this.row = newRow
        this.col = newCol

    }


}