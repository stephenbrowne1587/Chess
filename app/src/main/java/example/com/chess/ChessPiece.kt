package example.com.chess

import android.support.v4.content.ContextCompat
import android.widget.ImageView

/**
 * Created by steph on 9/14/2017.
 */
abstract class ChessPiece(var mainActivity: MainActivity, var row: Int, var col: Int) {

    fun highlightSelectedSpace(){
        val selectedSpot: ImageView = mainActivity.board.findViewWithTag("space:$row-$col") as ImageView
//        selectedSpot.setBackgroundColor(ContextCompat.getColor(mainActivity, R.color.selectedPieceColor))
        selectedSpot.setBackgroundResource(R.drawable.border)

    }
}