package com.anil.weatherapp.ui;

import com.anil.weatherapp.model.ResponseData;
import com.anil.weatherapp.service.OpenWeather;
import com.anil.weatherapp.specs.StringSpecs;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Theme("colored")
@Title("Weather Service")
@StyleSheet({"https://fonts.googleapis.com/css?family=Montserrat"})
@SpringUI
@UIScope
public class MainUI extends UI {

    final static Logger logger = Logger.getLogger(MainUI.class);

    private OpenWeather openWeather = new OpenWeather();

    private ClassLoader classLoader = getClass().getClassLoader();
    private File citiesJson = new File(classLoader.getResource("world-cities.json").getFile());

    private List<String> autoCompleteList;
    private ResponseData responseData;
    private List<String> savedCities = new ArrayList<>();


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        AbsoluteLayout mainlayout = new AbsoluteLayout();
        setContent(mainlayout);

        AbsoluteLayout blurLayout = new AbsoluteLayout();
        blurLayout.setStyleName("blurLayout");
        mainlayout.addComponent(blurLayout);

        VerticalLayout connector = new VerticalLayout();
        connector.setWidth(90, Unit.PERCENTAGE);
        connector.setSpacing(false);

        VerticalLayout searchLayout = new VerticalLayout();
        searchLayout.setWidth("100%");
        searchLayout.setStyleName("searchLayout");

        VerticalLayout info = new VerticalLayout();
        info.setMargin(false);
        info.setSpacing(true);
        info.addStyleName("infoDesign");
        info.setWidth(null);


        HorizontalLayout weatherData = new HorizontalLayout();
        weatherData.setWidth("100%");
        weatherData.addStyleName("weatherData");
        fillWeatherData(weatherData, responseData);

        TextField city = new TextField();
        city.addStyleName("tfwb");
        city.setPlaceholder("Istanbul");


        Label date = new Label(responseData != null ? responseData.getCity().getName() + "," + responseData.getCity().getCountry() : "Kocaeli,TR");
        date.addStyleName("date");
        Label time = new Label(DateTimeFormatter.ofPattern("HH:mm a").format(ZonedDateTime.now()));
        time.addStyleName("date");


        Image loc = new Image();
        loc.setSource(new ExternalResource("https://upload.wikimedia.org/wikipedia/commons/f/f4/White_Globe_Icon.png"));
        loc.setHeight(75, Unit.PIXELS);
        loc.setWidth(75, Unit.PIXELS);

        Button savedCity = new NativeButton("SAVED LOCATIONS");
        savedCity.setIcon(new ThemeResource("icon/map.png"));
        Button saveCity = new NativeButton("Save");
        savedCity.setStyleName("borderless");
        saveCity.addStyleName("borderless");

        info.addComponent(loc);
        info.addComponent(date);
        info.addComponent(time);
        info.addComponent(savedCity);
        info.addComponent(saveCity);

        CssLayout cityField = new CssLayout();
        Label label = new Label("");
        label.addStyleName("autoComplete");
        cityField.addComponent(city);
        cityField.addComponent(label);


        searchLayout.addComponent(cityField);
        searchLayout.setComponentAlignment(cityField, Alignment.TOP_CENTER);
        searchLayout.addComponent(info);
        searchLayout.setComponentAlignment(info, Alignment.TOP_RIGHT);


        connector.addComponent(searchLayout);
        connector.addComponent(weatherData);

        mainlayout.addComponent(connector, "left: 150px; top: 50px;");


        createCitiesList();


        city.addValueChangeListener(event -> {
            label.setValue(StringSpecs.isExist(autoCompleteList, event.getValue()));
            city.setValue(WordUtils.capitalize(event.getValue().toLowerCase()));
        });
        city.setValueChangeMode(ValueChangeMode.EAGER);

        city.addShortcutListener(new ShortcutListener("Search", ShortcutAction.KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object o, Object o1) {
                if (!city.isEmpty()) {
                    responseData = openWeather.getData(city.getValue());
                    if (responseData == null)
                        Notification.show("City is not found", Notification.Type.HUMANIZED_MESSAGE);
                    logger.info(responseData);
                    init(vaadinRequest);
                }
            }
        });

        saveCity.addClickListener(clickEvent -> {
            if (responseData != null && !savedCities.contains(responseData.getCity().getName())) {
                savedCities.add(responseData.getCity().getName());
                Notification.show("City added succesfully", Notification.Type.HUMANIZED_MESSAGE);
                logger.info(responseData.getCity().getName());
            } else
                Notification.show("City already added", Notification.Type.HUMANIZED_MESSAGE);

        });

        savedCity.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Panel weatherDataList = new Panel();
                weatherDataList.setSizeFull();
                logger.info(savedCities);
                VerticalLayout layoutHaveDatas = new VerticalLayout();
                for (int i = 0; i < savedCities.size(); i++) {
                    createHorizontalLayoutforWeather(savedCities.get(i), layoutHaveDatas);
                }
                weatherDataList.setContent(layoutHaveDatas);
                Button goBack = new NativeButton("Go Back");
                goBack.addStyleName("goBackButton");
                Button clear = new NativeButton("Clear");
                clear.addStyleName("goBackButton");
                goBack.setIcon(new ThemeResource("icon/back.png"));
                goBack.addClickListener(click -> {
                    mainlayout.removeComponent(goBack);
                    mainlayout.removeComponent(weatherDataList);
                    mainlayout.removeComponent(clear);
                    mainlayout.addComponent(connector, "left: 200px; top: 50px;");
                });
                clear.addClickListener(clickEvent1 -> {
                    savedCities.clear();
                    init(vaadinRequest);
                });
                mainlayout.removeComponent(connector);
                mainlayout.addComponent(clear, "top:10px ; right:20px;");
                mainlayout.addComponent(goBack, "left:20px ; top:10px");
                mainlayout.addComponent(weatherDataList, "left: 100px; top: 50px;");

            }
        });

    }

    private void createHorizontalLayoutforWeather(String city, VerticalLayout list) {
        Label savedCity = new Label(city);
        savedCity.addStyleName("cityName");
        list.addComponent(savedCity);
        list.setComponentAlignment(savedCity, Alignment.TOP_CENTER);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("90%");
        horizontalLayout.addStyleName("weatherData");
        fillWeatherData(horizontalLayout, openWeather.getData(city));
        list.addComponent(horizontalLayout);

    }

    private void createCitiesList() {
        autoCompleteList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            Object parsedData = parser.parse(new FileReader(citiesJson));
            JSONArray cityList = (JSONArray) parsedData;
            cityList.forEach((data) -> {
                JSONObject jsonObject = (JSONObject) data;
                autoCompleteList.add((String) jsonObject.get("name"));
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private void fillWeatherData(HorizontalLayout weatherData, ResponseData responseData) {
        if (responseData != null) {
            LocalDateTime firstDate = new Timestamp(responseData.getList().get(0).getDt() * 1000L).toLocalDateTime();

            VerticalLayout todayData = new VerticalLayout();
            Label degree = new Label(String.format("%.1f", responseData.getList().get(0).getTemp().getDay()) + "\u00b0");
            degree.addStyleName("degree");


            Label day = new Label(DateTimeFormatter.ofPattern("EEEE d").format(firstDate));
            day.addStyleName("days");

            todayData.addComponent(degree);
            todayData.addComponent(day);

            VerticalLayout v2 = new VerticalLayout();
            Image status = getImageFromTheme(responseData.getList().get(0).getWeather().get(0).getMain());

            status.setHeight(100, Unit.PIXELS);
            status.setWidth(100, Unit.PIXELS);
            Label humidity = new Label(responseData.getList().get(0).getSpeed() + " mph / %" + responseData.getList().get(0).getHumidity());
            humidity.addStyleName("days");
            v2.addComponent(status);
            v2.addComponent(humidity);

            weatherData.addComponent(todayData, 0);
            weatherData.addComponent(v2, 1);
            for (int dayData = 1; dayData < 7; dayData++) {
                LocalDateTime date = new Timestamp(responseData.getList().get(dayData).getDt() * 1000L).toLocalDateTime();
                VerticalLayout infoLayout = new VerticalLayout();
                Label infoDay = new Label(DateTimeFormatter.ofPattern("E").format(date));
                Label degreebyday = new Label(responseData.getList().get(dayData).getTemp().getDay() + "\u00b0");

                infoLayout.addStyleName("daysField");
                infoDay.addStyleName("days");
                degreebyday.addStyleName("degreebyday");

                infoLayout.addComponent(infoDay);
                infoLayout.addComponent(getImageFromTheme(responseData.getList().get(dayData).getWeather().get(0).getMain()));
                infoLayout.addComponent(degreebyday);

                weatherData.addComponent(infoLayout);

            }
        }
    }

    private Image getImageFromTheme(String key) {
        Image image = new Image();
        image.setSource(new ThemeResource("images/" + key + ".png"));
        image.setHeight(50, Unit.PIXELS);
        image.setWidth(50, Unit.PIXELS);
        return image;

    }


}