package com.innovandoapps.library.kernel.httpUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class RetrofitBase {

    private Retrofit retrofit = null;

    public RetrofitBase(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(getHostPath())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public abstract String getHostPath();

    public Retrofit getRetrofitHttpClient(){
        return retrofit;
    }
}
