package com.example.johan.phonebook.listener

import com.example.johan.phonebook.response.Phonebook

interface PhoneBookListRecyclerViewClickListener {
    fun listItemClicked(phonebook: Phonebook)
}