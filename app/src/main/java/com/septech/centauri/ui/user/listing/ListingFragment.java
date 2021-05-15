package com.septech.centauri.ui.user.listing;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.septech.centauri.R;
import com.septech.centauri.domain.models.ItemReview;
import com.septech.centauri.domain.models.WishlistItem;
import com.septech.centauri.ui.interfaces.CallBackListener;
import com.septech.centauri.ui.user.home.UserViewModel;
import com.septech.centauri.ui.user.itemreview.ItemReviewFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListingFragment extends Fragment {
    private ListingViewModel mViewModel;
    private UserViewModel mUserViewModel;

    private CallBackListener callBackListener;

    private RecyclerView mReviewRv;
    private ReviewAdapter mReviewAdapter;

    private ConstraintLayout mMainLayout;

    private ImageView itemImage;

    private ExtendedFloatingActionButton wishlistBtn;
    private ExtendedFloatingActionButton cartBtn;

    private ImageButton backBtn;
    private ImageButton imageBackBtn;
    private ImageButton imageForwardBtn;
    private ImageButton quantityBackBtn;
    private ImageButton quantityForwardBtn;

    private Button mBusinessProfileBtn;
    private Button mLeaveReviewBtn;

    private TextView mListingNameTv;
    private TextView mListingPriceTv;
    private TextView mListingDescTv;
    private TextView mListingRatingScore;
    private TextView mQuantityLeftTv;
    private TextView mReviewsFoundTv;

    private EditText mQuantityEditText;

    private RatingBar mListingRatingBar;

    private Spinner mListingSpinner;

    public static ListingFragment newInstance() {
        return new ListingFragment();
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
        View view = inflater.inflate(R.layout.user_listing_fragment, container, false);

        mMainLayout = view.findViewById(R.id.listing_layout);
        mMainLayout.setVisibility(View.GONE);
        callBackListener.showLoadingIcon();

        itemImage = view.findViewById(R.id.listing_item_image_iv);

        wishlistBtn = view.findViewById(R.id.listing_wishlist_btn);
        cartBtn = view.findViewById(R.id.listing_cart_btn);
        mLeaveReviewBtn = view.findViewById(R.id.listing_leave_review_btn);

        backBtn = view.findViewById(R.id.backBtn);

        imageBackBtn = view.findViewById(R.id.listing_image_back_btn);
        imageForwardBtn = view.findViewById(R.id.listing_image_forward_btn);

        quantityBackBtn = view.findViewById(R.id.listing_quantity_subtract_btn);
        quantityForwardBtn = view.findViewById(R.id.listing_quantity_add_btn);

        mBusinessProfileBtn = view.findViewById(R.id.listing_seller_profile_btn);

        mListingNameTv = view.findViewById(R.id.listing_item_name_tv);
        mListingPriceTv = view.findViewById(R.id.listing_item_price_tv);
        mListingDescTv = view.findViewById(R.id.listing_item_desc_tv);
        mListingRatingScore = view.findViewById(R.id.listingRatingScore);
        mQuantityLeftTv = view.findViewById(R.id.listing_quantity_remaining_tv);
        mReviewsFoundTv = view.findViewById(R.id.listing_reviews_found_tv);

        mQuantityEditText = view.findViewById(R.id.listing_quantity_edittext);

        //find spinner and set item listener
        mListingSpinner = view.findViewById(R.id.listingSpinner);

        mListingRatingBar = view.findViewById(R.id.listing_item_rating_bar);

        mReviewRv = view.findViewById(R.id.listing_review_rv);
        mReviewRv.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.HORIZONTAL));
        mReviewRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        mReviewAdapter = new ReviewAdapter(new ArrayList<>());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ListingViewModel.class);
        mUserViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        try {
            mViewModel.setItemId(getArguments().getInt("id"));
        } catch (NullPointerException e) {
            Log.e("Arguments", "Fragment needs bundle arguments");
        }

        mViewModel.setUserId(mUserViewModel.getUserId());

        createLiveDataObservers(savedInstanceState);
    }

    private void createLiveDataObservers(Bundle savedInstanceState) {
        mViewModel.getItemLiveData().observe(getViewLifecycleOwner(), item -> {
            if (item == null) return;

            Resources res = requireActivity().getResources();

            //add item specific button listeners

            wishlistBtn.setOnClickListener(v -> mViewModel.addToWishlist(mUserViewModel.getUserLiveData().getValue(), item));

            cartBtn.setOnClickListener(v -> {
                int quantity = mViewModel.getCurrentQuantity();

                if (quantity == 0) {
                    //TODO show toast message
                    return;
                }

                mViewModel.addToCart(mUserViewModel.getUserLiveData().getValue(), item, quantity);
            });

            if (savedInstanceState == null) {
                mViewModel.setCurrentQuantity(0);
                mQuantityEditText.setText(String.valueOf(mViewModel.getCurrentQuantity()));
            }

            updateQuantityBtnState(item.getQuantity());

            quantityBackBtn.setOnClickListener(v -> {
                mViewModel.setCurrentQuantity(mViewModel.getCurrentQuantity() - 1);
                mQuantityEditText.setText(String.valueOf(mViewModel.getCurrentQuantity()));
                updateQuantityBtnState(item.getQuantity());
            });

            quantityForwardBtn.setOnClickListener(v -> {
                mViewModel.setCurrentQuantity(mViewModel.getCurrentQuantity() + 1);
                mQuantityEditText.setText(String.valueOf(mViewModel.getCurrentQuantity()));
                updateQuantityBtnState(item.getQuantity());
            });

            mListingNameTv.setText(item.getName());

            mListingPriceTv.setText(res.getString(R.string.listing_price,
                    item.getDisplayablePrice()));
            mListingDescTv.setText(item.getDescription());
            mQuantityLeftTv.setText(res.getString((R.string.listingQuantityLeft),
                    item.getQuantity()));

            mLeaveReviewBtn.setOnClickListener(v -> {
                ItemReviewFragment fragment = ItemReviewFragment.newInstance();

                Bundle bundle = new Bundle();
                bundle.putInt("itemid", item.getId());
                bundle.putString("item_name", item.getName());
                bundle.putString("business_name", mViewModel.getBusinessLiveData().getValue().getBusinessName());
                fragment.setArguments(bundle);

                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentfragment, fragment)
                        .addToBackStack(null)
                        .commit();
            });

            //set rating bar and score

            float rating = item.getAverageRating();

            mListingRatingBar.setRating(rating);

            DecimalFormat df = new DecimalFormat("0.0");
            df.setMinimumFractionDigits(1);
            df.setMaximumFractionDigits(1);

            mListingRatingScore.setText(res.getString(R.string.listing_rating_score_text,
                    df.format(rating)));

            String reviewsFoundPlural = item.getReviews().size() == 1 ? "review" : "reviews";
            mReviewsFoundTv.setText(res.getString(R.string.listing_reviews_found_text,
                    item.getReviews().size(), reviewsFoundPlural));

            //add reviews adapter
            mReviewAdapter.setReviews(item.getReviews());
            mReviewRv.setAdapter(mReviewAdapter);

            mMainLayout.setVisibility(View.VISIBLE);

            callBackListener.hideLoadingIcon();
        });

        mViewModel.getBusinessLiveData().observe(getViewLifecycleOwner(), business -> {
            mBusinessProfileBtn.setText(requireActivity().getResources().getString((R.string.business_profile_text),
                    business.getBusinessName()));

            mBusinessProfileBtn.setOnClickListener(v -> {
                System.out.println("v = " + v);
            });
        });

        mViewModel.getImageLiveData().observe(getViewLifecycleOwner(), uris -> {
            mViewModel.setCurrentImage(0);
            itemImage.setImageURI(uris.get(mViewModel.getCurrentImage()));

            updateImageBtnState(uris);

            imageBackBtn.setOnClickListener(v -> {
                mViewModel.setCurrentImage(mViewModel.getCurrentImage() - 1);
                itemImage.setImageURI(uris.get(mViewModel.getCurrentImage()));

                updateImageBtnState(uris);
            });


            imageForwardBtn.setOnClickListener(v -> {
                mViewModel.setCurrentImage(mViewModel.getCurrentImage() + 1);
                itemImage.setImageURI(uris.get(mViewModel.getCurrentImage()));

                updateImageBtnState(uris);
            });
        });

        mViewModel.getReviews().observe(getViewLifecycleOwner(),
                itemReviews -> System.out.println("itemReviews = " + itemReviews));

        mViewModel.getOrderLiveData().observe(getViewLifecycleOwner(), order -> {
            if (order == null) return;

            mUserViewModel.updateOrderData(order);
        });

        mViewModel.getWishlistLiveData().observe(getViewLifecycleOwner(), wishlist -> {
            System.out.println("wishlist = " + wishlist);
            System.out.println("wishlist = " + wishlist.getWishlistItems());

            for (WishlistItem item :
                    wishlist.getWishlistItems()) {
                if (item.getItemid() == mViewModel.getItemId()) {
                    wishlistBtn.setText(R.string.listing_wishlist_added_text);
                    wishlistBtn.setIcon(ContextCompat.getDrawable(requireActivity(),
                            R.drawable.ic_baseline_check_24));
                }

            }
        });

        backBtn.setOnClickListener(v -> {
            System.out.println("v = " + v);
            requireActivity().onBackPressed();
        });

        mListingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("parent = " + parent + ", view = " + view + ", position = " + position + ", id = " + id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("parent = " + parent);
            }
        });

        //create spinner choices
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                requireActivity(),
                android.R.layout.simple_list_item_1,
                Arrays.asList("Most Recent", "Least Recent", "Highest Rated", "Lowest Rated",
                        "Most Helpful"));

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mListingSpinner.setAdapter(dataAdapter);
    }

    private void updateImageBtnState(List<Uri> uris) {
        if (mViewModel.getCurrentImage() == uris.size() - 1) {
            imageForwardBtn.setVisibility(View.GONE);
            imageForwardBtn.setActivated(false);
        } else {
            imageForwardBtn.setVisibility(View.VISIBLE);
            imageForwardBtn.setActivated(true);
        }

        if (mViewModel.getCurrentImage() == 0) {
            imageBackBtn.setVisibility(View.GONE);
            imageBackBtn.setActivated(false);
        } else {
            imageBackBtn.setVisibility(View.VISIBLE);
            imageBackBtn.setActivated(true);
        }
    }

    private void updateQuantityBtnState(int quantity) {
        if (mViewModel.getCurrentQuantity() == quantity) {
            quantityForwardBtn.setVisibility(View.GONE);
            quantityForwardBtn.setActivated(false);
        } else {
            quantityForwardBtn.setVisibility(View.VISIBLE);
            quantityForwardBtn.setActivated(true);
        }

        if (mViewModel.getCurrentQuantity() == 0) {
            quantityBackBtn.setVisibility(View.GONE);
            quantityBackBtn.setActivated(false);
        } else {
            quantityBackBtn.setVisibility(View.VISIBLE);
            quantityBackBtn.setActivated(true);
        }
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
            View itemView = inflater.inflate(R.layout.user_item_review_item_fragment, parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(itemView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ItemReview review = mReviews.get(position);

            holder.getReviewerName().setText(review.getUser().getFullName());

            String modifiedAt = review.getModifiedAt();

            modifiedAt = modifiedAt.substring(0, modifiedAt.indexOf("T"));

            holder.getPostDate().setText(modifiedAt);

            String content = review.getContent();

            if (content.equals("")) content = "No review content";

            holder.getReviewContent().setText(content);

            holder.getRatingBar().setRating(review.getRating());
        }

        public void setReviews(List<ItemReview> mReviews) {
            this.mReviews = mReviews;
        }

        @Override
        public int getItemCount() {
            return mReviews.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            private TextView reviewerName;
            private TextView postDate;
            private TextView reviewContent;

            private RatingBar ratingBar;

            public ViewHolder(View itemView) {
                super(itemView);

                reviewerName = itemView.findViewById(R.id.item_review_item_reviewer_name);
                postDate = itemView.findViewById(R.id.item_review_item_posted_date);
                reviewContent = itemView.findViewById(R.id.item_review_item_review_content);

                ratingBar = itemView.findViewById(R.id.item_review_item_ratingbar);
            }

            public TextView getReviewerName() {
                return reviewerName;
            }

            public TextView getPostDate() {
                return postDate;
            }

            public TextView getReviewContent() {
                return reviewContent;
            }

            public RatingBar getRatingBar() {
                return ratingBar;
            }
        }
    }
}