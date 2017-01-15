package com.example.milek.paliwomierz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

                if(isEmpty(marka) || isEmpty(model) || isEmpty(spalanie)){
                    Toast.makeText(DodawanieSamochodow.this, "Wprawadź wszystkie dane", Toast.LENGTH_SHORT).show();
                }
                else {
                    String markaP = marka.getText().toString();
                    String modelP = model.getText().toString();
                    float spalanieP = Float.parseFloat(spalanie.getText().toString());
                    //Intent intent = new Intent(getApplicationContext(), GlowneOkno.class);
                    db.DodajSamochod(new Samochod(markaP, modelP, spalanieP));
                    db.close();
                    finish();
                }
            }
        });
    }


    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;
        return true;
    }
}
