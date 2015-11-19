package com.example.filip.spendapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.filip.spendapp.R;
import com.example.filip.spendapp.SQLHelper;
import com.example.filip.spendapp.data.Category;

import java.util.ArrayList;

/**
 * Created by Filip on 18. 11. 2015.
 */
public class AddCategoryActivity extends AppCompatActivity implements View.OnClickListener, OnItemSelectedListener {


    private Button save;
    private EditText nameEditText;
    Spinner masterCategory ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        nameEditText = (EditText) findViewById(R.id.name);
        masterCategory = (Spinner) findViewById(R.id.masterCategory);
        masterCategory.setOnItemSelectedListener(this);


        SQLHelper db = new SQLHelper(this,"spendApp",null ,1);
        ArrayList<Category> categories = new ArrayList<>();
        categories = db.getcategories();
        String[] values = new String[categories.size()];

        for (int i = 0; i < categories.size(); i++){
            values[i] = categories.get(i).getName();
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        masterCategory.setAdapter(dataAdapter);


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
        if (id == R.id.action_spend) {
            Intent intent = new Intent(this,MainActivity.class);
            this.startActivity(intent);
        }else if (id == R.id.action_trasaction){

            Intent intent = new Intent(this,TransactionActivity.class);
            this.startActivity(intent);
            return true;
        }else if (id == R.id.action_category){
            Intent intent = new Intent(this,CategoryActivity.class);
            this.startActivity(intent);
            return true;

        }else if (id == R.id.action_settings){

            return true;

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.save:
                SQLHelper db = new SQLHelper(this,"spendApp",null ,1);
                Category category = new Category();
                category.setId(db.getMaxIDCategory()+ 1);
                category.setName(String.valueOf(nameEditText.getText()));
            //TODO dodelat ukladani nadrazene kategorie
                db.addCategory(category);

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
