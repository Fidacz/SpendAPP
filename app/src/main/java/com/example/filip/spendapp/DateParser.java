package com.example.filip.spendapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String datum = String.valueOf(date.getHours() + ":" +String.valueOf(date.getMinutes()) + " " + day + "." + month + "." + year);
        return datum;
    }

    public Date StringToDate(String datum){

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd.MM.yyyy");


        Date date = null;
        try {
            date = sdf.parse(datum);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /**
        date.setHours(Integer.valueOf(datum.substring(0, 1)));
        date.setMinutes(Integer.valueOf(datum.substring(3, 4)));
        date.setDate(Integer.valueOf(datum.substring(6, 7)));
        date.setMonth(Integer.valueOf(datum.substring(9, 10)));
        date.setYear(Integer.valueOf(datum.substring(12, 14)));
         */
        return date;
    }
}
