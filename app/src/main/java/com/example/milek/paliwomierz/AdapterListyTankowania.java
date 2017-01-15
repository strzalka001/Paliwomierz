package com.example.milek.paliwomierz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Milek on 2017-01-14.
 */

public class AdapterListyTankowania extends ArrayAdapter<Tankowanie> {

    List<Tankowanie> mylist;
    private final Context context;


    public AdapterListyTankowania(Context _context, List<Tankowanie> _mylist) {
        super(_context, R.layout.adapter_tankowania, _mylist);
        this.mylist = _mylist;
        this.context = _context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        convertView = new LinearLayout(getContext());
        String inflater = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
        convertView = vi.inflate(R.layout.adapter_tankowania, parent, false);

        // Product object
        Tankowanie product = getItem(position);

        //
        TextView data = (TextView) convertView.findViewById(R.id.textViewData);
        data.setText(product.getData());

        TextView cena = (TextView) convertView.findViewById(R.id.textViewCena);
        cena.setText(product.getCena_za_litr() + " zł");

        TextView litry = (TextView) convertView.findViewById(R.id.textViewLitry);
        litry.setText(product.getLitry() + " l");

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        float raz =  product.getCena_za_litr() * product.getLitry();
        TextView razem = (TextView) convertView.findViewById(R.id.textViewRazem);
        razem.setText(df.format(raz) + " zł");

        // show image
        //ImageView img = (ImageView)convertView.findViewById(R.id.image);

        // download image
        //img.setImageResource(product.img_url);
        return convertView;
    }

}


















