package com.example.filip.spendapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filip.spendapp.R;
import com.example.filip.spendapp.SQLHelper;
import com.example.filip.spendapp.adapter.CategoryAdapter;
import com.example.filip.spendapp.adapter.CategoryGroup;
import com.example.filip.spendapp.data.Category;
import com.example.filip.spendapp.data.Transaction;

import java.util.ArrayList;
//TODO dodelat upozorneni pri mazani kategorii ze se smazou i transakce s tou categorii
public class CategoryActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {

    private ExpandableListView listView;
    private Button dellBtn;
    private Button addBtn;
    private Button editBtn;
    private SparseArray<CategoryGroup> groups = new SparseArray<CategoryGroup>();;
    private View viewContainerAdd;
    private View viewContainerEdit;
    private CategoryAdapter adapter;

    private int selectedGroupID;
    private int selectedChildID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_category);

         createData();
        listView = (ExpandableListView) findViewById(R.id.exlistView);
        viewContainerAdd = findViewById(R.id.addbar);
        viewContainerEdit = findViewById(R.id.editbar);

         adapter = new CategoryAdapter(this,groups);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(this);
        showAddBar(viewContainerAdd);


    }

    @Override
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

        switch (v.getId()){
            case R.id.addbar_button:
                Intent intent = new Intent(this,AddCategoryActivity.class);
                this.startActivity(intent);
                break;

            case R.id.editbar_button:
            //TODO dodelat edit
                Intent intent2 = new Intent(this,AddCategoryActivity.class);
                if (selectedChildID == -1){
                    //je vybran rodic
                    intent2.putExtra("id",groups.get(selectedGroupID).category.getId());
                }else{
                    intent2.putExtra("id", groups.get(selectedGroupID).children.get(selectedChildID).getId());

                    //je vybrano dite
                }
                this.startActivity(intent2);
                break;
            case R.id.dellbar_button:

                final SQLHelper db = new SQLHelper(this,"spendApp",null ,1);
                if (selectedChildID == -1){
                    //je vybran rodic
                    int categoryID = groups.get(selectedGroupID).category.getId();
                    final ArrayList<Transaction> transactions = db.getTransactionsByCategory(categoryID);

                    //vyhledani deti
                    ArrayList<Category> categories = new ArrayList<>();
                    for (int i = 0; i < groups.get(selectedGroupID).children.size(); i++){

                        categories.add(db.getCategory(groups.get(selectedGroupID).children.get(i).getId()));
                    }

                    if( transactions.size() == 0 && categories.size() == 0 ){
                        //nema zadne potomky s transakcema nebo transakce
                        db.delteCategory(groups.get(selectedGroupID).category.getId());
                        for (int i = selectedGroupID; i < groups.size(); i ++){
                            groups.setValueAt(i, groups.get(i + 1));
                        }
                        groups.remove(groups.size() - 1);

                    }else {
                        //ma transakce nebo potomky s transakcema

                        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        //chci smazat i transace

                                        for (int i=0; i < transactions.size() ;i++) {
                                            //mazani transakcí
                                            db.delteTransaction(transactions.get(i).getId());
                                        }


                                        for (int i = 0; i < groups.get(selectedGroupID).children.size(); i++){

                                            final ArrayList<Transaction> childTransactions = db.getTransactionsByCategory(groups.get(selectedGroupID).children.get(i).getId());
                                            for (int m=0; m < childTransactions.size() ;m++) {
                                                //mazani transakcí
                                                db.delteTransaction(childTransactions.get(m).getId());
                                            }
                                            db.delteCategory(groups.get(selectedGroupID).children.get(i).getId());

                                        }

                                        db.delteCategory(groups.get(selectedGroupID).category.getId());
                                        for (int i = selectedGroupID; i < groups.size(); i ++){
                                            groups.setValueAt(i, groups.get(i + 1));
                                        }
                                        groups.remove(groups.size() - 1);
                                        adapter.notifyDataSetChanged();

                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //Ne nemazat je
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Tato kategorie obsahuje transakce nebo podKategorie chcete vše smazat ?").setPositiveButton("ANO", dialogClickListener)
                                .setNegativeButton("NE", dialogClickListener).show();


                    }





                }else{
                    //je vybrano dite
                    final int childID = groups.get(selectedGroupID).children.get(selectedChildID).getId();
                    final ArrayList<Transaction> transactions = db.getTransactionsByCategory(childID);


                    if (transactions.size() == 0) {
                        //samzani ditete Z DB pokud nejsou v teto categorii transakce
                        db.delteCategory(childID);
                        groups.get(selectedGroupID).children.remove(selectedChildID);
                    }else {
                        //categorie obsahuje nejake trsankce dialog zda je smazat taky
                        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                    //chci smazat i transace
                                        for (int i=0; i < transactions.size() ;i++) {
                                            //mazani transakcí
                                            db.delteTransaction(transactions.get(i).getId());
                                        }
                                        db.delteCategory(childID);
                                        groups.get(selectedGroupID).children.remove(selectedChildID);
                                        adapter.notifyDataSetChanged();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //Ne nemazat je
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Tato kategorie obsahuje "+ transactions.size()+" transakcí chceteje smazat ?").setPositiveButton("ANO", dialogClickListener)
                                .setNegativeButton("NE", dialogClickListener).show();

                    }


                }
                adapter.notifyDataSetChanged();
                db.close();

                viewContainerEdit.setVisibility(View.GONE);
                viewContainerAdd.setVisibility(View.VISIBLE);
                break;
        }

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


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            int groupPosition = ExpandableListView.getPackedPositionGroup(id);
            int childPosition = ExpandableListView.getPackedPositionChild(id);
            selectedGroupID = groupPosition;
            selectedChildID = childPosition;

            viewContainerAdd.setVisibility(View.GONE);
            showEditBar(viewContainerEdit);

            if (groups.get(groupPosition).children.get(childPosition).isSelected()){
                groups.get(groupPosition).children.get(childPosition).setIsSelected(false);
                adapter.notifyDataSetChanged();
                viewContainerEdit.setVisibility(View.GONE);
                viewContainerAdd.setVisibility(View.VISIBLE);
                return true;
            }
           parentsSetIsSelectedOnFalse();
            childrenSetIsSelectedOnFalse();
            groups.get(groupPosition).children.get(childPosition).setIsSelected(true);
            adapter.notifyDataSetChanged();

            return true;
        }


        if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
            int groupPosition = ExpandableListView.getPackedPositionGroup(id);
            int childPosition = ExpandableListView.getPackedPositionChild(id);
            selectedGroupID = groupPosition;
            selectedChildID = childPosition;

            viewContainerAdd.setVisibility(View.GONE);
            showEditBar(viewContainerEdit);

            if (groups.get(groupPosition).category.isSelected()){
                groups.get(groupPosition).category.setIsSelected(false);
                adapter.notifyDataSetChanged();
                viewContainerEdit.setVisibility(View.GONE);
                viewContainerAdd.setVisibility(View.VISIBLE);
                return true;
            }
            parentsSetIsSelectedOnFalse();
            childrenSetIsSelectedOnFalse();

            groups.get(groupPosition).category.setIsSelected(true);
            adapter.notifyDataSetChanged();

            return true;
        }
        return false;
    }

    public void parentsSetIsSelectedOnFalse(){
        for (int i = 0 ; i < groups.size(); i ++) {
            if (groups.get(i).category.isSelected()) {
                groups.get(i).category.setIsSelected(false);
            }
        }
    }
    public void childrenSetIsSelectedOnFalse(){
        for (int i = 0 ; i < groups.size(); i ++){
            for (int j = 0; j <groups.get(i).children.size(); j ++){
                if (groups.get(i).children.get(j).isSelected()){
                    groups.get(i).children.get(j).setIsSelected(false);
                }
            }
        }
    }

}
