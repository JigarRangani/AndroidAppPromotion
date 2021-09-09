package com.valora.promotions

import android.content.Context
import android.content.SharedPreferences


class PromotionSharedPreferenceManager constructor(context: Context) {
    private val NAME = "demo"
    private val MODE = Context.MODE_PRIVATE
    private val IS_FIRST_RUN_PREF = Pair("is_first_run", false)
    private val SELECTED_TIME = Pair("selectedTime", "05:30 am")
    private val COUNTER = Pair("counter", 0)
    private val APP_LIST = Pair("promotionApp", "")

//    var s: Set<String> = HashSet<String>(sharedPrefs.getStringSet("key", HashSet<String>()))

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

    var selectedTime: String
        get() = prefs.getString(SELECTED_TIME.first, SELECTED_TIME.second)!!
        set(value) = prefs.edit {
            it.putString(SELECTED_TIME.first, value)
        }

    var counter: Int
        get() = prefs.getInt(COUNTER.first, COUNTER.second)
        set(value) = prefs.edit {
            it.putInt(COUNTER.first, value)
        }

    fun deleteAll() {
        prefs.edit().clear().apply()
    }
}