package com.tasdelen.besinlerkitabi.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tasdelen.besinlerkitabi.data.Besin

@Dao
interface BesinDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg besin : Besin) : List<Long>

    @Query("SELECT * FROM besin")
    suspend fun getAll() : List<Besin>

    @Query("SELECT * FROM besin WHERE uuid = :besinId")
    suspend fun getBesin(besinId : Int) : Besin

    @Query("DELETE FROM besin")
    suspend fun deleteAll()
}