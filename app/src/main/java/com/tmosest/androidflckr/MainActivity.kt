package com.tmosest.androidflckr

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), GetRawData.OnDownloadComplete, GetFlickrJsonData.OnDataAvailable {

    private val flickrRecyclerViewAdapter = FlickrRecyclerViewAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        rcv_photos.layoutManager = LinearLayoutManager(this)
        rcv_photos.adapter = flickrRecyclerViewAdapter

        val url = createUrl()
        val getRawData = GetRawData(this)
        getRawData.execute(url)
        Log.d(TAG, "onCreate: ends")
    }

    private fun createUrl(searchCriteria: String = "oreo,android", lang: String = "en-us", matchAll: Boolean = true) : String {
        // "http://api.flickr.com/services/feeds/photos_public.gne?format=json&tagmode=any&tags=android&nojsoncallback=1"
        return Uri.parse("http://api.flickr.com/services/feeds/photos_public.gne")
        .buildUpon()
        .appendQueryParameter("tags", searchCriteria)
        .appendQueryParameter("tagmode", if (matchAll) "ALL" else "ANY")
        .appendQueryParameter("lang", lang)
        .appendQueryParameter("format", "json")
        .appendQueryParameter("nojsoncallback", "1")
        .build()
        .toString()
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
            val getFlickrJsonData = GetFlickrJsonData(this)
            getFlickrJsonData.execute(data)
        } else {
            Log.d(TAG, "onDownloadComplete: called with status $status and data $data")
        }
    }

    override fun onDataAvailable(data: List<Photo>) {
        Log.d(TAG, "onDataAvailable: called, data is $data")
        flickrRecyclerViewAdapter.loadNewData(data)
        Log.d(TAG, "onDataAvailable: ends")
    }

    override fun onError(exception: Exception) {
        Log.e(TAG, "onError: ${exception.message}")
    }
}
