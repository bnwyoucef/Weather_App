package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private TextView cityName,weatherDegree,weatherDescription,humidity, maxTemp, minTemp, pressure, windSpeed;
    private ImageView weatherIcon;
    private FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiViews();
    }

    private void intiViews() {
        cityName = findViewById(R.id.cityName);
        weatherDegree = findViewById(R.id.weatherValue);
        weatherDescription = findViewById(R.id.weatherDescription);
        humidity = findViewById(R.id.humidityValue);
        maxTemp = findViewById(R.id.max_tempValue);
        minTemp = findViewById(R.id.min_tempValue);
        pressure = findViewById(R.id.pressure);
        windSpeed = findViewById(R.id.windSpeedValue);
        weatherIcon = findViewById(R.id.weatherIcon);
        floatingActionButton = findViewById(R.id.floatingActionBtn);
    }
}