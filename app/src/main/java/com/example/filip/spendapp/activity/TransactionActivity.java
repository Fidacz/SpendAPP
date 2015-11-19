package com.example.filip.spendapp.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.filip.spendapp.R;
import com.example.filip.spendapp.SQLHelper;
import com.example.filip.spendapp.data.Category;
import com.example.filip.spendapp.data.Transaction;

import java.util.ArrayList;

public class TransactionActivity  extends AppCompatActivity implements View.OnClickListener  {


    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        listView = (ListView) findViewById(R.id.listview);

        SQLHelper db = new SQLHelper(this,"spendApp",null ,1);


        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions = db.getTransactions();
        String[] values = new String[transactions.size()];

        for (int i = 0; i < transactions.size(); i++){
            values[i] = String.valueOf(transactions.get(i).getValue());
        }
        db.close();

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_spend) {
            Intent intent = new Intent(this,MainActivity.class);
            this.startActivity(intent);
            return true;

        }else if (id == R.id.action_trasaction){

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

    }
}
