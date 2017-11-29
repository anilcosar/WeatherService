package com.anil.weatherapp.specs;

import com.anil.weatherapp.config.BaseMockitoTest;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StringSpecTest extends BaseMockitoTest {

    private List<String> cityList;

    @Before
    public void before() {
        cityList = new ArrayList<>();
        cityList.add("Ankara");
        cityList.add("Istanbul");
        cityList.add("Adana");
        cityList.add("New York");
        cityList.add("London");
        cityList.add("Paris");
        cityList.add("Dubai");
    }

    @Test
    public void should_isExist_in_List() {
        //given
        String city = "London";

        //when
        String result = StringSpecs.isExist(cityList, city);

        //then
        assertThat(result).isEqualTo("London");
    }

    @Test
    public void should_isNotExist_in_List() {
        //given
        String city = "Bursa";

        //when
        String result = StringSpecs.isExist(cityList, city);

        //then
        assertThat(result).isEqualTo("");
    }

}
