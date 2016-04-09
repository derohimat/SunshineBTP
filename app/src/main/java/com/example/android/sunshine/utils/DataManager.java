package com.example.android.sunshine.utils;

import android.content.Context;

import com.example.android.sunshine.model.realm.RealmContent;
import com.example.android.sunshine.model.realm.WeatherRealm;

import io.realm.Realm;
import io.realm.RealmResults;

public class DataManager {

    private DataManager() {
    }

    public static boolean existsRecord(Context context) {
        Realm realm = Realm.getInstance(context);
        RealmResults<RealmContent> contents = realm.allObjects(RealmContent.class);
        return contents.size() != 0;
    }

    public static RealmContent findData(Context context) {
        Realm realm = Realm.getInstance(context);
        RealmResults<RealmContent> apiResponses = realm.allObjects(RealmContent.class);
        return apiResponses.first();
    }

    public static WeatherRealm findWeatherAt(Context context, int idx) {
        Realm realm = Realm.getInstance(context);
        RealmResults<WeatherRealm> situations = realm.allObjects(WeatherRealm.class);
        situations.sort("id");
        if (situations.size() <= idx) {
            return null;
        }
        return situations.get(idx);
    }

}
