package com.tmosest.androidflckr

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View

private const val TAG = "BaseActivity"
internal const val FLICKER_QUERY = "FLICKER_QUERY"
internal const val PHOTO_TRASFER = "PHOTO_TRASFER"

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    // function for activating the toolbar
    internal fun activateToolBar(enabledHome: Boolean) {
        Log.d(TAG, ".activateToolBar")

        var toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(enabledHome)
    }
}
