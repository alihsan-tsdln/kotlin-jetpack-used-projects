package com.tasdelen.besinlerkitabi.viewModels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.tasdelen.besinlerkitabi.data.Besin
import com.tasdelen.besinlerkitabi.service.BesinAPIService
import com.tasdelen.besinlerkitabi.service.BesinDB
import com.tasdelen.besinlerkitabi.util.SpecialSharedPreferences
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListesiViewModel(application: Application) : BaseViewModel(application) {

    val besinler = MutableLiveData<List<Besin>>()
    val isBesinError = MutableLiveData<Boolean>()
    val isBesinLoading = MutableLiveData<Boolean>()

    private val apiService = BesinAPIService()
    private val disposable = CompositeDisposable()
    private val specialSharedPreferences = SpecialSharedPreferences(getApplication())
    private val timeOfUpdate = 60000000000L

    fun refreshData() {

        val timeOfLastUpdate = specialSharedPreferences.takeTime()
        println(System.nanoTime() - timeOfLastUpdate!!)
        if(timeOfLastUpdate != null &&
            timeOfLastUpdate != 0L &&
            System.nanoTime() - timeOfLastUpdate < timeOfUpdate) {
            takeDataFromSql()
        } else {
            takeDataFromInternet()
        }

    }

    fun takeDataFromInternet() {
        isBesinLoading.value = true

        disposable.add(
            apiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Besin>>() {
                    override fun onSuccess(t: List<Besin>) {
                        addSqlite(t)
                    }

                    override fun onError(e: Throwable) {
                        isBesinLoading.value = false
                        isBesinError.value = true
                        e.printStackTrace()
                    }

                })
        )
    }

    fun addSqlite(besinList : List<Besin>) {

        val dao = BesinDB(getApplication()).besinDAO()

        launch {
            dao.deleteAll()
            val uuidList = dao.insertAll(*besinList.toTypedArray())
            for (i in besinList.indices) {
                besinList[i].uuid = uuidList[i].toInt()
            }
        }

        showBesinler(besinList)
        specialSharedPreferences.saveTime(System.nanoTime())
        Toast.makeText(getApplication(), "We took data from Internet", Toast.LENGTH_SHORT).show()
    }

    private fun takeDataFromSql() {

        val dao = BesinDB(getApplication()).besinDAO()

        launch {
            val besinList = dao.getAll()
            showBesinler(besinList)
        }
        Toast.makeText(getApplication(), "We took data from Room", Toast.LENGTH_SHORT).show()
    }

    private fun showBesinler(besinList : List<Besin>) {
        besinler.value = besinList
        isBesinLoading.value = false
        isBesinError.value = false
    }
}