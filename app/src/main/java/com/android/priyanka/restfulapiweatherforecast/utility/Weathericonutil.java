package com.android.priyanka.restfulapiweatherforecast.utility;

import com.android.priyanka.restfulapiweatherforecast.R;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Weathericonutil {
    public static final Map<String,Integer> ICONS;

    static {
        Map<String, Integer > imageMap = new HashMap<>();
        imageMap.put("clear-day", R.drawable.ic_clearday);
        imageMap.put("clear-night",R.drawable.ic_clearnight);
        imageMap.put("rain",R.drawable.ic_rainy);
        imageMap.put("snow",R.drawable.ic_snow);
        imageMap.put("sleet",R.drawable.ic_sleet);
        imageMap.put("wind",R.drawable.ic_wave);
        imageMap.put("fog",R.drawable.ic_foggy);
        imageMap.put("cloudy",R.drawable.ic_cloud);
        imageMap.put("partly-cloudy-day",R.drawable.ic_cloud);

        ICONS = Collections.unmodifiableMap(imageMap);


    }
}
