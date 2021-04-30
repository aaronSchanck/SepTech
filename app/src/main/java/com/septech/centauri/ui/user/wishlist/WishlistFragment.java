package com.septech.centauri.ui.user.wishlist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.septech.centauri.R;
import com.septech.centauri.domain.models.Wishlist;
import com.septech.centauri.domain.models.WishlistItem;
import com.septech.centauri.ui.user.home.UserViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class WishlistFragment extends Fragment {

    private WishlistViewModel mViewModel;
    private UserViewModel mUserViewModel;

    private RecyclerView rvWishlist;
    private WishlistAdapter adapter;

    public static WishlistFragment newInstance() {
        return new WishlistFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_wishlist_fragment, container, false);



        rvWishlist = view.findViewById(R.id.user_wishlist_rv);
        rvWishlist.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.HORIZONTAL));
        rvWishlist.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new WishlistAdapter(new ArrayList<>());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WishlistViewModel.class);
        mUserViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        mUserViewModel.getWishlistLiveData().observe(getViewLifecycleOwner(), new Observer<Wishlist>() {
            @Override
            public void onChanged(Wishlist wishlist) {
                System.out.println("wishlist = " + wishlist);
                adapter.setWishlist(wishlist.getWishlistItems());

                rvWishlist.setAdapter(adapter);
            }
        });


    }

    static class WishlistAdapter extends
            RecyclerView.Adapter<WishlistFragment.WishlistAdapter.ViewHolder> {

        private List<WishlistItem> mWishlistItems;

        public WishlistAdapter(List<WishlistItem> mWishlistItems) {
            this.mWishlistItems = mWishlistItems;
        }

        @NonNull
        @Override
        public WishlistFragment.WishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View itemView = inflater.inflate(R.layout.user_wishlist_item_fragment, parent, false);

            // Return a new holder instance
            WishlistFragment.WishlistAdapter.ViewHolder viewHolder = new WishlistFragment.WishlistAdapter.ViewHolder(itemView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull WishlistFragment.WishlistAdapter.ViewHolder holder, int position) {
            WishlistItem wishlistItem = mWishlistItems.get(position);

            holder.getItemName().setText(wishlistItem.getItem().getName());
        }

        public void setWishlist(List<WishlistItem> mWishlistItems) {
            this.mWishlistItems = mWishlistItems;
        }

        @Override
        public int getItemCount() {
            return mWishlistItems.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private TextView itemName;
            private TextView itemPrice;

            private ImageView itemImage;
            private ImageView heartImage;

            private Button addToCartBtn;

            public ViewHolder(View itemView) {
                super(itemView);

                addToCartBtn = itemView.findViewById(R.id.user_wishlist_item_addcart_btn);

                heartImage = itemView.findViewById(R.id.user_wishlist_item_heart_iv);
                itemImage = itemView.findViewById(R.id.user_wishlist_item_picture_iv);

                itemPrice = itemView.findViewById(R.id.user_wishlist_item_price_tv);
                itemName = itemView.findViewById(R.id.user_wishlist_item_name_tv);
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

            public ImageView getHeartImage() {
                return heartImage;
            }

            public Button getAddToCartBtn() {
                return addToCartBtn;
            }
        }
    }
}