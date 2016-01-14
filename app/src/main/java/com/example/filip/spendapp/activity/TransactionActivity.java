package com.example.filip.spendapp.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.example.filip.spendapp.R;
import com.example.filip.spendapp.SQLHelper;
import com.example.filip.spendapp.TransactionComparator;
import com.example.filip.spendapp.adapter.CategoryAdapter;
import com.example.filip.spendapp.adapter.CategoryGroup;
import com.example.filip.spendapp.adapter.TransactioGroup;
import com.example.filip.spendapp.adapter.TransactionAdapter;
import com.example.filip.spendapp.data.Category;
import com.example.filip.spendapp.data.Transaction;

import java.util.ArrayList;
import java.util.Collections;

public class TransactionActivity  extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener   {



    //ArrayList<Transaction> transactions;
    private ExpandableListView listView;
    private SparseArray<TransactioGroup> groups = new SparseArray<TransactioGroup>();;
    private View viewContainerAdd;
    private View viewContainerEdit;
    private TransactionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);


        createData();
        listView = (ExpandableListView) findViewById(R.id.exlistView);
        //viewContainerAdd = findViewById(R.id.addbar);
        //viewContainerEdit = findViewById(R.id.editbar);

        adapter = new TransactionAdapter(this,groups);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(this);
        //showAddBar(viewContainerAdd);

        /**
        listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemLongClickListener(this);

        SQLHelper db = new SQLHelper(this,"spendApp",null ,1);


        transactions = new ArrayList<>();
        transactions = db.getTransactions();
        db.close();
        showNames();
         */
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

    private void createData() {
        SQLHelper db = new SQLHelper(this,"spendApp",null ,1);

        ArrayList<Transaction> transactions;
        transactions= db.getTransactions();
        Collections.sort(transactions, new TransactionComparator());

        int idgroup = -1;
        for (int i = 0; i < transactions.size(); i++) {
            TransactioGroup group = new TransactioGroup(transactions.get(i));
            idgroup++;
            if(transactions.size() == 1){
                group.getChildren().add(transactions.get(i));
                groups.append(idgroup, group);
                break;
            }
            //ArrayList<Transaction> transaction;
            while (transactions.get(i).getDateDate().getMonth() == transactions.get(i+1).getDateDate().getMonth() + 1){
                group.getChildren().add(transactions.get(i));
                i++;
                if (i >= transactions.size())
                    break;
            }
            i--;

            //for (int j = 0; j < slavesCategories.size(); j++) {
            //    group.children.add(slavesCategories.get(j));
            //}
            groups.append(idgroup, group);

        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


        return false;
    }


}
