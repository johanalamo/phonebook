package com.example.johan.phonebook.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.johan.phonebook.R
import com.example.johan.phonebook.databinding.LayoutDetailinfoListViewHolderBinding
import com.example.johan.phonebook.response.DetailInfo
import kotlinx.android.synthetic.main.layout_detailinfo_list_view_holder.view.*


class DetailInfoRecyclerViewAdapter(
    private val data: ArrayList<DetailInfo>
) : RecyclerView.Adapter<DetailInfoRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<LayoutDetailinfoListViewHolderBinding>(layoutInflater,
            R.layout.layout_detailinfo_list_view_holder, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = data[position]
        holder.binding.info = info
    }

    override fun getItemCount() = data.size

    //internal classes and interfaces
    class ViewHolder(val binding: LayoutDetailinfoListViewHolderBinding): RecyclerView.ViewHolder(binding.root)
}
