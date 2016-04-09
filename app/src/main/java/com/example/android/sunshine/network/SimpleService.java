package com.example.android.sunshine.network;

import com.example.android.sunshine.model.cloud.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public final class SimpleService {
  public static final String API_URL = "http://api.openweathermap.org/data/2.5/forecast/";

  public interface OpenWeatherMap {
    @GET("daily?mode=json")
    Call<ApiResponse> getWeather(
        @Query("q") String city,
        @Query("units") String units,
        @Query("cnt") int count,
        @Query("appid") String repo);
  }
}