package com.flatstack.android.utils.storage

import android.content.SharedPreferences

import com.google.gson.Gson

import java.lang.reflect.Type

/**
 * Created by ereminilya on 8/1/17.
 */

class Storage(private val sp: SharedPreferences, private val gson: Gson) : IStorage {

    override fun <T> get(key: String, type: Type): T? {
        val json = sp.getString(key, "")
        if ("" == json)
            return null
        else {
            return gson.fromJson<T>(json, type)
        }
    }

    override fun <T> put(key: String, item: T) {
        sp.edit().putString(key, gson.toJson(item)).apply()
    }

    override fun putString(key: String, str: String) {
        sp.edit().putString(key, str).apply()
    }

    override fun getString(key: String): String? {
        return sp.getString(key, null)
    }

    override fun putLong(key: String, number: Long) {
        sp.edit().putLong(key, number).apply()
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return sp.getLong(key, defaultValue)
    }

    override fun putInt(key: String, number: Int) {
        sp.edit().putInt(key, number).apply()
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return sp.getInt(key, defaultValue)
    }

    override fun putBoolean(key: String, value: Boolean) {
        sp.edit().putBoolean(key, value).apply()
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sp.getBoolean(key, defaultValue)
    }

    override fun remove(key: String) {
        sp.edit().remove(key).apply()
    }

    override fun <T> putCollection(key: String, items: List<T>) {
        put(key, items)
    }

    override fun <T> getCollection(key: String, type: Type): List<T>? {
        return get<List<T>>(key, type)
    }
}