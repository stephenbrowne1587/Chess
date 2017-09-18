package example.com.chess.Pieces

import example.com.chess.ChessPiece
import example.com.chess.MainActivity

/**
 * Created by steph on 9/14/2017.
 */
class BlackRook(mainActivity: MainActivity, row: Int, col: Int) : ChessPiece(mainActivity, row, col){
    override val color: String
        get() = "black"
    override var possibleMoves: MutableSet<Pair<Int, Int>> = mutableSetOf()

    override fun canMove(newRow: Int, newCol: Int): Boolean{
        return  possibleMoves.contains(Pair(newRow, newCol))
    }
    override fun highlightPossibleMoves(){

    }

    override fun movePiece(newRow: Int, newCol: Int) {

    }


}