package com.example.johan.phonebook.adapter

import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.ImageView
import com.squareup.picasso.Picasso;
import android.support.v7.app.AppCompatActivity
import com.example.johan.phonebook.R
import com.squareup.picasso.Callback


class PhoneBookListRecyclerViewViewHolder(val linearLyt: LinearLayout) : RecyclerView.ViewHolder(linearLyt) {
    private val myImageView: ImageView = itemView.findViewById<ImageView>(R.id.imgPhoto)

    fun updateImageWithUrl(url: String?, c:AppCompatActivity) {
     Picasso.with(itemView.context).load(url).into(myImageView,
         object
             : Callback {
             override fun onSuccess() {}
             override fun onError() {}
         }
        )
        }
}