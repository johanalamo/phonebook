package com.example.johan.phonebook.response

import com.google.gson.annotations.SerializedName

typealias PhonebookListResponseFromRemote = Array<Phonebook>

typealias  PhoneBookListResponse = Map<String?, Phonebook>

class Phonebook {
    @SerializedName("id")
    var id: String? = ""

    @SerializedName("name")
    val name: String? = ""

    @SerializedName("companyName")
    val companyName: String? = ""

    @SerializedName("isFavorite")
    val isFavorite: Boolean? = false

    @SerializedName("smallImageURL")
    val smallImageURL: String? = ""

    @SerializedName("largeImageURL")
    val largeImageURL: String? = ""

    @SerializedName("emailAddress")
    val emailAddress: String? = ""

    @SerializedName("birthdate")
    val birthdate: String? = ""

    @SerializedName("phone")
    var phone: Phone? = Phone()

    @SerializedName("address")
    var address: Address? = Address()
}


class Phone {

    @SerializedName("work")
    var work: String? = ""

    @SerializedName("home")
    val home: String? = ""

    @SerializedName("mobile")
    val mobile: String? = ""
}


class Address {

    @SerializedName("street")
    var street: String? = ""

    @SerializedName("city")
    val city: String? = ""

    @SerializedName("state")
    val state: String? = ""

    @SerializedName("country")
    val country: String? = ""

    @SerializedName("zipCode")
    val zipCode: String? = ""
}
