package com.example.filip.spendapp.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by fida on 16.8.15.
 */
public class TransactionAdapter extends ArrayAdapter{

    private final Context context;



    public TransactionAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }



}
