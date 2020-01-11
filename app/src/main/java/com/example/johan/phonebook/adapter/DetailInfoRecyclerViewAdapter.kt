package com.example.johan.phonebook.adapter

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.johan.phonebook.R
import com.example.johan.phonebook.response.DetailInfo
import kotlinx.android.synthetic.main.layout_detailinfo_list_recycler_view.view.*


class DetailInfoRecyclerViewAdapter(
    private val data: ArrayList<DetailInfo>
) : RecyclerView.Adapter<DetailInfoRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_detailinfo_list_recycler_view,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.view.txtTitle.text = data[position].fieldName
        holder.view.txtExtra.text = data[position].extraInfo
        holder.view.txtValue.text = data[position].value
    }

    override fun getItemCount() = data.size

    //internal classes and interfaces
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}
