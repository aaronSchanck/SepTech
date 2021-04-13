package com.septech.centauri.ui.user.search;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.septech.centauri.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchFragment extends Fragment implements ItemAdapter.OnItemListener {
    private static final String TAG = "SearchFragment";

    private SearchViewModel mViewModel;

    private RecyclerView rvItems;
    private ItemAdapter adapter;

    private ImageButton forwardArrow;
    private ImageButton backArrow;

    private TextView searchAmountTextView;

    private String query;

    //timing variables
    private long searchStartTime;
    private long searchEndtime;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_search_fragment, container, false);

        query = getArguments().getString("query");

        rvItems = view.findViewById(R.id.rvItems);

        adapter = new ItemAdapter(this, new ArrayList<>(), new HashMap<>());
        rvItems.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        searchAmountTextView = view.findViewById(R.id.itemCountTextView);

        forwardArrow = view.findViewById(R.id.forwardArrow);
        backArrow = view.findViewById(R.id.backArrow);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        createButtonListeners();
        createLiveDataObservers();

        searchStartTime = System.currentTimeMillis();
        mViewModel.newSearch(query);
    }

    private void createButtonListeners() {
        forwardArrow.setOnClickListener(v -> {
            mViewModel.nextPage();
            updatePageArrows();
        });

        backArrow.setOnClickListener(v -> {
            mViewModel.lastPage();
            updatePageArrows();
        });
    }

    private void updatePageArrows() {
        //set forward arrow visilibity
        if ((mViewModel.getCurrentPage() + 1) * mViewModel.getPageSize() < mViewModel.getSearchAmount().getValue()) {
            forwardArrow.setActivated(true);
            forwardArrow.setVisibility(View.VISIBLE);
        } else {
            forwardArrow.setActivated(false);
            forwardArrow.setVisibility(View.GONE);
        }
        //set back arrow visibility
        if (mViewModel.getCurrentPage() > 0) {
            backArrow.setActivated(true);
            backArrow.setVisibility(View.VISIBLE);
        } else {
            backArrow.setActivated(false);
            backArrow.setVisibility(View.GONE);
        }
    }

    private void createLiveDataObservers() {
        mViewModel.getItemsLiveData().observe(getViewLifecycleOwner(), items -> {
            Log.i(TAG, "ItemLiveData input");
            adapter.setItems(items);
            int[] itemIds = new int[items.size()];

            for (int i = 0; i < items.size(); i++) {
                itemIds[i] = items.get(i).getId();
            }

            mViewModel.getImages(itemIds);
        });

        mViewModel.getImagesLiveData().observe(getViewLifecycleOwner(), images -> {
            Log.i(TAG, "createLiveDataObservers: ImageLiveData incoming");
            adapter.setImages(images);

            rvItems.setAdapter(adapter);
        });

        mViewModel.getSearchAmount().observe(getViewLifecycleOwner(), integer -> {
            updatePageArrows();
            searchEndtime = System.currentTimeMillis();

            double timeElapsed = (searchEndtime - searchStartTime) / 1000.0;

            searchAmountTextView.setText(getResources().getString(R.string.item_amount_string,
                    String.valueOf(integer), String.valueOf(timeElapsed)));
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