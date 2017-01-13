package com.example.milek.paliwomierz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Milek on 2017-01-13.
 */

public class AdapterListySamochodow extends ArrayAdapter<Samochod> {

    List<Samochod> mylist;
    private final Context context;

    public AdapterListySamochodow(Context _context, List<Samochod> _mylist) {
        super(_context, R.layout.adapter_samochody, _mylist);

        this.mylist = _mylist;
        this.context = _context;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        convertView = new LinearLayout(getContext());
        String inflater = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
        convertView = vi.inflate(R.layout.adapter_samochody, parent, false);

        // Product object
        Samochod product = getItem(position);
        //
        TextView txtTitle = (TextView) convertView.findViewById(R.id.nazwa);
        txtTitle.setText(product.getMarka() + ", " + product.getModel());

        TextView txtPrice = (TextView) convertView.findViewById(R.id.cena);
        txtPrice.setText(Float.toString(product.getSpalanie()) + " ");

        TextView txtDesc = (TextView) convertView.findViewById(R.id.opis);
        txtDesc.setText("id: " + product.getId());

        // show image
        //ImageView img = (ImageView)convertView.findViewById(R.id.image);

        // download image
        //img.setImageResource(product.img_url);

        return convertView;
    }

}