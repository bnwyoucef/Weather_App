package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchNameActivity extends AppCompatActivity {

    private TextView  cityName,weatherDegree,weatherDescription,humidity, maxTemp, minTemp, pressure, windSpeed;
    private ImageView weatherIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_name);
        initViews();

    }

    private void initViews() {
        cityName = findViewById(R.id.searchCityName);
        weatherDegree = findViewById(R.id.searchWeatherValue);
        weatherDescription = findViewById(R.id.searchWeatherDescription);
        humidity = findViewById(R.id.searchHumidityValue);
        maxTemp = findViewById(R.id.searchMax_tempValue);
        minTemp = findViewById(R.id.searchMin_tempValue);
        pressure = findViewById(R.id.searchPressure);
        windSpeed = findViewById(R.id.searchWindSpeedValue);
        weatherIcon = findViewById(R.id.searchIcon);
    }
}