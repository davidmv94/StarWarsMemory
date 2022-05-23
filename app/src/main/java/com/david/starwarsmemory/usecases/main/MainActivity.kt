package com.david.starwarsmemory.usecases.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.david.starwarsmemory.databinding.ActivityMainBinding
import com.david.starwarsmemory.util.Constants
import com.david.starwarsmemory.util.extensions.changeActivity

private lateinit var binding : ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtons()
    }

    private fun setButtons() {
        binding.buttonEasy.setOnClickListener { changeActivity(Constants.GameMode.EASY.mode) }
        binding.buttonMedium.setOnClickListener { changeActivity(Constants.GameMode.MEDIUM.mode) }
        binding.buttonHard.setOnClickListener { changeActivity(Constants.GameMode.HARD.mode) }
    }
}