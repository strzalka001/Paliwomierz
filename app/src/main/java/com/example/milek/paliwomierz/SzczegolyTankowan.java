package com.example.milek.paliwomierz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

public class SzczegolyTankowan extends AppCompatActivity {

    ListView SzczegolyTankowan;
    int id_sam;
    float spalanie;
    Tankowanie tank;
    TankowanieImpl db;
    AdapterListyTankowania adapter;
    int czy_usunac = 0;
    int id_usuwanego=-1;
    RelativeLayout layout;
    Button usuwanie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_szczegoly_tankowan);

        SzczegolyTankowan = (ListView) findViewById(R.id.ListViewTankowania);
        layout = (RelativeLayout) findViewById(R.id.activity_szczegoly_tankowan);
        usuwanie = (Button) findViewById(R.id.buttonUsunTankowanie);
        id_sam = Integer.parseInt(this.getIntent().getExtras().getString("id"));
        spalanie = Float.parseFloat(this.getIntent().getExtras().getString("spalanie"));
        Toast.makeText(SzczegolyTankowan.this, "Id: " + id_sam, Toast.LENGTH_SHORT).show();

        BazaDanych baza = BazaDanych.PobierzBazeDanych(this, "bazadanych.db", null, 1);
        db = new TankowanieImpl(this,baza);
        db.open();
        addListenerOnButtonDeleteTankowanie();
        adapter = new AdapterListyTankowania(this,db.PobierzTankowaniaSamochodu(id_sam));

        for (Tankowanie cn : db.PobierzTankowaniaSamochodu(id_sam)) {
            String log = "Id: " + cn.getId() + " ,id samochodu: " + cn.getId_samochodu() + " , Litry: " + cn.getLitry();
            Log.d("Name: ","Id: " + cn.getId() + " ,id samochodu: " + cn.getId_samochodu() + " , Litry: " + cn.getLitry());
        }


        //pole = (TextView) findViewById(R.id.textView4);
        SzczegolyTankowan.setAdapter(adapter);
        SzczegolyTankowan.setClickable(true);


        SzczegolyTankowan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tank =(Tankowanie) SzczegolyTankowan.getItemAtPosition(position);
                // Toast.makeText(getApplicationContext(),product.getDesc(),Toast.LENGTH_LONG).show();
                //pole.setText(product.getDesc());

                if(czy_usunac==1) {
                    czy_usunac++;
                    id_usuwanego= tank.getId();
                }
                else if(czy_usunac==2) czy_usunac++;

                KlikniecieElementuListy();
            }
        });
    }
    @Override
    protected void onPause() {
        db.close();
        super.onPause();
    }

    @Override
    protected void onResume() {
        db.open();
        super.onResume();
    }





    public void addListenerOnButtonDeleteTankowanie() {
        final Context context = this;
        usuwanie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if(czy_usunac==0)
                {
                    czy_usunac=1;
                    layout.setBackgroundColor((getResources().getColor(R.color.kolor_usuwania)));
                }
                else if(czy_usunac>0)
                {
                    czy_usunac=0;
                    layout.setBackgroundColor((getResources().getColor(R.color.tlo)));
                }


            }
        });
    }


    public void KlikniecieElementuListy(){


        Log.d("Lista", "Klikniecie elementu");


        if(czy_usunac==2)
        {
            Toast.makeText(getApplicationContext(),"Wcisnij jeszcze raz aby usunac!",Toast.LENGTH_LONG).show();
        }
        if(czy_usunac==3 && id_usuwanego==tank.getId())
        {
            db.UsunTankowanie(tank.getId());
            adapter.remove(tank);
            adapter.notifyDataSetChanged();
            czy_usunac=0;
            layout.setBackgroundColor(getResources().getColor(R.color.tlo));
            for (Tankowanie cn : db.PobierzWszystkieTankowania()) {
                String log = "Id: " + cn.getId() + " ,id samochodu: " + cn.getId_samochodu() + " , Litry: " + cn.getLitry();
                Log.d("blablabla: ","Id: " + cn.getId() + " ,id samochodu: " + cn.getId_samochodu() + " , Litry: " + cn.getLitry());
            }
        }

        else if(czy_usunac==3) {
            id_usuwanego=tank.getId();
            czy_usunac=2;
        }
    }















}
