package com.example.marco.ristorante.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Marco on 27/02/2018.
 */

public class RistoranteDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ristorante.db";

    private static final int DATABASE_VERSION = 1;

    public RistoranteDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ORDINI_TABLE="CREATE TABLE "+DatabaseStrings.TBL_NAME+
                " ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                DatabaseStrings.FIELD_tipo_ordine+" TEXT," +
                DatabaseStrings.FIELD_specifiche+" TEXT," +
                DatabaseStrings.FIELD_ora+" TEXT," +
                DatabaseStrings.FIELD_tavolo+" TEXT," +
                DatabaseStrings.FIELD_note+" TEXT);" +
                "INSERT INTO ordini("+DatabaseStrings.FIELD_tipo_ordine+","+DatabaseStrings.FIELD_specifiche+","+DatabaseStrings.FIELD_ora+","+DatabaseStrings.FIELD_tavolo+","+DatabaseStrings.FIELD_note+
                ") VALUES ('PRIMO PIATTO','BIGOLI ALLA CARBONARA','20.00','12','FORMAGGIO');";

        db.execSQL(SQL_CREATE_ORDINI_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
