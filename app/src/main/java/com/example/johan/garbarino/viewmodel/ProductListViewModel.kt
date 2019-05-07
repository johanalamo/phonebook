package com.example.johan.garbarino.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import okhttp3.*
import java.io.IOException

import java.net.ConnectException
import java.net.UnknownHostException


import com.example.johan.garbarino.ConfigApp
import com.example.johan.garbarino.FakeData
import com.example.johan.garbarino.service.ProductService
import com.example.johan.garbarino.response.ProductListResponse
import com.google.gson.Gson

//https://medium.com/rocknnull/exploring-kotlin-using-android-architecture-components-and-vice-versa-aa16e600041a




class ProductListViewModel : ViewModel() {
   private val productList = MutableLiveData<ProductListResponse>()

    fun loadProductListData() {
      val client = OkHttpClient()
      val request = Request.Builder().url(ConfigApp.getUrlProductList()).build()

      client.newCall(request).enqueue( object : Callback {
          override fun onFailure(call: Call, e: IOException) {
            var msg: String = ""
            if (e::class == UnknownHostException::class)
                msg = "UnknownHostException:"
            if (e::class == ConnectException::class)
                msg = "ConnectException"
            println( "\n\n\n ************************ $msg  \n\n" + e.toString())

          }

          override fun onResponse(call: Call, response: Response) {
            var r = response.body()?.string()
            var c: Int = response.code()
//            println("\n\n\n =====================Respuesta desde el servidor ($c): \n\n" + r)

            var gson = Gson()
            var data = gson.fromJson(r, ProductListResponse::class.java)
            try{
              productList.postValue(data)
            }catch(e:Exception){
              println("===============error, exception catched=====================")
              println(e)
            }
          }
      }
      )
    }
    fun getProductList(): LiveData<ProductListResponse> {
        return productList
    }
}
