package com.anil.weatherapp.service;


import com.anil.weatherapp.model.ResponseData;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

@Service
public interface OpenWeatherService {

    @Headers({"Accept: application/json"})
    @GET("forecast/daily?mode=json&units=metric&cnt=7&appid=4de9503a2d23f406185ea8a7d09d8a98")
    Call<ResponseData> getWeatherDataByCity(@Query("q") String city);

}
