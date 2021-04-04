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

        RecyclerView rvCategory = findViewById(R.id.rvCategories);

        categories = Category.createCategories(100);
        CategoryAdapter adapter = new CategoryAdapter(categories);
        rvCategory.setAdapter(adapter);
        rvCategory.setLayoutManager(new GridLayoutManager(this, 2));
    }
}