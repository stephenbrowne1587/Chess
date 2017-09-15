package example.com.chess

import android.graphics.Color
import android.widget.ImageView


//class SelectionHandler(val mainActivity: MainActivity, val row: Int, val col: Int, val piece: Piece) {
//
//    fun highlightSelectedSpace(){
//        val selectedSpot: ImageView = mainActivity.board.findViewWithTag("$row-$col") as ImageView
//        selectedSpot.setBackgroundColor(R.color.selectedPieceColor)
//        when (piece){
//            Piece.WHITE_PAWN -> highlightMovesWhitePawn()
//        }
//
//
//    }
//    fun highlightMovesWhitePawn(){
//        for (i in 1..2){
//            val spot: ImageView = mainActivity.board.findViewWithTag("${row - i}-$col") as ImageView
//            spot.setBackgroundColor(Color.BLUE)
//        }
//    }
//
//}