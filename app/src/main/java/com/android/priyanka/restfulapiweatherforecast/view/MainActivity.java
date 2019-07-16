package com.android.priyanka.restfulapiweatherforecast.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.priyanka.restfulapiweatherforecast.R;
import com.android.priyanka.restfulapiweatherforecast.events.ErrorEvent;
import com.android.priyanka.restfulapiweatherforecast.events.WeatherEvent;
import com.android.priyanka.restfulapiweatherforecast.model.Currently;
import com.android.priyanka.restfulapiweatherforecast.util.WeatherServiceProvider;
import com.android.priyanka.restfulapiweatherforecast.utility.Weathericonutil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    //using Deark Sky API for weatherforecast app
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.textWeather)
    TextView textWeather;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.textViewSummary)
    TextView textViewSummary;
    @BindView(R.id.textTime)
    TextView textTime;
    @BindView(R.id.textDate)
    TextView textDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        requestCurrentWeather(19.075983, 72.877655);

        //moved into another class to make code cleaner
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.darksky.net/forecast/d7ad82aa80395ec75de0f18768ee6f1e/35.8265,-122.4233/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherService weatherService = retrofit.create(WeatherService.class);
       Call<WeatherInformation> weatherInformationCall = weatherService.getWeatherInformation();
       weatherInformationCall.enqueue(new Callback<WeatherInformation>() {
           @Override
           public void onResponse(Call<WeatherInformation> call, Response<WeatherInformation> response) {
             Currently currently = response.body().getCurrently();
               Log.i(TAG,"temperature: "+currently.getTemperature());
           }

           @Override
           public void onFailure(Call<WeatherInformation> call, Throwable t) {
                Log.e(TAG,"onFailure: Unable to get weather data");
           }
       });
*/
        DateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Date date = new Date();
        String currentDate = sdf.format(date);

        String dayOfWeek = new SimpleDateFormat("EE", Locale.ENGLISH).format(System.currentTimeMillis());
        String currentDay = dayOfWeek.toUpperCase();

        DateFormat sdfTime = new SimpleDateFormat("HH:mm aa");
        Date dateTime = new Date();
        String currentTime = sdfTime.format(dateTime);

        textDate.setText(currentDay+" "+currentDate);
        textTime.setText(currentTime);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeatherEvent(WeatherEvent weatherEvent) {
        Currently currently = weatherEvent.getWeatherInformation().getCurrently();

        Double tempFar=currently.getTemperature();
        String tempCel=String.valueOf(Math.round(convertFahrenheitToCelcius(tempFar)));
        textWeather.setText(tempCel+(char) 0x00B0);
       // textWeather.setText(String.valueOf(Math.round(currently.getTemperature())));

        textViewSummary.setText(currently.getSummary());

       /* Map<String, Integer > imageMap = new HashMap<>();
        imageMap.put("clear-day",R.drawable.ic_clearday);
        imageMap.put("clear-night",R.drawable.ic_clearnight);
        imageMap.put("rain",R.drawable.ic_rainy);
        imageMap.put("snow",R.drawable.ic_snow);
        imageMap.put("sleet",R.drawable.ic_sleet);
        imageMap.put("wind",R.drawable.ic_wave);
        imageMap.put("fog",R.drawable.ic_foggy);
        imageMap.put("cloudy",R.drawable.ic_cloud);
        imageMap.put("partly-cloudy-day",R.drawable.ic_cloud);

        imageView.setImageResource(imageMap.get(currently.getIcon()));*/

        imageView.setImageResource(Weathericonutil.ICONS.get(currently.getIcon()));
    }

    //set weather in textview without using eventbus
   /*
    private void requestCurrentWeather(double lat, double lon) {
        WeatherServiceProvider weatherServiceProvider = new WeatherServiceProvider();

        Callback callback = new Callback<WeatherInformation>() {


                @Override
                public void onResponse(Call<WeatherInformation> call, Response<WeatherInformation> response) {
                    Currently currently = response.body().getCurrently();
                    Log.i(TAG,"temperature: "+currently.getTemperature());
                    textWeather.setText(String.valueOf(Math.round(currently.getTemperature())));
                }

                @Override
                public void onFailure(Call<WeatherInformation> call, Throwable t) {
                    Log.e(TAG,"onFailure: Unable to get weather data");
                }
            };

        weatherServiceProvider.getWeather(19.075983, 72.877655, callback);
    }*/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorEvent(ErrorEvent errorEvent) {
        Toast.makeText(this, errorEvent.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    //set weather in textview using eventbus
    private void requestCurrentWeather(double lat, double lon) {
        WeatherServiceProvider weatherServiceProvider = new WeatherServiceProvider();
        weatherServiceProvider.getWeather(19.075983, 72.877655);
    }

    // Converts to celcius
    private float convertFahrenheitToCelcius(Double fahrenheit) {
        return (float) ((fahrenheit - 32) * 5 / 9);
    }
}
