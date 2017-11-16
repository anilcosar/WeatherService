package com.anil.weatherapp.service;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    private static final String URL = "http://api.openweathermap.org/data/2.5/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;
    }
}
