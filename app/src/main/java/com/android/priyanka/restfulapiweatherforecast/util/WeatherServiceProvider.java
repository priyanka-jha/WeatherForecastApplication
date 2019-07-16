package com.android.priyanka.restfulapiweatherforecast.util;

import android.util.Log;
import android.widget.Toast;

import com.android.priyanka.restfulapiweatherforecast.events.ErrorEvent;
import com.android.priyanka.restfulapiweatherforecast.events.WeatherEvent;
import com.android.priyanka.restfulapiweatherforecast.model.Currently;
import com.android.priyanka.restfulapiweatherforecast.model.WeatherInformation;
import com.android.priyanka.restfulapiweatherforecast.services.WeatherService;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherServiceProvider {
    private static final String TAG = WeatherServiceProvider.class.getSimpleName();
    private static final String BASE_URL = "https://api.darksky.net/forecast/d7ad82aa80395ec75de0f18768ee6f1e/";
    private Retrofit retrofit;

  private Retrofit getRetrofit() {
         if (this.retrofit == null) {
             this.retrofit = new Retrofit.Builder()
                     .baseUrl(BASE_URL)
                     .addConverterFactory(GsonConverterFactory.create())
                     .build();

         }
         return this.retrofit;

    }

public void getWeather(double lat, double lon) {

    WeatherService weatherService = getRetrofit().create(WeatherService.class);
    Call<WeatherInformation> weatherInformationCall = weatherService.getWeatherInformation(lat, lon);
    weatherInformationCall.enqueue(new Callback<WeatherInformation>() {
        @Override
        public void onResponse(Call<WeatherInformation> call, Response<WeatherInformation> response) {
            WeatherInformation weatherInformation = response.body();
            if(weatherInformation!=null) {

                Currently currently = weatherInformation.getCurrently();
                Log.i(TAG, "temperature: " + currently.getTemperature());
                EventBus.getDefault().post(new WeatherEvent(weatherInformation));
            }
            else{
                //if secret key is invalid
                Log.e(TAG,"onResponse: Check secret key");
                EventBus.getDefault().post(new ErrorEvent("No weather data available"));
            }
        }
//if url is wrong
        @Override
        public void onFailure(Call<WeatherInformation> call, Throwable t) {
            Log.e(TAG,"onFailure: Unable to get weather data");
            EventBus.getDefault().post(new ErrorEvent("Unable to connect weather server"));
           // Toast.makeText("Unable to get weather data", Toast.LENGTH_SHORT).show();
        }
    });

}

//set weather to textview without using eventbus
/*
    public void getWeather(double lat, double lon, Callback callback) {

        WeatherService weatherService = getRetrofit().create(WeatherService.class);
        Call<WeatherInformation> weatherInformationCall = weatherService.getWeatherInformation(lat, lon);
        weatherInformationCall.enqueue(callback);

    }*/
    //set weather to textview  using eventbus



}
