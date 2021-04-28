package com.septech.centauri.ui.user.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.septech.centauri.R;
import com.septech.centauri.domain.models.User;
import com.septech.centauri.ui.interfaces.CallBackListener;
import com.septech.centauri.ui.user.help.HelpFragment;
import com.septech.centauri.ui.user.orderhistory.OrderHistoryFragment;
import com.septech.centauri.ui.user.search.SearchFragment;
import com.septech.centauri.ui.user.cart.CartFragment;
import com.septech.centauri.ui.user.viewhistory.ViewHistoryFragment;
import com.septech.centauri.ui.user.wishlist.WishlistFragment;


public class HomeFragment extends Fragment {
    private UserViewModel mUserViewModel;

    private CallBackListener callBackListener;

    private ImageButton mViewItemsBtn;
    private ImageButton mOrdersBtn;
    private ImageButton mBidsBtn;
    private ImageButton mOrderHistoryBtn;
    private ImageButton mViewHistoryBtn;
    private ImageButton mHelpBtn;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            callBackListener = (CallBackListener) context;
            callBackListener.initFragment();
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
        View view = inflater.inflate(R.layout.user_home_fragment, container, false);

        mViewItemsBtn = view.findViewById(R.id.home_view_all_items_btn);
        mOrdersBtn = view.findViewById(R.id.home_my_orders_btn);
        mBidsBtn = view.findViewById(R.id.home_my_bids_btn);
        mOrderHistoryBtn = view.findViewById(R.id.home_order_history_btn);
        mViewHistoryBtn = view.findViewById(R.id.home_view_history_btn);
        mHelpBtn = view.findViewById(R.id.home_need_help_btn);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mUserViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        mUserViewModel.getUserLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user == null) return;
            }
        });

        mViewItemsBtn.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance();

            Bundle bundle = new Bundle();
            bundle.putString("query", "");
            fragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentfragment, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        //TODO change button
        mOrdersBtn.setOnClickListener(v -> {
            //TODO
        });

        //TODO also change button
        mBidsBtn.setOnClickListener(v -> {
            //TODO
        });

        mOrderHistoryBtn.setOnClickListener(v -> {
            OrderHistoryFragment fragment = OrderHistoryFragment.newInstance();

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentfragment, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        mViewHistoryBtn.setOnClickListener(v -> {
            ViewHistoryFragment fragment = ViewHistoryFragment.newInstance();

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentfragment, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        mHelpBtn.setOnClickListener(v -> {
            HelpFragment fragment = HelpFragment.newInstance();

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentfragment, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

}