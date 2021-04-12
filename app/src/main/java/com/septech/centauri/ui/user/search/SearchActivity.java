package com.septech.centauri.ui.user.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.septech.centauri.R;
import com.septech.centauri.domain.models.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {

    private SearchViewModel mViewModel;

    private RecyclerView rvItems;

    private ImageButton forwardArrow;
    private ImageButton backArrow;

    private EditText searchEditText;

    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);

        mViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        String query = getIntent().getStringExtra("query");

        mViewModel.getItems(query, 0);

        rvItems = findViewById(R.id.rvItems);

        adapter = new ItemAdapter(new ArrayList<>(), new HashMap<>());

        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(new GridLayoutManager(this, 2));

        //find buttons and bind listeners to them

        forwardArrow = findViewById(R.id.forwardArrow);
        backArrow = findViewById(R.id.backArrow);

        createButtonListeners();

        //find edit texts and bind watchers to them

        searchEditText = findViewById(R.id.searchEditText);

        createTextWatchers();

        //bind observers to watch the state of livedata

        createLiveDataObservers();
    }

    private void createButtonListeners() {
        forwardArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void createTextWatchers() {
    }

    private void createLiveDataObservers() {
        mViewModel.getItemsLiveData().observe(this, items -> {
            System.out.println("newitems incoming");
            adapter.setItems(items);
            int[] itemIds = new int[items.size()];

            for (int i = 0; i < items.size(); i++) {
                itemIds[i] = items.get(i).getId();
            }

            mViewModel.getImages(itemIds);
        });

        mViewModel.getImagesLiveData().observe(this, images -> {
            System.out.println("images incoming");
            adapter.setImages(images);

            rvItems.setAdapter(adapter);
        });
    }
}