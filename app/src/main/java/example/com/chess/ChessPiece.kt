package example.com.chess

import android.support.v4.content.ContextCompat
import android.widget.ImageView

/**
 * Created by steph on 9/14/2017.
 */
abstract class ChessPiece(var mainActivity: MainActivity, var row: Int, var col: Int) {

    abstract val color: String
    abstract var possibleMoves: MutableSet<Pair<Int, Int>>

    fun highlightSelectedSpace(){
        val selectedSpot: ImageView = mainActivity.board.findViewWithTag("overlay:$row-$col") as ImageView
        selectedSpot.setBackgroundResource(R.drawable.border)
    }
    abstract fun movePiece(newRow: Int, newCol: Int)

    abstract fun canMove(newRow: Int, newCol: Int): Boolean




    abstract fun highlightPossibleMoves()

}