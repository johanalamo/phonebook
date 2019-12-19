package com.example.johan.phonebook.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.layout_phonebook_list_recycler_view.view.*
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.example.johan.phonebook.response.PhoneBookListResponse
import com.example.johan.phonebook.PhoneBookDetailsActivity
import com.example.johan.phonebook.R
import com.example.johan.phonebook.listener.PhoneBookListRecyclerViewClickListener
import com.example.johan.phonebook.viewholder.PhoneBookListRecyclerViewViewHolder

class PhoneBookListAdapter(private val dataMap: PhoneBookListResponse, private val clickListener:PhoneBookListRecyclerViewClickListener) :
    RecyclerView.Adapter<PhoneBookListRecyclerViewViewHolder>() {
    private val data = ArrayList(dataMap.values)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneBookListRecyclerViewViewHolder {
        // create a new view
        val linearLyt = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_phonebook_list_recycler_view, parent, false) as LinearLayout
        // set the view's size, margins, paddings and layout parameters
        return PhoneBookListRecyclerViewViewHolder(linearLyt)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: PhoneBookListRecyclerViewViewHolder, position: Int) {
        holder.updateImageWithUrl(data[position].smallImageURL)
//        holder.linearLyt.imgPhoto.setImageDrawable(context.getDrawable(R.drawable.img32))
        holder.linearLyt.txtName.text  = data[position].name
        holder.linearLyt.txtCompanyName.text        = data[position].companyName
        //programacion evento click
        if (data[position].isFavorite!!)
            holder.linearLyt.imgStart.visibility = LinearLayout.VISIBLE
        else
            holder.linearLyt.imgStart.visibility = LinearLayout.INVISIBLE

        holder.linearLyt.setOnClickListener {
            clickListener.listItemClicked(data[position])
        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size
}
