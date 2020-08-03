package com.example.shibegallery.repository.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitInstance {

    private RetrofitInstance() {

    }

    private static class RetrofitHolder {
        private static final Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("http://shibe.online/api/")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(getClient())
                .build();

        private static OkHttpClient getClient() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            return new OkHttpClient.Builder().addInterceptor(interceptor).build();
        }
    }

    public static ShibeService getShibeService() {
        return RetrofitHolder.retrofit.create(ShibeService.class);
    }
}
