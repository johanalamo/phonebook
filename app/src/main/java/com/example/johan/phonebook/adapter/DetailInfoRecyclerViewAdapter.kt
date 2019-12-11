package com.example.johan.phonebook.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.support.v7.app.AppCompatActivity
import com.example.johan.phonebook.R
import com.example.johan.phonebook.response.DetailInfo
import kotlinx.android.synthetic.main.layout_detailinfo_list_recycler_view.view.*


class DetailInfoRecyclerViewAdapter(private val data: ArrayList<DetailInfo>, private val context:AppCompatActivity) :
    RecyclerView.Adapter<DetailInfoRecyclerViewViewHolder>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): DetailInfoRecyclerViewViewHolder {
        val linearLyt = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_detailinfo_list_recycler_view, parent, false) as LinearLayout
        return DetailInfoRecyclerViewViewHolder(linearLyt)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: DetailInfoRecyclerViewViewHolder, position: Int) {
        holder.linearLyt.txtTitle.text  = data[position].fieldName
        holder.linearLyt.txtExtra.text  = data[position].extraInfo
        holder.linearLyt.txtValue.text  = data[position].value
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size
}
