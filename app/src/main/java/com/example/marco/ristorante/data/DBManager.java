package com.example.marco.ristorante.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

/**
 * Created by Marco on 07/03/2018.
 */

public class DBManager {

    private RistoranteDbHelper dbhelper;

    public DBManager(Context ctx)
    {
        dbhelper=new RistoranteDbHelper(ctx);
    }

    public boolean salva(String tipo_ordinazione, String specifiche_ordinazione, String ora_ordinazione, String tavolo_ordinazione, String note)
    {
        boolean fatto=false;
        SQLiteDatabase db=dbhelper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(DatabaseStrings.FIELD_tipo_ordine, tipo_ordinazione);
        cv.put(DatabaseStrings.FIELD_specifiche, specifiche_ordinazione);
        cv.put(DatabaseStrings.FIELD_ora, ora_ordinazione);
        cv.put(DatabaseStrings.FIELD_tavolo, tavolo_ordinazione);
        cv.put(DatabaseStrings.FIELD_note, note);
        try
        {
            db.beginTransaction();
            db.insertOrThrow(DatabaseStrings.TBL_NAME, null,cv);
            fatto=true;
            db.setTransactionSuccessful();
        }
        catch (SQLiteException sqle)
        {
            System.out.println(sqle);
        }
        finally {
            db.endTransaction();
            if(db.isOpen()){
                System.out.println('\n'+"DATABASE CONNESSO"+'\n');
            }else{
                System.out.println('\n'+"DATABASE NON CONNESSO"+'\n');
            }
        }
        return fatto;
    }

    public boolean delete(long id)
    {
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        try
        {
            if (db.delete(DatabaseStrings.TBL_NAME, DatabaseStrings.FIELD_id+"=?", new String[]{Long.toString(id)})>0)
                return true;
            return false;
        }
        catch (SQLiteException sqle)
        {
            return false;
        }

    }

    public Cursor query()
    {
        Cursor crs=null;
        try
        {
            SQLiteDatabase database=dbhelper.getReadableDatabase();
            crs=database.rawQuery("SELECT * FROM ordini",null);
        }
        catch(Exception sqle)
        {
            return null;
        }
        return crs;
    }

}
