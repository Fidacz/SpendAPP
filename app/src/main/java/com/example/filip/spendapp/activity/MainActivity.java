package com.example.filip.spendapp.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TimePicker;

import com.example.filip.spendapp.DateParser;
import com.example.filip.spendapp.R;
import com.example.filip.spendapp.SQLHelper;
import com.example.filip.spendapp.adapter.CategoryAdapter;
import com.example.filip.spendapp.adapter.CategoryGroup;
import com.example.filip.spendapp.data.Category;
import com.example.filip.spendapp.data.Transaction;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    //TODO dodelat kdyz dojde ke zmene data o mesic 

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
    private SparseArray<CategoryGroup> groups = new SparseArray<CategoryGroup>();;

    private Button dateBTN;
    private Button save;



    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;

    private boolean isEdit = false;
    private Transaction transaction = new Transaction();
    private ArrayList<Category> categories = new ArrayList<>();
    private int[] categoryID;


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

        createData();
        category = (Spinner) findViewById(R.id.category);
       

        category.setOnItemSelectedListener(this);

        SQLHelper db = new SQLHelper(this,"spendApp",null ,1);
        categories = new ArrayList<>();
        categories = db.getcategories();



        dateBTN = (Button) findViewById(R.id.date);
        dateBTN.setOnClickListener(this);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);


        Bundle bundle = getIntent().getExtras();


        if (bundle != null ){
            // editace doplneni hodnot
            isEdit = true;
            transaction = db.getTransaction(bundle.getInt("id"));
            valueEditText.setText(String.valueOf(transaction.getValue()));
            commentEditText.setText(transaction.getComment());

            if (transaction.getType() == 0){
                valueSwitch.setChecked(false);
            }else{
                valueSwitch.setChecked(true);
            }
            boolean pom = true; // pomocna promena, kdyz kategorie co je u transakce neniv DB nastavi se na false

            int id = transaction.getCategory().getId();
            prepareCategorie();
            category.setSelection(id);
            //for (int i = 0; i < categories.size(); i ++){
              //  if (categories.get(i).getName().equals(transaction.getCategory())){
                //    category.setSelection(i);
                  //  pom = false;
               // }
            //}

            //if(pom){
              //     Category cat = transaction.getCategory();
                //   //cat.setName(db.getCategory(transaction.getCategory()).getName());
                  // categories.add(cat);
                   //prepareCategorie();
                   //category.setSelection(categories.size()-1);
               //}else {

               //}

             year = transaction.getYear();
             month = transaction.getMonth() ;
             day = transaction.getDay();
             hour = transaction.getHour();
            min = transaction.getMin();
            dateBTN.setText(hour + ":" + min + " " + day + "." + month + "." + year);


        }else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH) +1;
            day = cal.get(Calendar.DAY_OF_MONTH);
            hour = cal.get(Calendar.HOUR_OF_DAY);
            min =  cal.get(Calendar.MINUTE);
            dateBTN.setText(hour + ":" + min + " " + day + "." + month + "." + year);
            prepareCategorie();



        }
        db.close();

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

                SQLHelper db = new SQLHelper(this, "spendApp", null, 1);

                if (isEdit) {
                    String stringValue = String.valueOf(valueEditText.getText());
                    if (stringValue.equals("")) {
                        break; // pokud neni vyplnená catka tak se nic ukladat nebude
                    }
                    transaction.setValue(Double.valueOf(stringValue));
                    int id = category.getSelectedItemPosition();
                    transaction.setCategory(db.getCategory(categoryID[category.getSelectedItemPosition()]));
                    transaction.setDate(dateBTN.getText().toString());
                    if (valueSwitch.isChecked()) {
                        transaction.setType(1);  // pokud je to vydaj hodnota se udela zaporna
                    }else{
                        transaction.setType(0);
                    }

                    transaction.setComent(String.valueOf(commentEditText.getText()));
                    transaction.setIsTrasactionExportedToXML(0);
                    db.updateTransaction(transaction);

                }else {
                    String stringValue = String.valueOf(valueEditText.getText());


                    if (stringValue.equals("")) {
                        break; // pokud neni vyplnená catka tak se nic ukladat nebude
                    }
                    int typeOfTransacion = 0;


                    Double value = Double.parseDouble(stringValue);
                    String textKOmentare = String.valueOf(commentEditText.getText());


                    if (valueSwitch.isChecked()) {
                        typeOfTransacion = 1;  // pokud je to vydaj hodnota se udela zaporna
                    }

                    int id = category.getSelectedItemPosition();
                    Transaction transakce = new Transaction(db.getMaxIDTransaction() + 1, value, dateBTN.getText().toString(), textKOmentare, db.getCategory(categoryID[category.getSelectedItemPosition()]), typeOfTransacion, 0);


                    db.addTransaction(transakce);
                    db.close();
                }

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

    private void prepareCategorie() {
        String[] values = new String[categories.size()];
        categoryID = new int[categories.size()];;

        for (int i = 0; i < categories.size(); i++){
            values[i] = categories.get(i).getName();
            categoryID[i] = categories.get(i).getId();
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        category.setAdapter(dataAdapter);
    }

    public void createData() {
        //nastrkani dat z db
        SQLHelper db = new SQLHelper(this,"spendApp",null ,1);

        ArrayList<Category> masterCategories;
        masterCategories = db.getMasterCategories();


        for (int i = 0; i < masterCategories.size(); i++) {
            CategoryGroup group = new CategoryGroup(masterCategories.get(i));
            ArrayList<Category> slavesCategories;
            slavesCategories = db.getSlaveCategories(masterCategories.get(i).getName());
            for (int j = 0; j < slavesCategories.size(); j++) {
                group.children.add(slavesCategories.get(j));
            }
            groups.append(i, group);

        }

    }
}
