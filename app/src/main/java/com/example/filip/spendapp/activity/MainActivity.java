package com.example.filip.spendapp.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TimePicker;

import com.example.filip.spendapp.DateParser;
import com.example.filip.spendapp.R;
import com.example.filip.spendapp.SQLHelper;
import com.example.filip.spendapp.data.Category;
import com.example.filip.spendapp.data.Transaction;
import com.example.filip.spendapp.TransactionXMLParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


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

    private Button dateBTN;
    private Button save;



    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;



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
        category.setOnItemSelectedListener(this);

        SQLHelper db = new SQLHelper(this,"spendApp",null ,1);
        ArrayList<Category> categories = new ArrayList<>();
        categories = db.getcategories();
        String[] values = new String[categories.size()];

        for (int i = 0; i < categories.size(); i++){
            values[i] = categories.get(i).getName();
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        category.setAdapter(dataAdapter);

        dateBTN = (Button) findViewById(R.id.date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) +1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        min =  cal.get(Calendar.MINUTE);
        dateBTN.setText(hour+":"+min+" "+day+"."+month+"."+year);
        dateBTN.setOnClickListener(this);
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

        } else if (id == R.id.action_trasaction) {

            Intent intent = new Intent(this, TransactionActivity.class);
            this.startActivity(intent);
            return true;
        } else if (id == R.id.action_category) {
            Intent intent = new Intent(this, CategoryActivity.class);
            this.startActivity(intent);
            return true;

        } else if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent);
            return true;
        } else if (id == R.id.action_export) {


        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int ID ){
        //0 dialog na vyber datumu 1 dialog na vyber casu
        if (ID == 0){

            return new DatePickerDialog(this,dpLisener,year, month -1,day);
        }else if (ID == 1){

            return new TimePickerDialog(this,tpLisener,hour,min,true);
        }

        return null;
    }

    private TimePickerDialog.OnTimeSetListener tpLisener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            min = minute;
            dateBTN.setText(hour+":"+min+" "+day+"."+month+"."+year);
            //TODO dodelat vraceni na vyber hodin
        }
    };

    private DatePickerDialog.OnDateSetListener dpLisener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int yearPick, int monthOfYear, int dayOfMonth) {
           year = yearPick;
           month = monthOfYear +1;
            day = dayOfMonth ;
            dateBTN.setText(hour+":"+min+" "+day+"."+month+"."+year);
            showDialog(1);

        }
    };

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.date:
                showDialog(0);
                break;

            case R.id.save:


                String stringValue = String.valueOf(valueEditText.getText());


                if (stringValue.equals("")) {
                    break; // pokud neni vyplnená catka tak se nic ukladat nebude
                }
                    int typeOfTransacion = 0;
                    SQLHelper db = new SQLHelper(this,"spendApp",null ,1);

                    Double value = Double.parseDouble(stringValue);
                    String textKOmentare = String.valueOf(commentEditText.getText());


                    if (valueSwitch.isChecked()){
                       typeOfTransacion = 1;  // pokud je to vydaj hodnota se udela zaporna
                    }
                String osz = dateBTN.getText().toString();
                    Transaction transakce = new Transaction(db.getMaxIDTransaction()+ 1, value, dateBTN.getText().toString() , textKOmentare,category.getSelectedItem().toString(),typeOfTransacion,0 );


                    db.addTransaction(transakce);
                    db.close();



                this.finish();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
