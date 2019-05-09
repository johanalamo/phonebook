package com.example.johan.garbarino

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.arch.lifecycle.Observer
import com.example.johan.garbarino.viewmodel.ProductListViewModel
import android.arch.lifecycle.ViewModelProviders
import android.widget.Toast
import com.example.johan.garbarino.adapter.ProductListAdapter
import com.example.johan.garbarino.response.ProductListResponse
import android.os.SystemClock

class Person(name1: String, lastName1: String, age1: Int){
    var name: String = name1
    var lastName: String = lastName1
    var age: Int = age1
}



class ProductListActivity : AppCompatActivity() {

  var nameTable: MutableMap<String, Person> = mutableMapOf()
  var example1 = Person("Josh", "Cohen", 24)
  var example2 = Person("maria", "carey", 26)
  var example3 = Person("elvis", "presley", 28)


   private lateinit var recyclerView:RecyclerView
   private lateinit var viewAdapter: RecyclerView.Adapter<*>
   private lateinit var viewManager: RecyclerView.LayoutManager

//   private lateinit var viewModel:ProductListViewModel

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.layout_main_activity)

      nameTable["person1"] = example1
      nameTable["person2"] = example2
      nameTable["person3"] = example3
          for(entry in nameTable){
              println("===========persona: " + entry.value.lastName)
          }
      println(nameTable)
      println("======== contiene person2: " + nameTable.containsKey("person2").toString())
      var e:Person? = nameTable.get("person3")
      println("======con get person3: " + e!!.lastName)



//      SystemClock.sleep(1000)
      DataRepository.viewModelPersonList = ViewModelProviders.of(this).get(ProductListViewModel::class.java)
      DataRepository.viewModelPersonList.getProductList().observe(this,
               Observer {
                  productList -> createRecyclerViewProductList(productList!!)
                  }
      )
      DataRepository.viewModelPersonList.loadProductListData()
   }

   fun createRecyclerViewProductList(data:ProductListResponse){
      viewManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
      viewAdapter = ProductListAdapter(data, this)
      recyclerView = findViewById <RecyclerView>(R.id.rviewProducts).apply {
         setHasFixedSize(false);
         layoutManager = viewManager
         adapter = viewAdapter
      }
   }
}
