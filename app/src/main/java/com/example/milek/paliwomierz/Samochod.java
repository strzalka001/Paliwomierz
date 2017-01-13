package com.example.milek.paliwomierz;

/**
 * Created by Milek on 2017-01-12.
 */

public class Samochod {

    int id;
    String marka;
    String model;
    float spalanie;

    public Samochod() {
    }

    public Samochod(String marka, String model, float spalanie) {
        this.marka = marka;
        this.model = model;
        this.spalanie = spalanie;
    }

    public Samochod(int id, String marka, String model, float spalanie) {
        this.id = id;
        this.marka = marka;
        this.model = model;
        this.spalanie = spalanie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public float getSpalanie() {
        return spalanie;
    }

    public void setSpalanie(float spalanie) {
        this.spalanie = spalanie;
    }
}
