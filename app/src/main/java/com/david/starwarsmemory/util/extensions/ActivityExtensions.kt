package com.david.starwarsmemory.util.extensions

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import com.david.starwarsmemory.GameActivity


fun Activity.changeActivity(gameMode: Int) {
    val intent = Intent(this, GameActivity::class.java)
    intent.putExtra("GameMode", gameMode)
    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
}