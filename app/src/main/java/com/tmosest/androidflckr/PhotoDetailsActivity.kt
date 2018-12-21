package com.tmosest.androidflckr

import android.os.Bundle
import android.util.Log
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_photo_details.*

private const val TAG = "PhotoDetailsActivity"

class PhotoDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, ".onCreate: called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)
        activateToolBar(true)

        val photo =  intent.getParcelableExtra<Photo>(PHOTO_TRANSFER)
        tv_author.text = resources.getString(R.string.photo_author_text, "Author:", photo.author)
        tv_title.text = resources.getString(R.string.photo_title_text, photo.title)
        tv_tags.text = resources.getString(R.string.photo_tags_text, photo.tags)

        Picasso.get()
            .load(photo.link)
            .error(R.drawable.baseline_image_black_48)
            .placeholder(R.drawable.baseline_image_black_48)
            .into(iv_photo)

        Log.d(TAG, ".onCreate: done")
    }

}
