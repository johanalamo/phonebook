package com.example.johan.garbarino

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Paint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.layout_product_details.*
import kotlinx.android.synthetic.main.layout_product_details.*
import android.os.SystemClock
import com.example.johan.garbarino.adapter.ProductReviewListAdapter
import com.example.johan.garbarino.response.Image
import com.example.johan.garbarino.response.ProductDetailsResponse
import com.example.johan.garbarino.response.ProductReviewsResponse
import com.example.johan.garbarino.response.Review
import com.example.johan.garbarino.response.*
import com.example.johan.garbarino.viewmodel.ProductDetailsViewModel
import com.example.johan.garbarino.viewmodel.ProductReviewsViewModel

import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Callback

class ProductDetailsActivity : AppCompatActivity() {
    private var productId: String = ""

    private lateinit var recyclerViewImage:RecyclerView
    private lateinit var viewAdapterImage: RecyclerView.Adapter<*>
    private lateinit var viewManagerImage: RecyclerView.LayoutManager

    private lateinit var recyclerViewReview:RecyclerView
    private lateinit var viewAdapterReview: RecyclerView.Adapter<*>
    private lateinit var viewManagerReview: RecyclerView.LayoutManager

    private lateinit var productDetailsViewModel: ProductDetailsViewModel
    private lateinit var productReviewsViewModel: ProductReviewsViewModel

    private lateinit var person: Product


    private lateinit var myImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.slide_up, R.anim.slide_off)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_details_activity)

        try {
            this.productId = getIntent().getExtras().getString("p_id")
        } catch (e: Exception) {
            this.productId = "1"
        }
        println("================ProductDetailsActivity.productId = " + this.productId)

        DataRepository.viewModelPersonList.getProductList().observe(this,
                 Observer {
                    productList -> chargePerson(productList!!)
                    }
        )

//        person = DataRepository.viewModelPersonList.getProductList().get(this.productId);

        //println("******************* name: ************  " + person.toString())
//        loadProductDetailsViewModel()
//        loadProductReviewsViewModel()

    }

    private fun chargePerson(dataMap:ProductListResponse){

              val data = ArrayList(dataMap.values)
              val person = dataMap.get(this.productId)
              println(data)
              showDetailsOnUi(person)
              var extra: ArrayList<Review> = getExtraData(person!!)
              createRecyclerViewReviewList(extra)

              var url = person.largeImageURL


               myImageView = findViewById<ImageView>(R.id.imgPersonLarge)
              Picasso.with(this).load(url).into(myImageView)


    }

    private fun getExtraData(p:Product): ArrayList<Review>{
      var r: Review = Review ()

      var extra:ArrayList<Review> = ArrayList()


          if (p.phone!!.home!! != null){
            r = Review()
            r.fieldName = "PHONE"
            r.extraInfo = "Home"
            r.value = p.phone!!.home
            extra.add(r)
          }

          if (p.phone!!.mobile!! != null){
            r = Review()
            r.fieldName = "PHONE"
            r.extraInfo = "Mobile"
            r.value = p.phone!!.mobile
            extra.add(r)
          }

          if (p.phone!!.work!! != null){
            r = Review()
            r.fieldName = "PHONE"
            r.extraInfo = "Work"
            r.value = p.phone!!.work
            extra.add(r)
          }

          if (p.address!! != null){
            r = Review()
            r.fieldName = "ADDRESS"
            r.extraInfo = ""
            r.value = p.address!!.street + "\n" + p.address!!.city + ", " + p.address!!.state + " " + p.address!!.zipCode +  ", " + p.address!!.country +
            extra.add(r)
          }
          if (p.birthdate!! != null){
            r = Review()
            r.fieldName = "BIRTHDATE"
            r.extraInfo = ""
            r.value = p.birthdate
            extra.add(r)
          }

//emailAddress

      return extra

    }

    private fun loadProductReviewsViewModel() {
        println("===============================entro en productdetailsactivity.loadproduVM")
        productReviewsViewModel = ViewModelProviders.of(this).get(ProductReviewsViewModel::class.java)
        productReviewsViewModel.getProductDetails().observe(this,
            Observer { productReviews ->
                println("===============================entro en productdetailsactivity.loadproduVM.observer")
                showReviewsOnUI(productReviews!!)
                createRecyclerViewReviewList(productReviewsViewModel.getReviewList(ConfigApp.commentsToShow))
            }
        )
        productReviewsViewModel.loadProductReviewsData(this.productId)
    }

    fun showReviewsOnUI(res: ProductReviewsResponse) {
        var max:Float = res!!.items!![0]!!.reviewStatistics!!.average!!
        var showAnimationStarsThread:ShowAnimationStarsThread = ShowAnimationStarsThread(this, max)
        showAnimationStarsThread.start()
    }

    fun showDetailsOnUi(res: Product?) {
        txtDescription.text = res!!.name!!
        txtPrice.text = res.companyName

  /*      if (res.discount == 0)
            lytDiscount.visibility = LinearLayout.GONE
        else {*/
            txtListPrice.text = "$ " + res.name
//            txtDiscount.text = res.discount.toString() + "% OFF"
            txtListPrice.setPaintFlags(txtListPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        //}
//        createRecyclerViewImageList(res.resources!!.images)
    }

    fun createRecyclerViewReviewList(data:ArrayList<Review>){
        viewManagerReview = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewAdapterReview = ProductReviewListAdapter(data, this)
        recyclerViewReview = findViewById <RecyclerView>(R.id.rviewProductListReviews).apply {
            setHasFixedSize(false);
            layoutManager = viewManagerReview
            adapter = viewAdapterReview
        }
    }
}

class ShowAnimationStarsThread(p_caller:AppCompatActivity, maxPoints:Float): Thread(){
    var max:Float = maxPoints
    var caller:AppCompatActivity = p_caller
    val animationDuration:Float = 1000.toFloat()   //miliseguntos totales de la animación
    val framesPerSecond:Float = 60.toFloat()
    override fun run() {
        super.run()
        var totalFrames = animationDuration / 1000.toFloat() * framesPerSecond

        var sumByInterval: Float = max / totalFrames
        var interval = animationDuration / totalFrames

        for (i in 1..totalFrames.toInt()) {
            SystemClock.sleep(interval.toLong())
            caller.runOnUiThread {
                caller.txtEstrellas.text = String.format("%.2f", (sumByInterval * i))
                caller.rtbarProductDetails.rating = (sumByInterval * i)
            }
        }
    }
}
