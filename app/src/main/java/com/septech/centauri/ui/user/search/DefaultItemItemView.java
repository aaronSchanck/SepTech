package com.septech.centauri.ui.user.search;

import android.content.Context;
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

import java.util.List;
import java.util.Map;

class DefaultItemItemView extends
        RecyclerView.Adapter<DefaultItemItemView.ViewHolder> implements ItemViewConversion {

    private List<Item> mItems;

    private Map<Integer, Uri> images;

    private OnSearchItemListener onSearchItemListener;

    public DefaultItemItemView(OnSearchItemListener onSearchItemListener, List<Item> mItems,
                               Map<Integer, Uri> images) {
        this.onSearchItemListener = onSearchItemListener;
        this.mItems = mItems;
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View itemView = inflater.inflate(R.layout.user_search_item_fragment_default, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(itemView, onSearchItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = mItems.get(position);


    }

    public void setItems(List<Item> mItems) {
        this.mItems = mItems;
    }

    public void setImages(Map<Integer, Uri> images) {
        this.images = images;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int get(int position) {
        return mItems.get(position).getId();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView nameTextView;
        public ImageView itemImageView;
        public TextView priceTextView;

        public ImageView checkMarkImageView;

        OnSearchItemListener onSearchItemListener;

        public ViewHolder(View itemView, OnSearchItemListener onSearchItemListener) {
            super(itemView);

            this.onSearchItemListener = onSearchItemListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onSearchItemListener.onItemClick(getBindingAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onSearchItemListener.onItemLongClick(getBindingAdapterPosition());
            return true;
        }
    }
}