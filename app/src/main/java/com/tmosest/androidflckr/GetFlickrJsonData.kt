package com.tmosest.androidflckr

import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject

private const val TAG = "GetFlickrJsonData"

class GetFlickrJsonData(private val listener: OnDataAvailable): AsyncTask<String, Void, ArrayList<Photo>>() {
    interface OnDataAvailable {
        fun onDataAvailable(data: List<Photo>)
        fun onError(exception: Exception)
    }

    override fun onPostExecute(result: ArrayList<Photo>) {
        Log.d(TAG, "onPostExecute: called")
        listener.onDataAvailable(result)
    }

    override fun doInBackground(vararg params: String?): ArrayList<Photo> {
        Log.d(TAG, "doInBackground: called with ${params[0]}")
        val photoList = ArrayList<Photo>()
        try {
            val jsonData = JSONObject(params[0])
            val itemsArrays = jsonData.getJSONArray("items")
            for (i in 0 until itemsArrays.length()) {
                val jsonPhoto = itemsArrays.getJSONObject(i)
                val title = jsonPhoto.getString("title")
                val author = jsonPhoto.getString("author")
                val authorId = jsonPhoto.getString("author_id")
                val tags = jsonPhoto.getString("tags")

                val jsonMedia = jsonPhoto.getJSONObject("media")
                val photoUrl = jsonMedia.getString("m")
                val link = photoUrl.replaceFirst("_m.jpg", "_b.jpg")

                val photo = Photo(title, author, authorId, link, tags, photoUrl)
                photoList.add(photo)

                Log.d(TAG, "doInBackground: $photo")
            }
        } catch (exception : Exception) {
            exception.printStackTrace()
            Log.e(TAG, "doInBackground: Error processing data ${exception.message}")
            listener.onError(exception)
            // call this cancel method to prevent onPostExecute
            cancel(true)
        }
        Log.d(TAG, "doInBackground: ends")
        return photoList
    }
}
