package com.example.filip.spendapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.filip.spendapp.data.Category;
import com.example.filip.spendapp.data.Transaction;

import java.util.ArrayList;

/**
 * Created by fida on 9.8.15.
 *
 *
 * třidata starajcise o komunikaci s DB kde jsou ulozeny vesktere transakce
 */
public class SQLHelper extends SQLiteOpenHelper{



    protected static final String DATABASE_NAME = "spendApp";
    protected static final int DATABASE_VERSION = 1;

    protected static final String TB_TRANSACTION = "TransactionTab";
    protected static final String ID = "ID";
    protected static final String VALUE = "Value";
    protected static final String DATE = "Date";
    protected static final String COMENT = "Coment";
    protected static final String CATEGORY = "Category";
    protected static final String TYPE = "Type";





    public SQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Vytvoreni tabulky

        db.execSQL("CREATE TABLE " + TB_TRANSACTION + "(ID INTEGER PRIMARY KEY, Value REAL NOT NULL, Date TEXT NOT NULL, Coment TEXT, Category TEXT NOT NULL, Type INTEGER NOT NULL)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void addTransaction (Transaction transaction){
        //ulozeni transakce do DB
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID,transaction.getId());
        values.put(VALUE,transaction.getValue());
        values.put(DATE,transaction.getDate());
        values.put(CATEGORY,transaction.getCategory());
        values.put(COMENT,transaction.getComment());
        values.put(TYPE,transaction.getType());

        db.insert(TB_TRANSACTION, null, values);
        db.close();
    }

    public void delteTransaction(int ID){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM" + TB_TRANSACTION +
        "WHERE ID=" + ID);

        db.close();
    }



    public Transaction getTransaction (int ID){

        SQLiteDatabase db = this.getWritableDatabase();
        Transaction transaction = new Transaction();

        Cursor cursor = db.rawQuery("SELECT * FROM" + TB_TRANSACTION + "WHERE ID=" + ID, null);

        transaction.setId(Integer.valueOf(cursor.getString(0)));
        transaction.setValue(Double.valueOf(cursor.getString(1)));
        transaction.setDate(cursor.getString(2));
        transaction.setComent(cursor.getString(3));
        transaction.setCategory(cursor.getString(4));
        transaction.setType(Integer.valueOf(cursor.getString(5)));


        db.close();
        return transaction;
    }


    public ArrayList<Transaction> getTransactions (){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Transaction> transactions = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM" + TB_TRANSACTION, null);

        if (cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction();
                transaction.setId(Integer.valueOf(cursor.getString(0)));
                transaction.setValue(Double.valueOf(cursor.getString(1)));
                transaction.setDate(cursor.getString(2));
                transaction.setComent(cursor.getString(3));
                transaction.setCategory(cursor.getString(4));
                transaction.setType(Integer.valueOf(cursor.getString(5)));
                transactions.add(transaction);
            } while (cursor.moveToNext());
        }

        db.close();
        return transactions;
    }
}
