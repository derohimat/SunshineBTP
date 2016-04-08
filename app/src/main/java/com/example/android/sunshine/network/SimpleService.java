package com.example.android.sunshine.network;

import com.example.android.sunshine.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public final class SimpleService {
  public static final String API_URL = "http://api.openweathermap.org/data/2.5/forecast/";

  public interface OpenWeatherMap {
    @GET("daily?mode=json&units=metric")
    Call<ApiResponse> getWeather(
        @Query("q") String city,
        @Query("cnt") int count,
        @Query("appid") String repo);
  }
}