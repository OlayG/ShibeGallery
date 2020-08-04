package com.example.appkotlin.repository

import com.example.appkotlin.repository.remote.RetrofitInstance
import retrofit2.Call

class Repo {

    fun callListOfString(animal: String, count: Int): Call<List<String>> =
            RetrofitInstance.shibeService.getAnimal(animal, count)

}