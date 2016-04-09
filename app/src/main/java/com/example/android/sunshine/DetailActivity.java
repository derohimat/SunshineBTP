package com.example.android.sunshine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.sunshine.model.realm.WeatherRealm;
import com.example.android.sunshine.utils.DataManager;
import com.example.android.sunshine.utils.Utils;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView txtDetail = (TextView) findViewById(R.id.txtDetail);

//        String selectedWeather = getIntent().getStringExtra("detail");
//        if (selectedWeather != null) {
//            txtDetail.setText(selectedWeather);
//        } else {
//            txtDetail.setText("Data null");
//        }
        int position = getIntent().getIntExtra("position", 0);
        WeatherRealm weather = DataManager.findWeatherAt(this, position);
        String weatherString = Utils.getReadableDateString(weather.getDt()) + " - " + weather.getWeather().get(0).getMain()
                + " - " + Utils.formatHighLows(weather.getTemp().getMax(), weather.getTemp().getMin());
        txtDetail.setText(weatherString);

    }

}
