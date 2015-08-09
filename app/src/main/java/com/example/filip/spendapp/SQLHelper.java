package com.example.filip.spendapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fida on 9.8.15.
 *
 *
 * třidata starajcise o komunikaci s DB kde jsou ulozeny vesktere transakce
 */
public class SQLHelper extends SQLiteOpenHelper{



    protected static final String DATABASE_NAME = "spendApp";
    protected static final int DATABASE_VERSION = 2;
    protected static final String TB_NAME = "Transaction";


    public SQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Vytvoreni tabulky
        db.execSQL("CREATE TABLE" + TB_NAME + "( ID INTEGER PRIMARY KEY" +
                "VALUE INTEGER NOT NULL" +
                "DATE TEXT NOT NULL" +
                "COMENT TEXT" +
                "CATEGORY TEXT NOT NULL" +
                "TYPE INTEGER NOT NULL);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
