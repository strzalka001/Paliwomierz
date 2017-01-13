package com.example.milek.paliwomierz;

/**
 * Created by Milek on 2017-01-12.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BazaDanych extends SQLiteOpenHelper {

    private static BazaDanych bazadanych;

    private static final String DEBUG_TAG = "SqLite";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "bazadanych.db";

    private static final String tabela_samochody = "samochody";
    private static final String id = "id";
    private static final String marka = "marka";
    private static final String model = "model";
    private static final String spalanie = "spalanie";

    private static final String tabela_tankowania = "tankowania";
    private static final String id_tan = "id_tan";
    private static final String id_sam = "id_sam";
    private static final String litry = "litry";
    private static final String cena = "cena";
    private static final String data = "data";




    String UTWORZ_SAMOCHODY = "CREATE TABLE " + tabela_samochody + "("
            + id + " INTEGER PRIMARY KEY," + marka + " TEXT,"
            + model + " TEXT," + spalanie + " TEXT" + ");";

    String UTWORZ_TANKOWANIA = "CREATE TABLE " + tabela_tankowania + "("
            + id_tan + " INTEGER PRIMARY KEY," + id_sam + " TEXT,"
            + litry + " TEXT," + cena + " TEXT," + data + " TEXT" + ");";

    String USUN_SAMOCHODY =
            "DROP TABLE IF EXISTS " + tabela_samochody;

    String USUN_TANKOWANIA =
            "DROP TABLE IF EXISTS " + tabela_tankowania;


    public BazaDanych(Context context, String name,
                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static BazaDanych PobierzBazeDanych(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {


        if (bazadanych==null)
        {
            bazadanych = new BazaDanych(context, name, factory, version);
            return bazadanych;
        }
        else return bazadanych;
    }




    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(UTWORZ_SAMOCHODY);
        db.execSQL(UTWORZ_TANKOWANIA);

        Log.d(DEBUG_TAG, "Database creating...");
        Log.d(DEBUG_TAG, "Table " + tabela_samochody + " ver." + DB_VERSION + " created");
        Log.d(DEBUG_TAG, "Table " + tabela_tankowania + " ver." + DB_VERSION + " created");

    }
    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(USUN_SAMOCHODY);
        db.execSQL(USUN_TANKOWANIA);

        Log.d(DEBUG_TAG, "Database updating...");
        Log.d(DEBUG_TAG, "Table " + tabela_samochody + " updated from ver." + oldVersion + " to ver." + newVersion);
        Log.d(DEBUG_TAG, "Table " + tabela_tankowania + " updated from ver." + oldVersion + " to ver." + newVersion);
        Log.d(DEBUG_TAG, "All data is lost.");

        onCreate(db);
    }
}