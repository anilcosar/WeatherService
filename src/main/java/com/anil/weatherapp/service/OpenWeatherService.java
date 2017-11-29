package com.anil.weatherapp.service;


import com.anil.weatherapp.model.ResponseData;
import com.anil.weatherapp.utils.Constants;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface OpenWeatherService {

    @Headers({"Accept: application/json"})
    @GET("forecast/daily?mode=json&units=metric&cnt=7&appid="+ Constants.APPID)
    Call<ResponseData> getWeatherDataByCity(@Query("q") String city);

}
