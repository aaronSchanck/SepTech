package com.septech.centauri.data.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryEntity {

    @SerializedName("id")
    @Expose(serialize = false)
    private Integer id;

    @SerializedName("category_1")
    @Expose
    private String category1;

    @SerializedName("category_2")
    @Expose
    private String category2;

    @SerializedName("category_3")
    @Expose
    private String category3;

    @SerializedName("category_4")
    @Expose
    private String category4;

    @SerializedName("category_5")
    @Expose
    private String category5;

    public CategoryEntity() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
