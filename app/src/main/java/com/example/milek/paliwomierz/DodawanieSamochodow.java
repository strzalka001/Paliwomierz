package com.example.milek.paliwomierz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DodawanieSamochodow extends AppCompatActivity {


    EditText marka, model, spalanie;
    Button dodaj;
    SamochodImpl db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodawanie_samochodow);

        marka = (EditText) findViewById(R.id.editTextMarka);
        model = (EditText) findViewById(R.id.editTextModel);
        spalanie = (EditText) findViewById(R.id.editTextSpalanie);
        dodaj = (Button) findViewById(R.id.buttonStworzSamochod);


        BazaDanych baza = BazaDanych.PobierzBazeDanych(this, "bazadanych.db", null, 1);

        db = new SamochodImpl(this,baza);
        db.open();

        addListenerOnButtonAddCar();
    }


    public void addListenerOnButtonAddCar() {
        final Context context = this;
        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                String markaP = marka.getText().toString();
                String modelP = model.getText().toString();
                float spalanieP = Float.parseFloat(spalanie.getText().toString());
                //Intent intent = new Intent(getApplicationContext(), GlowneOkno.class);
/*

                intent.putExtra("marka",markaP);
                intent.putExtra("model",modelP);
                intent.putExtra("spalanie",Float.toString(spalanieP));
                startActivity(intent);
*/

                db.DodajSamochod(new Samochod(markaP, modelP, spalanieP));
                Intent intent = new Intent(context, GlowneOkno.class);
                startActivity(intent);
            }
        });
    }



}
