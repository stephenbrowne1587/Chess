package example.com.chess

import android.support.v4.content.ContextCompat
import android.widget.ImageView
import example.com.chess.Pieces.BlackPawn
import example.com.chess.Pieces.WhitePawn

/**
 * Created by steph on 9/14/2017.
 */
abstract class ChessPiece(var mainActivity: MainActivity, var row: Int, var col: Int) {

    abstract val color: String
    abstract var possibleMoves: MutableSet<Pair<Int, Int>>
    var isProtected = false

    fun highlightSelectedSpace(){
        val selectedSpot: ImageView = mainActivity.board.findViewWithTag("overlay:$row-$col") as ImageView
        selectedSpot.setBackgroundResource(R.drawable.border)
    }
    open fun movePiece(newRow: Int, newCol: Int){
        mainActivity.toggleActivePlayer()
        val selectedPiece: ChessPiece? = mainActivity.gameState[row][col]
        val newSpotOrPiece: ChessPiece? = mainActivity.gameState[newRow][newCol]
        if (newSpotOrPiece != null && newSpotOrPiece.color != selectedPiece?.color){
            mainActivity.capturePiece(newSpotOrPiece)
        }
        if (selectedPiece is WhitePawn && newRow == 0){
            mainActivity.showWhitePawnPromoteLayout()
        }else if (selectedPiece is BlackPawn && newRow == 7){
            mainActivity.showBlackPawnPromoteLayout()
        }

        mainActivity.lastMove = Pair(newRow, newCol)




    }

    abstract fun canMove(newRow: Int, newCol: Int): Boolean
    abstract fun refreshPossibleMoves(gameState: Array<Array<ChessPiece?>>)




    abstract fun highlightPossibleMoves()

}