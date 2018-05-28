package com.aprendiz.ragp.ensayo2d21;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class GestorDB extends SQLiteOpenHelper {
    public GestorDB(Context context) {
        super(context, "DatosM.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE DATOS (TITLE TEXT, DESCRIPTION TEXT, IMAGE TEXT, AUDIO TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Datos> listaRecuerdos(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DATOS",null);
        List<Datos> results = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                Datos recuerdo = new Datos();
                recuerdo.setTitle(cursor.getString(0));
                recuerdo.setImage(cursor.getString(2));
                recuerdo.setAudio(cursor.getString(3));

                results.add(recuerdo);

            }while (cursor.moveToNext());
        }
        return  results;

    }

}
