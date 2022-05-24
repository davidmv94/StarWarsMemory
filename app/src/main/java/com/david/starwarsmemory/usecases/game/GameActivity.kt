package com.david.starwarsmemory.usecases.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.david.starwarsmemory.*
import com.david.starwarsmemory.databinding.ActivityGameBinding
import com.david.starwarsmemory.usecases.OnFragmentActionsListener

private lateinit var binding: ActivityGameBinding
private lateinit var viewModel : GameViewModel

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
    }

    private fun setup(){
        setGameMode()
        binding.imageViewBack.setOnClickListener { onBackPressed() }
        binding.buttonPairsLeft.text = pairsLeft.toString()
    }

    private fun setGameMode() {
        gameMode = intent.getIntExtra("GameMode", 0)
        viewModel.setGameMode(gameMode)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    //Overrides

    override fun onBackPressed() {
        moves = 0
        timeLeft = 0
        if(timer != null) timer!!.cancel()
        super.onBackPressed()
    }

    override fun completePair() {
        viewModel.completePair(binding.buttonPairsLeft)
    }

    override fun incrementMoves() {
        viewModel.incrementMoves(binding.buttonMoves)
    }

    override fun createWinDialog() {
        viewModel.createWinDialog(this)
    }

    override fun startCountdown() {
        viewModel.startCountdown(this, binding.buttonTimer)
    }
}