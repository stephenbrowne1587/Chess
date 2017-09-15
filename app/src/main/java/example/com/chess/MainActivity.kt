package example.com.chess

import android.content.Context
import android.graphics.Color
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.GridLayout
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import example.com.chess.Pieces.*

class MainActivity : AppCompatActivity() {
    lateinit var board: GridLayout
//    internal var gameState = arrayOf(arrayOf<Piece?>(Piece.BLACK_ROOK, Piece.BLACK_KNIGHT, Piece.BLACK_BISHOP, Piece.BLACK_QUEEN, Piece.BLACK_KING, Piece.BLACK_BISHOP, Piece.BLACK_KNIGHT, Piece.BLACK_ROOK),
//                                    arrayOf<Piece?>(Piece.BLACK_PAWN, Piece.BLACK_PAWN, Piece.BLACK_PAWN, Piece.BLACK_PAWN, Piece.BLACK_PAWN, Piece.BLACK_PAWN, Piece.BLACK_PAWN, Piece.BLACK_PAWN),
//                                    arrayOf<Piece?>(null, null, null, null, null, null, null, null),
//                                    arrayOf<Piece?>(null, null, null, null, null, null, null, null),
//                                    arrayOf<Piece ?>(null, null, null, null, null, null, null, null),
//                                    arrayOf<Piece?>(null, null, null, null, null, null, null, null),
//                                    arrayOf<Piece?>(Piece.WHITE_PAWN, Piece.WHITE_PAWN, Piece.WHITE_PAWN, Piece.WHITE_PAWN, Piece.WHITE_PAWN, Piece.WHITE_PAWN, Piece.WHITE_PAWN, Piece.WHITE_PAWN),
//                                    arrayOf<Piece?>(Piece.WHITE_ROOK, Piece.WHITE_KNIGHT, Piece.WHITE_BISHOP, Piece.WHITE_QUEEN, Piece.WHITE_KING, Piece.WHITE_BISHOP, Piece.WHITE_KNIGHT, Piece.WHITE_ROOK))

    internal var gameState = arrayOf(arrayOf<ChessPiece?>(null, null, null, null, null, null, null, null),
                                    arrayOf<ChessPiece?>(null, null, null, null, null, null, null, null),
                                    arrayOf<ChessPiece?>(null, null, null, null, null, null, null, null),
                                    arrayOf<ChessPiece?>(null, null, null, null, null, null, null, null),
                                    arrayOf<ChessPiece?>(null, null, null, null, null, null, null, null),
                                    arrayOf<ChessPiece?>(null, null, null, null, null, null, null, null),
                                    arrayOf<ChessPiece?>(null, null, null, null, null, null, null, null),
                                    arrayOf<ChessPiece?>(null, null, null, null, null, null, null, null))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        board = findViewById(R.id.board) as GridLayout

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        val layoutParams = board.layoutParams
        layoutParams.width = width
        layoutParams.height = width

        board.layoutParams = layoutParams

        createBoard(width)
    }

    fun resetBoard(){
        for (row in 0..7) {
            for (col in 0..7) {
                var  pieceToAdd: ChessPiece? = null
                if (row == 0){
                    when (col){
                        0, 7 -> pieceToAdd = BlackRook(this, row, col)
                        1, 6 -> pieceToAdd = BlackKnight(this, row, col)
                        2, 5 -> pieceToAdd = BlackBishop(this, row, col)
                        3 -> pieceToAdd = BlackQueen(this, row, col)
                        4 -> pieceToAdd = BlackKing(this, row, col)
                    }
                }else if (row == 1){
                    pieceToAdd = BlackPawn(this, row, col)
                }else if (row == 6){
                    pieceToAdd = WhitePawn(this, row, col)
                }else if (row == 7){
                    when (col) {
                        0, 7 -> pieceToAdd = WhiteRook(this, row, col)
                        1, 6 -> pieceToAdd = WhiteKnight(this, row, col)
                        2, 5 -> pieceToAdd = WhiteBishop(this, row, col)
                        3 -> pieceToAdd = WhiteQueen(this, row, col)
                        4 -> pieceToAdd = WhiteKing(this, row, col)
                    }
                }
                gameState[row][col] = pieceToAdd
            }
        }
        placeImages()
    }


    fun placeImages(){
        for (row in 0..7) {
            for (col in 0..7) {
                val space:ImageView = board.findViewWithTag("space:$row-$col") as ImageView
//                val overlay:ImageView = board.findViewWithTag("overlay:$row-$col") as ImageView
//                if (row == 0){
//                    overlay.setImageResource(R.drawable.circle)
//                }

                when (gameState[row][col]){
                    is BlackPawn -> space.setImageResource(R.drawable.blackpawn)


                    is BlackRook -> space.setImageResource(R.drawable.blackpawn)
                    is BlackKnight -> space.setImageResource(R.drawable.blackknight)
                    is BlackBishop -> space.setImageResource(R.drawable.blackbishop)
                    is BlackKing -> space.setImageResource(R.drawable.blackking)
                    is BlackQueen -> space.setImageResource(R.drawable.blackking)
                    is WhitePawn -> space.setImageResource(R.drawable.whitepawn)
                    is WhiteRook -> space.setImageResource(R.drawable.whiterook)
                    is WhiteKnight -> space.setImageResource(R.drawable.whiteknight)
                    is WhiteBishop -> space.setImageResource(R.drawable.whitebishop)
                    is WhiteKing -> space.setImageResource(R.drawable.whiteking)
                    is WhiteQueen -> space.setImageResource(R.drawable.whitequeen)
                }
            }
        }
    }

    private fun createBoard(width: Int) {
        for (row in 0..7) {
            for (col in 0..7) {
                val space = ImageView(this)
                val overlay = ImageView(this)
                val childParams = GridLayout.LayoutParams()
                childParams.width = width / 8
                childParams.height = width / 8

                childParams.rowSpec = GridLayout.spec(row)
                childParams.columnSpec = GridLayout.spec(col)

                space.layoutParams = childParams
                overlay.layoutParams = childParams
                overlay.setPadding(40, 40, 40, 40)
                space.tag = "space:$row-$col"
                overlay.tag = "overlay:$row-$col"
                space.setOnClickListener {
                    unhighlightBoard()
                    if (gameState[row][col] != null){
                        val selectedPiece: ChessPiece = gameState[row][col]!!
                        selectedPiece.mainActivity = this
                        selectedPiece.highlightSelectedSpace()
//                        Toast.makeText(this, "space", Toast.LENGTH_LONG).show()
                    }
                }
//                overlay.setOnClickListener {
//                    Toast.makeText(this, "overlay", Toast.LENGTH_LONG).show()
//                }
                if (row % 2 == 0) {
                    if (col % 2 == 0) {
                        space.setBackgroundColor(Color.GRAY)
                    } else {
                        space.setBackgroundColor(Color.CYAN)
                    }
                } else {
                    if (col % 2 == 0) {
                        space.setBackgroundColor(Color.CYAN)
                    } else {
                        space.setBackgroundColor(Color.GRAY
                        )
                    }
                }
                board.addView(space)
                board.addView(overlay)
            }
        }
        resetBoard()
    }
    fun unhighlightBoard(){
        for (i in 0..7){
            for (j in 0..7){
                val space: ImageView = board.findViewWithTag("space:$i-$j") as ImageView
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        space.setBackgroundColor(Color.GRAY)
                    } else {
                        space.setBackgroundColor(Color.CYAN)
                    }
                } else {
                    if (j % 2 == 0) {
                        space.setBackgroundColor(Color.CYAN)
                    } else {
                        space.setBackgroundColor(Color.GRAY
                        )
                    }
                }
            }
        }
    }
}

