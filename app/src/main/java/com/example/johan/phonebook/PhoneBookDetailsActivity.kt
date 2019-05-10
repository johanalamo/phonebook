package com.example.johan.phonebook

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_phonebook_details_activity.*
import kotlinx.android.synthetic.main.layout_phonebook_details.*
import com.example.johan.phonebook.adapter.DetailInfoAdapter
import com.example.johan.phonebook.response.DetailInfo
import com.example.johan.phonebook.response.*

import android.widget.ImageView
import com.squareup.picasso.Picasso

class PhoneBookDetailsActivity : AppCompatActivity() {
    private var personId: String = ""


    private lateinit var recyclerViewDetails:RecyclerView
    private lateinit var viewAdapterDetails: RecyclerView.Adapter<*>
    private lateinit var viewManagerDetails: RecyclerView.LayoutManager

    private lateinit var imgPerson: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.slide_up, R.anim.slide_off)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_phonebook_details_activity)

        try {
            this.personId = getIntent().getExtras().getString("p_id")
        } catch (e: Exception) {
            this.personId = "1"
        }
        println("================PhoneBookDetailsActivity.personId = " + this.personId)

        DataRepository.viewModelPhoneBookList.getPhoneBookList().observe(this,
                 Observer {
                    productList -> chargePerson(productList!!)
                    }
        )

        //hide Action bar
        supportActionBar!!.hide()

    }

    private fun chargePerson(dataMap:PhoneBookListResponse){

              val data = ArrayList(dataMap.values)
              val person = dataMap.get(this.personId)
              println(data)
              showDetailsOnUi(person)
              var extra: ArrayList<DetailInfo> = getExtraData(person!!)
              createRecyclerViewReviewList(extra)

              var url = person.largeImageURL


               imgPerson = findViewById<ImageView>(R.id.imgPersonLarge)
              Picasso.with(this).load(url).into(imgPerson)


    }

    private fun getExtraData(p:Product): ArrayList<DetailInfo>{
      var r: DetailInfo = DetailInfo ()
      var extra:ArrayList<DetailInfo> = ArrayList()
      if (p.phone?.home != ""){
        r = DetailInfo()
        r.fieldName = getString(R.string.strPhone).toUpperCase() + ":"
        r.extraInfo = getString(R.string.strHome)
        r.value = p.phone!!.home
        extra.add(r)
      }

      if (p.phone?.mobile  != ""){
        r = DetailInfo()
        r.fieldName = getString(R.string.strPhone).toUpperCase() + ":"
        r.extraInfo = getString(R.string.strMobile)
        r.value = p.phone!!.mobile
        extra.add(r)
      }

      if (p.phone?.work != ""){
        r = DetailInfo()
        r.fieldName = getString(R.string.strPhone).toUpperCase() + ":"
        r.extraInfo = getString(R.string.strWork)
        r.value = p.phone!!.work
        extra.add(r)
      }

      if (p.address!! != null){
        r = DetailInfo()
        r.fieldName = getString(R.string.strAddress).toUpperCase() + ":"
        r.extraInfo = ""
        r.value = p.address!!.street + "\n" + p.address!!.city + ", " + p.address!!.state + " " + p.address!!.zipCode +  ", " + p.address!!.country +
        extra.add(r)
      }
      if (p.birthdate!! != null){
        r = DetailInfo()
        r.fieldName = getString(R.string.strBirthdate).toUpperCase() + ":"
        r.extraInfo = ""
        r.value = p.birthdate
        extra.add(r)
      }
      if (p.emailAddress!! != null){
        r = DetailInfo()
        r.fieldName = getString(R.string.strEmail).toUpperCase() + ":"
        r.extraInfo = ""
        r.value = p.emailAddress!!
        extra.add(r)
      }
      return extra
    }

    fun showDetailsOnUi(res: Product?) {
        txtName.text = res!!.name!!
        txtCompanyName.text = res.companyName

        if (res.isFavorite!!)
          imgIsFavorite.setImageDrawable(getDrawable(android.R.drawable.star_big_on))
        else
            imgIsFavorite.setImageDrawable(getDrawable(android.R.drawable.star_big_off))

    }

    fun createRecyclerViewReviewList(data:ArrayList<DetailInfo>){
        viewManagerDetails = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewAdapterDetails = DetailInfoAdapter(data, this)
        recyclerViewDetails = findViewById <RecyclerView>(R.id.rviewDetailInfo).apply {
            setHasFixedSize(false);
            layoutManager = viewManagerDetails
            adapter = viewAdapterDetails
        }
    }
}
