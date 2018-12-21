package com.tmosest.androidflckr

import android.os.Bundle
import android.util.Log

private const val TAG = "SearchActivity"

class SearchActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, ".onCreate: called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        activateToolBar(true)
        Log.d(TAG, ".onCreate: done")
    }

}
