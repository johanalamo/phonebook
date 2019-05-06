package com.example.johan.garbarino.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import com.example.johan.garbarino.ConfigApp
import com.example.johan.garbarino.FakeData
import com.example.johan.garbarino.service.ProductService
import com.example.johan.garbarino.response.ProductListResponse
import com.google.gson.Gson

//https://medium.com/rocknnull/exploring-kotlin-using-android-architecture-components-and-vice-versa-aa16e600041a




class ProductListViewModel : ViewModel() {
   private val productList = MutableLiveData<ProductListResponse>()

    fun loadProductListData() {
         val retrofit = Retrofit.Builder()
            .baseUrl(ConfigApp.getUrlProductList())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
         val service = retrofit.create(ProductService::class.java)
         val call = service.getProductListData()
         call.enqueue(object : retrofit2.Callback<ProductListResponse> {
            override fun onResponse(call: retrofit2.Call<ProductListResponse>, response: retrofit2.Response<ProductListResponse>) {
                   productList.value = if (response.code() == 200) response.body()!! else FakeData.getProductList()
            }
            override fun onFailure(call: retrofit2.Call<ProductListResponse>, t: Throwable) {
                println ("Error on connection on ProductListViewModel")
                productList.value = FakeData.getProductList()
            }
         })
    }
    fun getProductList(): LiveData<ProductListResponse> {
        return productList
    }
}
