package com.example.medicine_detect.ui.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.medicine_detect.extentions.observeOnUiThread
import com.example.medicine_detect.model.MedicinePlant
import com.example.medicine_detect.utils.apiutils.ApiUtils

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val medicinePlantLiveData: MutableLiveData<MutableList<MedicinePlant>> =
        MutableLiveData()

    init {
        loadListMedicinePlant(application)
    }

    private fun loadListMedicinePlant(context: Context) {
        ApiUtils.getInstance(context).loadDataFromApi()
            .observeOnUiThread()
            .subscribe { list ->
                medicinePlantLiveData.value = list
            }
    }

    fun getListData(): LiveData<MutableList<MedicinePlant>> {
        return medicinePlantLiveData
    }
}
