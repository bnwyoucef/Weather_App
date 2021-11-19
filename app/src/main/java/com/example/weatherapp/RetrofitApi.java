package com.example.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitApi {
    @GET("weather?appid=9bbdece11457b311081d87df723a6c90&units=metric")
    Call<OpenWeather> getWeatherStatusBYName(@Query("q") String city);
    @GET("weather?appid=9bbdece11457b311081d87df723a6c90&units=metric")
    Call<OpenWeather> getWeatherStatusByCoordination(@Query("lat") double lat, @Query("lon") double lon);
}
