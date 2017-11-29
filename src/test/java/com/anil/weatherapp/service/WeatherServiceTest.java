package com.anil.weatherapp.service;

import com.anil.weatherapp.config.BaseMockitoTest;
import com.anil.weatherapp.model.ResponseData;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.assertj.core.api.Assertions.assertThat;

public class WeatherServiceTest extends BaseMockitoTest{

    @InjectMocks
    OpenWeather openWeather;

    @Test
    public void should_city_search(){
        //given
        String city="Ankara";

        //when
        ResponseData responseData=openWeather.getData(city);
        //then
        assertThat(responseData).isNotNull();
        assertThat(responseData.getCod()).isEqualTo("200");
        assertThat(responseData.getList().isEmpty()).isEqualTo(false);

    }

    @Test
    public void should_city_search_404(){
        //given
        String city="Aa";

        //when
        ResponseData responseData=openWeather.getData(city);
        //then
        assertThat(responseData).isNull();

    }

}
