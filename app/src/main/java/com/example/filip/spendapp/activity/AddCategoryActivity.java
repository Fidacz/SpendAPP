package com.example.filip.spendapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.filip.spendapp.R;
import com.example.filip.spendapp.SQLHelper;
import com.example.filip.spendapp.data.Category;

import java.util.ArrayList;

/**
 * Created by Filip on 18. 11. 2015.
 */
public class AddCategoryActivity extends AppCompatActivity implements View.OnClickListener {


    private Button save;
    private EditText nameEditText;
    private Spinner masterCategory ;
    private Category category = new Category();
    private boolean isEdit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        nameEditText = (EditText) findViewById(R.id.name);
        masterCategory = (Spinner) findViewById(R.id.masterCategory);
        SQLHelper db = new SQLHelper(this,"spendApp",null ,1);
        ArrayList<Category> categories = new ArrayList<>();
        categories = db.getMasterCategories();
        String[] values = new String[categories.size() + 1];

        for (int i = 0; i < categories.size() +1 ; i++){
            if (i == 0){
                //prazdna kategorie
            values[i] = "";
        }else {
                values[i] = categories.get(i - 1).getName();
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        masterCategory.setAdapter(dataAdapter);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();


        if (bundle != null ){
            // editace doplneni hodnot
            isEdit = true;
            category = db.getCategory(bundle.getInt("id"));
            nameEditText.setText(category.getName());

                if (category.getMasterCategory() == null){
                    masterCategory.setSelection(0);
                }else {
                    for (int i = 0; i < categories.size() +1 ; i++){
                        if (category.getMasterCategory().equals(values[i])) {
                             masterCategory.setSelection(i);
                    }
                }
            }


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
                SQLHelper db = new SQLHelper(this, "spendApp", null, 1);
                if (isEdit){
                    //jedna se o editaci existujici kategorie


                    if(category.getMasterCategory() == null && !String.valueOf(masterCategory.getSelectedItem()).equals("")  ){
                        // pokud master se stava slave musis slaves prejit pod noveho mastra
                        ArrayList<Category> slaveCategories = db.getSlaveCategories(category.getName());
                        for (int i = 0; i <slaveCategories.size(); i++ ){
                            slaveCategories.get(i).setMasterCategory(String.valueOf(masterCategory.getSelectedItem()));
                            db.updateCategory(slaveCategories.get(i));
                        }
                    }

                    if (String.valueOf(masterCategory.getSelectedItem()).equals("")) {
                        //je mastr
                        category.setMasterCategory(null);
                        if (!category.getName().equals(String.valueOf(nameEditText.getText()))){
                            //mastr meni meno, u sve jeho slave se musi zmenit mastrname
                            ArrayList<Category> slaveCategories = db.getSlaveCategories(category.getName());
                            for (int i = 0; i <slaveCategories.size(); i++ ){
                                slaveCategories.get(i).setMasterCategory(String.valueOf(nameEditText.getText()));
                                db.updateCategory(slaveCategories.get(i));
                            }
                        }
                    }else {
                        category.setMasterCategory(String.valueOf(masterCategory.getSelectedItem()));
                    }

                    category.setName(String.valueOf(nameEditText.getText()));




                    db.updateCategory(category);

                }else {
                    //nova categorie

                    category.setId(db.getMaxIDCategory() + 1);
                    category.setName(String.valueOf(nameEditText.getText()));
                    if (!String.valueOf(masterCategory.getSelectedItem()).equals("")) {
                        //kdyz to je mastr kategorie
                        category.setMasterCategory(String.valueOf(masterCategory.getSelectedItem()));
                    }

                    db.addCategory(category);

                }
                db.close();
                this.finish();
                break;

        }

    }


}
