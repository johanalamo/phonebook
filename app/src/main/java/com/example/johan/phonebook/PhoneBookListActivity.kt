package com.example.johan.phonebook

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity


class PhoneBookListActivity : AppCompatActivity(), PersonListFragment.OnFragmentInteractionListener {

    private lateinit var personListFragment:PersonListFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_phonebook_list_activity)



        personListFragment = PersonListFragment.newInstance()
        personListFragment.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, it, "string")
                .addToBackStack(null)
                .commit()
        }

        //hide Action bar
        supportActionBar!!.hide()
    }


    override fun onListItemClicked(personId:String) {
        val intent: Intent = Intent(this, PhoneBookDetailsActivity::class.java)
        intent.putExtra("p_id", personId)
        startActivity(intent)
    }
}
