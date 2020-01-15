package com.example.johan.phonebook

import DataRepository
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.johan.phonebook.adapter.DetailInfoRecyclerViewAdapter
import com.example.johan.phonebook.adapter.DetailsAdapter
import com.example.johan.phonebook.response.DetailInfo
import com.example.johan.phonebook.response.Phonebook
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
private const val ARG_PARAM1 = "personId"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PersonDetailsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PersonDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PersonDetailsFragment : Fragment() {

    private val TAG = PersonDetailsFragment::class.java.simpleName

    // TODO: Rename and change types of parameters
    private var personId: String? = null

    private lateinit var recyclerViewDetails: RecyclerView

    private lateinit var imgPerson: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            personId = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_person_details, container, false)

        val personList = DataRepository.viewModelPhoneBookList.getPhoneBookList()

        view?.let {
            val dataMap = personList.value!!

            val person = dataMap.get(this.personId)
            val res = person

            val name = it.findViewById<TextView>(R.id.txtName)
            name?.let {
                name.text = res!!.name!!
            }

            val companyName = it.findViewById<TextView>(R.id.txtCompanyName)
            companyName?.let {
                companyName.text = res?.companyName ?: ""
            }

            val isFavorite = it.findViewById<ImageView>(R.id.imgIsFavorite)
            if (res?.isFavorite!!)
                isFavorite.setImageDrawable(context?.getDrawable(android.R.drawable.star_big_on))
            else
                isFavorite.setImageDrawable(context?.getDrawable(android.R.drawable.star_big_off))

            var extra: ArrayList<DetailInfo> = DetailsAdapter.getExtraData(person, context as Context)

            recyclerViewDetails = it.findViewById<RecyclerView>(R.id.rviewDetailInfo)

            recyclerViewDetails.setHasFixedSize(false)
            recyclerViewDetails.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recyclerViewDetails.adapter = DetailInfoRecyclerViewAdapter(extra)


            var url = person.largeImageURL


            imgPerson = it.findViewById<ImageView>(R.id.imgPersonLarge)
            Picasso.with(context).load(url).into(imgPerson)
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param personId Parameter 1.
         * @return A new instance of fragment PersonDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(personId: String) =
            PersonDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, personId)
                }
            }
    }
}
