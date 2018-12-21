package com.tmosest.androidflckr

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), GetRawData.OnDownloadComplete {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val getRawData = GetRawData(this)
        // getRawData.setDownloadCompleteListener(this)
        getRawData.execute("http://api.flickr.com/services/feeds/photos_public.gne?format=json&tagmode=any&tags=android&nojsoncallback=1")

        // fab.setOnClickListener { view ->
        //     Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //         .setAction("Action", null).show()
        // }
        Log.d(TAG, "onCreate: ends")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "onCreateOptionsMenu: called")
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected: called")
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDownloadComplete(data: String, status: DownloadStatus) {
        if (status == DownloadStatus.OK) {
            Log.d(TAG, "onDownloadComplete: called with data $data")
        } else {
            Log.d(TAG, "onDownloadComplete: called with status $status and data $data")
        }
    }
}
