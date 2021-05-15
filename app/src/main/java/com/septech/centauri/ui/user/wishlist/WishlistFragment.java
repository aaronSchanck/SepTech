package com.septech.centauri.ui.user.wishlist;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.septech.centauri.R;
import com.septech.centauri.domain.models.WishlistItem;
import com.septech.centauri.ui.interfaces.CallBackListener;
import com.septech.centauri.ui.user.home.UserViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.septech.centauri.persistent.CentauriApp.getAppContext;

public class WishlistFragment extends Fragment implements WishlistBtnListener {
    private static final String TAG = "WishlistFragment";

    private WishlistViewModel mViewModel;
    private UserViewModel mUserViewModel;

    private RecyclerView mWishlistRv;
    private WishlistAdapter mWishlistAdapter;

    private CallBackListener mCallBackListener;

    public static WishlistFragment newInstance() {
        return new WishlistFragment();
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
        View view = inflater.inflate(R.layout.user_wishlist_fragment, container, false);

        mWishlistRv = view.findViewById(R.id.user_wishlist_rv);
        mWishlistRv.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.HORIZONTAL));
        mWishlistRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        mWishlistAdapter = new WishlistAdapter(this, new ArrayList<>());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WishlistViewModel.class);
        mUserViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        mUserViewModel.getWishlistLiveData().observe(getViewLifecycleOwner(), wishlist -> {
            System.out.println("wishlist = " + wishlist);
            mViewModel.setWishlistLiveData(wishlist);

            mWishlistAdapter.setWishlist(wishlist.getWishlistItems());

            mWishlistRv.setAdapter(mWishlistAdapter);

            if (wishlist.getWishlistItems().size() > 0) {
                mViewModel.getImagesLiveData().observe(getViewLifecycleOwner(), images -> {
                    if (images == null) {
                        return;
                    }

                    mCallBackListener.hideLoadingIcon();

                    mWishlistAdapter.setImages(images);
                    mWishlistRv.setAdapter(mWishlistAdapter);

                    mWishlistRv.setVisibility(View.VISIBLE);
                });
            } else {
                mCallBackListener.hideLoadingIcon();

                mWishlistRv.setAdapter(mWishlistAdapter);
                mWishlistRv.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void heartBtnOnClick(View v, int position) {
        WishlistItem wishlistItem = mWishlistAdapter.getWishlistItems().get(position);
        Log.d(TAG, "heartBtnOnClick: " + position);
    }

    @Override
    public void addCartBtnOnClick(View v, int position) {
        WishlistItem wishlistItem = mWishlistAdapter.getWishlistItems().get(position);
        Log.d(TAG, "addCartBtnOnClick: " + position);
    }

    static class WishlistAdapter extends
            RecyclerView.Adapter<WishlistFragment.WishlistAdapter.ViewHolder> {

        private final WishlistBtnListener mWishlistListener;

        private List<WishlistItem> mWishlistItems;

        private Map<Integer, Uri> mImages;

        public WishlistAdapter(WishlistBtnListener wishlistListener, List<WishlistItem> mWishlistItems) {
            this.mWishlistItems = mWishlistItems;
            this.mWishlistListener = wishlistListener;
        }

        @NonNull
        @Override
        public WishlistFragment.WishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View itemView = inflater.inflate(R.layout.user_wishlist_item_fragment, parent, false);

            // Return a new holder instance
            WishlistFragment.WishlistAdapter.ViewHolder viewHolder =
                    new WishlistFragment.WishlistAdapter.ViewHolder(itemView, mWishlistListener);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull WishlistFragment.WishlistAdapter.ViewHolder holder, int position) {
            WishlistItem wishlistItem = mWishlistItems.get(position);
            Uri imageUri = mImages.get(wishlistItem.getItemid());

            Resources res = getAppContext().getResources();

            holder.getItemName().setText(wishlistItem.getItem().getName());

            holder.getItemImage().setImageURI(imageUri);
        }

        public List<WishlistItem> getWishlistItems() {
            return mWishlistItems;
        }

        public void setWishlist(List<WishlistItem> mWishlistItems) {
            this.mWishlistItems = mWishlistItems;
        }

        public void setImages(Map<Integer, Uri> images) {
            this.mImages = images;
        }

        @Override
        public int getItemCount() {
            return mWishlistItems.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final WishlistBtnListener mWishlistListener;

            private TextView itemName;
            private TextView itemPrice;
            private ImageView itemImage;
            private ImageButton heartImage;
            private Button addToCartBtn;

            public ViewHolder(View itemView, WishlistBtnListener wishlistListener) {
                super(itemView);

                addToCartBtn = itemView.findViewById(R.id.listing_seller_profile_btn);

                heartImage = itemView.findViewById(R.id.user_wishlist_item_heart_iv);
                itemImage = itemView.findViewById(R.id.user_wishlist_item_picture_iv);

                itemPrice = itemView.findViewById(R.id.user_wishlist_item_price_tv);
                itemName = itemView.findViewById(R.id.user_wishlist_item_name_tv);

                mWishlistListener = wishlistListener;

                addToCartBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mWishlistListener.addCartBtnOnClick(itemView, getBindingAdapterPosition());
                    }
                });

                heartImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mWishlistListener.heartBtnOnClick(itemView, getBindingAdapterPosition());
                    }
                });
            }

            public TextView getItemName() {
                return itemName;
            }

            public TextView getItemPrice() {
                return itemPrice;
            }

            public ImageView getItemImage() {
                return itemImage;
            }

            public ImageButton getHeartImage() {
                return heartImage;
            }

            public Button getAddToCartBtn() {
                return addToCartBtn;
            }
        }
    }
}