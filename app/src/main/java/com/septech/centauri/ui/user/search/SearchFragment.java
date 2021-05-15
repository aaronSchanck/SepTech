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
import com.septech.centauri.ui.interfaces.CallBackListener;
import com.septech.centauri.ui.user.home.FilterViewModel;
import com.septech.centauri.ui.user.listing.ListingFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchFragment extends Fragment implements OnSearchItemListener {
    private static final String TAG = "SearchFragment";

    private SearchViewModel mViewModel;
    private FilterViewModel mFilterViewModel;

    private RecyclerView mItemsRv;
    private CompactItemItemView mItemsAdapter;

    private ImageButton mForwardArrow;
    private ImageButton mBackArrow;

    private TextView mSearchAmtTv;

    //callback listener
    private CallBackListener mCallBackListener;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mCallBackListener = (CallBackListener) context;
            mCallBackListener.initFragment();
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

        mItemsRv = view.findViewById(R.id.rvItems);

        mItemsAdapter = new CompactItemItemView(this, new ArrayList<>(), new HashMap<>());
        mItemsRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mItemsRv.setAdapter(mItemsAdapter);

        mSearchAmtTv = view.findViewById(R.id.itemCountTextView);

        mForwardArrow = view.findViewById(R.id.forwardArrow);
        mBackArrow = view.findViewById(R.id.backArrow);

        mCallBackListener.showLoadingIcon();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        mViewModel.setQuery(getArguments().getString("query"));

        mFilterViewModel = new ViewModelProvider(requireActivity()).get(FilterViewModel.class);

        mCallBackListener.showLoadingIcon();

        createButtonListeners();
        createLiveDataObservers();
    }

    private void createButtonListeners() {
        mForwardArrow.setOnClickListener(v -> {
            mItemsRv.setVisibility(View.GONE);
            mCallBackListener.showLoadingIcon();
            mItemsAdapter = new CompactItemItemView(this, new ArrayList<>(), new HashMap<>());

            mViewModel.nextPage();
            updatePageArrows();
        });

        mBackArrow.setOnClickListener(v -> {
            mItemsRv.setVisibility(View.GONE);
            mCallBackListener.showLoadingIcon();
            mItemsAdapter = new CompactItemItemView(this, new ArrayList<>(), new HashMap<>());

            mViewModel.lastPage();
            updatePageArrows();
        });
    }

    private void updatePageArrows() {
        //set forward arrow visilibity
        if ((mViewModel.getCurrentPage() + 1) * mViewModel.getPageSize() < mViewModel.getSearchAmountLiveData().getValue()) {
            mForwardArrow.setActivated(true);
            mForwardArrow.setVisibility(View.VISIBLE);
        } else {
            mForwardArrow.setActivated(false);
            mForwardArrow.setVisibility(View.GONE);
        }
        //set back arrow visibility
        if (mViewModel.getCurrentPage() > 0) {
            mBackArrow.setActivated(true);
            mBackArrow.setVisibility(View.VISIBLE);
        } else {
            mBackArrow.setActivated(false);
            mBackArrow.setVisibility(View.GONE);
        }
    }

    private void createLiveDataObservers() {
        mViewModel.getSearchAmountLiveData().observe(getViewLifecycleOwner(), integer -> {
            if(integer == null) {
                return;
            }

            mSearchAmtTv.setText(getResources().getString(R.string.item_amount_string,
                    String.valueOf(integer), String.valueOf(mViewModel.getSearchTime())));
            updatePageArrows();
        });

        mViewModel.getItemsLiveData().observe(getViewLifecycleOwner(), items -> {
            if(items == null) {
                return;
            }

            Log.i(TAG, "ItemLiveData input");
            mItemsAdapter.setItems(items);

            if (items.size() > 0) {
                mViewModel.getImagesLiveData().observe(getViewLifecycleOwner(), images -> {
                    if (images == null) {
                        return;
                    }

                    Log.i(TAG, "createLiveDataObservers: ImageLiveData incoming");
                    mItemsAdapter.setImages(images);
                    mItemsRv.setAdapter(mItemsAdapter);
                    mCallBackListener.hideLoadingIcon();
                    mItemsRv.setVisibility(View.VISIBLE);
                });
            } else {
                mItemsRv.setAdapter(mItemsAdapter);
                mCallBackListener.hideLoadingIcon();
                mItemsRv.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        ListingFragment fragment = ListingFragment.newInstance();

        int itemId = mItemsAdapter.get(position);

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