package com.example.johan.phonebook.viewmodel


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.johan.phonebook.ConfigApp
import com.example.johan.phonebook.response.PhoneBookListResponse
import com.example.johan.phonebook.response.PhonebookListResponseFromRemote
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException

class PhoneBookListViewModel : ViewModel() {
    private val phoneBookList = MutableLiveData<PhoneBookListResponse>()

    fun loadPhoneBookListData() {
        val client = OkHttpClient()
        val request = Request.Builder().url(ConfigApp.getUrlPhoneBookList()).build()

        client.newCall(request).enqueue(
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    var msg: String = ""
                    if (e::class == UnknownHostException::class)
                        msg = "UnknownHostException:"
                    if (e::class == ConnectException::class)
                        msg = "ConnectException"
                    println("\n\n\n ************************ $msg  \n\n" + e.toString())

                }

                override fun onResponse(call: Call, response: Response) {
                    var r = response.body()?.string()

                    var gson = Gson()
                    var data = gson.fromJson(r, PhonebookListResponseFromRemote::class.java)
                    val map = data.associateBy({ it.id }, { it })
                    try {
                        phoneBookList.postValue(map)
                    } catch (e: Exception) {
                        println("===============error, exception catched=====================")
                        println(e)
                    }
                }
            }
        )
    }

    fun getPhoneBookList(): LiveData<PhoneBookListResponse> {
        return phoneBookList
    }
}
