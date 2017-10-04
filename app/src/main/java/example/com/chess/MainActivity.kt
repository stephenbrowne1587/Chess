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
import android.widget.*
import example.com.chess.Pieces.*
import java.lang.reflect.Array.get

class MainActivity : AppCompatActivity() {
    lateinit var board: GridLayout
    lateinit var whiteCaptured: GridLayout
    lateinit var blackCaptured: GridLayout
    lateinit var whitePawnPromoteLayout: GridLayout
    lateinit var blackPawnPromoteLayout: GridLayout
    lateinit var whiteCheckTextView: TextView
    lateinit var blackCheckTextView: TextView

    internal var gameState: Array<Array<ChessPiece?>> = Array(8, {Array<ChessPiece?>(8, {i -> null})}) // 2D array initialized with all null values.


    var selectedSpot: Pair<Int, Int>? = null
    var lastMove: Pair<Int, Int>? = null
    var activePlayer: String = WHITE
    var pawnPromoteLayoutVisible = false
    var whiteInCheck = false
    var blackInCheck = false
    var isBlockingBlack = false
    var isBlockingWhite = false
    var blockSpots: MutableSet<Pair<Int, Int>> = mutableSetOf()

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
        whitePawnPromoteLayout = findViewById(R.id.whitePawnPromoteLayout) as GridLayout
        blackPawnPromoteLayout = findViewById(R.id.blackPawnPromoteLayout) as GridLayout
        whiteCheckTextView = findViewById(R.id.light_check_textview) as TextView
        blackCheckTextView = findViewById(R.id.dark_check_textview) as TextView

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

        createWhitePawnPromoteLayout(boardWidth, whitePawnPromoteLayout, capturedSectionPaddingPX)
        createBlackPawnPromoteLayout(boardWidth, blackPawnPromoteLayout, capturedSectionPaddingPX)

        whiteCaptured.layoutParams = capturedWhiteLayoutParams
        blackCaptured.layoutParams = capturedBlackLayoutParams

        createCapturedSections(capturedSectionWidth)
        createBoard(boardWidth)

    }

    fun createWhitePawnPromoteLayout(boardWidth: Int, promoteLayout: GridLayout, capturedSetionPaddingPX:Int){
        val layoutParams: RelativeLayout.LayoutParams = whitePawnPromoteLayout.layoutParams as RelativeLayout.LayoutParams
        layoutParams.width = boardWidth / 4 * 3 + capturedSetionPaddingPX
        layoutParams.height = boardWidth / 6 + capturedSetionPaddingPX
        layoutParams.setMargins(0, 0, 0, boardWidth / 32 * 13)
        promoteLayout.setPadding(13,10,10,10)

        for (col in 0.until(4)){
            val whiteImage = ImageView(this)
            whiteImage.setPadding(15,15,15,15)
            val imageLayoutParams = GridLayout.LayoutParams()
            imageLayoutParams.rowSpec = GridLayout.spec(0)
            imageLayoutParams.columnSpec = GridLayout.spec(col)
            when(col){
                0 -> {
                    whiteImage.setImageResource(R.drawable.whitequeen)
                    whiteImage.setTag("whitequeen")
                }
                1 -> {
                    whiteImage.setImageResource(R.drawable.whitebishop)
                    whiteImage.setTag("whitebishop")
                }
                2 -> {
                    whiteImage.setImageResource(R.drawable.whiterook)
                    whiteImage.setTag("whiterook")
                }
                3 -> {
                    whiteImage.setImageResource(R.drawable.whiteknight)
                    whiteImage.setTag("whiteknight")
                }
            }
            imageLayoutParams.width = boardWidth / 16 * 3
            imageLayoutParams.height = boardWidth / 16 * 3
            whiteImage.layoutParams = imageLayoutParams
            whiteImage.setOnClickListener {
                when(whiteImage.tag){
                    "whitequeen" -> promotePawn(WhiteQueen(this, lastMove!!.first, lastMove!!.second))
                    "whitebishop" -> promotePawn(WhiteBishop(this, lastMove!!.first, lastMove!!.second))
                    "whiterook" -> promotePawn(WhiteRook(this, lastMove!!.first, lastMove!!.second))
                    "whiteknight" -> promotePawn(WhiteKnight(this, lastMove!!.first, lastMove!!.second))
                }
            }
            promoteLayout.addView(whiteImage)
        }
    }

    fun createBlackPawnPromoteLayout(boardWidth: Int, promoteLayout: GridLayout, capturedSetionPaddingPX:Int){
        val layoutParams: RelativeLayout.LayoutParams = blackPawnPromoteLayout.layoutParams as RelativeLayout.LayoutParams
        layoutParams.width = boardWidth / 4 * 3 + capturedSetionPaddingPX
        layoutParams.height = boardWidth / 6 + capturedSetionPaddingPX
        layoutParams.setMargins(0, 0, 0, boardWidth / 32 * 13)
        promoteLayout.setPadding(13,10,10,10)

        for (col in 0.until(4)){
            val blackImage = ImageView(this)
            blackImage.setPadding(15,15,15,15)
            val imageLayoutParams = GridLayout.LayoutParams()
            imageLayoutParams.rowSpec = GridLayout.spec(0)
            imageLayoutParams.columnSpec = GridLayout.spec(col)
            when(col){
                0 -> {
                    blackImage.setImageResource(R.drawable.blackqueen)
                    blackImage.setTag("blackqueen")
                }
                1 -> {
                    blackImage.setImageResource(R.drawable.blackbishop)
                    blackImage.setTag("blackbishop")
                }
                2 -> {
                    blackImage.setImageResource(R.drawable.blackrook)
                    blackImage.setTag("blackrook")
                }
                3 -> {
                    blackImage.setImageResource(R.drawable.blackknight)
                    blackImage.setTag("blackknight")
                }
            }
            imageLayoutParams.width = boardWidth / 16 * 3
            imageLayoutParams.height = boardWidth / 16 * 3
            blackImage.layoutParams = imageLayoutParams
            blackImage.setOnClickListener {
                when(blackImage.tag){
                    "blackqueen" -> promotePawn(BlackQueen(this, lastMove!!.first, lastMove!!.second))
                    "blackbishop" -> promotePawn(BlackBishop(this, lastMove!!.first, lastMove!!.second))
                    "blackrook" -> promotePawn(BlackRook(this, lastMove!!.first, lastMove!!.second))
                    "blackknight" -> promotePawn(BlackKnight(this, lastMove!!.first, lastMove!!.second))
                }
            }
            promoteLayout.addView(blackImage)
        }
    }
    fun promotePawn(piece: ChessPiece){
        gameState[piece.row][piece.col] = piece
        val space:ImageView = board.findViewWithTag("space:${piece.row}-${piece.col}") as ImageView
        when(piece){
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
        blackPawnPromoteLayout.translationX = -2000f
        whitePawnPromoteLayout.translationX = -2000f
        pawnPromoteLayoutVisible = false
        detectCheck(gameState)
        setCheckWarning()
    }
    fun showWhitePawnPromoteLayout(){
        pawnPromoteLayoutVisible = true
        whitePawnPromoteLayout.translationX = 0f
    }
    fun showBlackPawnPromoteLayout(){
        pawnPromoteLayoutVisible = true
        blackPawnPromoteLayout.translationX = 0f
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
        selectedPiece.refreshPossibleMoves(gameState)
        if (selectedPiece.possibleMoves.isNotEmpty()){//only allow a piece to be selected if it can move somewhere
            selectedPiece.highlightPossibleMoves()
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
                    isBlockingBlack = false
                    isBlockingWhite = false

                    val attackerAndKingPairThreat: Pair<ChessPiece, ChessPiece>? =  detectThreat(gameState, Pair(row, col)) // check if the king is in any pieces possible moves if the selected piece were removed.
                    val attackerAndKingPair: Pair<ChessPiece, ChessPiece>? =  detectCheck(gameState)// check if a king is in any pieces set of possible moves
                    if (attackerAndKingPairThreat != null && attackerAndKingPairThreat.second is WhiteKing){
                        isBlockingWhite = true
                        val attacker = attackerAndKingPairThreat.first
                        val king = attackerAndKingPairThreat.second
                        blockSpots = getBlockSpots(Pair(attacker.row, attacker.col), Pair(king.row, king.col))
                    }else if (attackerAndKingPairThreat != null && attackerAndKingPairThreat.second is BlackKing){
                        isBlockingBlack = true
                        val attacker = attackerAndKingPairThreat.first
                        val king = attackerAndKingPairThreat.second
                        blockSpots = getBlockSpots(Pair(attacker.row, attacker.col), Pair(king.row, king.col))
                    }
                    if (attackerAndKingPair != null){
                        val attacker = attackerAndKingPair.first
                        val king = attackerAndKingPair.second
                        blockSpots = getBlockSpots(Pair(attacker.row, attacker.col), Pair(king.row, king.col))
                        filterKingInCheckMoves(Pair(king.row, king.col))
                    }
                    if (!pawnPromoteLayoutVisible){// Any condition that would cause the board to be disabled.
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
                    setCheckWarning()
                }

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
    fun setCheckWarning(){
        if (whiteInCheck){
            whiteCheckTextView.visibility = View.VISIBLE
        }else {
            whiteCheckTextView.visibility = View.GONE
        }
        if (blackInCheck){
            blackCheckTextView.visibility = View.VISIBLE
        }else{
            blackCheckTextView.visibility = View.GONE
        }
    }

    fun Array<Array<ChessPiece?>>.copy() = Array(size) { get(it).clone() }

    fun filterKingInCheckMoves(king: Pair<Int, Int>) {
        val thisKing = gameState[king.first][king.second]
        val tempGameState = gameState.copy()
        tempGameState[king.first][king.second] = null
        for (row in gameState) {
            for (piece in row) {
                if (piece != null && piece.color != thisKing?.color) {
                    piece.refreshPossibleMoves(tempGameState)
                    if (piece is WhitePawn || piece is BlackPawn){//do not remove pawns forward move since they cannot attack with it
                        thisKing?.possibleMoves = thisKing?.possibleMoves?.filter { it !in piece.possibleMoves || piece.col == it.second}!!.toMutableSet()
                    }else{
                        thisKing?.possibleMoves = thisKing?.possibleMoves?.filter { it !in piece.possibleMoves }!!.toMutableSet()
                    }

//                    piece.refreshPossibleMoves(gameState)
                }
            }

        }
        println(thisKing?.possibleMoves)
    }


    fun getBlockSpots(attacker: Pair<Int, Int>, king: Pair<Int, Int>): MutableSet<Pair<Int, Int>>{
        val spots: MutableSet<Pair<Int, Int>> = mutableSetOf()
        spots.add(Pair(attacker.first, attacker.second))
        for (j in 1.until(8)){//handle right
            if (attacker.second + j > 7){// check if out of bounds
                break
            }else if (Pair(attacker.first, attacker.second + j) == king){
                return spots
            }else if (gameState[attacker.first][ attacker.second + j] == null){
                spots.add(Pair(attacker.first, attacker.second + j))
            }
        }
        spots.clear()
        spots.add(Pair(attacker.first, attacker.second))
        for (j in 1.until(8)){//handle left
            if (attacker.second - j < 0){// check if out of bounds
                break
            }else if (Pair(attacker.first, attacker.second - j) == king){
                return spots
            }else if (gameState[attacker.first][ attacker.second - j] == null){
                spots.add(Pair(attacker.first, attacker.second - j))
            }
        }
        spots.clear()
        spots.add(Pair(attacker.first, attacker.second))
        for (j in 1.until(8)){//handle forward
            if (attacker.first - j < 0){// check if out of bounds
                break
            }else if (Pair(attacker.first - j, attacker.second) == king){
                return spots
            }else if (gameState[attacker.first - j][ attacker.second] == null){
                spots.add(Pair(attacker.first - j, attacker.second))
            }
        }
        spots.clear()
        spots.add(Pair(attacker.first, attacker.second))
        for (j in 1.until(8)){//handle backward
            if (attacker.first + j > 7){// check if out of bounds
                break
            }else if (Pair(attacker.first + j, attacker.second) == king){
                return spots
            }else if (gameState[attacker.first + j][ attacker.second] == null){
                spots.add(Pair(attacker.first + j, attacker.second))
            }
        }
        spots.clear()
        spots.add(Pair(attacker.first, attacker.second))
        for (j in 1.until(8)){//handle left forward diagonal
            if (attacker.first - j < 0 || attacker.second - j < 0){// check if out of bounds
                break
            }else if (Pair(attacker.first - j, attacker.second - j) == king){
                return spots
            }else if (gameState[attacker.first - j][ attacker.second - j] == null){
                spots.add(Pair(attacker.first - j, attacker.second - j))
            }
        }
        spots.clear()
        spots.add(Pair(attacker.first, attacker.second))
        for (j in 1.until(8)){//handle right forward diagonal
            if (attacker.first - j < 0 || attacker.second + j > 7){// check if out of bounds
                break
            }else if (Pair(attacker.first - j, attacker.second + j) == king){
                return spots
            }else if (gameState[attacker.first - j][ attacker.second + j] == null){
                spots.add(Pair(attacker.first - j, attacker.second + j))
            }
        }
        spots.clear()
        spots.add(Pair(attacker.first, attacker.second))
        for (j in 1.until(8)){//handle right backwards diagonal
            if (attacker.first + j < 0 || attacker.second + j > 7){// check if out of bounds
                break
            }else if (Pair(attacker.first + j, attacker.second + j) == king){
                return spots
            }else if (attacker.first + j < 8 && attacker.second + j < 8 && gameState[attacker.first + j][ attacker.second + j] == null){
                spots.add(Pair(attacker.first + j, attacker.second + j))
            }
        }
        spots.clear()
        spots.add(Pair(attacker.first, attacker.second))
        for (j in 1.until(8)){//handle left backwards diagonal
            if (attacker.first + j < 0 || attacker.second - j < 0){// check if out of bounds
                break
            }else if (Pair(attacker.first + j, attacker.second - j) == king){
                return spots
            }else if (attacker.first + j < 8 && attacker.second - j >= 0 && gameState[attacker.first + j][ attacker.second - j] == null){
                spots.add(Pair(attacker.first + j, attacker.second - j))
            }
        }
        return mutableSetOf(Pair(attacker.first, attacker.second))
    }

    fun detectThreat(gameStateVal: Array<Array<ChessPiece?>>, spot: Pair<Int, Int>):Pair<ChessPiece, ChessPiece>?{
        val tempGameState: Array<Array<ChessPiece?>> = gameStateVal.copy()
        tempGameState[spot.first][spot.second] = null
        for (boardRow in tempGameState){
            for (piece in boardRow){
                if (piece != null){
                    piece.refreshPossibleMoves(tempGameState)
                    for (move in piece.possibleMoves){
                        if (tempGameState[move.first][move.second] != null && tempGameState[move.first][move.second] is WhiteKing){
                            return Pair(piece, tempGameState[move.first][move.second]!!)
                        }else if (tempGameState[move.first][move.second] is BlackKing){
                            return Pair(piece, tempGameState[move.first][move.second]!!)
                        }
                    }
                }
            }
        }
        return null
    }

    fun detectCheck(gameStateVal: Array<Array<ChessPiece?>>): Pair<ChessPiece, ChessPiece>?{// If king is in check, return the attacker and the king.
        for (boardRow in gameStateVal){
            for (piece in boardRow){
                if (piece != null){
                    piece.refreshPossibleMoves(gameStateVal)
                    for (move in piece.possibleMoves){
                        if (gameStateVal[move.first][move.second] != null && gameStateVal[move.first][move.second] is WhiteKing){
                            whiteInCheck = true
                            return Pair(piece, gameStateVal[move.first][move.second]!!)
                        }else if (gameStateVal[move.first][move.second] is BlackKing){
                            blackInCheck = true
                            return Pair(piece, gameStateVal[move.first][move.second]!!)
                        }
                    }
                }
            }
        }
        whiteInCheck = false
        blackInCheck = false
        return null
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

