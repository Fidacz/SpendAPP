package com.example.filip.spendapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.filip.spendapp.data.Category;
import com.example.filip.spendapp.data.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    protected static final String ID_TRANSACTION = "ID";
    //todo prejmenovat
    protected static final String VALUE = "Value";
    protected static final String YEAR = "Year";
    protected static final String MONTH = "Month";
    protected static final String DAY = "Day";
    protected static final String TIME = "Time";
    protected static final String COMENT = "Coment";
    protected static final String CATEGORY = "Category";
    protected static final String TYPE = "Type";
    protected static final String IS_TRANSACTION_EXPORTED_TO_XML = "isTrasactionExportedToXML";


    protected static final String TB_CATEGORY = "CategoryTab";
    protected static final String ID_CATEGORY = "ID";
    protected static final String NAME_CATEGORY = "Name";
    protected static final String MASTER_CATEGORY = "MasterCategory";



    public SQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Vytvoreni tabulky

        db.execSQL("CREATE TABLE " + TB_CATEGORY + "(" + ID_CATEGORY + " INTEGER PRIMARY KEY, " + NAME_CATEGORY + " TEXT NOT NULL UNIQUE, " + MASTER_CATEGORY + " TEXT)");
        db.execSQL("CREATE TABLE " + TB_TRANSACTION + "(ID INTEGER PRIMARY KEY, Value REAL NOT NULL, Day INTEGER NOT NULL, Month INTEGER NOT NULL, Year INTEGER NOT NULL, Time STRING NOT NULL, Coment TEXT,Category INTEGER ,Type INTEGER NOT NULL, isTrasactionExportedToXML INTEGER, FOREIGN KEY(Category) REFERENCES CategoryTab(ID))"); //// TODO: 17. 11. 2015 opravit


        //defaultni kategorie

        //predpripravene kategorie
        ContentValues values = new ContentValues();
        values.put(ID_CATEGORY,0);
        values.put(NAME_CATEGORY,"Jídlo");
        db.insert(TB_CATEGORY, null, values);
        values=null;

        values = new ContentValues();
        values.put(ID_CATEGORY, 1);
        values.put(NAME_CATEGORY,"Příjem");
        db.insert(TB_CATEGORY, null, values);
        values=null;

        values = new ContentValues();
        values.put(ID_CATEGORY, 2);
        values.put(NAME_CATEGORY,"Práce");
        values.put(MASTER_CATEGORY,"Příjem");
        db.insert(TB_CATEGORY, null, values);
        values=null;

        values = new ContentValues();
        values.put(ID_CATEGORY,3);
        values.put(NAME_CATEGORY,"Práce");
        values.put(MASTER_CATEGORY,"Ostatní");
        db.insert(TB_CATEGORY, null, values);
        values=null;

        values = new ContentValues();
        values.put(ID_CATEGORY,4);
        values.put(NAME_CATEGORY,"Doprava");
        db.insert(TB_CATEGORY, null, values);
        values=null;

        // db.close();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void addTransaction (Transaction transaction){
        //ulozeni transakce do DB
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID_TRANSACTION,transaction.getId());
        values.put(VALUE,transaction.getValue());
        values.put(DAY,transaction.getDay());
        values.put(MONTH,transaction.getMonth());
        values.put(YEAR,transaction.getYear());
        values.put(TIME,transaction.getHour()+":"+transaction.getMin());
        values.put(CATEGORY,transaction.getCategory().getId());
        values.put(COMENT,transaction.getComment());
        values.put(TYPE,transaction.getType());
        values.put(IS_TRANSACTION_EXPORTED_TO_XML, transaction.getIsTrasactionExportedToXML());

        db.insert(TB_TRANSACTION, null, values);
        db.close();
    }

    public void delteTransaction(int ID){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TB_TRANSACTION +
                " WHERE ID=" + ID);

        db.close();
    }



    public Transaction getTransaction (int ID){
        SQLiteDatabase db = this.getWritableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_TRANSACTION + " WHERE ID=" + ID, null);
        cursor.moveToFirst();

        int id = cursor.getInt(0);
        double value = cursor.getDouble(1);
        String date = cursor.getString(5)+" "+ cursor.getString(2) +"."+ cursor.getString(3)+ "." + cursor.getString(4);
        String coment = cursor.getString(6);
        Category category = getCategory(cursor.getInt(7));
        int type = cursor.getInt(8);
        int isTrasactionExportedToXML = cursor.getInt(9);
        Transaction transaction = new Transaction(id, value, date, coment, category, type, isTrasactionExportedToXML);
        db.close();
        return transaction;
    }


    public ArrayList<Transaction> getTransactions (){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Transaction> transactions = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_TRANSACTION, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                double value = cursor.getDouble(1);
                String date = cursor.getString(5)+" "+ cursor.getString(2) +"."+ cursor.getString(3)+ "." + cursor.getString(4);
                String coment = cursor.getString(6);
                Category category = getCategory(cursor.getInt(7));
                int type = cursor.getInt(8);
                int isTrasactionExportedToXML = cursor.getInt(9);
                Transaction transaction = new Transaction(id, value, date, coment, category, type, isTrasactionExportedToXML);
                transactions.add(transaction);
            } while (cursor.moveToNext());
        }

        db.close();
        return transactions;
    }

    public ArrayList<Transaction> getNotExportedTransactions (){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Transaction> transactions = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_TRANSACTION + " WHERE " +IS_TRANSACTION_EXPORTED_TO_XML + " =0" , null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                double value = cursor.getDouble(1);
                String date = cursor.getString(5)+" "+ cursor.getString(2) +"."+ cursor.getString(3)+ "." + cursor.getString(4);
                String coment = cursor.getString(6);
                Category category = getCategory(cursor.getInt(7));
                int type = cursor.getInt(8);
                int isTrasactionExportedToXML = cursor.getInt(9);
                Transaction transaction = new Transaction(id, value, date, coment, category, type, isTrasactionExportedToXML);
                transactions.add(transaction);
            } while (cursor.moveToNext());
        }

        db.close();
        return transactions;
    }

    public ArrayList<Transaction> getTransactionsByCategory (int categoryID){
        //získání transakcí podle categorie
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Transaction> transactions = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_TRANSACTION + " WHERE " + CATEGORY + "=" + categoryID , null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                double value = cursor.getDouble(1);
                String date = cursor.getString(5)+" "+ cursor.getString(2) +"."+ cursor.getString(3)+ "." + cursor.getString(4);
                String coment = cursor.getString(6);
                Category category = getCategory(cursor.getInt(7));
                int type = cursor.getInt(8);
                int isTrasactionExportedToXML = cursor.getInt(9);
                Transaction transaction = new Transaction(id, value, date, coment, category, type, isTrasactionExportedToXML);
                transactions.add(transaction);
            } while (cursor.moveToNext());
        }

        db.close();
        return transactions;
    }


    public ArrayList<Transaction> getMonthTransaction(int month, int year){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Transaction> transactions = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_TRANSACTION + " WHERE " +MONTH + " =" + month+" AND "+YEAR+" = "+year , null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                double value = cursor.getDouble(1);
                String date = cursor.getString(5)+" "+ cursor.getString(2) +"."+ cursor.getString(3)+ "." + cursor.getString(4);
                String coment = cursor.getString(6);
                Category category = getCategory(cursor.getInt(7));
                int type = cursor.getInt(8);
                int isTrasactionExportedToXML = cursor.getInt(9);
                Transaction transaction = new Transaction(id, value, date, coment, category, type, isTrasactionExportedToXML);
                transactions.add(transaction);
            } while (cursor.moveToNext());
        }

        db.close();
        return transactions;
    }


    public void updateTransaction(Transaction transaction){
        SQLiteDatabase db = this.getWritableDatabase();



        db.execSQL("UPDATE " + TB_TRANSACTION +
                " SET "+ VALUE+"="+transaction.getValue()+", "+ DAY+"="+transaction.getDay()+", "+ MONTH+"="+transaction.getMonth()+", "+ YEAR+"="+transaction.getYear()+", "+ TIME+"='"+transaction.getHour()+":"+transaction.getMin()+"', "+ COMENT+"='"+transaction.getComment()+"', "+ CATEGORY+"='"+transaction.getCategory().getId()+"', "+TYPE+"='"+transaction.getType()+"', "+ IS_TRANSACTION_EXPORTED_TO_XML+"="+transaction.getIsTrasactionExportedToXML()+
                " WHERE ID=" + transaction.getId());

        db.close();

    }

    public void updateTransactionExportedParametr(int id){
        //metoda nastavi u transakce priznak ze byla exportovana do XML
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE " + TB_TRANSACTION +
                " SET " + IS_TRANSACTION_EXPORTED_TO_XML + " = 1" +
                " WHERE ID = " + id);

        db.close();

    }



    public  int getMaxIDTransaction(){
        int ID = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT max(" + ID_TRANSACTION + ") FROM " + TB_TRANSACTION, null);
        cursor.moveToFirst();

        if (null != cursor.getString(0)) {
            return ID = Integer.valueOf(cursor.getString(0));
        } //pokud v DB neni zadna transakce tak vrati 0


        return 0;

    }










   public void addCategory(Category category) {
       //saving category to DB
       SQLiteDatabase db = this.getWritableDatabase();

       ContentValues values = new ContentValues();
       values.put(ID_CATEGORY, category.getId());
       values.put(NAME_CATEGORY,category.getName());
       values.put(MASTER_CATEGORY, category.getMasterCategory());


       db.insert(TB_CATEGORY, null, values);
       db.close();
   }

    public void updateCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (category.getMasterCategory() == null) {
            //nema zadneho mastra
            db.execSQL("UPDATE  " + TB_CATEGORY +
                    " SET " + NAME_CATEGORY + " = '" + category.getName() + "', " + MASTER_CATEGORY + " = NULL WHERE ID=" + category.getId());

        } else {
            db.execSQL("UPDATE  " + TB_CATEGORY +
                    " SET " + NAME_CATEGORY + " = '" + category.getName() + "', " + MASTER_CATEGORY + " = '" + category.getMasterCategory() +
                    "' WHERE ID=" + category.getId());
       }
        db.close();
    }

    public Category getCategory(int ID){

        SQLiteDatabase db = this.getWritableDatabase();
        Category category = new Category();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_CATEGORY + " WHERE ID=" + ID, null);
        cursor.moveToFirst();
        category.setId(Integer.valueOf(cursor.getString(0)));
        category.setName(cursor.getString(1));
        category.setMasterCategory(cursor.getString(2));

        db.close();
        return category;

    }
    public ArrayList<Category> getcategories(){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Category> categories = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_CATEGORY , null);

        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(Integer.valueOf(cursor.getString(0)));
                category.setName(cursor.getString(1));
                category.setMasterCategory(cursor.getString(2));
                categories.add(category);
            } while (cursor.moveToNext());
        }

        db.close();
        return categories;

    }

    public void delteCategory(int ID){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TB_CATEGORY +
                " WHERE ID=" + ID);

        db.close();
    }



    public  int getMaxIDCategory(){
        int ID = 0;

        SQLiteDatabase db = this.getWritableDatabase();
         Cursor cursor = db.rawQuery("SELECT max(" + ID_CATEGORY + ") FROM " + TB_CATEGORY, null);
        cursor.moveToFirst();

        if (null != cursor.getString(0)) {
            return ID = Integer.valueOf(cursor.getString(0));
        } //pokud v DB neni zadna transakce tak vrati 0
        return 0;
    }

    public ArrayList<Category> getMasterCategories(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Category> categories = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_CATEGORY + " WHERE "+ MASTER_CATEGORY +" IS NULL ORDER BY " + NAME_CATEGORY +" COLLATE NOCASE ", null);

        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(Integer.valueOf(cursor.getString(0)));
                category.setName(cursor.getString(1));
                category.setMasterCategory(cursor.getString(2));
                categories.add(category);
            } while (cursor.moveToNext());
        }

        db.close();
        return categories;
    }

    public ArrayList<Category> getSlaveCategories(String masterCategoryName){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Category> categories = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_CATEGORY + " WHERE "+ MASTER_CATEGORY + " = '" + masterCategoryName + "' ORDER BY " + NAME_CATEGORY +" COLLATE NOCASE ", null);

        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(Integer.valueOf(cursor.getString(0)));
                category.setName(cursor.getString(1));
                category.setMasterCategory(cursor.getString(2));
                categories.add(category);
            } while (cursor.moveToNext());
        }

        db.close();
        return categories;
    }






}
