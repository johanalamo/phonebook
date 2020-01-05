package com.example.johan.phonebook

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.FrameLayout
import android.widget.TextView


class PhoneBookListActivity : AppCompatActivity(), PersonListFragment.OnFragmentInteractionListener {

    private val TAG = PhoneBookListActivity::class.java.simpleName

    private var personListFragment:PersonListFragment? = null

    private var personDetailsFragment: PersonDetailsFragment? = null

    private var fragmentContainer: FrameLayout? = null

    private var isLargeScreen: Boolean = false

    private var markIsLand: TextView? = null

    private var isLand = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_phonebook_list_activity)

        fragmentContainer = findViewById<FrameLayout>(R.id.fragment_container)

        markIsLand = findViewById<TextView>(R.id.markIsLand)

        isLand = markIsLand != null

        Log.d(TAG, " --------- is Land ->  " + isLand.toString())
        isLargeScreen = (fragmentContainer != null)

        var cols:Int = 1
        if (isLand) {
            cols = 1 //temporary disabled
        }
        personListFragment = PersonListFragment.newInstance(cols)
        personListFragment?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, it)
                .addToBackStack(null)
                .commit()
        }

        //hide Action bar
        supportActionBar!!.hide()
    }


    override fun onListItemClicked(personId:String) {
        if (!isLargeScreen) {
            val intent: Intent = Intent(this, PhoneBookDetailsActivity::class.java)
            intent.putExtra("p_id", personId)
            startActivity(intent)
        } else {
//            title = list.name
            personDetailsFragment = PersonDetailsFragment.newInstance(personId)
            personDetailsFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .addToBackStack(null)
                    .commit()
            }

        }
    }
}
