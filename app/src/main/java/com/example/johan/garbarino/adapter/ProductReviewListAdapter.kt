package com.example.johan.garbarino.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.example.johan.garbarino.R
import com.example.johan.garbarino.response.Review
import kotlinx.android.synthetic.main.layout_product_review_list_recycler_view.view.*


class ProductReviewListAdapter(private val data: ArrayList<Review>, private val context:AppCompatActivity) :
    RecyclerView.Adapter<ProductReviewListAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val linearLyt: LinearLayout) : RecyclerView.ViewHolder(linearLyt){    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val linearLyt = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_product_review_list_recycler_view, parent, false) as LinearLayout
        return MyViewHolder(linearLyt)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.linearLyt.txtNumReview.text  = (position + 1).toString()
        holder.linearLyt.txtReviewStars.text  = data[position].rating.toString()
        holder.linearLyt.rtbarProductReview.rating = data[position].rating!!

        holder.linearLyt.txtReviewDate.text  = data[position].submissionTime?.subSequence(0,10)
        if ((data[position].title == null) or (data[position].title == ""))
            holder.linearLyt.txtReviewTitle.text  = data[position].title
        else
            holder.linearLyt.txtReviewTitle.visibility = TextView.GONE
        if ((data[position].reviewText == null) or (data[position].reviewText == ""))
            holder.linearLyt.txtReviewDescription.visibility  = TextView.GONE
        else
            holder.linearLyt.txtReviewDescription.text  = data[position].reviewText
        holder.linearLyt.txtReviewNickname.text  = "Por: " + data[position].userNickname
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size
}
