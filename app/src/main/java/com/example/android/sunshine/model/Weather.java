package com.example.android.sunshine.model;

import java.util.List;

public class Weather {

    /**
     * dt : 1459569600
     * temp : {"day":24.1,"min":20.77,"max":24.1,"night":20.77,"eve":23.73,"morn":24.1}
     * pressure : 926.88
     * humidity : 99
     * weather : [{"id":502,"main":"Rain","description":"heavy intensity rain","icon":"10d"}]
     * speed : 0.96
     * deg : 122
     * clouds : 20
     * rain : 15.48
     */

    private int dt;
    /**
     * day : 24.1
     * min : 20.77
     * max : 24.1
     * night : 20.77
     * eve : 23.73
     * morn : 24.1
     */

    private TempEntity temp;
    private double pressure;
    private int humidity;
    private double speed;
    private int deg;
    private int clouds;
    private double rain;
    /**
     * id : 502
     * main : Rain
     * description : heavy intensity rain
     * icon : 10d
     */

    private List<WeatherEntity> weather;

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public TempEntity getTemp() {
        return temp;
    }

    public void setTemp(TempEntity temp) {
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

    public List<WeatherEntity> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherEntity> weather) {
        this.weather = weather;
    }

    public static class TempEntity {
        private double day;
        private double min;
        private double max;
        private double night;
        private double eve;
        private double morn;

        public double getDay() {
            return day;
        }

        public void setDay(double day) {
            this.day = day;
        }

        public double getMin() {
            return min;
        }

        public void setMin(double min) {
            this.min = min;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public double getNight() {
            return night;
        }

        public void setNight(double night) {
            this.night = night;
        }

        public double getEve() {
            return eve;
        }

        public void setEve(double eve) {
            this.eve = eve;
        }

        public double getMorn() {
            return morn;
        }

        public void setMorn(double morn) {
            this.morn = morn;
        }
    }

    public static class WeatherEntity {
        private int id;
        private String main;
        private String description;
        private String icon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}