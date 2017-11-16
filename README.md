# WeatherService

"Weather Service" is a small Vaadin project that makes use of the OpenWeatherMap service for fetching and displaying weather data and forecasts based on a given location (city).

## Requirements

* [Spring Boot](https://spring.io/guides/gs/spring-boot/) - The framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Vaadin](https://vaadin.com/docs) - Used for design UI

## Installing

You have two options:

a) To clone the repository read-only from Github:
```
$ git clone https://github.com/anilcosar/WeatherService.git
```
b) To clone the repository from the read-write code review repository:
```
$ git clone ssh://<username>@dev.vaadin.com:29418/book-examples.git
```
## Run application via server

To build and install the project into the local repository run
```
mvn clean install 
```
```
spring-boot:run
```

for the demo directory and navigate to http://localhost:8080
