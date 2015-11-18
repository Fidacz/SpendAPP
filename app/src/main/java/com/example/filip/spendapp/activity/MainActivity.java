package com.example.filip.spendapp.activity;

import android.content.Intent;
import android.os.Environment;
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

import com.example.filip.spendapp.R;
import com.example.filip.spendapp.SQLHelper;
import com.example.filip.spendapp.data.Transaction;
import com.example.filip.spendapp.TransactionXMLParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
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

    private int id=13;
    private ArrayList<Transaction> transactionList = new ArrayList<>();


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

        //docastne reseni prepinani mezi aktivitama
        //TODO docelat prepinani mezi aktivitama
        if (id == R.id.action_spend) {

        }else if (id == R.id.action_trasaction){

            Intent intent = new Intent(this,TransactionActivity.class);
            this.startActivity(intent);
            return true;
        }else if (id == R.id.action_category){
            Intent intent = new Intent(this,CategoryActivity.class);
            this.startActivity(intent);
            return true;

        }else if (id == R.id.action_settings){
            Intent intent = new Intent(this,SettingsActivity.class);
            this.startActivity(intent);
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
                String stringValue = String.valueOf(valueEditText.getText());
                //TODO nacteni kategorie

                if (stringValue != null) {
                    Double value = Double.parseDouble(stringValue);
                    String textKOmentare = String.valueOf(commentEditText.getText());
                    Date date = new Date();
                    Transaction transakce = new Transaction(id, value, date, textKOmentare,"testkategorie",0);
                    transactionList.add(transakce);



                    SQLHelper db = new SQLHelper(this,"spendApp",null ,1);
                    db.addTransaction(transakce);

                    // docastne resene ukladani xml
                    File file = new File(Environment.getExternalStorageDirectory() + File.separator + "spendapp.xml");

                    try {
                        file.createNewFile();
                        FileOutputStream fileos = new FileOutputStream(file);
                        OutputStreamWriter osw = new OutputStreamWriter(fileos);
                        // Write the string to the file
                        osw.write(TransactionXMLParser.creteXML(transactionList));
                        // save and close
                        osw.flush();
                        osw.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


                break;
        }
    }
}
