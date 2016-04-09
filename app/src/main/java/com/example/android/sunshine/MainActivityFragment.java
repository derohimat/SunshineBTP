package com.example.android.sunshine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.sunshine.model.cloud.ApiResponse;
import com.example.android.sunshine.model.realm.RealmContent;
import com.example.android.sunshine.model.realm.WeatherRealm;
import com.example.android.sunshine.network.SimpleService;
import com.example.android.sunshine.utils.DataManager;
import com.example.android.sunshine.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
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
    private Realm mRealm;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);
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

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(mContext).build();
        Realm.deleteRealm(realmConfiguration);
        mRealm = Realm.getInstance(realmConfiguration);
        mRealm.beginTransaction();

        ListView listView = (ListView) rootView.findViewById(R.id.list_view_forecast);
        listView.setAdapter(mWeekForecastAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedWeather = mWeekForecastAdapter.getItem(position);
                Intent intent = new Intent(mContext, DetailActivity.class);
//                intent.putExtra("detail", selectedWeather);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(mContext, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_maps) {
            onPreferedLocationInMap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onPreferedLocationInMap() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        String location = sharedPreferences.getString(getString(R.string.pref_location_key),
                getString(R.string.pref_location_default));

        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", location)
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.e(MainActivityFragment.class.getSimpleName(), "Couldn't call " + location + ", no result for this");
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        loadFromDatabase();
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mRealm.close();
//    }

    private void loadFromDatabase() {

        mWeekForecastAdapter.clear();
        if (DataManager.existsRecord(mContext)) {
            RealmContent apiResponse = DataManager.findData(mContext);
            for (int i = 0; i < apiResponse.getList().size(); i++) {
                WeatherRealm weather = apiResponse.getList().get(i);
                String weatherString = Utils.getReadableDateString(weather.getDt()) + " - " + weather.getWeather().get(0).getMain()
                        + " - " + Utils.formatHighLows(weather.getTemp().getMax(), weather.getTemp().getMin());
                weekForecastString.add(weatherString);
            }
            mWeekForecastAdapter.notifyDataSetChanged();
        } else {
            loadFromCloud();
        }
    }

    private void loadFromCloud() {
        mWeekForecastAdapter.clear();

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(mContext);

        String location = sharedPreferences.getString(getString(R.string.pref_location_key),
                getString(R.string.pref_location_default));
        String units = sharedPreferences.getString(getString(R.string.pref_units_key),
                getString(R.string.pref_units_metric));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SimpleService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SimpleService.OpenWeatherMap service = retrofit
                .create(SimpleService.OpenWeatherMap.class);

        Call<ApiResponse> listCall = service.getWeather(location, units,
                7, "0e6c26e20ccb5a5166f7922aa42644a7");

        listCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call,
                                   Response<ApiResponse> response) {

                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    String s = new Gson().toJson(apiResponse);
                    mRealm.createOrUpdateObjectFromJson(RealmContent.class, s);

//                    for (int i = 0; i < apiResponse.getList().size(); i++) {
//                        Weather weather = apiResponse.getList().get(i);
//                        String weatherString = Utils.getReadableDateString(weather.getDt()) + " - " + weather.getWeather().get(0).getMain()
//                                + " - " + Utils.formatHighLows(weather.getTemp().getMax(), weather.getTemp().getMin());
//                        weekForecastString.add(weatherString);
//                    }
//                    mWeekForecastAdapter.notifyDataSetChanged();
                    loadFromDatabase();
                } else {
                    Toast.makeText(mContext, "Gagal Ambil GSON", Toast.LENGTH_LONG).show();
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
