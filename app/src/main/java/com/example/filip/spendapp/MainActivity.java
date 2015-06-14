package com.example.filip.spendapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Date;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {


    private TextView titleValue;
    private TextView titleValueSwitch ;
    private TextView titleComment;
    private TextView titleDate;
    private TextView titleDateSwitch;
    private TextView titleCategory;

    private EditText valueEditText;
    private EditText commentEditText;

    private Switch valueSwitch;
    private Switch dateSwitch;

    private Spinner category;

    private Button date;
    private Button save;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleValue = (TextView) findViewById(R.id.titleValue);
        titleValueSwitch = (TextView) findViewById(R.id.titleValueSwitch);
        titleCategory = (TextView) findViewById(R.id.titleCategory);
        titleDate = (TextView) findViewById(R.id.titleDate);
        titleDateSwitch = (TextView) findViewById(R.id.titleDateSwitch);
        titleComment = (TextView) findViewById(R.id.titleComment);

        valueEditText = (EditText) findViewById(R.id.value);
        commentEditText = (EditText) findViewById(R.id.comment);

        valueSwitch = (Switch) findViewById(R.id.valueSwitch);
        dateSwitch = (Switch) findViewById(R.id.dateSwitch);

        category = (Spinner) findViewById(R.id.category);

        date = (Button) findViewById(R.id.date);
        date.setOnClickListener(this);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.date:
                break;

            case R.id.save:
                id++;
                double value = Double.parseDouble(String.valueOf(valueEditText.getText()));
                String textKOmentare = String.valueOf(commentEditText.getText());
                Date date = new Date();
                Transaction transakce = new Transaction(id, value, date, textKOmentare,null);


                break;
        }
    }
}
