package com.example.filip.spendapp.data;

import java.util.ArrayList;

/**
 * Created by fida on 15.8.15.
 */
public class Category {

    private int id;
    private String name;
    private ArrayList<Category> subCategories = new ArrayList<>();


    public Category(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Category> getSubCategories() {
        return subCategories;
    }

    public void addSubCategory (Category category){
        subCategories.add(category);
    }
}
