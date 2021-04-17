package com.septech.centauri.ui.user.cart;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.septech.centauri.R;
import com.septech.centauri.domain.models.Item;
import com.septech.centauri.domain.models.OrderItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.septech.centauri.persistent.CentauriApp.getAppContext;

class CartItemAdapter extends
        RecyclerView.Adapter<CartItemAdapter.ViewHolder> {

    private List<OrderItem> mOrderItems;

    private Map<Integer, Uri> images;

    private OnCartItemListener onItemListener;

    public CartItemAdapter(OnCartItemListener onItemListener, List<OrderItem> mOrderItems,
                           Map<Integer, Uri> images) {
        this.onItemListener = onItemListener;
        this.mOrderItems = mOrderItems;
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View itemView = inflater.inflate(R.layout.user_cart_item_fragment, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(itemView, onItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Resources res = getAppContext().getResources();

        OrderItem orderItem = mOrderItems.get(position);

        Uri imageUri = images.get(orderItem.getItemid());

        holder.getItemImageView().setImageURI(imageUri);

        holder.getCartItemName().setText(res.getString(R.string.cart_item_name, orderItem.getItem().getName()));
        holder.getCartItemQuantity().setText(res.getString(R.string.cart_quantity,
                orderItem.getQuantity()));
        BigDecimal priceEach = orderItem.getItem().getBigDecDollarPrice();
        BigDecimal quantityBD = new BigDecimal(String.valueOf(orderItem.getQuantity()));

        BigDecimal totalOrderItemPrice = priceEach.multiply(quantityBD);

        String printString = "$" + totalOrderItemPrice;
        holder.getCartItemPrice().setText(printString);
    }

    public void setCart(List<OrderItem> orderItems) {
        this.mOrderItems = orderItems;
    }

    public void setImages(Map<Integer, Uri> images) {
        this.images = images;
    }

    @Override
    public int getItemCount() {
        return mOrderItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private OnCartItemListener onItemListener;

        private ImageView itemImageView;

        private TextView cartItemName;
        private TextView cartItemQuantity;
        private TextView cartItemPrice;

        public ViewHolder(View itemView, OnCartItemListener onItemListener) {
            super(itemView);

            this.onItemListener = onItemListener;

            this.itemImageView = itemView.findViewById(R.id.cartItemImage);

            this.cartItemName = itemView.findViewById(R.id.cartItemName);
            this.cartItemQuantity = itemView.findViewById(R.id.cartItemQuantity);
            this.cartItemPrice = itemView.findViewById(R.id.cartItemPrice);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public OnCartItemListener getOnItemListener() {
            return onItemListener;
        }

        public void setOnItemListener(OnCartItemListener onItemListener) {
            this.onItemListener = onItemListener;
        }

        public ImageView getItemImageView() {
            return itemImageView;
        }

        public void setItemImageView(ImageView itemImageView) {
            this.itemImageView = itemImageView;
        }

        public TextView getCartItemName() {
            return cartItemName;
        }

        public void setCartItemName(TextView cartItemName) {
            this.cartItemName = cartItemName;
        }

        public TextView getCartItemQuantity() {
            return cartItemQuantity;
        }

        public void setCartItemQuantity(TextView cartItemQuantity) {
            this.cartItemQuantity = cartItemQuantity;
        }

        public TextView getCartItemPrice() {
            return cartItemPrice;
        }

        public void setCartItemPrice(TextView cartItemPrice) {
            this.cartItemPrice = cartItemPrice;
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getBindingAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onItemListener.onItemLongClick(getBindingAdapterPosition());
            return true;
        }
    }

    public interface OnCartItemListener {
        void onItemClick(int position);
        void onItemLongClick(int position);
    }
}