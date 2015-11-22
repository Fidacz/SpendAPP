package com.example.filip.spendapp.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Filip on 31. 5. 2015.
 */
public class Transaction {
    private int id;
    private double value;
    private Date date;
    private String comment;
    private String category;
    private int type; //type 0 is income, 1 is cost

    public Transaction() {

    }

    public Transaction(int id, double value, Date date, String comment, String category, int type) {
        this.id = id;
        this.value = value;
        this.date = date;
        this.comment = comment;
        this.category = category;
        this.type = type;

    }
    public Transaction(int id, double value, String date, String comment, String category, int type) {
        this.id = id;
        this.value = value;
        this.date = dateParser(date);
        this.comment = comment;
        this.category = category;
        this.type = type;

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDate() {
        // prevedeni Date na string ve formatu hh:mm DD.MM.YYYY
        //TODO opravit

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String datum = String.valueOf(date.getHours() + ":" +String.valueOf(date.getMinutes()) + " " + day + "." + month + "." + year);

        return datum;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public void setDate(String date) {
        //zakomentovado z duvody chybne udelane metody getDate
         this.date = dateParser(date);

    }

    public String getComment() {
        return comment;
    }

    public void setComent(String comment) {
        this.comment = comment;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private Date dateParser (String date){
        // prevedeni stringu na date
            this.date = new Date();
            this.date.setHours(Integer.valueOf(date.substring(0, 1)));
            this.date.setMinutes(Integer.valueOf(date.substring(3, 4)));
            this.date.setDate(Integer.valueOf(date.substring(6, 7)));
            this.date.setMonth(Integer.valueOf(date.substring(9, 10)));
            this.date.setYear(Integer.valueOf(date.substring(12, 14)));




        return null;
    }
}
