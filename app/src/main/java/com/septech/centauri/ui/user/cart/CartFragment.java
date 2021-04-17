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
import com.septech.centauri.ui.user.home.CallBackListener;
import com.septech.centauri.ui.user.home.HomeViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class CartFragment extends Fragment implements CartItemAdapter.OnCartItemListener {

    private CartViewModel mViewModel;
    private HomeViewModel mHomeViewModel;

    private RecyclerView rvCartItems;
    private CartItemAdapter cartItemAdapter;

    private CallBackListener callBackListener;

    private TextView taxTextView;
    private TextView shippingTextView;
    private TextView totalTextView;

    private Button checkoutBtn;

    private DecimalFormat df;

    public static CartFragment newInstance() {
        return new CartFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_cart_fragment, container, false);

        rvCartItems = view.findViewById(R.id.rvCartItems);

        cartItemAdapter = new CartItemAdapter(this, new ArrayList<>(), new HashMap<>());
        rvCartItems.setLayoutManager(new LinearLayoutManager(getActivity()));

        taxTextView = view.findViewById(R.id.cartTaxTextView);
        shippingTextView = view.findViewById(R.id.cartShippingText);
        totalTextView = view.findViewById(R.id.cartTotalTextView);

        checkoutBtn = view.findViewById(R.id.cartCheckoutBtn);

        df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(0);
        df.setGroupingUsed(false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        mHomeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        mViewModel.setUserId(mHomeViewModel.getUserId());

        callBackListener.showLoadingIcon();
        mViewModel.getOrderLiveData().observe(getViewLifecycleOwner(), order -> {
            if (order == null) {
                return;
            }

            cartItemAdapter.setCart(order.getOrderItems());
            rvCartItems.setAdapter(cartItemAdapter);

            mHomeViewModel.updateOrderData(order);

            if (order.getOrderItems().size() > 0) {
                mViewModel.getImagesLiveData().observe(getViewLifecycleOwner(), images -> {
                    if (images == null) {
                        return;
                    }

                    callBackListener.hideLoadingIcon();

                    cartItemAdapter.setImages(images);
                    rvCartItems.setAdapter(cartItemAdapter);

                    rvCartItems.setVisibility(View.VISIBLE);
                });
            } else {
                callBackListener.hideLoadingIcon();

                rvCartItems.setAdapter(cartItemAdapter);
                rvCartItems.setVisibility(View.VISIBLE);
            }



            getResources();

            taxTextView.setText(getResources().getString(R.string.cartTaxText,
                    df.format(mViewModel.getTax())));
            shippingTextView.setText(getResources().getString(R.string.cartShippingText,
                    df.format(mViewModel.getShippingPrice())));
            totalTextView.setText(getResources().getString(R.string.cartTotalText,
                    df.format(mViewModel.getTotalPrice())));

            checkoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("v = " + v);
                }
            });
        });
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemLongClick(int position) {

    }
}