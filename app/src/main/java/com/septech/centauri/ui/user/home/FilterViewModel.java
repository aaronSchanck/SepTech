package com.septech.centauri.ui.user.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


//probably better to call a search everytime that the filters are updated, after pressing an
// "apply filters" button
public class FilterViewModel extends ViewModel {
    MutableLiveData<String> searchQuery = new MutableLiveData<>();

    private MutableLiveData<Float> leftSliderLiveData = new MutableLiveData<>();
    private MutableLiveData<Float> rightSliderLiveData = new MutableLiveData<>();

    public FilterViewModel() {
        System.out.println();
    }

    public MutableLiveData<Float> getLeftSliderLiveData() {
        return leftSliderLiveData;
    }

    public MutableLiveData<Float> getRightSliderLiveData() {
        return rightSliderLiveData;
    }
}

