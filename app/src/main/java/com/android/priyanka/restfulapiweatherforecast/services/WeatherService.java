package com.android.priyanka.restfulapiweatherforecast.services;

import com.android.priyanka.restfulapiweatherforecast.model.WeatherInformation;
import com.android.priyanka.restfulapiweatherforecast.util.WeatherServiceProvider;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherService {
    @GET("{lat},{lon}")
    Call<WeatherInformation> getWeatherInformation(@Path("lat") double lat, @Path("lon") double lon);

}
