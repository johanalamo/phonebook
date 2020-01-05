package com.example.johan.phonebook

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.johan.phonebook.adapter.PhonebookListRecyclerViewAdapter
import com.example.johan.phonebook.response.PhoneBookListResponse
import com.example.johan.phonebook.viewmodel.PhoneBookListViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "cols"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PersonListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PersonListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PersonListFragment : Fragment(),  PhonebookListRecyclerViewAdapter.ClickListener {
    // TODO: Rename and change types of parameters
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var recyclerView: RecyclerView

    private var cols:Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cols = it.getString(ARG_PARAM1, "1").toInt()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person_list, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    override fun listItemClicked(personId: String) {
        listener?.onListItemClicked(personId)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        DataRepository.viewModelPhoneBookList =
            ViewModelProviders.of(this).get(PhoneBookListViewModel::class.java)
        DataRepository.viewModelPhoneBookList.getPhoneBookList().observe(this,
            Observer { phoneBookList ->
                createRecyclerViewPhoneBookList(phoneBookList!!, R.id.rviewPhoneBookList, true)
            }
        )
        DataRepository.viewModelPhoneBookList.loadPhoneBookListData()

    }


    fun createRecyclerViewPhoneBookList(
        data: PhoneBookListResponse,
        idRecyclerView: Int,
        isFav: Boolean
    ) {
        val sortedData =
            data.filter { it.value.isFavorite!! == isFav } + data.filter { it.value.isFavorite!! == !isFav }
        view?.let {
            recyclerView = it.findViewById<RecyclerView>(idRecyclerView)
            recyclerView.setHasFixedSize(false)
            recyclerView.layoutManager =
                GridLayoutManager(context, cols)
            recyclerView.adapter = PhonebookListRecyclerViewAdapter(sortedData, this)
        }
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListItemClicked(personId: String)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PersonListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(cols:Int) =
            PersonListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, cols.toString())
                }
            }
    }
}
