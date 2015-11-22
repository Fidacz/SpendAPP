package com.example.filip.spendapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.filip.spendapp.data.Category;
import com.example.filip.spendapp.data.Transaction;

import java.util.ArrayList;
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
    protected static final String DATE = "Date";
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

        db.execSQL("CREATE TABLE " + TB_TRANSACTION + "(ID INTEGER PRIMARY KEY, Value REAL NOT NULL, Date TEXT NOT NULL, Coment TEXT, Category TEXT NOT NULL, Type INTEGER NOT NULL, isTrasactionExportedToXML INTEGER)"); //// TODO: 17. 11. 2015 opravit
        db.execSQL("CREATE TABLE " + TB_CATEGORY + "(" + ID_CATEGORY + " INTEGER PRIMARY KEY, " + NAME_CATEGORY + " TEXT NOT NULL, " + MASTER_CATEGORY + " TEXT)");

        //defaultni kategorie

        ContentValues values = new ContentValues();
        values.put(ID_CATEGORY,0);
        values.put(NAME_CATEGORY,"Jídlo");
        values.put(MASTER_CATEGORY,"");

        db.insert(TB_CATEGORY, null, values);

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
        values.put(DATE,transaction.getDate());
        values.put(CATEGORY,transaction.getCategory());
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
        Transaction transaction = new Transaction();

        Cursor cursor = db.rawQuery("SELECT * FROM" + TB_TRANSACTION + "WHERE ID=" + ID, null);

        transaction.setId(Integer.valueOf(cursor.getString(0)));
        transaction.setValue(Double.valueOf(cursor.getString(1)));
        transaction.setDate(cursor.getString(2));
        transaction.setComent(cursor.getString(3));
        transaction.setCategory(cursor.getString(4));
        transaction.setType(Integer.valueOf(cursor.getString(5)));
        transaction.setIsTrasactionExportedToXML(Integer.valueOf(cursor.getString(6)));

        db.close();
        return transaction;
    }


    public ArrayList<Transaction> getTransactions (){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Transaction> transactions = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_TRANSACTION, null);

        if (cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction();
                transaction.setId(Integer.valueOf(cursor.getString(0)));
                transaction.setValue(Double.valueOf(cursor.getString(1)));
                transaction.setDate(cursor.getString(2));
                transaction.setComent(cursor.getString(3));
                transaction.setCategory(cursor.getString(4));
                transaction.setType(Integer.valueOf(cursor.getString(5)));
                transaction.setIsTrasactionExportedToXML(Integer.valueOf(cursor.getString(6)));
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
                Transaction transaction = new Transaction();
                transaction.setId(Integer.valueOf(cursor.getString(0)));
                transaction.setValue(Double.valueOf(cursor.getString(1)));
                transaction.setDate(cursor.getString(2));
                transaction.setComent(cursor.getString(3));
                transaction.setCategory(cursor.getString(4));
                transaction.setType(Integer.valueOf(cursor.getString(5)));
                transaction.setIsTrasactionExportedToXML(Integer.valueOf(cursor.getString(6)));
                transactions.add(transaction);
            } while (cursor.moveToNext());
        }

        db.close();
        return transactions;
    }

    public void updateTransaction(Transaction transaction){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE " + TB_TRANSACTION +
                " SET "+ VALUE+"="+transaction.getValue()+" "+ DATE+"="+transaction.getDate()+" "+ COMENT+"="+transaction.getComment()+" "+ CATEGORY+"="+transaction.getCategory()+" "+ IS_TRANSACTION_EXPORTED_TO_XML+"="+transaction.getIsTrasactionExportedToXML()+
                " WHERE ID=" + transaction.getId());

        db.close();

    }

    public void updateTransactionExportedParametr(int id){
        //metoda nastavi u transakce priznak ze byla exportovana do XML
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE " + TB_TRANSACTION +
                " SET "+ IS_TRANSACTION_EXPORTED_TO_XML+" = 1"+
                " WHERE ID = " + id);

        db.close();

    }



    public  int getMaxIDTransaction(){
        int ID = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT max("+ID_TRANSACTION+") FROM " + TB_TRANSACTION, null);
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
       values.put(ID_CATEGORY,category.getId());
       values.put(NAME_CATEGORY,category.getName());
       values.put(MASTER_CATEGORY,category.getMasterCategory());

       db.insert(TB_CATEGORY, null, values);
       db.close();
   }

    public Category getCategory(int ID){

        SQLiteDatabase db = this.getWritableDatabase();
        Category category = new Category();

        Cursor cursor = db.rawQuery("SELECT * FROM" + TB_CATEGORY + "WHERE ID=" + ID, null);

        category.setId(Integer.valueOf(cursor.getString(0)));
        category.setName(cursor.getString(3));
        category.setMasterCategory(cursor.getString(4));

        db.close();
        return category;

    }
    public ArrayList<Category> getcategories(){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Category> categories = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_CATEGORY + " ORDER BY " + NAME_CATEGORY +" COLLATE NOCASE ", null);

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
        Cursor cursor = db.rawQuery("SELECT max("+ID_CATEGORY+") FROM " + TB_CATEGORY, null);
        cursor.moveToFirst();
        return ID = Integer.valueOf(cursor.getString(0));
    }






}
