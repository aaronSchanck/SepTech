package com.septech.centauri.ui.user.search;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.septech.centauri.R;
import com.septech.centauri.data.repository.ItemDataRepository;
import com.septech.centauri.domain.models.Item;
import com.septech.centauri.domain.repository.ItemRepository;

import java.util.List;
import java.util.Map;

public class ItemAdapter extends
        RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> mItems;

    private Map<Integer, Uri> images;

    public ItemAdapter(List<Item> mItems, Map<Integer, Uri> images) {
        this.mItems = mItems;
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.fragment_user_search_item_compact, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = mItems.get(position);

        ImageView imageView = holder.itemImageView;

        imageView.setImageURI(images.get(item.getId()));

        TextView textView = holder.nameTextView;
        textView.setText(item.getName());
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public ImageView itemImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            itemImageView = itemView.findViewById(R.id.itemImage);
            nameTextView = itemView.findViewById(R.id.itemName);
        }
    }
}