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

    //timing variables
//    private long searchStartTime;
//    private long searchEndtime;

    //callback listener
    private CallBackListener callBackListener;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            callBackListener = (CallBackListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CallBackListener");
        }
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
        mViewModel.setQuery(getArguments().getString("query"));

        mFilterViewModel = new ViewModelProvider(requireActivity()).get(FilterViewModel.class);

        createButtonListeners();
        createLiveDataObservers();

        System.out.println(mAlreadyLoaded);
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
        if ((mViewModel.getCurrentPage() + 1) * mViewModel.getPageSize() < mViewModel.getSearchAmountLiveData().getValue()) {
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
        mViewModel.getSearchAmountLiveData().observe(getViewLifecycleOwner(), integer -> {
            if(integer == null) {
                return;
            }

            searchAmountTextView.setText(getResources().getString(R.string.item_amount_string,
                    String.valueOf(integer), String.valueOf(0)));
            updatePageArrows();
        });

        mViewModel.getItemsLiveData().observe(getViewLifecycleOwner(), items -> {
            if(items == null) {
                return;
            }

            Log.i(TAG, "ItemLiveData input");
            adapter.setItems(items);

            if (items.size() > 0) {
                mViewModel.getImagesLiveData().observe(getViewLifecycleOwner(), images -> {
                    if (images == null) {
                        return;
                    }

                    Log.i(TAG, "createLiveDataObservers: ImageLiveData incoming");
                    adapter.setImages(images);
                    rvItems.setAdapter(adapter);
                });
            } else {
                rvItems.setAdapter(adapter);
            }
            callBackListener.hideLoadingIcon();

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

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentfragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onItemLongClick(int position) {
        Log.i(TAG, "onItemLongClick: " + position);
    }
}