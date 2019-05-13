package com.example.johan.phonebook

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.arch.lifecycle.Observer
import com.example.johan.phonebook.viewmodel.PhoneBookListViewModel
import android.arch.lifecycle.ViewModelProviders
import com.example.johan.phonebook.adapter.PhoneBookListAdapter
import com.example.johan.phonebook.response.PhoneBookListResponse


class ProductListActivity : AppCompatActivity() {

   private lateinit var recyclerView:RecyclerView
   private lateinit var viewAdapter: RecyclerView.Adapter<*>
   private lateinit var viewManager: RecyclerView.LayoutManager

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.layout_phonebook_list_activity)

      DataRepository.viewModelPhoneBookList = ViewModelProviders.of(this).get(PhoneBookListViewModel::class.java)
      DataRepository.viewModelPhoneBookList.getPhoneBookList().observe(this,
               Observer {
                  phoneBookList -> createRecyclerViewPhoneBookList(phoneBookList!!, R.id.rviewPhoneBookList, true)
                  }
      )
      DataRepository.viewModelPhoneBookList.loadPhoneBookListData()
      //hide Action bar
      supportActionBar!!.hide()
   }

   fun createRecyclerViewPhoneBookList(data:PhoneBookListResponse, idRecyclerView:Int, isFav:Boolean){
      viewManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
      //val filteredData = data.filter { it.value.isFavorite!! == isFav  }
      viewAdapter = PhoneBookListAdapter(data, this)
      recyclerView = findViewById <RecyclerView>(idRecyclerView).apply {
         setHasFixedSize(false)
         layoutManager = viewManager
         adapter = viewAdapter
      }
   }
}
