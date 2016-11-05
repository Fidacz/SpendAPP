package com.example.filip.spendapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.example.filip.spendapp.R;
import com.example.filip.spendapp.SQLHelper;
import com.example.filip.spendapp.data.Category;
import com.example.filip.spendapp.data.Transaction;

import java.util.Calendar;

/**
 * Created by fida on 16.8.15.
 */
public class TransactionAdapter extends BaseExpandableListAdapter {

    private final SparseArray<TransactioGroup> groups;
    public LayoutInflater inflater;
    public Activity activity;

    public TransactionAdapter(Activity act,SparseArray<TransactioGroup> groups) {
        activity = act;
        this.groups = groups;
        inflater = act.getLayoutInflater();
    }


    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).getChildren().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.transaction_listrow_group, null);
        }

        TransactioGroup group = (TransactioGroup) getGroup(groupPosition);
        Calendar cal = Calendar.getInstance();
        cal.setTime(group.getTransaction().getDateDate());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);

        switch (month){
            case 0:
                ((CheckedTextView) convertView).setText(convertView.getContext().getString(R.string.m0) + " " + String.valueOf(year));
                break;
            case 1:
                ((CheckedTextView) convertView).setText(convertView.getContext().getString(R.string.m1) + " " + String.valueOf(year));
                break;
            case 2:
                ((CheckedTextView) convertView).setText(convertView.getContext().getString(R.string.m2) + " " + String.valueOf(year) );
                break;
            case 3:
                ((CheckedTextView) convertView).setText(convertView.getContext().getString(R.string.m3) + " " + String.valueOf(year));
                break;
            case 4:
                ((CheckedTextView) convertView).setText(convertView.getContext().getString(R.string.m4) + " " + String.valueOf(year));
                break;
            case 5:
                ((CheckedTextView) convertView).setText(convertView.getContext().getString(R.string.m5) + " " + String.valueOf(year));
                break;
            case 6:
                ((CheckedTextView) convertView).setText(convertView.getContext().getString(R.string.m6) + " " + String.valueOf(year));
                break;
            case 7:
                ((CheckedTextView) convertView).setText(convertView.getContext().getString(R.string.m7) + " " + String.valueOf(year));
                break;
            case 8:
                ((CheckedTextView) convertView).setText(convertView.getContext().getString(R.string.m8) + " " + String.valueOf(year));
                break;
            case 9:
                ((CheckedTextView) convertView).setText(convertView.getContext().getString(R.string.m9) + " " + String.valueOf(year));
                break;
            case 10:
                ((CheckedTextView) convertView).setText(convertView.getContext().getString(R.string.m10) + " " + String.valueOf(year));
                break;
            case 11:
                ((CheckedTextView) convertView).setText(convertView.getContext().getString(R.string.m11) + " " + String.valueOf(year));
                break;

        }


        /**
        CategoryGroup group = (CategoryGroup) getGroup(groupPosition);
        if (group.category.isSelected()) {
            ((CheckedTextView) convertView).setText(group.category.getName());
            ((CheckedTextView) convertView).setChecked(isExpanded);
            convertView.setBackgroundColor(0xffcccccc);
        }else {
            ((CheckedTextView) convertView).setText(group.category.getName());
            ((CheckedTextView) convertView).setChecked(isExpanded);
            convertView.setBackgroundColor(0x0);
        }

         */
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Transaction children = (Transaction)getChild(groupPosition, childPosition);
        TextView date = null;
        TextView category = null;
        TextView value = null;
        TransactioGroup group = (TransactioGroup) getGroup(groupPosition);
        Calendar cal = Calendar.getInstance();
        cal.setTime(children.getDateDate());
        String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.transaction_listrow_details, null);
        }

        if (children.isSelected()) {
            //zabarveni jestli ze ej vybranej item
            convertView.setBackgroundColor(0xffcccccc);
        }else {
            convertView.setBackgroundColor(0x0);
        }

        date = (TextView) convertView.findViewById(R.id.textDate);

        date.setText(day);
        //date.setText("datum");
        category = (TextView) convertView.findViewById(R.id.textCategory);;
        category.setText(children.getCategory().getName());
        value = (TextView) convertView.findViewById(R.id.textValue);

        if (children.getType() == 1){
            value.setText(String.valueOf("-"+children.getValue()));
            value.setTextColor(Color.RED);
        }else if (children.getType() == 0){
            value.setText(String.valueOf(children.getValue()));
            value.setTextColor(Color.BLACK);
        }
        /**
        final Category children = (Category)getChild(groupPosition, childPosition);
        TextView text = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.category_listrow_details, null);
        }
        if (children.isSelected()) {
            text = (TextView) convertView.findViewById(R.id.textView1);
            text.setText(children.getName());
            convertView.setBackgroundColor(0xffcccccc);
        }else {
            text = (TextView) convertView.findViewById(R.id.textView1);
            text.setText(children.getName());
            convertView.setBackgroundColor(0x0);
        }


        */
        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
