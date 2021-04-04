package com.septech.centauri.domain.models;

import java.util.ArrayList;

public class Category {

    private String category1;
    private String category2;
    private String category3;
    private String category4;
    private String category5;

    public Category() {

    }

    public Category(String category1, String category2, String category3, String category4, String category5) {
        this.category1 = category1;
        this.category2 = category2;
        this.category3 = category3;
        this.category4 = category4;
        this.category5 = category5;
    }

    public String getCategory1() {
        return category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public String getCategory3() {
        return category3;
    }

    public void setCategory3(String category3) {
        this.category3 = category3;
    }

    public String getCategory4() {
        return category4;
    }

    public void setCategory4(String category4) {
        this.category4 = category4;
    }

    public String getCategory5() {
        return category5;
    }

    public void setCategory5(String category5) {
        this.category5 = category5;
    }

    public static ArrayList<Category> createCategories(int i) {
        ArrayList<Category> arr = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            Category cat = new Category();
            cat.setCategory1("Category1_" + j);
            cat.setCategory2("Category2_" + j);
            cat.setCategory3("Category3_" + j);
            cat.setCategory4("Category4_" + j);
            cat.setCategory5("Category5_" + j);
            arr.add(cat);
        }
        return arr;
    }

}
