package com.example.filip.spendapp;

import com.example.filip.spendapp.data.Transaction;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by Filip on 4. 1. 2016.
 */
public class TransactionComparator implements Comparator<Transaction> {
    @Override
    public int compare(Transaction t1, Transaction t2) {
        return Long.compare(t1.getDateDate().getTime(),t2.getDateDate().getTime());
        //return t1.getDateDate().getTime().compareTo(t2.getDateDate().getTime());

    }
}
