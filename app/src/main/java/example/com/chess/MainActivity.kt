package example.com.chess

import android.content.Context
import android.graphics.Color
import android.graphics.Color.WHITE
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
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
    lateinit var whiteCaptured: GridLayout
    lateinit var blackCaptured: GridLayout

    internal var gameState = arrayOf(arrayOf<ChessPiece?>(null, null, null, null, null, null, null, null),
                                    arrayOf<ChessPiece?>(null, null, null, null, null, null, null, null),
                                    arrayOf<ChessPiece?>(null, null, null, null, null, null, null, null),
                                    arrayOf<ChessPiece?>(null, null, null, null, null, null, null, null),
                                    arrayOf<ChessPiece?>(null, null, null, null, null, null, null, null),
                                    arrayOf<ChessPiece?>(null, null, null, null, null, null, null, null),
                                    arrayOf<ChessPiece?>(null, null, null, null, null, null, null, null),
                                    arrayOf<ChessPiece?>(null, null, null, null, null, null, null, null))

    var selectedSpot: Pair<Int, Int>? = null
    var activePlayer: String = WHITE
    val capturedWhite: ArrayList<ChessPiece?> = arrayListOf()
    val capturedBlack: ArrayList<ChessPiece?> = arrayListOf()

    companion object {
        const val BLACK = "black"
        const val WHITE = "white"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val density = this.getResources().getDisplayMetrics().density

        board = findViewById(R.id.board) as GridLayout
        whiteCaptured = findViewById(R.id.light_captured_layout) as GridLayout
        blackCaptured = findViewById(R.id.dark_captured_layout) as GridLayout

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
//        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        val boardWidth = width - 50
        val capturedSectionPaddingPX = (10 * density.toInt())
        val capturedSectionWidth = width / 13 * 8
        val capturedSectionHeight = width / 13 * 2

        val layoutParams = board.layoutParams
        layoutParams.width = boardWidth
        layoutParams.height = boardWidth
        board.layoutParams = layoutParams

        val capturedWhiteLayoutParams = whiteCaptured.layoutParams
        capturedWhiteLayoutParams.width = capturedSectionWidth + capturedSectionPaddingPX
        capturedWhiteLayoutParams.height = capturedSectionHeight + capturedSectionPaddingPX

        val capturedBlackLayoutParams = blackCaptured.layoutParams
        capturedBlackLayoutParams.width = capturedSectionWidth + capturedSectionPaddingPX
        capturedBlackLayoutParams.height = capturedSectionHeight + capturedSectionPaddingPX



        whiteCaptured.layoutParams = capturedWhiteLayoutParams
        blackCaptured.layoutParams = capturedBlackLayoutParams

        createCapturedSections(capturedSectionWidth)
        createBoard(boardWidth)

    }

    fun toggleActivePlayer(){
        if (activePlayer == WHITE){
            activePlayer = BLACK
        }else{
            activePlayer = WHITE
        }
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
                    is BlackRook -> space.setImageResource(R.drawable.blackrook)
                    is BlackKnight -> space.setImageResource(R.drawable.blackknight)
                    is BlackBishop -> space.setImageResource(R.drawable.blackbishop)
                    is BlackKing -> space.setImageResource(R.drawable.blackking)
                    is BlackQueen -> space.setImageResource(R.drawable.blackqueen)
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
    fun selectPiece(row: Int, col: Int){
        val selectedPiece: ChessPiece = gameState[row][col]!!
        selectedSpot = Pair(row, col)
        selectedPiece.mainActivity = this
        selectedPiece.highlightPossibleMoves()
        if (selectedPiece.possibleMoves.isNotEmpty()){//only allow a piece to be selected if it can move somewhere
            selectedPiece.highlightSelectedSpace()
        }else{
            selectedSpot = null
        }
    }
    fun capturePiece(piece: ChessPiece){
        Log.i("piece captured", piece.color + " piece captured")
        if (piece.color == WHITE){
            val row = if (capturedWhite.size >= 8) 1 else 0
            val col = capturedWhite.size % 8
            capturedWhite.add(piece)
            val spot = whiteCaptured.findViewWithTag("light:$row-$col") as ImageView
            when (piece){
                is WhitePawn -> spot.setImageResource(R.drawable.whitepawn)
                is WhiteRook -> spot.setImageResource(R.drawable.whiterook)
                is WhiteKnight -> spot.setImageResource(R.drawable.whiteknight)
                is WhiteBishop -> spot.setImageResource(R.drawable.whitebishop)
                is WhiteKing -> spot.setImageResource(R.drawable.whiteking)
                is WhiteQueen -> spot.setImageResource(R.drawable.whitequeen)
            }
        }else{
            val row = if (capturedBlack.size >= 8) 1 else 0
            val col = capturedBlack.size % 8
            capturedBlack.add(piece)
            val spot = blackCaptured.findViewWithTag("dark:$row-$col") as ImageView
            when (piece){
                is BlackPawn -> spot.setImageResource(R.drawable.blackpawn)
                is BlackRook -> spot.setImageResource(R.drawable.blackrook)
                is BlackKnight -> spot.setImageResource(R.drawable.blackknight)
                is BlackBishop -> spot.setImageResource(R.drawable.blackbishop)
                is BlackKing -> spot.setImageResource(R.drawable.blackking)
                is BlackQueen -> spot.setImageResource(R.drawable.blackqueen)
            }
        }

    }
    private fun createCapturedSections(width: Int){
        for (row in 0..1) {
            for (col in 0..7) {
                val lightSpot = ImageView(this)
                val darkSpot = ImageView(this)
                val childParams = GridLayout.LayoutParams()
                childParams.width = width / 8
                childParams.height = width / 8

                childParams.rowSpec = GridLayout.spec(row)
                childParams.columnSpec = GridLayout.spec(col)

                lightSpot.layoutParams = childParams
                darkSpot.layoutParams = childParams

                lightSpot.tag = "light:$row-$col"
                darkSpot.tag = "dark:$row-$col"

                whiteCaptured.addView(lightSpot)
                blackCaptured.addView(darkSpot)
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
                    if (selectedSpot == null){//If there is no selected spot
                        if (gameState[row][col] != null && gameState[row][col]?.color == activePlayer){//If the user taps on a spot with a piece
                            selectPiece(row, col)
                        }
                    }else{
                        val selectedPiece: ChessPiece = gameState[selectedSpot!!.first][selectedSpot!!.second]!!
                        if (selectedSpot == Pair(row, col)){//If the user tapped on the spot that was already selected
                            selectedSpot = null
                        }else if (gameState[row][col]?.color == selectedPiece.color){//If the user wants to switch selection to another piece
                            selectPiece(row, col)
                        }else if(selectedPiece.canMove(row, col)){
                            selectedPiece.movePiece(row, col)
                            selectedSpot = null
                        }else{//The user tapped an empty spot that is not a valid move.  Unselecte piece
                            selectedSpot = null
                        }
                    }

                }
//                overlay.setOnClickListener {
//                    Toast.makeText(this, "overlay", Toast.LENGTH_LONG).show()
//                }
                if (row % 2 == 0) {
                    if (col % 2 == 0) {
                        space.setBackgroundResource(R.drawable.light_square)
                    } else {
                        space.setBackgroundResource(R.drawable.dark_square)
                    }
                } else {
                    if (col % 2 == 0) {
                        space.setBackgroundResource(R.drawable.dark_square)
                    } else {
                        space.setBackgroundResource(R.drawable.light_square)
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
                val overlay: ImageView = board.findViewWithTag("overlay:$i-$j") as ImageView
                overlay.setBackgroundResource(R.drawable.blank)
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        space.setBackgroundResource(R.drawable.light_square)

                    } else {
                        space.setBackgroundResource(R.drawable.dark_square)
                    }
                } else {
                    if (j % 2 == 0) {
                        space.setBackgroundResource(R.drawable.dark_square)
                    } else {
                        space.setBackgroundResource(R.drawable.light_square)

                    }
                }
            }
        }
    }
}

