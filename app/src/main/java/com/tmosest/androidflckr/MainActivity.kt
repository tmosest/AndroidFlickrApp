package com.tmosest.androidflckr

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.content_main.*

private const val TAG = "MainActivity"

class MainActivity :
    BaseActivity(),
    GetRawData.OnDownloadComplete,
    GetFlickrJsonData.OnDataAvailable,
    RecyclerItemClickListener.OnRecyclerClickListener {

    private val flickrRecyclerViewAdapter = FlickrRecyclerViewAdapter(ArrayList())

    private fun getData(tags : String = "android,oreo") {
        val url = createUrl(searchCriteria = tags)
        val getRawData = GetRawData(this)
        getRawData.execute(url)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activateToolBar(false)

        rcv_photos.layoutManager = LinearLayoutManager(this)
        rcv_photos.addOnItemTouchListener(RecyclerItemClickListener(this, rcv_photos, this))
        rcv_photos.adapter = flickrRecyclerViewAdapter

        getData()

        Log.d(TAG, "onCreate: ends")
    }

    override fun onResume() {
        Log.d(TAG, "onResume: starts")
        super.onResume()

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val queryResult = sharedPrefs.getString(FLICKER_QUERY, "")
        Log.d(TAG, "onResume: queryResult $queryResult")

        if (queryResult != null && queryResult.isNotEmpty()) {
            getData(queryResult)
        }

        Log.d(TAG, "onResume: ends")
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
            R.id.action_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                return true
            }
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

    override fun onItemClick(view: View, position: Int) {
        Toast.makeText(this, "Normal tap at position $position", Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(view: View, position: Int) {
        Log.d(TAG, "onItemLongClick: starts")
        Toast.makeText(this, "Long tap at position $position", Toast.LENGTH_SHORT).show()
        val photo = flickrRecyclerViewAdapter.getPhoto(position)
        if (photo != null) {
            val intent = Intent(this, PhotoDetailsActivity::class.java)
            intent.putExtra(PHOTO_TRANSFER, photo)
            startActivity(intent)
        }
    }
}
