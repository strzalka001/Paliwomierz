package com.example.milek.paliwomierz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SzczegolySamochodu extends AppCompatActivity {

    int id_sam;
    float spalanie;
    Button DodajTankowanie, SzczegolyTankowania;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_szczegoly_samochodu);


        id_sam = Integer.parseInt(this.getIntent().getExtras().getString("id"));
        spalanie = Float.parseFloat(this.getIntent().getExtras().getString("spalanie"));
        Toast.makeText(SzczegolySamochodu.this, "Id: " + id_sam, Toast.LENGTH_SHORT).show();

        DodajTankowanie = (Button) findViewById(R.id.buttonDodajTankowanie);
        SzczegolyTankowania = (Button) findViewById(R.id.buttonTankowania);




        PrzejdzDoDodawaniaTankowania();
        PrzejdzDoSzczegolowTankowania();
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
