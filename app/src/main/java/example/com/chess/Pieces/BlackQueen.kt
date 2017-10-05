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
                if (gameState[row-j][col] != null && gameState[row-j][col]?.color == "white"){
                    possibleMoves.add(Pair(row-j, col))
                    break
                }else if (gameState[row-j][col] != null && gameState[row-j][col]?.color == "black"){
                    gameState[row-j][col]?.isProtected = true
                    break
                }
                possibleMoves.add(Pair(row-j, col))
            }
        }
        for (j in 1.until(8)){//handle back
            if (row + j < 8){
                if (gameState[row+j][col] != null && gameState[row+j][col]?.color == "white"){
                    possibleMoves.add(Pair(row+j, col))
                    break
                }else if (gameState[row+j][col] != null && gameState[row+j][col]?.color == "black"){
                    gameState[row+j][col]?.isProtected = true
                    break
                }
                possibleMoves.add(Pair(row+j, col))
            }
        }
        for (j in 1.until(8)){//handle right
            if (col + j < 8){
                if (gameState[row][col+j] != null && gameState[row][col+j]?.color == "white"){
                    possibleMoves.add(Pair(row, col+j))
                    break
                }else if (gameState[row][col+j] != null && gameState[row][col+j]?.color == "black"){
                    gameState[row][col+j]?.isProtected = true
                    break
                }
                possibleMoves.add(Pair(row, col+j))
            }
        }
        for (j in 1.until(8)){//handle left
            if (col - j >= 0){
                if (gameState[row][col-j] != null && gameState[row][col-j]?.color == "white"){
                    possibleMoves.add(Pair(row, col-j))
                    break
                }else if (gameState[row][col-j] != null && gameState[row][col-j]?.color == "black"){
                    gameState[row][col-j]?.isProtected = true
                    break
                }
                possibleMoves.add(Pair(row, col-j))
            }
        }

        for (i in 1.until(8)){//handle left forward diagonal
            if (row - i >= 0 && col - i >= 0){
                if (gameState[row-i][col-i] != null && gameState[row-i][col-i]?.color == "white"){
                    possibleMoves.add(Pair(row-i, col-i))
                    break
                }else if (gameState[row-i][col-i] != null && gameState[row-i][col-i]?.color == "black"){
                    gameState[row-i][col-i]?.isProtected = true
                    break
                }
                possibleMoves.add(Pair(row-i, col-i))
            }
        }
        for (j in 1.until(8)){//handle right forward diagonal
            if (row - j >= 0 && col + j < 8){
                if (gameState[row-j][col+j] != null && gameState[row-j][col+j]?.color == "white"){
                    possibleMoves.add(Pair(row-j, col+j))
                    break
                }else if (gameState[row-j][col+j] != null && gameState[row-j][col+j]?.color == "black"){
                    gameState[row-j][col+j]?.isProtected = true
                    break
                }
                possibleMoves.add(Pair(row-j, col+j))
            }
        }
        for (j in 1.until(8)){//handle right backwards diagonal
            if (row + j < 8 && col + j < 8){
                if (gameState[row+j][col+j] != null && gameState[row+j][col+j]?.color == "white"){
                    possibleMoves.add(Pair(row+j, col+j))
                    break
                }else if (gameState[row+j][col+j] != null && gameState[row+j][col+j]?.color == "black"){
                    gameState[row+j][col+j]?.isProtected = true
                    break
                }
                possibleMoves.add(Pair(row+j, col+j))
            }
        }
        for (j in 1.until(8)){//handle left back diagonal
            if (row + j < 8 && col - j >=0){
                if (gameState[row+j][col-j] != null && gameState[row+j][col-j]?.color == "white"){
                    possibleMoves.add(Pair(row+j, col-j))
                    break
                }else if (gameState[row+j][col-j] != null && gameState[row+j][col-j]?.color == "black"){
                    gameState[row+j][col-j]?.isProtected = true
                    break
                }
                possibleMoves.add(Pair(row+j, col-j))
            }
        }
        if (mainActivity.blackInCheck || mainActivity.isBlockingBlack){
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
        newSpot.setImageResource(R.drawable.blackqueen)
        this.row = newRow
        this.col = newCol
        mainActivity.detectCheck(mainActivity.gameState)
        mainActivity.setCheckWarning()
        mainActivity.detectCheckmate()
    }


}