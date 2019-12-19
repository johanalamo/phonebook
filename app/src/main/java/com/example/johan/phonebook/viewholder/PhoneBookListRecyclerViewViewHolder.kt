package com.example.johan.phonebook.viewholder

import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.johan.phonebook.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class PhoneBookListRecyclerViewViewHolder(val linearLyt: LinearLayout) :
    RecyclerView.ViewHolder(linearLyt) {
    private val myImageView: ImageView = itemView.findViewById<ImageView>(R.id.imgPhoto)

    fun updateImageWithUrl(url: String?) {
        Picasso.with(itemView.context).load(url).into(myImageView,
            object
                : Callback {
                override fun onSuccess() {}
                override fun onError() {}
            }
        )
    }
}
