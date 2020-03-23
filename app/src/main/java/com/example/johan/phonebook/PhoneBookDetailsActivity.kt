package com.example.johan.phonebook

import DataRepository
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.example.johan.phonebook.adapter.DetailInfoRecyclerViewAdapter
import com.example.johan.phonebook.adapter.DetailsAdapter
import com.example.johan.phonebook.response.DetailInfo
import com.example.johan.phonebook.response.PhoneBookListResponse
import com.example.johan.phonebook.response.Phonebook
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_phonebook_details.*
import kotlinx.android.synthetic.main.layout_phonebook_details_activity.*

class PhoneBookDetailsActivity : AppCompatActivity() {

    private var personId: String = ""

    private lateinit var recyclerViewDetails: RecyclerView

    private lateinit var imgPerson: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.slide_up, R.anim.slide_off)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_phonebook_details_activity)

        try {
            this.personId = intent?.extras?.getString("p_id") ?: ""
        } catch (e: Exception) {
            this.personId = "1"
        }
        println("================PhoneBookDetailsActivity.personId = " + this.personId)

        DataRepository.viewModelPhoneBookList.getPhoneBookList().observe(this,
            Observer { phonebookList ->
                chargePerson(phonebookList!!)
            }
        )

        //hide Action bar
        supportActionBar!!.hide()
    }

    private fun chargePerson(dataMap: PhoneBookListResponse) {

        val data = ArrayList(dataMap.values)
        val person = dataMap.get(this.personId)
        println(data)
        showDetailsOnUi(person)
        var extra: ArrayList<DetailInfo> = DetailsAdapter.getExtraData(person!!, this)
        createRecyclerViewReviewList(extra)

        var url = person.largeImageURL


        imgPerson = findViewById<ImageView>(R.id.imgPersonLarge)
        Picasso.get().load(url).into(imgPerson)


    }

    fun showDetailsOnUi(res: Phonebook?) {
        txtName.text = res!!.name!!
        txtCompanyName.text = res.companyName

        if (res.isFavorite!!)
            imgIsFavorite.setImageDrawable(getDrawable(android.R.drawable.star_big_on))
        else
            imgIsFavorite.setImageDrawable(getDrawable(android.R.drawable.star_big_off))

    }

    fun createRecyclerViewReviewList(data: ArrayList<DetailInfo>) {

        recyclerViewDetails = findViewById<RecyclerView>(R.id.rviewDetailInfo)

        recyclerViewDetails.setHasFixedSize(false)
        recyclerViewDetails.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewDetails.adapter = DetailInfoRecyclerViewAdapter(data)
    }

    fun pressButton(view: View) {
        when (view.id) {
            R.id.btnBack -> finish()
            R.id.imgIsFavorite -> {
                Toast.makeText(
                    this,
                    getString(R.string.strInConstruction).toUpperCase(),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }
}
