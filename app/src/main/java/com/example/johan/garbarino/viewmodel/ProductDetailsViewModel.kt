package com.example.johan.garbarino.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import com.example.johan.garbarino.*
import com.example.johan.garbarino.response.ProductDetailsResponse
import com.example.johan.garbarino.service.ProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//https://medium.com/rocknnull/exploring-kotlin-using-android-architecture-components-and-vice-versa-aa16e600041a

class ProductDetailsViewModel : ViewModel() {
   private val productDetails = MutableLiveData<ProductDetailsResponse>()

    fun loadProductDetailsData(productId:String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(ConfigApp.getUrlProductList())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        //  val service = retrofit.create(ProductDetailsService::class.java)
        //  val call = service.getProductDetailsData()
          val service = retrofit.create(ProductService::class.java)
          val call = service.getProductDetailsData(productId)
        call.enqueue(object : Callback<ProductDetailsResponse> {
            override fun onResponse(call: Call<ProductDetailsResponse>, response: Response<ProductDetailsResponse>) {
                    productDetails.value = if (response.code() == 200) response.body()!! else FakeData.getProductDetails(productId)
            }
            override fun onFailure(call: Call<ProductDetailsResponse>, t: Throwable) {
                println ("Error on connection on ProductDetailsViewModel")
               productDetails.value = FakeData.getProductDetails(productId)
            }
        })
    }
    fun getProductDetails(): LiveData<ProductDetailsResponse> {
        return productDetails
    }
}
