package com.example.filip.spendapp.data;

import com.example.filip.spendapp.DateParser;

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
    private int isTrasactionExportedToXML;

    public Transaction() {

    }

    public Transaction(int id, double value, Date date, String comment, String category, int type, int isTrasactionExportedToXML) {
        this.id = id;
        this.value = value;
        this.date = date;
        this.comment = comment;
        this.category = category;
        this.type = type;
        this.isTrasactionExportedToXML = isTrasactionExportedToXML;

    }
    public Transaction(int id, double value, String date, String comment, String category, int type, int isTrasactionExportedToXML) {
        this.id = id;
        this.value = value;
        DateParser dateParser = new DateParser();
        this.date = dateParser.StringToDate(date);
        this.comment = comment;
        this.category = category;
        this.type = type;
        this.isTrasactionExportedToXML = isTrasactionExportedToXML;

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

        DateParser dateParser = new DateParser();
        return dateParser.dateToString(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public void setDate(String date) {
        //zakomentovado z duvody chybne udelane metody getDate
        DateParser dateParser = new DateParser();
         this.date = dateParser.StringToDate(date);

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

    public int getIsTrasactionExportedToXML() {
        return isTrasactionExportedToXML;
    }

    public void setIsTrasactionExportedToXML(int isTrasactionExportedToXML) {
        this.isTrasactionExportedToXML = isTrasactionExportedToXML;
    }
}
