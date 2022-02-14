package com.s1.fetchrewardschallenge.data

import com.beust.klaxon.Klaxon
import java.net.URL

class Item(val id : Int, val listId : Int, val name : String?) {

    companion object {
        fun getListItems(): List<Item>? {
            val url = "https://fetch-hiring.s3.amazonaws.com/hiring.json"
            val datum = URL(url).readText()
            return Klaxon().parseArray(datum)
        }
    }
}