package com.tasdelen.besinlerkitabi.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tasdelen.besinlerkitabi.data.Besin
import com.tasdelen.besinlerkitabi.service.BesinDB
import kotlinx.coroutines.launch

class BesinDetayiViewModel(application: Application) : BaseViewModel(application) {
    val besin = MutableLiveData<Besin>()

    fun takeRoomData(id : Int) {
        val besinDAO = BesinDB(getApplication()).besinDAO()
        launch {
            besin.value = besinDAO.getBesin(id)
        }
    }
}