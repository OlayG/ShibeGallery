package com.example.shibegallery.repository;

import com.example.shibegallery.repository.remote.RetrofitInstance;

import java.util.List;

import retrofit2.Call;

public class ShibeRepo {

    public Call<List<String>> getImageUrls(String path, int count) {
        return RetrofitInstance.getShibeService().getImageUrls(path, count);
    }
}
