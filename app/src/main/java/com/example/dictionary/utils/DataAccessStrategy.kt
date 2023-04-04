package com.example.dictionary.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData

import com.example.dictionary.utils.Resource.Status.ERROR
import com.example.dictionary.utils.Resource.Status.SUCCESS
import kotlinx.coroutines.Dispatchers


fun <T> performGetOperation(
    networkCall: suspend () -> Resource<T>,
): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        Log.d("performGetOperation", "performGetOperation: ")
        emit(Resource.loading())



        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {

            emit(responseStatus)

        } else if (responseStatus.status == ERROR) {
            emit(Resource.error(responseStatus.message!!))

        }
    }