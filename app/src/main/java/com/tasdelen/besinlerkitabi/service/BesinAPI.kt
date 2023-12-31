package com.tasdelen.besinlerkitabi.service

import com.tasdelen.besinlerkitabi.data.Besin
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface BesinAPI {

    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    fun getBesin() : Single<List<Besin>>
}