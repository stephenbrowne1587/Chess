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

        for (j in 1.until(8)){//handle forward
            if (row - j >= 0){
                if (gameState[row-j][col] != null && gameState[row-j][col]?.color == "black"){
                    possibleMoves.add(Pair(row-j, col))
                    break
                }else if (gameState[row-j][col] != null && gameState[row-j][col]?.color == "white"){
                    break
                }
                possibleMoves.add(Pair(row-j, col))
            }
        }
        for (j in 1.until(8)){//handle back
            if (row + j < 8){
                if (gameState[row+j][col] != null && gameState[row+j][col]?.color == "black"){
                    possibleMoves.add(Pair(row+j, col))
                    break
                }else if (gameState[row+j][col] != null && gameState[row+j][col]?.color == "white"){
                    break
                }
                possibleMoves.add(Pair(row+j, col))
            }
        }
        for (j in 1.until(8)){//handle right
            if (col + j < 8){
                if (gameState[row][col+j] != null && gameState[row][col+j]?.color == "black"){
                    possibleMoves.add(Pair(row, col+j))
                    break
                }else if (gameState[row][col+j] != null && gameState[row][col+j]?.color == "white"){
                    break
                }
                possibleMoves.add(Pair(row, col+j))
            }
        }
        for (j in 1.until(8)){//handle left
            if (col - j >= 0){
                if (gameState[row][col-j] != null && gameState[row][col-j]?.color == "black"){
                    possibleMoves.add(Pair(row, col-j))
                    break
                }else if (gameState[row][col-j] != null && gameState[row][col-j]?.color == "white"){
                    break
                }
                possibleMoves.add(Pair(row, col-j))
            }
        }
        if (mainActivity.whiteInCheck || mainActivity.isBlockingWhite){
            possibleMoves = possibleMoves.intersect(mainActivity.blockSpots).toMutableSet()
        }
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
        mainActivity.detectCheck(mainActivity.gameState)
        mainActivity.setCheckWarning()

    }


}