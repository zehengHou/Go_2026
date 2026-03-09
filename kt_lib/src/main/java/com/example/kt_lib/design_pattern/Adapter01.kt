package com.example.kt_lib.design_pattern

interface JsonParser {
    fun parse(json: String)
}

class GsonParser {
    fun fromJson(json: String) {

    }
}

class GsonAdapter(
    private val gson: GsonParser
) : JsonParser {
    override fun parse(json: String) {
        gson.fromJson(json)
    }
}
