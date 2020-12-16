package com.koleychik.testnotifiction

import com.google.gson.Gson

class WorkWithJson<T> {

    private var json : String? = null
    private val gSon = Gson()

    fun serializeToJson(t: T): String {
        json = gSon.toJson(t)
        return json!!
    }

    fun getJSon(): String? = json

    inline fun <reified T> deserializeFromJson(t : String): T?{
        val gSon = Gson()
        return gSon.fromJson(t, T::class.java)
    }

}