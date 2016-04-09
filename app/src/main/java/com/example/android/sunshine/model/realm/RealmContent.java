package com.example.android.sunshine.model.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmContent extends RealmObject {
    @PrimaryKey
    private long id;

    public CityRealm city;
    public int cnt;
    public String cod;
    public String message;
    public RealmList<WeatherRealm> list;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CityRealm getCity() {
        return city;
    }

    public void setCity(CityRealm city) {
        this.city = city;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RealmList<WeatherRealm> getList() {
        return list;
    }

    public void setList(RealmList<WeatherRealm> list) {
        this.list = list;
    }
}