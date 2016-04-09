package com.example.android.sunshine.model.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class WeatherRealm extends RealmObject {
    @PrimaryKey
    private long id;

    private int dt;
    private TempRealm temp;
    private double pressure;
    private int humidity;
    private double speed;
    private int deg;
    private int clouds;
    private double rain;
    private RealmList<WeatherEntityRealm> weather;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public TempRealm getTemp() {
        return temp;
    }

    public void setTemp(TempRealm temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public RealmList<WeatherEntityRealm> getWeather() {
        return weather;
    }

    public void setWeather(RealmList<WeatherEntityRealm> weather) {
        this.weather = weather;
    }

}