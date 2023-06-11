package com.tasdelen.besinlerkitabi.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tasdelen.besinlerkitabi.adapters.BesinRecyclerAdapter
import com.tasdelen.besinlerkitabi.data.Besin


@Database(entities = [Besin::class], version = 1)
abstract class BesinDB : RoomDatabase(){

    abstract fun besinDAO() : BesinDAO

    companion object {
        @Volatile var instance : BesinDB? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createDb(context).also {
                instance = it
            }
        }
        private fun createDb(context : Context) = Room.databaseBuilder(
            context,
            BesinDB::class.java,
            name = "besinDB"
        ).build()
    }
}