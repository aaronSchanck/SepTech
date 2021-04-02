package com.septech.centauri.data.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryEntity {

    @SerializedName("id")
    @Expose(serialize = false)
    private Integer id;

    @SerializedName("category_1")
    @Expose
    private Integer category1;

    @SerializedName("category_2")
    @Expose
    private String category2;

    @SerializedName("category_3")
    @Expose
    private Integer category3;

    @SerializedName("category_4")
    @Expose
    private String category4;

    @SerializedName("category_5")
    @Expose
    private Integer category5;

    public CategoryEntity() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategory1() {
        return category1;
    }

    public void setCategory1(Integer category1) {
        this.category1 = category1;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public Integer getCategory3() {
        return category3;
    }

    public void setCategory3(Integer category3) {
        this.category3 = category3;
    }

    public String getCategory4() {
        return category4;
    }

    public void setCategory4(String category4) {
        this.category4 = category4;
    }

    public Integer getCategory5() {
        return category5;
    }

    public void setCategory5(Integer category5) {
        this.category5 = category5;
    }
}
