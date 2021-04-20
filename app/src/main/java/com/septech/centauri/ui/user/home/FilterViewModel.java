package com.septech.centauri.ui.user.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


//probably better to call a search everytime that the filters are updated, after pressing an
// "apply filters" button
public class FilterViewModel extends ViewModel {
    private MutableLiveData<String> searchQuery;

    private MutableLiveData<Float> leftSliderLiveData;
    private MutableLiveData<Float> rightSliderLiveData;

    public FilterViewModel() {
    }

    public MutableLiveData<String> getQueryLiveData() {
        if(searchQuery == null) {
            searchQuery = new MutableLiveData<>();
        }
        return searchQuery;
    }

    public MutableLiveData<Float> getLeftSliderLiveData() {
        if(leftSliderLiveData == null) {
            leftSliderLiveData = new MutableLiveData<>();
        }
        return leftSliderLiveData;
    }

    public MutableLiveData<Float> getRightSliderLiveData() {
        if(rightSliderLiveData == null) {
            rightSliderLiveData = new MutableLiveData<>();
        }
        return rightSliderLiveData;
    }
}

