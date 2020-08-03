package com.example.shibegallery.repository.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ShibeService {

    @GET("{animal}")
    Call<List<String>> getImageUrls(
            @Path("animal") String path,
            @Query("count") int count
    );
}
