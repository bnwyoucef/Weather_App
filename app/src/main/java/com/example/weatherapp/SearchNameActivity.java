package com.example.weatherapp;

import static com.example.weatherapp.MainActivity.SEND_INTENT;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchNameActivity extends AppCompatActivity {

    private TextView city,weatherDegree,weatherDescription,humidity, maxTemp, minTemp, pressure, windSpeed;
    private ImageView weatherIcon, searchIcon;
    private EditText searchEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_name);
        initViews();
        receiveData();
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String citySearched = searchEditText.getText().toString();
                if (TextUtils.isEmpty(citySearched)) {
                    searchEditText.setError("Enter city name please");
                    return;
                }else {
                    getWeatherData(citySearched);
                    searchEditText.setText("");
                }
            }
        });

    }

    /**
     * init the data by the data for the current location
     * **/
    private void receiveData() {
        Intent intent = getIntent();
        if (intent != null) {
            String data = intent.getStringExtra(SEND_INTENT);
            Gson gson = new Gson();
            OpenWeather openWeather = gson.fromJson(data, OpenWeather.class);
            if (openWeather != null) {
                city.setText(openWeather.getName() + " ," + openWeather.getSys().getCountry());
                weatherDegree.setText(String.valueOf(openWeather.getMain().getTemp()) + "°C");
                weatherDescription.setText(String.valueOf(openWeather.getWeather().get(0).getDescription()));
                humidity.setText(String.valueOf(openWeather.getMain().getHumidity()) + "%");
                maxTemp.setText(String.valueOf(openWeather.getMain().getTempMax()) + "°C");
                minTemp.setText(String.valueOf(openWeather.getMain().getTempMin()) + "°C");
                windSpeed.setText(String.valueOf(openWeather.getWind().getSpeed()) + " km/h");
                pressure.setText(String.valueOf(openWeather.getMain().getPressure()) + " mbar");
                String iconNumber = openWeather.getWeather().get(0).getIcon();
                Glide.with(SearchNameActivity.this)
                        .load("https://openweathermap.org/img/wn/" + iconNumber + "@2x.png")
                        .into(weatherIcon);
            }
        }
    }

    private void getWeatherData(String cityName) {
        RetrofitApi retrofitApi = RetrofitClass.getInstance().create(RetrofitApi.class);
        Call<OpenWeather> list = retrofitApi.getWeatherStatusBYName(cityName);
        list.enqueue(new Callback<OpenWeather>() {
            @Override
            public void onResponse(Call<OpenWeather> call, Response<OpenWeather> response) {
                if (response.isSuccessful()) {
                    city.setText(response.body().getName() + " ," + response.body().getSys().getCountry());
                    weatherDegree.setText(String.valueOf(response.body().getMain().getTemp()) + "°C");
                    weatherDescription.setText(String.valueOf(response.body().getWeather().get(0).getDescription()));
                    humidity.setText(String.valueOf(response.body().getMain().getHumidity()) + "%");
                    maxTemp.setText(String.valueOf(response.body().getMain().getTempMax()) + "°C");
                    minTemp.setText(String.valueOf(response.body().getMain().getTempMin()) + "°C");
                    windSpeed.setText(String.valueOf(response.body().getWind().getSpeed()) + " km/h");
                    pressure.setText(String.valueOf(response.body().getMain().getPressure()) + " mbar");
                    String iconNumber = response.body().getWeather().get(0).getIcon();
                    Glide.with(SearchNameActivity.this)
                            .load("https://openweathermap.org/img/wn/" + iconNumber + "@2x.png")
                            .into(weatherIcon);
                }else {
                    Toast.makeText(SearchNameActivity.this, "we can't get the city name please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OpenWeather> call, Throwable t) {

            }
        });
    }

    private void initViews() {
        searchEditText = findViewById(R.id.searchCityEditTxt);
        city = findViewById(R.id.searchCityName);
        weatherDegree = findViewById(R.id.searchWeatherValue);
        weatherDescription = findViewById(R.id.searchWeatherDescription);
        humidity = findViewById(R.id.searchHumidityValue);
        maxTemp = findViewById(R.id.searchMax_tempValue);
        minTemp = findViewById(R.id.searchMin_tempValue);
        pressure = findViewById(R.id.searchPressure);
        windSpeed = findViewById(R.id.searchWindSpeedValue);
        weatherIcon = findViewById(R.id.searchWeatherIcon);
        searchIcon = findViewById(R.id.searchIcon);
    }
}