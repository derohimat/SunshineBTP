package com.example.android.sunshine.model.realm;

import io.realm.RealmObject;

public class CityRealm extends RealmObject {

    private String name;
    private CoordRealm coord;
    private String country;
    private int population;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoordRealm getCoord() {
        return coord;
    }

    public void setCoord(CoordRealm coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

}