package com.valora.promotions

import android.content.Context
import android.content.SharedPreferences


class PromotionSharedPreferenceManager constructor(context: Context) {
    private val NAME = "demo"
    private val MODE = Context.MODE_PRIVATE
    private val IS_FIRST_RUN_PREF = Pair("is_first_run", false)
    private val COUNTER = Pair("counter", 0)
    private val APP_LIST = Pair("promotionApp", "")
    private val COUNTER_RATE = Pair("counterRate", 0)
    private val IS_RATED = Pair("rate", false)

    val prefs = context.getSharedPreferences(NAME, MODE)

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var firstRun: Boolean
        get() = prefs.getBoolean(IS_FIRST_RUN_PREF.first, IS_FIRST_RUN_PREF.second)
        set(value) = prefs.edit {
            it.putBoolean(IS_FIRST_RUN_PREF.first, value)
        }


    var promotionList: String
        get() = prefs.getString(APP_LIST.first,APP_LIST.second)!!
        set(value) = prefs.edit {
            it.putString(APP_LIST.first,value)
        }

    var counter: Int
        get() = prefs.getInt(COUNTER.first, COUNTER.second)
        set(value) = prefs.edit {
            it.putInt(COUNTER.first, value)
        }
    var counterRate: Int
        get() = prefs.getInt(COUNTER_RATE.first, COUNTER_RATE.second)
        set(value) = prefs.edit {
            it.putInt(COUNTER_RATE.first, value)
        }
    var isRated: Boolean
        get() = prefs.getBoolean(IS_RATED.first, IS_RATED.second)
        set(value) = prefs.edit {
            it.putBoolean(IS_RATED.first, value)
        }

    fun deleteAll() {
        prefs.edit().clear().apply()
    }


}