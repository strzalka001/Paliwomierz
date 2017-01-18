package com.example.milek.paliwomierz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class SzczegolySamochodu extends AppCompatActivity {

    int id_sam;
    float spalanie;
    Button DodajTankowanie, SzczegolyTankowania;
    TextView koszty, sredniaCena, calkowiteLitry, calkowiteKm, iloscTankowan;
    float koszt=0, SredniaCena=0, CalkowiteLitry=0, CalkowiteKm=0, IloscTankowan=0;
    TankowanieImpl db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_szczegoly_samochodu);

        BazaDanych baza = BazaDanych.PobierzBazeDanych(this, "bazadanych.db", null, 1);
        db = new TankowanieImpl(this,baza);
        db.open();

        id_sam = Integer.parseInt(this.getIntent().getExtras().getString("id"));
        spalanie = Float.parseFloat(this.getIntent().getExtras().getString("spalanie"));


        DodajTankowanie = (Button) findViewById(R.id.buttonDodajTankowanie);
        SzczegolyTankowania = (Button) findViewById(R.id.buttonTankowania);
        koszty = (TextView) findViewById(R.id.textViewkoszty);
        sredniaCena = (TextView) findViewById(R.id.textViewSredniaCena);
        calkowiteLitry = (TextView) findViewById(R.id.textViewLitryZat);
        calkowiteKm = (TextView) findViewById(R.id.textViewKm);
        iloscTankowan = (TextView) findViewById(R.id.textViewLiczbaTan);

        Oblicz();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        koszty.setText(df.format(koszt) + " zł");
        sredniaCena.setText(df.format(SredniaCena) + " zł");
        calkowiteLitry.setText(df.format(CalkowiteLitry)+ " l");
        calkowiteKm.setText(df.format(CalkowiteKm) + " km");
        iloscTankowan.setText(df.format(IloscTankowan)+"");





        PrzejdzDoDodawaniaTankowania();
        PrzejdzDoSzczegolowTankowania();
    }
    @Override
    protected void onPause() {
        db.close();
        super.onPause();
    }

    @Override
    protected void onResume() {

        db.open();
        Oblicz();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        koszty.setText(df.format(koszt) + " zł");
        sredniaCena.setText(df.format(SredniaCena) + " zł");
        calkowiteLitry.setText(df.format(CalkowiteLitry)+ " l");
        calkowiteKm.setText(df.format(CalkowiteKm) + " km");
        iloscTankowan.setText(df.format(IloscTankowan)+"");

        super.onResume();

    }





    public void Oblicz() {
        koszt=0;
        SredniaCena=0;
        CalkowiteLitry=0;
        CalkowiteKm=0;
        IloscTankowan=0;
        for (Tankowanie t : db.PobierzTankowaniaSamochodu(id_sam)) {

            Log.d("Name: ","Id: " + t.getId() + " ,id samochodu: " + t.getId_samochodu() + " , Litry: " + t.getLitry());

            koszt+=t.getCena_za_litr() * t.getLitry();
            CalkowiteLitry+=t.getLitry();
            IloscTankowan++;
            CalkowiteKm += (100.0*t.getLitry())/spalanie;
        }

        if(koszt>0 && CalkowiteLitry>0)
            SredniaCena = koszt/CalkowiteLitry;

    }



    public void PrzejdzDoDodawaniaTankowania() {
        final Context context = this;
        DodajTankowanie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), DodawanieTankowania.class);
                intent.putExtra("id", Integer.toString(id_sam));
                startActivity(intent);
                }
        });
    }

    public void PrzejdzDoSzczegolowTankowania() {
        final Context context = this;
        SzczegolyTankowania.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(getApplicationContext(), SzczegolyTankowan.class);
                intent.putExtra("id", Integer.toString(id_sam));
                intent.putExtra("spalanie", Float.toString(spalanie));
                startActivity(intent);
            }
        });
    }




}
