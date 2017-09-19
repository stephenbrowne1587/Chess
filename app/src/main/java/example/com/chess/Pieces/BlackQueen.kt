package example.com.chess.Pieces

import android.widget.ImageView
import example.com.chess.ChessPiece
import example.com.chess.MainActivity
import example.com.chess.R

/**
 * Created by steph on 9/14/2017.
 */
class BlackQueen(mainActivity: MainActivity, row: Int, col: Int) : ChessPiece(mainActivity, row, col){
    override val color: String
        get() = "black"

    override var possibleMoves: MutableSet<Pair<Int, Int>> = mutableSetOf()
    override fun highlightPossibleMoves(){
        possibleMoves.clear()

        for (j in 1.until(8)){//handle forward
            if (row - j >= 0){
                if (mainActivity.gameState[row-j][col] != null && mainActivity.gameState[row-j][col]?.color == "white"){
                    val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row-j}-${col}") as ImageView
                    nextSpot.setBackgroundResource(R.drawable.circle2)
                    possibleMoves.add(Pair(row-j, col))
                    break
                }else if (mainActivity.gameState[row-j][col] != null && mainActivity.gameState[row-j][col]?.color == "black"){
                    break
                }
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row-j}-${col}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row-j, col))
            }
        }
        for (j in 1.until(8)){//handle back
            if (row + j < 8){
                if (mainActivity.gameState[row+j][col] != null && mainActivity.gameState[row+j][col]?.color == "white"){
                    val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row+j}-${col}") as ImageView
                    nextSpot.setBackgroundResource(R.drawable.circle2)
                    possibleMoves.add(Pair(row+j, col))
                    break
                }else if (mainActivity.gameState[row+j][col] != null && mainActivity.gameState[row+j][col]?.color == "black"){
                    break
                }
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row+j}-${col}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row+j, col))
            }
        }
        for (j in 1.until(8)){//handle right
            if (col + j < 8){
                if (mainActivity.gameState[row][col+j] != null && mainActivity.gameState[row][col+j]?.color == "white"){
                    val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row}-${col+j}") as ImageView
                    nextSpot.setBackgroundResource(R.drawable.circle2)
                    possibleMoves.add(Pair(row, col+j))
                    break
                }else if (mainActivity.gameState[row][col+j] != null && mainActivity.gameState[row][col+j]?.color == "black"){
                    break
                }
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row}-${col+j}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row, col+j))
            }
        }
        for (j in 1.until(8)){//handle left
            if (col - j >= 0){
                if (mainActivity.gameState[row][col-j] != null && mainActivity.gameState[row][col-j]?.color == "white"){
                    val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row}-${col-j}") as ImageView
                    nextSpot.setBackgroundResource(R.drawable.circle2)
                    possibleMoves.add(Pair(row, col-j))
                    break
                }else if (mainActivity.gameState[row][col-j] != null && mainActivity.gameState[row][col-j]?.color == "black"){
                    break
                }
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row}-${col-j}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row, col-j))
            }
        }

        for (i in 1.until(8)){//handle left forward diagonal
            if (row - i >= 0 && col - i >= 0){
                if (mainActivity.gameState[row-i][col-i] != null && mainActivity.gameState[row-i][col-i]?.color == "white"){
                    val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row-i}-${col-i}") as ImageView
                    nextSpot.setBackgroundResource(R.drawable.circle2)
                    possibleMoves.add(Pair(row-i, col-i))
                    break
                }else if (mainActivity.gameState[row-i][col-i] != null && mainActivity.gameState[row-i][col-i]?.color == "black"){
                    break
                }
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row-i}-${col-i}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row-i, col-i))
            }
        }
        for (j in 1.until(8)){//handle right forward diagonal
            if (row - j >= 0 && col + j < 8){
                if (mainActivity.gameState[row-j][col+j] != null && mainActivity.gameState[row-j][col+j]?.color == "white"){
                    val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row-j}-${col+j}") as ImageView
                    nextSpot.setBackgroundResource(R.drawable.circle2)
                    possibleMoves.add(Pair(row-j, col+j))
                    break
                }else if (mainActivity.gameState[row-j][col+j] != null && mainActivity.gameState[row-j][col+j]?.color == "black"){
                    break
                }
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row-j}-${col+j}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row-j, col+j))
            }
        }
        for (j in 1.until(8)){//handle right backwards diagonal
            if (row + j < 8 && col + j < 8){
                if (mainActivity.gameState[row+j][col+j] != null && mainActivity.gameState[row+j][col+j]?.color == "white"){
                    val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row+j}-${col+j}") as ImageView
                    nextSpot.setBackgroundResource(R.drawable.circle2)
                    possibleMoves.add(Pair(row+j, col+j))
                    break
                }else if (mainActivity.gameState[row+j][col+j] != null && mainActivity.gameState[row+j][col+j]?.color == "black"){
                    break
                }
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row+j}-${col+j}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row+j, col+j))
            }
        }
        for (j in 1.until(8)){//handle left back diagonal
            if (row + j < 8 && col - j >=0){
                if (mainActivity.gameState[row+j][col-j] != null && mainActivity.gameState[row+j][col-j]?.color == "white"){
                    val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row+j}-${col-j}") as ImageView
                    nextSpot.setBackgroundResource(R.drawable.circle2)
                    possibleMoves.add(Pair(row+j, col-j))
                    break
                }else if (mainActivity.gameState[row+j][col-j] != null && mainActivity.gameState[row+j][col-j]?.color == "black"){
                    break
                }
                val nextSpot: ImageView = mainActivity.board.findViewWithTag("overlay:${row+j}-${col-j}") as ImageView
                nextSpot.setBackgroundResource(R.drawable.circle2)
                possibleMoves.add(Pair(row+j, col-j))
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
        newSpot.setImageResource(R.drawable.blackqueen)
        this.row = newRow
        this.col = newCol
    }


}