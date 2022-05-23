package com.david.starwarsmemory.usecases.game

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.david.starwarsmemory.*
import com.david.starwarsmemory.databinding.ActivityGameBinding
import com.david.starwarsmemory.util.Constants
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import java.util.*

private lateinit var binding: ActivityGameBinding
private lateinit var viewModel : GameViewModel

private var totalPairs = 0
private var pairsLeft = 0
private var moves = 0
private var gameMode = 0
private var timeLeft = 0
private var timer: CountDownTimer? = null
private lateinit var fragment: Fragment

class GameActivity : AppCompatActivity(), OnFragmentActionsListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)

        //Content
        setContentView(binding.root)

        //ViewModel
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        //Setup
        setup()


        binding.buttonPairsLeft.text = pairsLeft.toString()
    }

    private fun setup(){
        setGameMode()
        setBackButton()
    }

    private fun setGameMode() {
        gameMode = intent.getIntExtra("GameMode", 0)

        when (gameMode) {
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

        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    private fun setBackButton() {
        binding.imageViewBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun createTimeOutDialog() {
        val dialog = Dialog(this)
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
            onBackPressed()
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()
    }

    //Overrides

    override fun onBackPressed() {
        moves = 0
        timeLeft = 0
        if(timer != null) timer!!.cancel()
        super.onBackPressed()
    }

    override fun completePair() {
        pairsLeft--
        binding.buttonPairsLeft.text = pairsLeft.toString()
    }

    override fun incrementMoves() {
        moves++
        binding.buttonMoves.text = moves.toString()
    }

    override fun createWinDialog() {
        timer!!.cancel()
        viewModel.setKonfetti(binding.viewKonfetti)

        val dialog = Dialog(this)
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
            onBackPressed()
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()
    }

    override fun startCountdown() {
        timer = object : CountDownTimer(61000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timeLeft = (millisUntilFinished / 1000).toInt()
                binding.buttonTimer.text = timeLeft.toString()
            }

            override fun onFinish() {
                timer!!.cancel()
                createTimeOutDialog()
            }
        }

        timer!!.start()
    }
}