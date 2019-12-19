package com.example.johan.phonebook.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.johan.phonebook.R
import com.example.johan.phonebook.response.PhoneBookListResponse
import com.example.johan.phonebook.response.Phonebook
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_phonebook_list_recycler_view.view.*

class PhoneBookListAdapter(
    private val dataMap: PhoneBookListResponse,
    private val clickListener: PhoneBookListAdapter.ClickListener
) :
    RecyclerView.Adapter<PhoneBookListAdapter.ViewHolder>() {

    private val data = ArrayList(dataMap.values)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhoneBookListAdapter.ViewHolder {
        val linearLyt = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_phonebook_list_recycler_view, parent, false) as LinearLayout
        return PhoneBookListAdapter.ViewHolder(linearLyt)
    }

    override fun onBindViewHolder(holder: PhoneBookListAdapter.ViewHolder, position: Int) {
        holder.updateImageWithUrl(data[position].smallImageURL)
        holder.linearLyt.txtName.text = data[position].name
        holder.linearLyt.txtCompanyName.text = data[position].companyName
        if (data[position].isFavorite!!)
            holder.linearLyt.imgStart.visibility = LinearLayout.VISIBLE
        else
            holder.linearLyt.imgStart.visibility = LinearLayout.INVISIBLE
        holder.linearLyt.setOnClickListener {
            clickListener.listItemClicked(data[position])
        }
    }

    override fun getItemCount() = data.size


    //Internal classes and interfaces

    class ViewHolder(val linearLyt: LinearLayout) :
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

    interface ClickListener {
        fun listItemClicked(phonebook: Phonebook)
    }

}
