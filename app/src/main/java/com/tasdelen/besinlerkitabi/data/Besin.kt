package com.tasdelen.besinlerkitabi.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "besin")
data class Besin(
    @ColumnInfo(name = "isim")
    @SerializedName("isim")
    val name : String?,
    @ColumnInfo(name = "kalori")
    @SerializedName("kalori")
    val kalori : String?,
    @ColumnInfo(name = "karbonhidrat")
    @SerializedName("karbonhidrat")
    val karbonhidrat : String?,
    @ColumnInfo(name = "protein")
    @SerializedName("protein")
    val protein : String?,
    @ColumnInfo(name = "yag")
    @SerializedName("yag")
    val yag : String?,
    @ColumnInfo(name = "gorsel")
    @SerializedName("gorsel")
    val gorsel : String?
) {
    @PrimaryKey(autoGenerate = true)
    var uuid = 0
}
