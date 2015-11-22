package com.example.filip.spendapp.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.filip.spendapp.R;
import com.example.filip.spendapp.SQLHelper;
import com.example.filip.spendapp.data.Category;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener{

    ListView listView;
    private Button save;
    ArrayList<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        listView = (ListView) findViewById(R.id.listview);

        SQLHelper db = new SQLHelper(this,"spendApp",null ,1);


        categories = new ArrayList<>();
        categories = db.getcategories();

        db.close();

        showNames();
        listView.setOnItemLongClickListener(this);

        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);

    }


    private void showNames(){
        // docasna metoda co bude zobrazovat vypis kategorii
        String[] values = new String[categories.size()];

        for (int i = 0; i < categories.size(); i++){
            values[i] = categories.get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        listView.setAdapter(adapter);

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
        //docastne reseni prepinani mezi aktivitama
        //TODO docelat prepinani mezi aktivitama
        if (id == R.id.action_spend) {
            Intent intent = new Intent(this,MainActivity.class);
            this.startActivity(intent);
        }else if (id == R.id.action_trasaction){

            Intent intent = new Intent(this,TransactionActivity.class);
            this.startActivity(intent);
            return true;
        }else if (id == R.id.action_category){

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

        switch (v.getId()) {
            case R.id.save:
                Intent intent = new Intent(this,AddCategoryActivity.class);
                this.startActivity(intent);

                break;

        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        SQLHelper db = new SQLHelper(this,"spendApp",null ,1);
        db.delteCategory(categories.get(position).getId());
        db.close();
        categories.remove(position);
        showNames();


        return false;
    }
}
