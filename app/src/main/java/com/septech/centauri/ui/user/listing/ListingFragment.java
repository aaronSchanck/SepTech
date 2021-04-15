package com.septech.centauri.ui.user.listing;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.septech.centauri.R;
import com.septech.centauri.domain.models.Item;
import com.septech.centauri.domain.models.ItemReview;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListingFragment extends Fragment {

    private ListingViewModel mViewModel;

    private RecyclerView listingRV;
    private ReviewAdapter adapter;

    private ImageButton backBtn;
    private ImageButton imageBackBtn;
    private ImageButton imageForwardBtn;

    private TextView listingNameTextView;
    private TextView listingPriceTextView;
    private TextView listingDescTextView;
    private TextView listingRatingScore;

    private RatingBar listingRatingBar;

    private Spinner listingSpinner;

    public static ListingFragment newInstance() {
        return new ListingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_listing_fragment, container, false);

        backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("v = " + v);
                getActivity().onBackPressed();
            }
        });

        imageBackBtn = view.findViewById(R.id.imageBackBtn);
        imageBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("v = " + v);
            }
        });

        imageForwardBtn = view.findViewById(R.id.imageForwardBtn);
        imageForwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("v = " + v);
            }
        });

        listingNameTextView = view.findViewById(R.id.listingNameTextView);
        listingPriceTextView = view.findViewById(R.id.listingPriceTextView);
        listingDescTextView = view.findViewById(R.id.listingDescTextView);
        listingRatingScore = view.findViewById(R.id.listingRatingScore);

        listingSpinner = view.findViewById(R.id.listingSpinner);
        listingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("parent = " + parent + ", view = " + view + ", position = " + position + ", id = " + id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("parent = " + parent);
            }
        });

        List<String> spinnerChoices = new ArrayList<>();
        spinnerChoices.add("Most Recent");
        spinnerChoices.add("Least Recent");
        spinnerChoices.add("Highest Rated");
        spinnerChoices.add("Lowest Rated");
        spinnerChoices.add("Most Helpful");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_list_item_1);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        listingSpinner.setAdapter(dataAdapter);

        listingRatingBar = view.findViewById(R.id.listingRatingBar);

        listingRV = view.findViewById(R.id.listingRV);

        adapter = new ReviewAdapter(new ArrayList<>());
        listingRV.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ListingViewModel.class);

        mViewModel.getItem(getArguments().getInt("id"));

        createLiveDataObservers();
    }

    private void createLiveDataObservers() {
        mViewModel.getItemLiveData().observe(getViewLifecycleOwner(), new Observer<Item>() {
            @Override
            public void onChanged(Item item) {
                mViewModel.getImages(item);

                listingNameTextView.setText(item.getName());

                String COUNTRY = "US";
                String LANGUAGE = "en";

                listingPriceTextView.setText(NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(item.getBuyoutPrice()));
                listingDescTextView.setText(item.getDescription());

                //set rating bar and score

                //add reviews adapter
            }
        });

        mViewModel.getReviews().observe(getViewLifecycleOwner(),
                itemReviews -> System.out.println("itemReviews = " + itemReviews));
    }

    static class ReviewAdapter extends
            RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

        private List<ItemReview> mReviews;

        public ReviewAdapter(List<ItemReview> mReviews) {
            this.mReviews = mReviews;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View itemView = inflater.inflate(R.layout.user_cart_item_fragment, parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(itemView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ItemReview review = mReviews.get(position);
        }

        public void setReviews(List<ItemReview> mReviews) {
            this.mReviews = mReviews;
        }

        @Override
        public int getItemCount() {
            return mReviews.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}