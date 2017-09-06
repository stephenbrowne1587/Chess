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

class MainActivity : AppCompatActivity() {
    lateinit var board: GridLayout
    internal var gameState = arrayOf(arrayOf<Piece?>(Piece.BLACK_ROOK, Piece.BLACK_KNIGHT, Piece.BLACK_BISHOP, Piece.BLACK_QUEEN, Piece.BLACK_KING, Piece.BLACK_BISHOP, Piece.BLACK_KNIGHT, Piece.BLACK_ROOK),
                                    arrayOf<Piece?>(Piece.BLACK_PAWN, Piece.BLACK_PAWN, Piece.BLACK_PAWN, Piece.BLACK_PAWN, Piece.BLACK_PAWN, Piece.BLACK_PAWN, Piece.BLACK_PAWN, Piece.BLACK_PAWN),
                                    arrayOf<Piece?>(null, null, null, null, null, null, null, null),
                                    arrayOf<Piece?>(null, null, null, null, null, null, null, null),
                                    arrayOf<Piece ?>(null, null, null, null, null, null, null, null),
                                    arrayOf<Piece?>(null, null, null, null, null, null, null, null),
                                    arrayOf<Piece?>(Piece.WHITE_PAWN, Piece.WHITE_PAWN, Piece.WHITE_PAWN, Piece.WHITE_PAWN, Piece.WHITE_PAWN, Piece.WHITE_PAWN, Piece.WHITE_PAWN, Piece.WHITE_PAWN),
                                    arrayOf<Piece?>(Piece.WHITE_ROOK, Piece.WHITE_KNIGHT, Piece.WHITE_BISHOP, Piece.WHITE_QUEEN, Piece.WHITE_KING, Piece.WHITE_BISHOP, Piece.WHITE_KNIGHT, Piece.WHITE_ROOK))

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

    private fun createBoard(width: Int) {
        for (row in 0..7) {
            for (col in 0..7) {
                val rowVal = row
                val colVal = col
                val space = ImageView(this)
                val childParams = GridLayout.LayoutParams()
                childParams.width = width / 8
                childParams.height = width / 8

                childParams.rowSpec = GridLayout.spec(row)
                childParams.columnSpec = GridLayout.spec(col)

                if (gameState[row][col] != null) {
                    space.setImageResource(getImage(row, col))

                }
                space.layoutParams = childParams
                space.setOnClickListener { Toast.makeText(this@MainActivity, rowVal.toString() + "  " + colVal, Toast.LENGTH_SHORT).show() }
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
            }
        }
    }

    fun getImage(row: Int, col: Int): Int {
        var image = R.drawable.blackking
        if (gameState[row][col] != null) {
            val piece = gameState[row][col]
            when (piece) {
                Piece.BLACK_KING -> image = R.drawable.blackking
                Piece.BLACK_QUEEN -> image = R.drawable.blackqueen
                Piece.BLACK_BISHOP -> image = R.drawable.blackbishop
                Piece.BLACK_KNIGHT -> image = R.drawable.blackknight
                Piece.BLACK_ROOK -> image = R.drawable.blackrook
                Piece.BLACK_PAWN -> image = R.drawable.blackpawn
                Piece.WHITE_KING -> image = R.drawable.whiteking
                Piece.WHITE_QUEEN -> image = R.drawable.whitequeen
                Piece.WHITE_BISHOP -> image = R.drawable.whitebishop
                Piece.WHITE_KNIGHT -> image = R.drawable.whiteknight
                Piece.WHITE_ROOK -> image = R.drawable.whiterook
                Piece.WHITE_PAWN -> image = R.drawable.whitepawn
            }
        }
        return image
    }
}

