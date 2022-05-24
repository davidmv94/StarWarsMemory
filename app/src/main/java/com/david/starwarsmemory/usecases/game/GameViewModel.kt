package com.david.starwarsmemory.usecases.game

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.david.starwarsmemory.usecases.easy.EasyFragment
import com.david.starwarsmemory.usecases.hard.HardFragment
import com.david.starwarsmemory.usecases.medium.MediumFragment
import com.david.starwarsmemory.R
import com.david.starwarsmemory.util.Constants
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

//Properties
var totalPairs = 0
var pairsLeft = 0
var moves = 0
var gameMode = 0
var timeLeft = 0
var timer: CountDownTimer? = null
lateinit var fragment: Fragment

class GameViewModel : ViewModel() {

    fun setKonfetti(viewKonfetti: KonfettiView){
        viewKonfetti.build()
            .addColors(Color.YELLOW)
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(1000L)
            .addShapes(Shape.Square, Shape.Circle)
            .addSizes(Size(12))
            .setPosition(-50f, viewKonfetti.width + 50f, -50f, -50f)
            .streamFor(300, 2000L)
    }

    fun setGameMode(gameDifficulty : Int){
        when (gameDifficulty) {
            Constants.GameMode.EASY.mode -> {
                totalPairs = Constants.EASY_TOTAL_PAIRS
                pairsLeft = Constants.EASY_TOTAL_PAIRS
                fragment = EasyFragment()
            }
            Constants.GameMode.MEDIUM.mode -> {
                totalPairs = Constants.MEDIUM_TOTAL_PAIRS
                pairsLeft = Constants.MEDIUM_TOTAL_PAIRS
                fragment = MediumFragment()
            }
            Constants.GameMode.HARD.mode -> {
                totalPairs = Constants.HARD_TOTAL_PAIRS
                pairsLeft = Constants.HARD_TOTAL_PAIRS
                fragment = HardFragment()
            }
        }
    }

    fun incrementMoves(buttonToUpdate: Button){
        moves++
        buttonToUpdate.text = moves.toString()
    }

    fun completePair(buttonToUpdate: Button){
        pairsLeft--
        buttonToUpdate.text = pairsLeft.toString()
    }

    fun createTimeOutDialog(activity: GameActivity) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_layout)

        val txtTitle = dialog.findViewById<TextView>(R.id.textViewTitle)
        val txtDifficulty = dialog.findViewById<TextView>(R.id.textViewDifficulty)
        val txtMoves = dialog.findViewById<TextView>(R.id.textViewMoves)
        val txtPairs = dialog.findViewById<TextView>(R.id.textViewTime)
        val btnCancel = dialog.findViewById<Button>(R.id.btnCancel)

        txtTitle.text = "Time Out!"
        txtDifficulty.text = "Difficulty: $gameMode"
        txtMoves.text = "Moves: $moves"
        txtPairs.text = "Pairs: ${totalPairs - pairsLeft}/$totalPairs"

        btnCancel.setOnClickListener {
            activity.onBackPressed()
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()
    }

    fun createWinDialog(activity: GameActivity){
        timer!!.cancel()
        setKonfetti(activity.findViewById(R.id.viewKonfetti) as KonfettiView)

        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_layout)

        val txtDifficulty = dialog.findViewById<TextView>(R.id.textViewDifficulty)
        val txtMoves = dialog.findViewById<TextView>(R.id.textViewMoves)
        val txtTime = dialog.findViewById<TextView>(R.id.textViewTime)
        val btnCancel = dialog.findViewById<Button>(R.id.btnCancel)

        txtDifficulty.text = "Difficulty: $gameMode"
        txtMoves.text = "Moves: $moves"
        txtTime.text = "Time left: $timeLeft"

        btnCancel.setOnClickListener {
            activity.onBackPressed()
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()
    }

    fun startCountdown(activity: GameActivity, buttonTimer : Button){
        timer = object : CountDownTimer(61000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timeLeft = (millisUntilFinished / 1000).toInt()
                buttonTimer.text = timeLeft.toString()
            }

            override fun onFinish() {
                timer!!.cancel()
                createTimeOutDialog(activity)
            }
        }

        timer!!.start()
    }

}