package com.example.filip.spendapp;

import java.util.Date;

/**
 * Created by Filip on 31. 5. 2015.
 */
public class Transaction {
    int id;
    double value;
    Date date;
    String comment;
    String category;

    public Transaction() {

    }

    public Transaction(int id, double value, Date date, String comment, String category) {
        this.id = id;
        this.value = value;
        this.date = date;
        this.comment = comment;
        this.category = category;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return comment;
    }

    public void setNote(String comment) {
        this.comment = comment;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
