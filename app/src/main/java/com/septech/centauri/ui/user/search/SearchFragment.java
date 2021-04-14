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
import com.septech.centauri.ui.user.home.CallBackListener;
import com.septech.centauri.ui.user.home.FilterViewModel;
import com.septech.centauri.ui.user.listing.ListingFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchFragment extends Fragment implements OnSearchItemListener {
    private static final String TAG = "SearchFragment";

    private SearchViewModel mViewModel;
    private FilterViewModel mFilterViewModel;

    private boolean mAlreadyLoaded;

    private RecyclerView rvItems;
    private CompactItemItemView adapter;

    private ImageButton forwardArrow;
    private ImageButton backArrow;

    private TextView searchAmountTextView;

    private String query;

    //timing variables
    private long searchStartTime;
    private long searchEndtime;

    //callback listener
    private CallBackListener callBackListener;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            callBackListener = (CallBackListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CallBackListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mAlreadyLoaded = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAlreadyLoaded = true;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_search_fragment, container, false);

        query = getArguments().getString("query");

        rvItems = view.findViewById(R.id.rvItems);

        adapter = new CompactItemItemView(this, new ArrayList<>(), new HashMap<>());
        rvItems.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        rvItems.setAdapter(adapter);

        searchAmountTextView = view.findViewById(R.id.itemCountTextView);

        forwardArrow = view.findViewById(R.id.forwardArrow);
        backArrow = view.findViewById(R.id.backArrow);

        callBackListener.showLoadingIcon();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        mFilterViewModel = new ViewModelProvider(requireActivity()).get(FilterViewModel.class);

        createButtonListeners();
        createLiveDataObservers();

        searchStartTime = System.currentTimeMillis();

        System.out.println(mAlreadyLoaded);
        if(!mAlreadyLoaded)  {
            mViewModel.newSearch(query);
        }
    }

    private void createButtonListeners() {
        forwardArrow.setOnClickListener(v -> {
            rvItems.setVisibility(View.GONE);
            callBackListener.showLoadingIcon();
            adapter = new CompactItemItemView(this, new ArrayList<>(), new HashMap<>());

            mViewModel.nextPage();
            updatePageArrows();
        });

        backArrow.setOnClickListener(v -> {
            rvItems.setVisibility(View.GONE);
            callBackListener.showLoadingIcon();
            adapter = new CompactItemItemView(this, new ArrayList<>(), new HashMap<>());

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
        mViewModel.getSearchAmount().observe(getViewLifecycleOwner(), integer -> {
            updatePageArrows();
            searchEndtime = System.currentTimeMillis();

            double timeElapsed = (searchEndtime - searchStartTime) / 1000.0;

            searchAmountTextView.setText(getResources().getString(R.string.item_amount_string,
                    String.valueOf(integer), String.valueOf(timeElapsed)));
        });

        mViewModel.getItemsLiveData().observe(getViewLifecycleOwner(), items -> {
            Log.i(TAG, "ItemLiveData input");
            adapter.setItems(items);
            int[] itemIds = new int[items.size()];

            for (int i = 0; i < items.size(); i++) {
                itemIds[i] = items.get(i).getId();
            }

            if(itemIds.length > 0) {
                mViewModel.getImages(itemIds);
            } else {
                rvItems.setVisibility(View.VISIBLE);
                rvItems.setAdapter(adapter);

                callBackListener.hideLoadingIcon();
            }
        });

        mViewModel.getImagesLiveData().observe(getViewLifecycleOwner(), images -> {
            callBackListener.hideLoadingIcon();
            Log.i(TAG, "createLiveDataObservers: ImageLiveData incoming");
            adapter.setImages(images);
            rvItems.setAdapter(adapter);

            rvItems.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void onItemClick(int position) {
        ListingFragment fragment = ListingFragment.newInstance();

        int itemId = adapter.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id", itemId);

        fragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentfragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onItemLongClick(int position) {
        Log.i(TAG, "onItemLongClick: " + position);
    }

}