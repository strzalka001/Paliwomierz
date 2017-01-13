package com.example.milek.paliwomierz;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GlowneOkno extends AppCompatActivity {

    AdapterListySamochodow adapter;
    List samochody;
    Button dodajSamochod, usunSamochod;
    ListView listaSamochodow;
    Samochod auto;
    SamochodImpl db;
    int czy_usunac = 0;
    RelativeLayout layout;
    Context context;
    int id_usuwanego=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glowne_okno);
        dodajSamochod = (Button) findViewById(R.id.buttonDodajSamochod);
        usunSamochod = (Button) findViewById(R.id.buttonUsunSamochod);
        listaSamochodow= (ListView) findViewById(R.id.ListViewSamochody);
        layout = (RelativeLayout) findViewById(R.id.activity_glowne_okno);
        context=this;

        samochody = new ArrayList();

        BazaDanych baza = BazaDanych.PobierzBazeDanych(this, "bazadanych.db", null, 1);

         db = new SamochodImpl(this,baza);
         db.open();

        db.DodajSamochod(new Samochod("kia","rio", 9.8f));
        db.DodajSamochod(new Samochod("honda","accord", 12.6f));
        db.DodajSamochod(new Samochod("fiat","panda", 7.8f));


        adapter = new AdapterListySamochodow(this,db.PobierzWszystkieSamochody());
        //DodajDoBazySamochod();


        for (Samochod cn : db.PobierzWszystkieSamochody()) {
            String log = "Id: " + cn.getId() + " ,Model: " + cn.getMarka() + " , Marka: " + cn.getModel();
            Log.d("Name: ", log);
        }

        listaSamochodow = (ListView) findViewById( R.id.ListViewSamochody);
        //pole = (TextView) findViewById(R.id.textView4);
        listaSamochodow.setAdapter(adapter);
        listaSamochodow.setClickable(true);

        addListenerOnButtonAddCarWindow();
        addListenerOnButtonDeleteCar();
        listaSamochodow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                auto =(Samochod) listaSamochodow.getItemAtPosition(position);
                // Toast.makeText(getApplicationContext(),product.getDesc(),Toast.LENGTH_LONG).show();
                //pole.setText(product.getDesc());

                if(czy_usunac==1) {
                    czy_usunac++;
                    id_usuwanego= auto.getId();
                }
                else if(czy_usunac==2) czy_usunac++;

                KlikniecieElementuListy();
            }
        });





    }

    public void addListenerOnButtonDeleteCar() {
        final Context context = this;
        usunSamochod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if(czy_usunac>0)
                {
                    czy_usunac=0;
                    layout.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                if(czy_usunac==0)
                {
                    czy_usunac=1;
                    layout.setBackgroundColor(Color.parseColor("#ff0000"));
                }

            }
        });
    }

    public void addListenerOnButtonAddCarWindow() {
        final Context context = this;
        dodajSamochod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                db.close();
                Intent intent = new Intent(context, DodawanieSamochodow.class);
                startActivity(intent);
                //adapter.notifyDataSetChanged();
            }
        });
    }


    public void KlikniecieElementuListy(){


        Log.d("Lista", "Klikniecie elementu");
        if(czy_usunac==0) {
            Intent intent = new Intent(getApplicationContext(), SzczegolySamochodu.class);
            intent.putExtra("id", Integer.toString(auto.getId()));
            startActivity(intent);
        }

        if(czy_usunac==2)
        {
            Toast.makeText(getApplicationContext(),"Wcisnij jeszcze raz aby usunac!",Toast.LENGTH_LONG).show();
        }
        if(czy_usunac==3 && id_usuwanego==auto.getId())
        {
            db.UsunSamochod(auto.getId());
            adapter.remove(auto);
            adapter.notifyDataSetChanged();
            czy_usunac=0;
            layout.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        else if(czy_usunac==3) {
            id_usuwanego=auto.getId();
            czy_usunac=2;
        }
    }

/*
    public void DodajDoBazySamochod() {
       if(this.getIntent().getExtras().getString("marka")!=null) {
           String marka1 = this.getIntent().getExtras().getString("marka");
           String model1 = this.getIntent().getExtras().getString("model");
           Float spalanie1 = Float.parseFloat(this.getIntent().getExtras().getString("spalanie"));
           db.DodajSamochod(new Samochod(marka1, model1, spalanie1));
           adapter.add(new Samochod(marka1, model1, spalanie1));
       }
    }
*/






}
