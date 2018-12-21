package com.tmosest.androidflckr

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MotionEvent
import android.view.View

private const val TAG = "RecyclerItemClickList"

class RecyclerItemClickListener(context: Context, recyclerView: RecyclerView, private val listener : OnRecyclerClickListener) :
    RecyclerView.SimpleOnItemTouchListener() {

    interface OnRecyclerClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        Log.d(TAG, "onInterceptTouchEvent: view $rv with event $e")
        return super.onInterceptTouchEvent(rv, e)
    }
}
