package com.example.milek.paliwomierz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Milek on 2017-01-12.
 */
public class TankowanieImpl {



    private static final String tabela_tankowania = "tankowania";
    private static final String id_tan = "id_tan";
    private static final String id_sam = "id_sam";
    private static final String litry = "litry";
    private static final String cena = "cena";
    private static final String data_tan = "data";


    private SQLiteDatabase db;
    private Context context;
    private BazaDanych baza;


    public TankowanieImpl() {}


    public TankowanieImpl(Context context, BazaDanych baza) {
        this.baza=baza;
        this.context = context;
    }



    public TankowanieImpl open(){
       // baza = new BazaDanych(context, DB_NAME, null, DB_VERSION);
        try {
            db = baza.getWritableDatabase();
        } catch (SQLException e) {
            db = baza.getReadableDatabase();
        }
        return this;
    }
    public void close() {
        baza.close();
    }

    public long DodajTankowanie(Tankowanie task) {

        ContentValues newTodoValues = new ContentValues();
        newTodoValues.put(id_sam, task.getId_samochodu());
        newTodoValues.put(litry, task.getLitry());
        newTodoValues.put(cena, task.getCena_za_litr());
        newTodoValues.put(data_tan, task.getData());
        return db.insert(tabela_tankowania, null, newTodoValues);
    }



    public boolean UaktualnijTankowanie(Tankowanie task) {
        int idP = task.getId();
        int id_samP = task.getId_samochodu();
        float litryP = task.getLitry();
        float cenaP = task.getCena_za_litr();
        String dataP = task.getData();

        return UaktualnijTankowanie(idP, id_samP,litryP, cenaP, dataP);
    }

    public boolean UaktualnijTankowanie(int idP,int id_samP,float litryP, float cenaP, String dataP) {
        String where = id_tan + "=" + idP;

        ContentValues updateProductValues = new ContentValues();
        updateProductValues.put(id_sam, id_samP);
        updateProductValues.put(litry, litryP);
        updateProductValues.put(cena, cenaP);
        updateProductValues.put(data_tan, dataP);
        return db.update(tabela_tankowania, updateProductValues, where, null) > 0;
    }

    public List<Tankowanie> PobierzWszystkieTankowania() {

        List<Tankowanie> pom =new ArrayList<Tankowanie>();

        String[] columns = {id_tan, id_sam, litry, cena, data_tan};
        Cursor cursor=  db.query(tabela_tankowania,columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Tankowanie item = new Tankowanie();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setId_samochodu(Integer.parseInt(cursor.getString(1)));
                item.setLitry(Float.parseFloat(cursor.getString(2)));
                item.setCena_za_litr(Float.parseFloat(cursor.getString(3)));
                item.setData(cursor.getString(4));

                pom.add(item);
            } while (cursor.moveToNext());
        }
        return pom;
    }


    public List<Tankowanie> PobierzTankowaniaSamochodu(int idP) {

        String where = id_sam + "=" + idP;

        List<Tankowanie> pom =new ArrayList<Tankowanie>();

        String[] columns = {id_tan, id_sam, litry, cena, data_tan};
        Cursor cursor=  db.query(tabela_tankowania,columns, where, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Tankowanie item = new Tankowanie();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setId_samochodu(Integer.parseInt(cursor.getString(1)));
                item.setLitry(Float.parseFloat(cursor.getString(2)));
                item.setCena_za_litr(Float.parseFloat(cursor.getString(3)));
                item.setData(cursor.getString(4));

                pom.add(item);
            } while (cursor.moveToNext());
        }
        return pom;
    }

    public boolean UsunTankowanie(long id){

        String where = id_tan + "=" + id;
        return db.delete(tabela_tankowania, where, null) > 0;

    }
}


































