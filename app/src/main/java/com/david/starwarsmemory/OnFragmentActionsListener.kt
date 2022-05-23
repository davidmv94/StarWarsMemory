package com.david.starwarsmemory

import androidx.fragment.app.Fragment

interface OnFragmentActionsListener {
    fun completePair()
    fun incrementMoves()
    fun createWinDialog()
    fun startCountdown()
}