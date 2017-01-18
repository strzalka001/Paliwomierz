package com.example.milek.paliwomierz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class DodawanieTankowania extends AppCompatActivity {

    int id_sam;
    EditText Elitry, Ecena, Edata;
    Button dodaj;
    TankowanieImpl db;
    BazaDanych baza;
    TextWatcher tw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodawanie_tankowania);

        id_sam = Integer.parseInt(this.getIntent().getExtras().getString("id"));
       // Toast.makeText(DodawanieTankowania.this, "Id: " + id_sam, Toast.LENGTH_SHORT).show();

        baza = BazaDanych.PobierzBazeDanych(this, "bazadanych.db", null, 1);
        db = new TankowanieImpl(this,baza);
        db.open();
        dodaj = (Button) findViewById(R.id.buttonDodaj);
        Elitry  = (EditText)findViewById(R.id.editTextLitry);
        Ecena  = (EditText)findViewById(R.id.editTextCena);
        Edata  = (EditText)findViewById(R.id.editTextData);

        //  Do prawidlowego formatu daty!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        TextWatcher tw = new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    Edata.setText(current);
                    Edata.setSelection(sel < current.length() ? sel : current.length());
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        };

        Edata.addTextChangedListener(tw);
        PrzejdzDoDodawaniaTankowania();



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






    public void PrzejdzDoDodawaniaTankowania() {
        final Context context = this;
        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

               if(isEmpty(Elitry) || isEmpty(Ecena) || isEmpty(Edata)){
                   Toast.makeText(DodawanieTankowania.this, "Wprawadź wszystkie dane", Toast.LENGTH_SHORT).show();
               }
               else {
                   float litry = Float.parseFloat(Elitry.getText().toString());
                   float cena = Float.parseFloat(Ecena.getText().toString());
                   String data = Edata.getText().toString();
                   db.DodajTankowanie(new Tankowanie(id_sam, litry, cena, data));
                   Toast.makeText(DodawanieTankowania.this, "Dodano!", Toast.LENGTH_SHORT).show();

                   Log.d("Blablalbla", "bla");


                   for (Tankowanie cn : db.PobierzWszystkieTankowania()) {
                       String log = "Id: " + cn.getId() + " ,id samochodu: " + cn.getId_samochodu() + " , Litry: " + cn.getLitry();
                       Log.d("blablabla: ","Id: " + cn.getId() + " ,id samochodu: " + cn.getId_samochodu() + " , Litry: " + cn.getLitry());
                   }







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
