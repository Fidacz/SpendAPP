package com.example.filip.spendapp.activity;

import android.app.DownloadManager;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;


import com.example.filip.spendapp.R;
import com.example.filip.spendapp.SQLHelper;
import com.example.filip.spendapp.TransactionComparator;
import com.example.filip.spendapp.TransactionXMLParser;
import com.example.filip.spendapp.adapter.CategoryAdapter;
import com.example.filip.spendapp.adapter.CategoryGroup;
import com.example.filip.spendapp.adapter.TransactioGroup;
import com.example.filip.spendapp.adapter.TransactionAdapter;
import com.example.filip.spendapp.data.Category;
import com.example.filip.spendapp.data.Transaction;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class TransactionActivity  extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener   {



    //ArrayList<Transaction> transactions;
    private ExpandableListView listView;
    private Button dellBtn;
    private Button addBtn;
    private Button editBtn;
    private SparseArray<TransactioGroup> groups = new SparseArray<TransactioGroup>();;
    private View viewContainerAdd;
    private View viewContainerEdit;
    private TransactionAdapter adapter;

    private int selectedGroupID;
    private int selectedChildID;

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
        viewContainerAdd = findViewById(R.id.addbar);
        viewContainerEdit = findViewById(R.id.editbar);
        showAddBar(viewContainerAdd);
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

    public void onResume() {
        super.onResume();
        groups.clear();
        createData();
        adapter.notifyDataSetChanged();
        viewContainerEdit.setVisibility(View.GONE);
        viewContainerAdd.setVisibility(View.VISIBLE);

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

        }else if (id == R.id.action_export){
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) +1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int min =  cal.get(Calendar.MINUTE);
            int sec = cal.get(Calendar.SECOND);




            // Creating HTTP client
           // HttpClient httpClient = new DefaultHttpClient();

            // Creating HTTP Post
            //HttpPost httpPost = new HttpPost("https://script.google.com/macros/s/AKfycbz87AsX7Vhtkv7Q9eth0POyQTW9nOaHU3fK0NuGNwCl/dev?teplota=28.65&svetlo=154");

            /**
            SQLHelper db = new SQLHelper(this,"spendApp",null ,1);

            ArrayList<Transaction> transactions;
            transactions= db.getNotExportedTransactions();
            //TODO dodelat export
            Collections.sort(transactions, new TransactionComparator());
            db.close();
            int expoYear = 0;
            int expoMont = 0;

            for (int i = 0; i < transactions.size(); i ++){

                if (transactions.get(i).getMonth() != month || transactions.get(i).getYear() != year){
                    if (transactions.get(i).getMonth() != expoMont || transactions.get(i).getYear() != expoYear){
                        expoYear = transactions.get(i).getYear();
                        expoMont = transactions.get(i).getMonth();
                        ArrayList<Transaction> exportTransactions;
                        exportTransactions = db.getMonthTransaction(expoMont, expoYear);
                        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "spendapp"+expoMont+"-"+expoYear+".xml");

                        try {
                            file.createNewFile();
                            FileOutputStream fileos = new FileOutputStream(file);
                            OutputStreamWriter osw = new OutputStreamWriter(fileos);
                            // Write the string to the file
                            osw.write(TransactionXMLParser.creteXML(exportTransactions));
                            // save and close
                            osw.flush();
                            osw.close();
                            for (int k = 0; k < exportTransactions.size(); k++){
                                exportTransactions.get(k).setIsTrasactionExportedToXML(1);
                                db.updateTransaction(exportTransactions.get(k));
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }

            db.close();


            */
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.addbar_button:
                Intent intent = new Intent(this,MainActivity.class);
                this.startActivity(intent);
                break;

            case R.id.editbar_button:
                //TODO dodelat edit
                Intent intent2 = new Intent(this,MainActivity.class);
                intent2.putExtra("id",groups.get(selectedGroupID).getChildren().get(selectedChildID).getId());
                this.startActivity(intent2);
                /**
                Intent intent2 = new Intent(this,AddCategoryActivity.class);
                if (selectedChildID == -1){
                    //je vybran rodic
                    intent2.putExtra("id",groups.get(selectedGroupID).category.getId());
                }else{
                    intent2.putExtra("id", groups.get(selectedGroupID).children.get(selectedChildID).getId());

                    //je vybrano dite
                }
                this.startActivity(intent2);
                 */
                break;
            case R.id.dellbar_button:

            /**

                if (selectedChildID == -1){
                    //je vybran rodic
                    for (int i = 0; i < groups.get(selectedGroupID).children.size(); i++){
                        db.delteCategory(groups.get(selectedGroupID).children.get(i).getId());

                    }

                    db.delteCategory(groups.get(selectedGroupID).category.getId());
                    for (int i = selectedGroupID; i < groups.size(); i ++){
                        groups.setValueAt(i, groups.get(i + 1));
                    }
                    groups.remove(groups.size() - 1);


                }else{

             */
                    SQLHelper db = new SQLHelper(this,"spendApp",null ,1);
                    db.delteTransaction(groups.get(selectedGroupID).getChildren().get(selectedChildID).getId());
                    groups.get(selectedGroupID).getChildren().remove(selectedChildID);

                    //je vybrano dite


                adapter.notifyDataSetChanged();
                db.close();

                viewContainerEdit.setVisibility(View.GONE);
                viewContainerAdd.setVisibility(View.VISIBLE);

                break;
        }

    }

    private void createData() {
        SQLHelper db = new SQLHelper(this,"spendApp",null ,1);

        ArrayList<Transaction> transactions;
        transactions= db.getTransactions();
        Collections.sort(transactions, new TransactionComparator());

        int idgroup = -1;
        for (int i = 0; i < transactions.size(); i ++) {
            double groupCount = 0;
            TransactioGroup group = new TransactioGroup(transactions.get(i));
            idgroup++;
            if(transactions.size() == 1){
                group.getChildren().add(transactions.get(i));


                if (transactions.get(i).getType() == 0) {
                    groupCount = groupCount + transactions.get(i).getValue();
                }else if(transactions.get(i).getType() == 1){
                    groupCount = groupCount - transactions.get(i).getValue();
                }

                group.getChildren().add( new Transaction(0, groupCount, new Date(), null, new Category("sum", 0), 2, 2));
                groups.append(idgroup, group);
                break;
            }
            //ArrayList<Transaction> transaction;
            if ( transactions.size() != i+1) {



                group.getChildren().add(transactions.get(i));
                //promena kde se ulozi celkový součet za měsíc

                if (transactions.get(i).getType() == 0) {
                    groupCount = groupCount + transactions.get(i).getValue();
                }else if(transactions.get(i).getType() == 1){
                    groupCount = groupCount - transactions.get(i).getValue();
                }

                while (i < transactions.size()-1 && transactions.get(i).getDateDate().getMonth() == transactions.get(i + 1).getDateDate().getMonth()) {
                    group.getChildren().add(transactions.get(i+1));

                    //vytvareni sumy

                    if (transactions.get(i+1).getType() == 0) {
                        groupCount = groupCount + transactions.get(i+1).getValue();
                    }else if(transactions.get(i+1).getType() == 1){
                        groupCount = groupCount - transactions.get(i+1).getValue();
                    }


                    i++;

                }

            }else{
                group.getChildren().add(transactions.get(i));

                if (transactions.get(i).getType() == 0) {
                    groupCount = groupCount + transactions.get(i).getValue();
                }else if(transactions.get(i).getType() == 1){
                    groupCount = groupCount - transactions.get(i).getValue();
                }

                i++;

            }

            //for (int j = 0; j < slavesCategories.size(); j++) {
            //    group.children.add(slavesCategories.get(j));
            //}
            group.getChildren().add( new Transaction(0, groupCount, new Date(), null, new Category("SUM", 0), 2, 2));
            groups.append(idgroup, group);

        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


        if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            int groupPosition = ExpandableListView.getPackedPositionGroup(id);
            int childPosition = ExpandableListView.getPackedPositionChild(id);

            selectedGroupID = groupPosition;
            selectedChildID = childPosition;

            viewContainerAdd.setVisibility(View.GONE);
            showEditBar(viewContainerEdit);

            if (groups.get(groupPosition).getChildren().get(childPosition).isSelected()){
                groups.get(groupPosition).getChildren().get(childPosition).setIsSelected(false);
                adapter.notifyDataSetChanged();
                viewContainerEdit.setVisibility(View.GONE);
                viewContainerAdd.setVisibility(View.VISIBLE);
                return true;
            }
            parentsSetIsSelectedOnFalse();
            childrenSetIsSelectedOnFalse();
            groups.get(groupPosition).getChildren().get(childPosition).setIsSelected(true);
            adapter.notifyDataSetChanged();


            return true;
        }


        if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
            int groupPosition = ExpandableListView.getPackedPositionGroup(id);
            int childPosition = ExpandableListView.getPackedPositionChild(id);


            return true;
        }
        return false;


    }
    public void showAddBar(final View viewContainer) {
        addBtn = (Button) viewContainer.findViewById(R.id.addbar_button);
        addBtn.setOnClickListener(this);
        viewContainer.setVisibility(View.VISIBLE);
        viewContainer.setAlpha(1);
        viewContainer.animate();


    }

    public void showEditBar(final View viewContainer) {
        editBtn = (Button) viewContainer.findViewById(R.id.editbar_button);
        editBtn.setOnClickListener(this);
        dellBtn = (Button) viewContainer.findViewById(R.id.dellbar_button);
        dellBtn.setOnClickListener(this);
        viewContainer.setVisibility(View.VISIBLE);
        viewContainer.setAlpha(1);
        viewContainer.animate();


    }

    public void parentsSetIsSelectedOnFalse(){
        for (int i = 0 ; i < groups.size(); i ++) {
            if (groups.get(i).getTransaction().isSelected()) {
                groups.get(i).getTransaction().setIsSelected(false);
            }
        }
    }
    public void childrenSetIsSelectedOnFalse(){
        for (int i = 0 ; i < groups.size(); i ++){
            for (int j = 0; j <groups.get(i).getChildren().size(); j ++){
                if (groups.get(i).getChildren().get(j).isSelected()){
                    groups.get(i).getChildren().get(j).setIsSelected(false);
                }
            }
        }
    }


}
