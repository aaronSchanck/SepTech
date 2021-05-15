package com.septech.centauri.ui.user.cart;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.septech.centauri.R;
import com.septech.centauri.lib.Formatting;
import com.septech.centauri.ui.interfaces.CallBackListener;
import com.septech.centauri.ui.user.home.UserViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class CartFragment extends Fragment implements CartItemAdapter.OnCartItemListener {

    private CartViewModel mViewModel;
    private UserViewModel mUserViewModel;

    private RecyclerView mCartRv;
    private CartItemAdapter mCartAdapter;

    private TextView mTaxTv;
    private TextView mShippingTv;
    private TextView mTotalTv;

    private Button mCheckoutBtn;

    private CallBackListener mCallBackListener;

    public static CartFragment newInstance() {
        return new CartFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_cart_fragment, container, false);

        mCartRv = view.findViewById(R.id.rvCartItems);

        mCartAdapter = new CartItemAdapter(this, new ArrayList<>(), new HashMap<>());
        mCartRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        mTaxTv = view.findViewById(R.id.cartTaxTextView);
        mShippingTv = view.findViewById(R.id.cartShippingText);
        mTotalTv = view.findViewById(R.id.cartTotalTextView);

        mCheckoutBtn = view.findViewById(R.id.cartCheckoutBtn);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        mUserViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        mViewModel.setUserId(mUserViewModel.getUserId());

        mCallBackListener.showLoadingIcon();
        mUserViewModel.getOrderLiveData().observe(getViewLifecycleOwner(), order -> {
            if (order == null) {
                return;
            }

            mViewModel.setOrderLiveData(order);

            mCartAdapter.setCart(order.getOrderItems());
            mCartRv.setAdapter(mCartAdapter);

            if (order.getOrderItems().size() > 0) {
                mViewModel.getImagesLiveData().observe(getViewLifecycleOwner(), images -> {
                    if (images == null) {
                        return;
                    }

                    mCallBackListener.hideLoadingIcon();

                    mCartAdapter.setImages(images);
                    mCartRv.setAdapter(mCartAdapter);

                    mCartRv.setVisibility(View.VISIBLE);
                });
            } else {
                mCallBackListener.hideLoadingIcon();

                mCartRv.setAdapter(mCartAdapter);
                mCartRv.setVisibility(View.VISIBLE);
            }

            DecimalFormat df = Formatting.getMoneyDecimalFormat();

            mTaxTv.setText(getResources().getString(R.string.cartTaxText,
                    df.format(mViewModel.getTax())));
            mShippingTv.setText(getResources().getString(R.string.cartShippingText,
                    df.format(mViewModel.getShippingPrice())));
            mTotalTv.setText(getResources().getString(R.string.cartTotalText,
                    df.format(mViewModel.getTotalPrice())));

            mCheckoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("v = " + v);
                }
            });
        });

        mUserViewModel.refreshUserCart();
    }

    @Override
    public void onItemClick(int position) {
    }

    @Override
    public void onItemLongClick(int position) {
    }
}