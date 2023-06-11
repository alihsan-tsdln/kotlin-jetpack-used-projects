package com.tasdelen.besinlerkitabi.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

open class SpecialSharedPreferences {

    companion object {

        private const val TIME = "time"

        private var sharedPreferences : SharedPreferences? = null

        @Volatile var instance : SpecialSharedPreferences? = null

        private val lock = Any()

        operator fun invoke(context: Context) : SpecialSharedPreferences = instance ?: synchronized(lock) {
            instance ?: createSpecialSharedPreferences(context).also {
                instance = it
            }
        }

        private fun createSpecialSharedPreferences(context : Context) : SpecialSharedPreferences {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return SpecialSharedPreferences()
        }
    }

    fun saveTime(time : Long) {
        sharedPreferences?.edit(commit = true) {
            putLong(TIME, time)
        }
    }

    fun takeTime() : Long? {
        println("TAKE TIME CAME$sharedPreferences?.getLong(TIME, 0)")
        return sharedPreferences?.getLong(TIME, 0)
    }

}