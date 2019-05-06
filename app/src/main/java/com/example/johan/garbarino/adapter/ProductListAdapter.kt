package com.example.johan.garbarino.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.layout_product_list_recycler_view.view.*
import android.graphics.*
import android.widget.ImageView
import com.squareup.picasso.Picasso;
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.example.johan.garbarino.response.Product
import com.example.johan.garbarino.ProductDetailsActivity
import com.example.johan.garbarino.R
import com.squareup.picasso.Callback

import com.example.johan.garbarino.FakeData

class ProductListAdapter(private val data: Array<Product>, private val context:AppCompatActivity) :
    RecyclerView.Adapter<ProductListAdapter.MyViewHolder>() {

    class MyViewHolder(val linearLyt: LinearLayout) : RecyclerView.ViewHolder(linearLyt) {
        private val myImageView: ImageView = itemView.findViewById<ImageView>(R.id.imgProduct)
        
        fun updateImageWithUrl(url: String, c:AppCompatActivity) {
         Picasso.with(itemView.context).load(url).into(myImageView,
             object
                 : Callback {
                 override fun onSuccess() {                 }
                 override fun onError() {
                     linearLyt.imgProduct.setImageDrawable(FakeData.getFakeDrawableFor(c, url))
                     println("********************error en la carga: " + url)
                 }
             }
            )
            }
    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // create a new view
        val linearLyt = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_product_list_recycler_view, parent, false) as LinearLayout
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(linearLyt)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.updateImageWithUrl("http:" + data[position].image_url, context)
//        holder.linearLyt.imgProduct.setImageDrawable(context.getDrawable(R.drawable.img32))
        holder.linearLyt.txtDescription.text  = data[position].description
        holder.linearLyt.txtPrice.text        = "$ " + data[position].price.toString()
        if (data[position].discount == 0)
            holder.linearLyt.lytDiscount.visibility = LinearLayout.GONE
        else {
            holder.linearLyt.txtListPrice.text = "$ " + data[position].list_price.toString()
            holder.linearLyt.txtDiscount.text = data[position].discount.toString() + "% OFF"
            holder.linearLyt.txtListPrice.setPaintFlags(holder.linearLyt.txtListPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        }
        //programacion evento click
        holder.linearLyt.setOnClickListener({
            val intent: Intent = Intent(this.context, ProductDetailsActivity::class.java)
            intent.putExtra("p_product_id", data[position].id)
            this.context.startActivity(intent)
        })
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size
}
