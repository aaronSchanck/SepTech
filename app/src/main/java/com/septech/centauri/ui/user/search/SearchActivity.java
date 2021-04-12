package com.septech.centauri.ui.user.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.septech.centauri.R;
import com.septech.centauri.domain.models.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity implements ItemAdapter.OnItemListener {
    private static final String TAG = "SearchActivity";

    private SearchViewModel mViewModel;

    private RecyclerView rvItems;

    private ImageButton forwardArrow;
    private ImageButton backArrow;

    private TextView searchAmountTextView;

    private EditText searchEditText;

    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);

        mViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        String query = getIntent().getStringExtra("query");

        rvItems = findViewById(R.id.rvItems);

        adapter = new ItemAdapter(this, new ArrayList<>(), new HashMap<>());

//        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(new GridLayoutManager(this, 2));

        //find buttons and bind listeners to them

        forwardArrow = findViewById(R.id.forwardArrow);
        backArrow = findViewById(R.id.backArrow);

        backArrow.setVisibility(View.GONE);
        backArrow.setActivated(false);

        searchAmountTextView = findViewById(R.id.itemCountTextView);

        mViewModel.newSearch(query);

        createButtonListeners();

        //find edit texts and bind watchers to them

        searchEditText = findViewById(R.id.searchEditText);

        createTextWatchers();

        //bind observers to watch the state of livedata

        createLiveDataObservers();
    }

    private void createButtonListeners() {
        forwardArrow.setOnClickListener(v -> mViewModel.nextPage());

        backArrow.setOnClickListener(v -> mViewModel.lastPage());
    }

    private void createTextWatchers() {
    }

    private void createLiveDataObservers() {
        mViewModel.getItemsLiveData().observe(this, items -> {
            Log.i(TAG, "ItemLiveData input");
            adapter.setItems(items);
            int[] itemIds = new int[items.size()];

            for (int i = 0; i < items.size(); i++) {
                itemIds[i] = items.get(i).getId();
            }

            mViewModel.getImages(itemIds);
        });

        mViewModel.getImagesLiveData().observe(this, images -> {
            Log.i(TAG, "createLiveDataObservers: ImageLiveData incoming");
            adapter.setImages(images);

            rvItems.setAdapter(adapter);
        });

        mViewModel.getSearchAmount().observe(this, integer -> {
            searchAmountTextView.setText(getResources().getString(R.string.item_amount_string,
                    String.valueOf(integer), "2"));

        });
    }

    @Override
    public void onItemClick(int position) {
        Log.i(TAG, "onItemClick: " + position);
    }

    @Override
    public void onItemLongClick(int position) {
        Log.i(TAG, "onItemLongClick: " + position);
    }
}