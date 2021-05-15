package com.septech.centauri.domain.models;

import androidx.annotation.NonNull;

public abstract class GenericModel {
    private int id;

    public GenericModel() {
        this.id = -1;
    }

    public GenericModel(int id) {
        this.id = id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public abstract void initTestData();

    @NonNull
    @Override
    public String toString() {
        return "model " + ((id == -1) ? "NaN" : id);
    }
}
