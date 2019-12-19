package com.example.johan.phonebook

import DataRepository
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.johan.phonebook.adapter.PhoneBookListAdapter
import com.example.johan.phonebook.response.PhoneBookListResponse
import com.example.johan.phonebook.response.Phonebook
import com.example.johan.phonebook.viewmodel.PhoneBookListViewModel


class PhoneBookListActivity : AppCompatActivity(), PhoneBookListAdapter.ClickListener {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_phonebook_list_activity)

        DataRepository.viewModelPhoneBookList =
            ViewModelProviders.of(this).get(PhoneBookListViewModel::class.java)
        DataRepository.viewModelPhoneBookList.getPhoneBookList().observe(this,
            Observer { phoneBookList ->
                createRecyclerViewPhoneBookList(phoneBookList!!, R.id.rviewPhoneBookList, true)
            }
        )
        DataRepository.viewModelPhoneBookList.loadPhoneBookListData()
        //hide Action bar
        supportActionBar!!.hide()
    }

    fun createRecyclerViewPhoneBookList(
        data: PhoneBookListResponse,
        idRecyclerView: Int,
        isFav: Boolean
    ) {
        val sortedData =
            data.filter { it.value.isFavorite!! == isFav } + data.filter { it.value.isFavorite!! == !isFav }

        recyclerView = findViewById<RecyclerView>(idRecyclerView)
        recyclerView.setHasFixedSize(false)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = PhoneBookListAdapter(sortedData, this)
    }

    override fun listItemClicked(phonebook: Phonebook) {
        val intent: Intent = Intent(this, PhoneBookDetailsActivity::class.java)
        intent.putExtra("p_id", phonebook.id)
        startActivity(intent)
    }
}
