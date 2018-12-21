package com.tmosest.androidflckr

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

private const val TAG = "GetRawData"

enum class DownloadStatus {
    OK, IDLE, NOT_INITIALIZED, FAILED_OR_EMPTY, PERMISSIONS_ERROR, ERROR
}

class GetRawData(private val listener: OnDownloadComplete) : AsyncTask<String, Void, String>() {
    private var downloadStatus = DownloadStatus.IDLE

    interface OnDownloadComplete {
        fun onDownloadComplete(data: String, status: DownloadStatus)
    }

    // private var listener: MainActivity? = null

    // fun setDownloadCompleteListener(callbackObject: MainActivity) {
    //     listener = callbackObject
    // }

    override fun onPostExecute(result: String) {
        Log.d(TAG, "onPostExecute: called, with $result")
        listener?.onDownloadComplete(result, downloadStatus)
    }

    override fun doInBackground(vararg params: String?): String {
        if (null == params[0]) {
            downloadStatus = DownloadStatus.NOT_INITIALIZED
            return "No URL specified"
        }

        try {
            downloadStatus = DownloadStatus.OK
            return URL(params[0]).readText()
        } catch (exception : Exception) {
            val errorMessage = when(exception) {
                is MalformedURLException -> {
                    downloadStatus = DownloadStatus.NOT_INITIALIZED
                    "doInBackground: Invalid URL ${exception.message}"
                }
                is IOException -> {
                    downloadStatus = DownloadStatus.NOT_INITIALIZED
                    "doInBackground: IOException ${exception.message}"
                }
                is SecurityException -> {
                    downloadStatus = DownloadStatus.PERMISSIONS_ERROR
                    "doInBackground: SecurityException ${exception.message}"
                }
                else -> {
                    downloadStatus = DownloadStatus.ERROR
                    "Unknown error: ${exception.message}"
                }
            }
            Log.e(TAG, errorMessage)
            return errorMessage
        }
    }
}
