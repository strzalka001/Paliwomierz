package com.example.milek.paliwomierz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Milek on 2017-01-12.
 */

public class SamochodImpl {

    private static final String DEBUG_TAG = "SqLite";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "bazadanych.db";
    private static final String tabela_samochody = "samochody";
    private static final String id = "id";
    private static final String marka = "marka";
    private static final String model = "model";
    private static final String spalanie = "spalanie";

    private SQLiteDatabase db;
    private Context context;
    private BazaDanych baza;

    public SamochodImpl() {}


    public SamochodImpl(Context context, BazaDanych baza) {
        this.baza=baza;
        this.context = context;
    }


    public SamochodImpl open(){
        baza = new BazaDanych(context, DB_NAME, null, DB_VERSION);
        try {
            db = baza.getWritableDatabase();
        } catch (SQLException e) {
            db = baza.getReadableDatabase();
        }
        return this;
    }
    public void close() {
        baza.close();
    }




    public void DodajSamochod(Samochod task) {
        ContentValues newTodoValues = new ContentValues();
        newTodoValues.put(marka, task.getMarka());
        newTodoValues.put(model, task.getModel());
        newTodoValues.put(spalanie, task.getSpalanie());
         db.insert(tabela_samochody, null, newTodoValues);
    }


    public boolean UaktualnijSamochod(Samochod task) {
        int idP = task.getId();
        String markaP = task.getMarka();
        String modelP = task.getModel();
        float spalanieP = task.getSpalanie();


        return UaktualnijSamochod(idP, markaP,modelP, spalanieP);
    }

    public boolean UaktualnijSamochod(int idP,String markaP,String modelP, float spalanieP) {
        String where = id + "=" + idP;

        ContentValues updateProductValues = new ContentValues();
        updateProductValues.put(marka, markaP);
        updateProductValues.put(model, modelP);
        updateProductValues.put(spalanie, spalanieP);
        return db.update(tabela_samochody, updateProductValues, where, null) > 0;
    }



    public Samochod PobierzSamochod(int  idp) {
        String[] columns = {id, marka,model, spalanie};
        String where = id + "="+idp;
        Cursor cursor = db.query(tabela_samochody, columns, where, null, null, null, null);
        Samochod task = null;
        if(cursor != null && cursor.moveToFirst()) {
            String marka = cursor.getString(1);
            String model = cursor.getString(2);
            float spalanie = Float.parseFloat(cursor.getString(3));
            task = new Samochod(idp, marka, model,spalanie);
        }
        return task;
    }










    public List<Samochod> PobierzWszystkieSamochody() {

        List<Samochod> pom =new ArrayList<Samochod>();

        String[] columns = {id, marka, model, spalanie};
        Cursor cursor=  db.query(tabela_samochody,columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Samochod item = new Samochod();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setMarka(cursor.getString(1));
                item.setModel(cursor.getString(2));
                item.setSpalanie(Float.parseFloat(cursor.getString(3)));
                pom.add(item);
            } while (cursor.moveToNext());
        }
        return pom;
    }

    public boolean UsunSamochod(long id){

        String where = id + "=" + id;
        return db.delete(tabela_samochody, where, null) > 0;

    }

}























