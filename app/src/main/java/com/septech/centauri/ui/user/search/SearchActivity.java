package com.septech.centauri.ui.user.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.septech.centauri.R;
import com.septech.centauri.domain.models.Item;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private SearchViewModel mViewModel;

    private RecyclerView rvItems;

    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);

        mViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        String query = getIntent().getStringExtra("query");

        mViewModel.getItems(query);

        rvItems = findViewById(R.id.rvItems);

        adapter = new ItemAdapter(new ArrayList<>());

        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(new GridLayoutManager(this, 2));

        createButtonListeners();

        createTextWatchers();

        createLiveDataObservers();
    }

    private void createButtonListeners() {
    }

    private void createTextWatchers() {
    }

    private void createLiveDataObservers() {
        mViewModel.getItemsLiveData().observe(this, items -> {
            System.out.println("newitems incoming");
            adapter.setItems(items);
        });
    }
}