package com.example.appkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appkotlin.repository.Repo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _urls = MutableLiveData<List<String>>()
    val urlObservable: LiveData<List<String>>
        get() = _urls

    fun fetchImages(animal: String, count: Int) {
        Repo().callListOfString(animal, count).enqueue(object : Callback<List<String>> {
            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                _urls.value = response.body()
            }

        })
    }
}