package com.david.starwarsmemory.usecases

interface OnFragmentActionsListener {
    fun completePair()
    fun incrementMoves()
    fun createWinDialog()
    fun startCountdown()
}