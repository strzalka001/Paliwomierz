package com.example.milek.paliwomierz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class SzczegolyTankowan extends AppCompatActivity {

    ListView SzczegolyTankowan;
    int id_sam;
    float spalanie;
    TankowanieImpl db;
    AdapterListyTankowania adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_szczegoly_tankowan);

        SzczegolyTankowan = (ListView) findViewById(R.id.ListViewTankowania);
        id_sam = Integer.parseInt(this.getIntent().getExtras().getString("id"));
        spalanie = Float.parseFloat(this.getIntent().getExtras().getString("spalanie"));
        Toast.makeText(SzczegolyTankowan.this, "Id: " + id_sam, Toast.LENGTH_SHORT).show();

        BazaDanych baza = BazaDanych.PobierzBazeDanych(this, "bazadanych.db", null, 1);
        db = new TankowanieImpl(this,baza);
        db.open();

        adapter = new AdapterListyTankowania(this,db.PobierzTankowaniaSamochodu(id_sam));

        for (Tankowanie cn : db.PobierzTankowaniaSamochodu(id_sam)) {
            String log = "Id: " + cn.getId() + " ,id samochodu: " + cn.getId_samochodu() + " , Litry: " + cn.getLitry();
            Log.d("Name: ","Id: " + cn.getId() + " ,id samochodu: " + cn.getId_samochodu() + " , Litry: " + cn.getLitry());
        }


        //pole = (TextView) findViewById(R.id.textView4);
        SzczegolyTankowan.setAdapter(adapter);
        //SzczegolyTankowan.setClickable(true);




    }
}
