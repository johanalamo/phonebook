package com.example.johan.phonebook

class ConfigApp {
    companion object {
        fun getUrlPhoneBookList(): String =
            "https://s3.amazonaws.com/technical-challenge/v3/contacts.json"
    }
}
