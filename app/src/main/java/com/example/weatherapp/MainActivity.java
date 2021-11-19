package com.example.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView cityName,weatherDegree,weatherDescription,humidity, maxTemp, minTemp, pressure, windSpeed;
    private ImageView weatherIcon;
    private FloatingActionButton floatingActionButton;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Double lat, lon;
    private OpenWeather sendResponse;
    public static final String SEND_BUNDLE = "bundle";
    public static final String SEND_INTENT = "intent";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiViews();
        cityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchNameActivity.class);
                intent.putExtra(SEND_INTENT, sendResponse);
                startActivity(intent);
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lat = location.getLatitude();
                lon = location.getLongitude();
                getWeatherData();
            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 1);
        }else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 50, locationListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && permissions.length > 0 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 50, locationListener);
        }
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

    private void getWeatherData() {
        RetrofitApi retrofitApi = RetrofitClass.getInstance().create(RetrofitApi.class);
        Call<OpenWeather> list = retrofitApi.getWeatherStatusByCoordination(lat, lon);
        list.enqueue(new Callback<OpenWeather>() {
            @Override
            public void onResponse(Call<OpenWeather> call, Response<OpenWeather> response) {
                sendResponse = response.body();
                cityName.setText(response.body().getName() + " ," +response.body().getSys().getCountry());
                weatherDegree.setText(String.valueOf(response.body().getMain().getTemp()) + "°C");
                weatherDescription.setText(response.body().getWeather().get(0).getDescription());
                humidity.setText(String.valueOf(response.body().getMain().getHumidity()));
                maxTemp.setText(String.valueOf(String.valueOf(response.body().getMain().getTempMax())) + "°C");
                minTemp.setText(String.valueOf(String.valueOf(response.body().getMain().getTempMin())) + "°C");
                pressure.setText(String.valueOf(response.body().getMain().getPressure()));
                windSpeed.setText(String.valueOf(response.body().getWind().getSpeed()));
                String iconNumber = response.body().getWeather().get(0).getIcon();
                Glide.with(MainActivity.this)
                        .load("https://openweathermap.org/img/wn/" + iconNumber +"@2x.png")
                        .into(weatherIcon);
            }

            @Override
            public void onFailure(Call<OpenWeather> call, Throwable t) {

            }
        });
    }
}