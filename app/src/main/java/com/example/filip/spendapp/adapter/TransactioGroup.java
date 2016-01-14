package com.example.filip.spendapp.adapter;

import com.example.filip.spendapp.data.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Filip on 4. 1. 2016.
 */
public class TransactioGroup {
    private Transaction transaction;
    private final List<Transaction> children = new ArrayList<Transaction>();



    public TransactioGroup(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public List<Transaction> getChildren() {
        return children;
    }
}

