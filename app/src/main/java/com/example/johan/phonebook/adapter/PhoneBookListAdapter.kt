package com.example.johan.phonebook.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.layout_phonebook_list_recycler_view.view.*
import android.widget.ImageView
import com.squareup.picasso.Picasso;
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.example.johan.phonebook.response.PhoneBookListResponse
import com.example.johan.phonebook.PhoneBookDetailsActivity
import com.example.johan.phonebook.R
import com.squareup.picasso.Callback

class PhoneBookListAdapter(private val dataMap: PhoneBookListResponse, private val context:AppCompatActivity) :
    RecyclerView.Adapter<PhoneBookListAdapter.ViewHolder>() {
    private val data = ArrayList(dataMap.values)
    class ViewHolder(val linearLyt: LinearLayout) : RecyclerView.ViewHolder(linearLyt) {
        private val myImageView: ImageView = itemView.findViewById<ImageView>(R.id.imgProduct)

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
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create a new view
        val linearLyt = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_phonebook_list_recycler_view, parent, false) as LinearLayout
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(linearLyt)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateImageWithUrl(data[position].smallImageURL, context)
//        holder.linearLyt.imgProduct.setImageDrawable(context.getDrawable(R.drawable.img32))
        holder.linearLyt.txtName.text  = data[position].name
        holder.linearLyt.txtCompanyName.text        = data[position].companyName
        //programacion evento click
        if (data[position].isFavorite!!)
            holder.linearLyt.imgStart.visibility = LinearLayout.VISIBLE
        else
            holder.linearLyt.imgStart.visibility = LinearLayout.INVISIBLE
            
        holder.linearLyt.setOnClickListener({
            val intent: Intent = Intent(this.context, PhoneBookDetailsActivity::class.java)
            intent.putExtra("p_id", data[position].id)
            this.context.startActivity(intent)
        })
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size
}
