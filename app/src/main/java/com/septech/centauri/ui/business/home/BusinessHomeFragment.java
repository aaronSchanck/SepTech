package com.septech.centauri.ui.business.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.septech.centauri.R;
import com.septech.centauri.domain.models.Business;
import com.septech.centauri.ui.business.addlisting.AddListingFragment;

public class BusinessHomeFragment extends Fragment {

    private BusinessHomeViewModel mViewModel;
    private BusinessViewModel mBusinessViewModel;

    private TextView businessNameTextView;
    private TextView businessDescTextView;

    private Button addListingBtn;
    private Button myAuctionsBtn;
    private Button ordersBtn;

    public static BusinessHomeFragment newInstance() {
        return new BusinessHomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.business_home_fragment, container, false);

        businessNameTextView = view.findViewById(R.id.seller_homepage_name_text);
        businessDescTextView = view.findViewById(R.id.seller_homepage_description_text);

        addListingBtn = view.findViewById(R.id.addListingBtn);
        myAuctionsBtn = view.findViewById(R.id.myAuctionsBtn);
        ordersBtn = view.findViewById(R.id.ordersBtn);

        addListingBtn.setOnClickListener(v -> {
            AddListingFragment fragment = new AddListingFragment();

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentfragment, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        myAuctionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        ordersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BusinessHomeViewModel.class);
        mBusinessViewModel = new ViewModelProvider(requireActivity()).get(BusinessViewModel.class);

        mBusinessViewModel.getBusinessLiveData().observe(getViewLifecycleOwner(), new Observer<Business>() {
            @Override
            public void onChanged(Business business) {
                if(business == null) return;

                businessNameTextView.setText(business.getBusinessName());
                businessDescTextView.setText(business.getOwnerFullName());
            }
        });

    }

}