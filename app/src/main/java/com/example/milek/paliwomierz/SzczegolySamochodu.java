package com.example.milek.paliwomierz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SzczegolySamochodu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_szczegoly_samochodu);

        String idp = this.getIntent().getExtras().getString("id");

        int id = Integer.parseInt(idp);
        Toast.makeText(SzczegolySamochodu.this,
                "Id: " + id,
                Toast.LENGTH_SHORT).show();
    }
}
