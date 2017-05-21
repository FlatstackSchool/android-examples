package com.flatstack.android.utils.storage

import java.lang.reflect.Type

/**
 * Created by ereminilya on 8/1/17.
 */

interface IStorage {

    operator fun <T> get(key: String, type: Type): T?

    fun <T> put(key: String, item: T)

    fun putString(key: String, str: String)

    fun getString(key: String): String?

    fun putLong(key: String, number: Long)

    fun getLong(key: String, defaultValue: Long): Long

    fun putInt(key: String, number: Int)

    fun getInt(key: String, defaultValue: Int): Int

    fun putBoolean(key: String, value: Boolean)

    fun getBoolean(key: String, defaultValue: Boolean): Boolean

    fun remove(key: String)

    fun <T> putCollection(key: String, items: List<T>)

    fun <T> getCollection(key: String, type: Type): List<T>?
}
