package com.anil.weatherapp.controller;

import com.anil.weatherapp.model.ResponseData;
import com.anil.weatherapp.service.OpenWeather;
import org.springframework.stereotype.Controller;

@Controller
public class WeatherController {

    private OpenWeather openWeather;

    public WeatherController(OpenWeather openWeather) {
        this.openWeather = openWeather;
    }

    public ResponseData getData(String city){
       return openWeather.getData(city);
    }
}
