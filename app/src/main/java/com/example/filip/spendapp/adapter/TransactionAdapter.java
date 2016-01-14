package com.example.filip.spendapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.example.filip.spendapp.R;
import com.example.filip.spendapp.data.Category;
import com.example.filip.spendapp.data.Transaction;

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
        ((CheckedTextView) convertView).setText(group.getTransaction().getDate());
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

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.transaction_listrow_details, null);
        }

        date = (TextView) convertView.findViewById(R.id.textDate);
        date.setText(children.getDate());
        //date.setText("datum");
        category = (TextView) convertView.findViewById(R.id.textCategory);
        category.setText(children.getCategory());
        value = (TextView) convertView.findViewById(R.id.textValue);
        value.setText(String.valueOf(children.getValue()));
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
