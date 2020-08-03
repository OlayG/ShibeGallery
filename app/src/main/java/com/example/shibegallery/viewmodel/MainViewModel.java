package com.example.shibegallery.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shibegallery.repository.ShibeRepo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<String>> _imageUrls = new MutableLiveData<>();

    public LiveData<List<String>> getImageUrls() {
        return _imageUrls;
    }

    public void fetchShibeImageUrls(String path, int count) {
        new ShibeRepo().getImageUrls(path, count).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(@NotNull Call<List<String>> call, @NotNull Response<List<String>> response) {
                List<String> urls = response.body();
                _imageUrls.setValue(urls);
            }

            @Override
            public void onFailure(@NotNull Call<List<String>> call, @NotNull Throwable t) {
                // TODO: 7/31/2020 Handle Error
            }
        });
    }
}
