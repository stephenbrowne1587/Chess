package example.com.chess.Pieces

import android.widget.ImageView
import example.com.chess.ChessPiece
import example.com.chess.MainActivity
import example.com.chess.R

/**
 * Created by steph on 9/14/2017.
 */
class BlackPawn(mainActivity: MainActivity, row: Int, col: Int) : ChessPiece(mainActivity, row, col){
    override val color: String
        get() = "black"
    var isFirstMove: Boolean = true

    override var possibleMoves: MutableSet<Pair<Int, Int>> = mutableSetOf()

    override fun highlightPossibleMoves(){
        refreshPossibleMoves()
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
    override fun refreshPossibleMoves(){
        possibleMoves.clear()
        if (row + 1 < 8 && mainActivity.gameState[row+1][col] == null){
            possibleMoves.add(Pair(row+1, col))
        }
        if (isFirstMove && row + 2 >= 0 && mainActivity.gameState[row+2][col] == null){
            possibleMoves.add(Pair(row+2, col))
        }
        if (col - 1 >= 0 && mainActivity.gameState[row+1][col-1]?.color == "white"){
            possibleMoves.add(Pair(row+1, col-1))
        }
        if (col + 1 < 8 && mainActivity.gameState[row+1][col+1]?.color == "white"){
            possibleMoves.add(Pair(row+1, col+1))
        }
    }

    override fun movePiece(newRow: Int, newCol: Int) {
        super.movePiece(newRow, newCol)
        isFirstMove = false
        mainActivity.gameState[newRow][newCol] = this
        mainActivity.gameState[row][col] = null


        val newSpot: ImageView = mainActivity.board.findViewWithTag("space:$newRow-$newCol") as ImageView
        val oldSpot: ImageView = mainActivity.board.findViewWithTag("space:$row-$col") as ImageView

        oldSpot.setImageResource(R.drawable.blank)
        newSpot.setImageResource(R.drawable.blackpawn)
        this.row = newRow
        this.col = newCol
    }


}