package com.example.appkotlin.repository.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShibeService {

    @GET("{animal}")
    fun getAnimal(
            @Path("animal") animal: String,
            @Query("count") count: Int
    ) : Call<List<String>>
}