package com.anil.weatherapp;


import com.anil.weatherapp.model.ResponseData;
import com.anil.weatherapp.service.OpenWeather;
import com.anil.weatherapp.utils.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherappApplicationTests {

	@Test
	public void contextLoads() {
	}

}
