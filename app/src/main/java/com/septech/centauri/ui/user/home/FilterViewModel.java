package com.septech.centauri.ui.user.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.domain.models.SearchFilters;
import com.septech.centauri.ui.user.search.SearchSortType;
import com.septech.centauri.ui.user.search.SearchViewType;

import io.reactivex.annotations.NonNull;


//probably better to call a search everytime that the filters are updated, after pressing an
// "apply filters" button
public class FilterViewModel extends ViewModel {
    private MutableLiveData<String> mQueryLiveData;

    private MutableLiveData<SearchFilters> mSearchFiltersLiveData;

    public MutableLiveData<String> getQueryLiveData() {
        if (mQueryLiveData == null) {
            mQueryLiveData = new MutableLiveData<>();
        }
        return mQueryLiveData;
    }

    public void setQuery(String query) {
        if (mQueryLiveData == null) {
            mQueryLiveData = new MutableLiveData<>();
        }
        mQueryLiveData.setValue(query);
    }
    
    public String getQuery() {
        return mQueryLiveData.getValue();
    }

    @NonNull
    public SearchFilters getSearchFilters() {
        if (mSearchFiltersLiveData == null) {
            mSearchFiltersLiveData = new MutableLiveData<>();
        }
        return mSearchFiltersLiveData.getValue();
    }

    public void initSearchFilters() {
        if (mSearchFiltersLiveData == null) {
            mSearchFiltersLiveData = new MutableLiveData<>();
        }

        SearchFilters searchFilters = new SearchFilters(SearchViewType.COMPACT,
                SearchSortType.LOWEST_PRICE, 0, 0, false, false, false, 0, 0);

        mSearchFiltersLiveData.setValue(searchFilters);
    }
}

