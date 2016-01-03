package com.example.filip.spendapp.adapter;

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;

import android.widget.TextView;


import com.example.filip.spendapp.R;
import com.example.filip.spendapp.data.Category;



/**
 * Created by Filip on 31. 12. 2015.
 */
public class CategoryAdapter extends BaseExpandableListAdapter  {

    private final SparseArray<CategoryGroup> groups;
    public LayoutInflater inflater;
    public Activity activity;


    public CategoryAdapter(Activity act, SparseArray<CategoryGroup> groups) {
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
        return groups.get(groupPosition).children.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).children.get(childPosition);
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
                convertView = inflater.inflate(R.layout.category_listrow_group, null);
            }
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


            return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
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



        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }


    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }


}
