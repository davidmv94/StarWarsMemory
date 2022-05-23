package com.david.starwarsmemory.util

import com.david.starwarsmemory.R

object Constants{


    enum class GameMode(val mode : Int){
        EASY(1),
        MEDIUM(2),
        HARD(3)
    }

    //EASY
    const val EASY_TOTAL_CARDS = 16
    const val EASY_TOTAL_PAIRS = 8

    //MEDIUM
    const val MEDIUM_TOTAL_CARDS = 24
    const val MEDIUM_TOTAL_PAIRS = 12

    //HARD
    const val HARD_TOTAL_CARDS = 30
    const val HARD_TOTAL_PAIRS = 15

    //IMAGES
    enum class Images(val image : Int){
        //Easy
        LUKE(R.drawable.luke),
        LEIA(R.drawable.leia),
        DARTHVADER(R.drawable.darthvader),
        YODA(R.drawable.yoda),
        C3PO(R.drawable.c3po),
        R2D2(R.drawable.r2d2),
        BOBAFETT(R.drawable.bobbafet),
        STORM(R.drawable.storm1),
        //Medium
        STORM2(R.drawable.storm2),
        ANAKIN(R.drawable.anakin),
        OBIWAN(R.drawable.obiwan),
        LIGHTSABER(R.drawable.saber),
        //Hard
        STORM3(R.drawable.storm3),
        KYLOREN(R.drawable.kylo),
        XWING(R.drawable.xwing)
    }

    //DELAYS
    const val CARD_DELAY : Long = 100
    const val FAIL_DELAY : Long = 800
}

