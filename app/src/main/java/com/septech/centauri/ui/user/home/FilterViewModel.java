package com.septech.centauri.ui.user.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FilterViewModel extends ViewModel {
    MutableLiveData<String> searchQuery = new MutableLiveData<>();

    private MutableLiveData<Float> sliderLiveData = new MutableLiveData<>();

    public FilterViewModel() {
        System.out.println();
    }


    public MutableLiveData<Float> getSliderLiveData() {
        return sliderLiveData;
    }
}

