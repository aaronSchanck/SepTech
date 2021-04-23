package com.septech.centauri.ui.user.itemreview;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.septech.centauri.R;
import com.septech.centauri.domain.models.ItemReview;
import com.septech.centauri.ui.user.home.UserViewModel;

public class ItemReviewFragment extends Fragment {

    private ItemReviewViewModel mViewModel;
    private UserViewModel mUserViewModel;

    private TextView itemNameTv;
    private TextView businessNameTv;

    private RatingBar itemRatingBar;

    private EditText reviewContent;

    private Button cancelBtn;
    private Button submitBtn;

    public static ItemReviewFragment newInstance() {
        return new ItemReviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_item_review_fragment, container, false);

        Resources res = getActivity().getResources();

        itemNameTv = view.findViewById(R.id.item_review_item_name_textview);
        businessNameTv = view.findViewById(R.id.item_review_business_name_textview);

        itemNameTv.setText(res.getString(R.string.item_review_item_name_text, getArguments().getString("item_name")));
        businessNameTv.setText(res.getString(R.string.item_review_business_name_text,
                getArguments().getString("business_name")));

        itemRatingBar = view.findViewById(R.id.item_review_ratingbar);

        reviewContent = view.findViewById(R.id.item_review_review_edittext);

        cancelBtn = view.findViewById(R.id.item_review_cancel_btn);
        submitBtn = view.findViewById(R.id.item_review_submit_btn);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ItemReviewViewModel.class);
        mUserViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        mViewModel.setItemid(getArguments().getInt("itemid"));

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.addItemReview(mUserViewModel.getUserId(), mViewModel.getItemid(),
                        itemRatingBar.getRating(), reviewContent.getText().toString());
            }
        });

        mViewModel.getItemReviewLiveData().observe(getViewLifecycleOwner(), new Observer<ItemReview>() {
            @Override
            public void onChanged(ItemReview itemReview) {
                System.out.println("itemReview = " + itemReview);
            }
        });
    }

}