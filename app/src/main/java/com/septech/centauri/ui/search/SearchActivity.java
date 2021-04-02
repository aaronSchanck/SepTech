package com.septech.centauri.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.septech.centauri.R;
import com.septech.centauri.domain.models.Category;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ArrayList<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Lookup the recyclerview in activity layout
        RecyclerView rvCategory = (RecyclerView) findViewById(R.id.rvCategories);

        // Initialize contacts
        categories = Category.createCategories(100);
        // Create adapter passing in the sample user data
        CategoryAdapter adapter = new CategoryAdapter(categories);
        // Attach the adapter to the recyclerview to populate items
        rvCategory.setAdapter(adapter);
        // Set layout manager to position the items
        rvCategory.setLayoutManager(new GridLayoutManager(this, 2));
        // That's all!
    }
}