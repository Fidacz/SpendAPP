package com.example.filip.spendapp;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Filip on 22. 11. 2015.
 */
public class DateParser {

    public DateParser() {
    }

    public String dateToString (Date date){

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String datum = String.valueOf(date.getHours() + ":" +String.valueOf(date.getMinutes()) + " " + day + "." + month + "." + year);
        return datum;
    }

    public Date StringToDate(String datum){

        Date date = new Date();
        date.setHours(Integer.valueOf(datum.substring(0, 1)));
        date.setMinutes(Integer.valueOf(datum.substring(3, 4)));
        date.setDate(Integer.valueOf(datum.substring(6, 7)));
        date.setMonth(Integer.valueOf(datum.substring(9, 10)));
        date.setYear(Integer.valueOf(datum.substring(12, 14)));
        return date;
    }
}
