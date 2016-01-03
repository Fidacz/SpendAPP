package com.example.filip.spendapp.adapter;

import com.example.filip.spendapp.data.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Filip on 31. 12. 2015.
 */
public class CategoryGroup{

public Category category;
public final List<Category> children = new ArrayList<Category>();



        public CategoryGroup(Category category) {
            this.category = category;
        }

}
