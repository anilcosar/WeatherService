package com.anil.weatherapp.service;

import com.anil.weatherapp.model.ResponseData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;


@Service
public class OpenWeather {

    OpenWeatherService openWeatherService;


    final static Logger logger = Logger.getLogger(OpenWeather.class);


    public ResponseData getData(String city) {
        ResponseData weatherData = new ResponseData();

        openWeatherService = ApiClient.getClient().create(OpenWeatherService.class);
        Call<ResponseData> mApiService = openWeatherService.getWeatherDataByCity(city);
        try {
            logger.info(mApiService.request().toString());
            weatherData = mApiService.execute().body();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return weatherData;
    }


}
