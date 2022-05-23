package com.david.starwarsmemory.usecases.game

import android.content.Context
import android.graphics.Color
import android.os.CountDownTimer
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.david.starwarsmemory.EasyFragment
import com.david.starwarsmemory.HardFragment
import com.david.starwarsmemory.MediumFragment
import com.david.starwarsmemory.util.Constants
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

//Properties
var totalPairsB = 0
var pairsLeftB = 0
var movesB = 0
var gameModeB = 0
var timeLeftB = 0
var timerB: CountDownTimer? = null
lateinit var fragmentB: Fragment

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

    fun setGameMode(gameMode : Int){
        when (gameMode) {
            Constants.GameMode.EASY.mode -> {
                totalPairsB = Constants.EASY_TOTAL_PAIRS
                pairsLeftB = Constants.EASY_TOTAL_PAIRS
                fragmentB = EasyFragment()
            }
            Constants.GameMode.MEDIUM.mode -> {
                totalPairsB = Constants.MEDIUM_TOTAL_PAIRS
                pairsLeftB = Constants.MEDIUM_TOTAL_PAIRS
                fragmentB = MediumFragment()
            }
            Constants.GameMode.HARD.mode -> {
                totalPairsB = Constants.HARD_TOTAL_PAIRS
                pairsLeftB = Constants.HARD_TOTAL_PAIRS
                fragmentB = HardFragment()
            }
        }
    }
}