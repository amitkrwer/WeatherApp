package com.example.root.weatherapp.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.example.root.weatherapp.data.WeatherContract;
import com.example.root.weatherapp.utilities.NetworkUtils;
import com.example.root.weatherapp.utilities.OpenWeatherJsonUtils;

import java.net.URL;

public class WeatherSyncTask {

    synchronized public static void syncWeather(Context context) {

        try {

            URL weatherRequestUrl = NetworkUtils.getUrl(context);

            String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);

            ContentValues[] weatherValues = OpenWeatherJsonUtils
                    .getWeatherContentValuesFromJson(context, jsonWeatherResponse);


            if (weatherValues != null && weatherValues.length != 0) {
                ContentResolver weatherContentResolver = context.getContentResolver();
                weatherContentResolver.delete(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        null,
                        null);

                weatherContentResolver.bulkInsert(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        weatherValues);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
