package com.example.android.sunshine;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.sunshine.model.ApiResponse;
import com.example.android.sunshine.model.Weather;
import com.example.android.sunshine.network.SimpleService;
import com.example.android.sunshine.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private ArrayAdapter<String> mWeekForecastAdapter;
    List<String> weekForecastString = new ArrayList<>();
    private Context mContext;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

//        List<String> weekForecastString = new ArrayList<>();
//        weekForecastString.add("Raining - 25 - 2-4-16");
//        weekForecastString.add("Raining - 20 - 3-4-16");
//        weekForecastString.add("Raining - 22 - 4-4-16");
//        weekForecastString.add("Hot - 27 - 5-4-16");
//        weekForecastString.add("Very Hot - 30 - 6-4-16");
//        weekForecastString.add("Very Hot - 33 - 7-4-16");
//        weekForecastString.add("Very Hot - 33 - 8-4-16");

        mWeekForecastAdapter = new ArrayAdapter<>(mContext,
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                weekForecastString);

        ListView listView = (ListView) rootView.findViewById(R.id.list_view_forecast);
        listView.setAdapter(mWeekForecastAdapter);

        loadData();

        return rootView;
    }

    private void loadData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SimpleService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SimpleService.OpenWeatherMap service = retrofit
                .create(SimpleService.OpenWeatherMap.class);

        Call<ApiResponse> listCall = service.getWeather("Bandung",
                7, "2528ef91b3d13e10e3631b11bf8b5550");

        listCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call,
                                   Response<ApiResponse> response) {

                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    for (int i = 0; i < apiResponse.getList().size(); i++) {
                        Weather weather = apiResponse.getList().get(i);
                        String weatherString = Utils.getReadableDateString(weather.getDt()) + " - " + weather.getWeather().get(0).getMain()
                                + " - " + Utils.formatHighLows(weather.getTemp().getMax(), weather.getTemp().getMin());
                        weekForecastString.add(weatherString);
                    }
                    mWeekForecastAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call,
                                  Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
