package com.septech.centauri.ui.user.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.septech.centauri.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CartFragment extends Fragment implements CartItemAdapter.OnCartItemListener {

    private CartViewModel mViewModel;

    private RecyclerView rvItems;
    private CartItemAdapter itemAdapter;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_cart_fragment, container, false);

        rvItems = view.findViewById(R.id.rvItems);

        itemAdapter = new CartItemAdapter(this, new ArrayList<>(), new HashMap<>());
        rvItems.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CartViewModel.class);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemLongClick(int position) {

    }
}