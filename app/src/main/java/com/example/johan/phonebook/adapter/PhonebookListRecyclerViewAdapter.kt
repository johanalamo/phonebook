package com.example.johan.phonebook.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.johan.phonebook.R
import com.example.johan.phonebook.databinding.LayoutPhonebookListViewHolderBinding
import com.example.johan.phonebook.response.PhoneBookListResponse
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_phonebook_list_view_holder.view.*

class PhonebookListRecyclerViewAdapter(
    private val dataMap: PhoneBookListResponse,
    private val clickListener: ClickListener
) :
    RecyclerView.Adapter<PhonebookListRecyclerViewAdapter.ViewHolder>() {

    private val data = ArrayList(dataMap.values)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layourInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<LayoutPhonebookListViewHolderBinding>(layourInflater,
            R.layout.layout_phonebook_list_view_holder, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhonebookListRecyclerViewAdapter.ViewHolder, position: Int) {
        val person = data[position]
        holder.binding.person = person

        Picasso.get().load(person.smallImageURL).into(holder.binding.root.imgPhoto)

        val isFavorite = person.isFavorite ?: false
        if (isFavorite) {
            holder.binding.root.imgStart.visibility = LinearLayout.VISIBLE
        } else {
            holder.binding.root.imgStart.visibility = LinearLayout.INVISIBLE
        }
        holder.binding.root.itemCardView.setOnClickListener {
            clickListener.listItemClicked(data[position].id!! )
        }
    }

    override fun getItemCount() = data.size


    //Internal classes and interfaces

    class ViewHolder(val binding: LayoutPhonebookListViewHolderBinding): RecyclerView.ViewHolder(binding.root)

    interface ClickListener {
        fun listItemClicked(personId: String)
    }

}
