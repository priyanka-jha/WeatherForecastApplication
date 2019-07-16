package com.android.priyanka.restfulapiweatherforecast.events;

import com.android.priyanka.restfulapiweatherforecast.model.WeatherInformation;

public class WeatherEvent {
    private final WeatherInformation weatherInformation;

    public WeatherEvent(WeatherInformation weatherInformation) {
        this.weatherInformation = weatherInformation;
    }

    public WeatherInformation getWeatherInformation() {
        return weatherInformation;
    }
}
