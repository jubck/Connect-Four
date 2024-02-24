package com.hfad.connectfour

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import android.util.Log


class ConnectFour : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val userSelectedSquares = mutableSetOf<Int>()
    private val computerSelectedSquares = mutableSetOf<Int>()
    private val selectedSquares = mutableSetOf<Int>()
    private var gameActive = true // Flag to track if the game is active
    private var playerTurn = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_connect_four, container, false)

        val resetButton = view.findViewById<Button>(R.id.resetButton)
        resetButton?.isEnabled = false

        sharedViewModel.text.observe(viewLifecycleOwner) { name ->
            view.findViewById<TextView>(R.id.greetingText)?.text = "Hello, $name!"
        }

        val buttonsIds = listOf(
            R.id.imageButton0,
            R.id.imageButton1,
            R.id.imageButton2,
            R.id.imageButton3,
            R.id.imageButton4,
            R.id.imageButton5,
            R.id.imageButton6,
            R.id.imageButton7,
            R.id.imageButton8,
            R.id.imageButton9,
            R.id.imageButton10,
            R.id.imageButton11,
            R.id.imageButton12,
            R.id.imageButton13,
            R.id.imageButton14,
            R.id.imageButton15,
            R.id.imageButton16,
            R.id.imageButton17,
            R.id.imageButton18,
            R.id.imageButton19,
            R.id.imageButton20,
            R.id.imageButton21,
            R.id.imageButton22,
            R.id.imageButton23,
            R.id.imageButton24,
            R.id.imageButton25,
            R.id.imageButton26,
            R.id.imageButton27,
            R.id.imageButton28,
            R.id.imageButton29,
            R.id.imageButton30,
            R.id.imageButton31,
            R.id.imageButton32,
            R.id.imageButton33,
            R.id.imageButton34,
            R.id.imageButton35
        ) // Add all your button IDs here
        buttonsIds.forEach { buttonId ->
            view.findViewById<ImageButton>(buttonId)?.setOnClickListener { buttonView ->
                if (gameActive && buttonId !in selectedSquares) {
                    buttonView.setBackgroundResource(R.drawable.button_outline_red)
                    userSelectedSquares.add(buttonId)
                    selectedSquares.add(buttonId)
                    if (checkForWin(userSelectedSquares)) {
                        gameActive = false
                        setButtonsClickable(view, false)
                        resetButton.isEnabled = true
                    } else {
                        setButtonsClickable(
                            view,
                            false
                        ) // Disable user interaction before computer's move
                        computerMove(buttonsIds, view) {
                            setButtonsClickable(view, gameActive)
                        }
                    }
                }
            }
        }


        view.findViewById<Button>(R.id.resetButton)?.setOnClickListener {
            buttonsIds.forEach { buttonIds ->
                view.findViewById<ImageButton>(buttonIds)
                    ?.setBackgroundResource(R.drawable.button_outline)
            }
            userSelectedSquares.clear()
            computerSelectedSquares.clear()
            selectedSquares.clear()
            gameActive = true
            view.findViewById<Button>(R.id.resetButton)?.isEnabled = false
            setButtonsClickable(view, true) // Re-enable buttons for a new game
        }

        return view
    }

    private fun setButtonsClickable(view: View, clickable: Boolean) {
        val buttonsIds = listOf(
            R.id.imageButton0,
            R.id.imageButton34,
            R.id.imageButton1,
            R.id.imageButton2,
            R.id.imageButton3,
            R.id.imageButton4,
            R.id.imageButton5,
            R.id.imageButton6,
            R.id.imageButton7,
            R.id.imageButton8,
            R.id.imageButton9,
            R.id.imageButton10,
            R.id.imageButton11,
            R.id.imageButton12,
            R.id.imageButton18,
            R.id.imageButton14,
            R.id.imageButton15,
            R.id.imageButton16,
            R.id.imageButton17,
            R.id.imageButton18,
            R.id.imageButton18,
            R.id.imageButton14,
            R.id.imageButton15,
            R.id.imageButton16,
            R.id.imageButton17,
            R.id.imageButton14,
            R.id.imageButton30,
            R.id.imageButton29,
            R.id.imageButton28,
            R.id.imageButton27,
            R.id.imageButton34,
            R.id.imageButton34,
            R.id.imageButton29,
            R.id.imageButton28,
            R.id.imageButton27,
            R.id.imageButton34
        ) // List of your button IDs again
        buttonsIds.forEach { buttonId ->
            view.findViewById<ImageButton>(buttonId)?.isEnabled = clickable
        }
    }

    private fun computerMove(buttonsIds: List<Int>, view: View, onMoveCompleted: () -> Unit) {
        if (!gameActive) return
        playerTurn = false
        updateTurnStatus()

        // Simulate computer's move with a delay
        Handler(Looper.getMainLooper()).postDelayed({
            val availableSquares = buttonsIds.filterNot { it in selectedSquares }
            if (availableSquares.isNotEmpty()) {
                val randomSquareId = availableSquares.random()
                activity?.runOnUiThread {
                    view.findViewById<ImageButton>(randomSquareId)?.let { button ->
                        button.setBackgroundResource(R.drawable.button_outline_blue)
                        computerSelectedSquares.add(randomSquareId)
                        selectedSquares.add(randomSquareId)
                    }

                    if (checkForWin(computerSelectedSquares)) {
                        activity?.runOnUiThread {
                            gameActive = false
                            setButtonsClickable(view, false)
                        }
                    } else {

                        playerTurn = true
                        updateTurnStatus()
                        onMoveCompleted()
                    }
                }
            }
        }, 2000) // Delay for 2 seconds to simulate computer thinking
    }

    private fun updateTurnStatus(winner: String? = null) {
        val turnStatusTextView = view?.findViewById<TextView>(R.id.turnStatus)
        turnStatusTextView?.text = when (winner) {
            "player" -> "Player Wins"
            "computer" -> "Computer Wins"
            null -> if (playerTurn) "Player's Turn" else "Computer's Turn"
            else -> "Draw"
        }
    }

    private fun checkForWin(selectedButtonIds: Set<Int>): Boolean {
        val winPatterns = listOf(
            // Horizontal lines
            listOf(R.id.imageButton0, R.id.imageButton1, R.id.imageButton2, R.id.imageButton3),
            listOf(R.id.imageButton1, R.id.imageButton2, R.id.imageButton3, R.id.imageButton4),
            listOf(R.id.imageButton2, R.id.imageButton3, R.id.imageButton4, R.id.imageButton5),
            listOf(R.id.imageButton6, R.id.imageButton7, R.id.imageButton8, R.id.imageButton9),
            listOf(R.id.imageButton7, R.id.imageButton8, R.id.imageButton9, R.id.imageButton10),
            listOf(R.id.imageButton8, R.id.imageButton9, R.id.imageButton10, R.id.imageButton11),
            listOf(R.id.imageButton12, R.id.imageButton13, R.id.imageButton14, R.id.imageButton15),
            listOf(R.id.imageButton13, R.id.imageButton14, R.id.imageButton15, R.id.imageButton16),
            listOf(R.id.imageButton14, R.id.imageButton15, R.id.imageButton16, R.id.imageButton17),
            listOf(R.id.imageButton18, R.id.imageButton19, R.id.imageButton20, R.id.imageButton21),
            listOf(R.id.imageButton19, R.id.imageButton20, R.id.imageButton21, R.id.imageButton22),
            listOf(R.id.imageButton20, R.id.imageButton21, R.id.imageButton22, R.id.imageButton23),
            listOf(R.id.imageButton24, R.id.imageButton25, R.id.imageButton26, R.id.imageButton27),
            listOf(R.id.imageButton25, R.id.imageButton26, R.id.imageButton27, R.id.imageButton28),
            listOf(R.id.imageButton26, R.id.imageButton27, R.id.imageButton28, R.id.imageButton29),
            listOf(R.id.imageButton30, R.id.imageButton31, R.id.imageButton32, R.id.imageButton33),
            listOf(R.id.imageButton31, R.id.imageButton32, R.id.imageButton33, R.id.imageButton34),
            listOf(R.id.imageButton32, R.id.imageButton33, R.id.imageButton34, R.id.imageButton35),
            // Vertical lines
            listOf(R.id.imageButton0, R.id.imageButton6, R.id.imageButton12, R.id.imageButton18),
            listOf(R.id.imageButton6, R.id.imageButton12, R.id.imageButton18, R.id.imageButton24),
            listOf(R.id.imageButton12, R.id.imageButton18, R.id.imageButton24, R.id.imageButton30),
            listOf(R.id.imageButton1, R.id.imageButton7, R.id.imageButton13, R.id.imageButton19),
            listOf(R.id.imageButton7, R.id.imageButton13, R.id.imageButton19, R.id.imageButton25),
            listOf(R.id.imageButton13, R.id.imageButton19, R.id.imageButton25, R.id.imageButton31),
            listOf(R.id.imageButton2, R.id.imageButton8, R.id.imageButton14, R.id.imageButton20),
            listOf(R.id.imageButton8, R.id.imageButton14, R.id.imageButton20, R.id.imageButton26),
            listOf(R.id.imageButton14, R.id.imageButton20, R.id.imageButton26, R.id.imageButton32),
            listOf(R.id.imageButton3, R.id.imageButton9, R.id.imageButton15, R.id.imageButton21),
            listOf(R.id.imageButton9, R.id.imageButton15, R.id.imageButton21, R.id.imageButton27),
            listOf(R.id.imageButton15, R.id.imageButton21, R.id.imageButton27, R.id.imageButton33),
            listOf(R.id.imageButton4, R.id.imageButton10, R.id.imageButton16, R.id.imageButton22),
            listOf(R.id.imageButton10, R.id.imageButton16, R.id.imageButton22, R.id.imageButton28),
            listOf(R.id.imageButton16, R.id.imageButton22, R.id.imageButton28, R.id.imageButton34),
            listOf(R.id.imageButton5, R.id.imageButton11, R.id.imageButton17, R.id.imageButton23),
            listOf(R.id.imageButton11, R.id.imageButton17, R.id.imageButton23, R.id.imageButton29),
            listOf(R.id.imageButton17, R.id.imageButton23, R.id.imageButton29, R.id.imageButton35),
            // Diagonal lines (down-right)
            listOf(R.id.imageButton0, R.id.imageButton7, R.id.imageButton14, R.id.imageButton21),
            listOf(R.id.imageButton1, R.id.imageButton8, R.id.imageButton15, R.id.imageButton22),
            listOf(R.id.imageButton2, R.id.imageButton9, R.id.imageButton16, R.id.imageButton23),
            listOf(R.id.imageButton6, R.id.imageButton13, R.id.imageButton20, R.id.imageButton27),
            listOf(R.id.imageButton7, R.id.imageButton14, R.id.imageButton21, R.id.imageButton28),
            listOf(R.id.imageButton8, R.id.imageButton15, R.id.imageButton22, R.id.imageButton29),
            listOf(R.id.imageButton12, R.id.imageButton19, R.id.imageButton26, R.id.imageButton33),
            listOf(R.id.imageButton13, R.id.imageButton20, R.id.imageButton27, R.id.imageButton34),
            listOf(R.id.imageButton14, R.id.imageButton21, R.id.imageButton28, R.id.imageButton35),
            // Diagonal lines (down-left)
            listOf(R.id.imageButton3, R.id.imageButton8, R.id.imageButton13, R.id.imageButton18),
            listOf(R.id.imageButton4, R.id.imageButton9, R.id.imageButton14, R.id.imageButton19),
            listOf(R.id.imageButton5, R.id.imageButton10, R.id.imageButton15, R.id.imageButton20),
            listOf(R.id.imageButton9, R.id.imageButton14, R.id.imageButton19, R.id.imageButton24),
            listOf(R.id.imageButton10, R.id.imageButton15, R.id.imageButton20, R.id.imageButton25),
            listOf(R.id.imageButton11, R.id.imageButton16, R.id.imageButton21, R.id.imageButton26),
            listOf(R.id.imageButton15, R.id.imageButton20, R.id.imageButton25, R.id.imageButton30),
            listOf(R.id.imageButton16, R.id.imageButton21, R.id.imageButton26, R.id.imageButton31),
            listOf(R.id.imageButton17, R.id.imageButton22, R.id.imageButton27, R.id.imageButton32)
        )
        val winDetected = winPatterns.any { pattern -> selectedButtonIds.containsAll(pattern) }

        if (winDetected) {
            val winner =
                if (userSelectedSquares.containsAll(selectedButtonIds)) "player" else "computer"
            updateTurnStatus(winner)
            gameActive = false
            view?.findViewById<Button>(R.id.resetButton)?.isEnabled = true

        }

        return winDetected
    }
}