package com.example.johan.phonebook.adapter

import android.content.Context
import com.example.johan.phonebook.R
import com.example.johan.phonebook.response.DetailInfo
import com.example.johan.phonebook.response.Phonebook

class DetailsAdapter {
    companion object {
        fun getExtraData(p: Phonebook, context: Context): ArrayList<DetailInfo> {
            var extra: ArrayList<DetailInfo> = ArrayList()
            p.phone?.home?.let {
                if (it.trim() != "")
                    extra.add(
                        DetailInfo(context.getString(R.string.strPhone).toUpperCase() + ":", p.phone!!.home, context.getString(
                            R.string.strHome))
                    )
            }

            p.phone?.mobile?.let  {
                if (it.trim() != "")
                    extra.add(
                        DetailInfo(context.getString(R.string.strPhone).toUpperCase() + ":", p.phone!!.mobile, context.getString(
                            R.string.strMobile))
                    )
            }

            p.phone?.work?.let  {
                if (it.trim() != "")
                    extra.add(
                        DetailInfo(context.getString(R.string.strPhone).toUpperCase() + ":", p.phone!!.work, context.getString(
                            R.string.strWork))
                    )
            }

            p.address?.let {
                extra.add(
                    DetailInfo(
                        context.getString(R.string.strAddress).toUpperCase() + ":",
                        p.address!!.street + "\n" + p.address!!.city + ", " + p.address!!.state + " " + p.address!!.zipCode + ", " + p.address!!.country
                    )
                )
            }

            p.birthdate?.let {
                if (it.trim() != "")
                    extra.add(DetailInfo(context.getString(R.string.strBirthdate).toUpperCase() + ":", p.birthdate))
            }

            p.emailAddress?.let {
                if (it.trim() != "")
                    extra.add(DetailInfo(context.getString(R.string.strEmail).toUpperCase() + ":", it ))
            }
            return extra
        }

    }
}