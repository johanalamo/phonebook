package com.example.johan.garbarino.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ImageView
import com.squareup.picasso.Picasso;
import android.support.v7.app.AppCompatActivity
import com.example.johan.garbarino.response.Image
import com.example.johan.garbarino.R
import com.squareup.picasso.Callback
import kotlinx.android.synthetic.main.layout_product_image_list_recycler_view.view.*

import com.example.johan.garbarino.FakeData

class ProductImageListAdapter(private val data: Array<Image>, private val context:AppCompatActivity) :
    RecyclerView.Adapter<ProductImageListAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val linearLyt: LinearLayout) : RecyclerView.ViewHolder(linearLyt){
        private val myImageView: ImageView = itemView.findViewById<ImageView>(R.id.imgListImageProduct)
        fun updateImageWithUrl(url: String, c:AppCompatActivity) = Picasso.with(itemView.context).load(url).into(myImageView,
            object
                : Callback {
                override fun onSuccess() { }
                override fun onError() {
                    linearLyt.imgListImageProduct.setImageDrawable(FakeData.getFakeDrawableFor(c, url))
                    println("*********************************************ListImage: error on internet connection, loaded from local: " + url)
                }
            })
    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        // create a new view
        val linearLyt = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_product_image_list_recycler_view, parent, false) as LinearLayout
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(linearLyt)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) = holder.updateImageWithUrl("http:" + data[position].url, context)
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size
}

