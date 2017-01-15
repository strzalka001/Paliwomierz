package com.example.milek.paliwomierz;

import java.util.Date;

/**
 * Created by Milek on 2017-01-12.
 */

public class Tankowanie {

    int id;
    int id_samochodu;
    float litry;
    float cena_za_litr;
    String data;

    public Tankowanie() {}


    public Tankowanie(int id_samochodu, float litry, float cena_za_litr, String data) {
        this.id_samochodu = id_samochodu;
        this.litry = litry;
        this.cena_za_litr = cena_za_litr;
        this.data = data;
    }

    public Tankowanie(int id, int id_samochodu, float litry, float cena_za_litr, String data) {
        this.id = id;
        this.id_samochodu = id_samochodu;
        this.litry = litry;
        this.cena_za_litr = cena_za_litr;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_samochodu() {
        return id_samochodu;
    }

    public void setId_samochodu(int id_samochodu) {
        this.id_samochodu = id_samochodu;
    }

    public float getLitry() {
        return litry;
    }

    public void setLitry(float litry) {
        this.litry = litry;
    }

    public float getCena_za_litr() {
        return cena_za_litr;
    }

    public void setCena_za_litr(float cena_za_litr) {
        this.cena_za_litr = cena_za_litr;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
