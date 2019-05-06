package com.example.johan.garbarino

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.arch.lifecycle.Observer
import  com.example.johan.garbarino.viewmodel.ProductListViewModel
import android.arch.lifecycle.ViewModelProviders
import android.widget.Toast
import com.example.johan.garbarino.adapter.ProductListAdapter
import com.example.johan.garbarino.response.Product

class ProductListActivity : AppCompatActivity() {

   private lateinit var recyclerView:RecyclerView
   private lateinit var viewAdapter: RecyclerView.Adapter<*>
   private lateinit var viewManager: RecyclerView.LayoutManager

   private lateinit var viewModel:ProductListViewModel

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.layout_main_activity)

      viewModel = ViewModelProviders.of(this).get(ProductListViewModel::class.java)
      viewModel.getProductList().observe(this,
               Observer { 
                  productList -> createRecyclerViewProductList(productList!!.items)
                  }
      )
      viewModel.loadProductListData()
   }

   fun createRecyclerViewProductList(data:Array<Product>){
      viewManager = GridLayoutManager(this, 2) //LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
      viewAdapter = ProductListAdapter(data, this)
      recyclerView = findViewById <RecyclerView>(R.id.rviewProducts).apply {
         setHasFixedSize(false);
         layoutManager = viewManager
         adapter = viewAdapter
      }
   }
}
