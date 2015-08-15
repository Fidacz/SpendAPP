package com.example.filip.spendapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    protected static final String TB_NAME = "Transaction";


    public SQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Vytvoreni tabulky
        db.execSQL("CREATE TABLE" + TB_NAME + "( ID INTEGER PRIMARY KEY" +
                "Value DOUBLE NOT NULL" +
                "Date TEXT NOT NULL" +
                "Coment TEXT" +
                "Category TEXT NOT NULL" +
                "Type INTEGER NOT NULL);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void addTransaction (Transaction transaction){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO" + TB_NAME + "(ID, Value, Date, Coment, Category, Type)" +
                "VALUES (" + transaction.getId() +
                            transaction.getValue() +
                            transaction.getDate() +
                            transaction.getComment() +
                            transaction.getCategory() +
                            transaction.getType() + ")");
        db.close();
    }

    public void delteTransaction(int ID){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM" + TB_NAME +
        "WHERE ID=" + ID);

        db.close();
    }



    public Transaction getTransaction (int ID){

        SQLiteDatabase db = this.getWritableDatabase();
        Transaction transaction = new Transaction();

        Cursor cursor = db.rawQuery("SELECT * FROM" + TB_NAME + "WHERE ID=" + ID, null);

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

        Cursor cursor = db.rawQuery("SELECT * FROM" + TB_NAME , null);

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
