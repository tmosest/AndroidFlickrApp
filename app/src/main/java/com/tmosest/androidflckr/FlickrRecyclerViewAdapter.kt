package com.tmosest.androidflckr

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

private const val TAG = "FlickrRecyclerViewAdapt"

class FlickrImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var thumbnail : ImageView = view.findViewById(R.id.iv_thumbnail)
    var title : TextView = view.findViewById(R.id.tv_title)
}

class FlickrRecyclerViewAdapter(private var photoList: List<Photo>) : RecyclerView.Adapter<FlickrImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, index: Int): FlickrImageViewHolder {
        // Called by layout manager when it needs a new view
        Log.d(TAG, "onCreateViewHolder: called")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_browse_photo, parent, false)
        return FlickrImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onBindViewHolder(viewHolder: FlickrImageViewHolder, index: Int) {
        // Called by the layout manager when it wants new data in an existing view
        val photoItem = photoList[index]

        Picasso.get()
            .load(photoItem.image)
            .placeholder(R.drawable.baseline_image_black_48)
            .error(R.drawable.baseline_image_black_48)
            .into(viewHolder.thumbnail)

        viewHolder.title.text = photoItem.title
    }

    fun loadNewData(newPhotoList: List<Photo>) {
        photoList = newPhotoList
        notifyDataSetChanged()
    }

    fun getPhoto(position: Int) : Photo ? {
        return if (photoList.isEmpty()) null else photoList[position]
    }
}
