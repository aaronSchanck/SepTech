package com.septech.centauri.ui.user.home;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.septech.centauri.R;

import java.text.NumberFormat;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private FilterViewModel mViewModel;

    private TextView cartItemsTextView;
    private EditText searchEditText;

    private SearchView searchView;
    private Toolbar toolbar;

    //drawer stuff

    private DrawerLayout mDrawerLayout;
    private NavigationView navView;

    private MaterialButtonToggleGroup toggleButton;

    private RangeSlider slider;

    private SwitchMaterial lowestPriceSwitch;
    private SwitchMaterial highestPriceSwitch;
    private SwitchMaterial popularitySwitch;
    private SwitchMaterial ratingSwitch;

    private MaterialCheckBox buyNowCheck;
    private MaterialCheckBox auctionCheck;
    private MaterialCheckBox auctionEndingSoonCheck;

    private RatingBar minItemRatingBar;
    private RatingBar minSellerRatingBar;

    private Button applyFiltersBtn;
    private Button applyFiltersContinueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        mViewModel = new ViewModelProvider(this).get(FilterViewModel.class);

        createTextWatchers();

        toolbar = (MaterialToolbar) findViewById(R.id.topAppBar);

        setSupportActionBar(toolbar);

        setupDrawer();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentfragment, HomeFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        }

        mViewModel.getLeftSliderLiveData().setValue(slider.getValues().get(0));

        System.out.println("savedInstanceState = " + savedInstanceState);
    }

    private void setupDrawer() {
        mDrawerLayout = findViewById(R.id.drawer);
        navView = findViewById(R.id.navView);

        // Show the burger button on the ActionBar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        toggleButton = findViewById(R.id.toggleButton);

        toggleButton.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                System.out.println("group = " + group + ", checkedId = " + checkedId + ", isChecked = " + isChecked);
            }
        });

        slider = findViewById(R.id.rangeSlider);

        slider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
                System.out.println("slider = " + slider);
            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                System.out.println("slider = " + slider);
            }
        });

        slider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                System.out.println("slider = " + slider + ", value = " + value + ", fromUser = " + fromUser);
            }
        });

        slider.setLabelFormatter(value -> {
            String COUNTRY = "US";
            String LANGUAGE = "en";

            return NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(value);
        });

        lowestPriceSwitch = findViewById(R.id.lowestPriceSwitch);
        highestPriceSwitch = findViewById(R.id.highestPriceSwitch);
        popularitySwitch = findViewById(R.id.popularitySwitch);
        ratingSwitch = findViewById(R.id.ratingSwitch);

        lowestPriceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("buttonView = " + buttonView + ", isChecked = " + isChecked);
            }
        });
        highestPriceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("buttonView = " + buttonView + ", isChecked = " + isChecked);
            }
        });

        popularitySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("buttonView = " + buttonView + ", isChecked = " + isChecked);
            }
        });

        ratingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("buttonView = " + buttonView + ", isChecked = " + isChecked);
            }
        });

        buyNowCheck = findViewById(R.id.buyNowCheck);
        auctionCheck = findViewById(R.id.auctionCheck);
        auctionEndingSoonCheck = findViewById(R.id.auctionEndingSoonCheck);

        buyNowCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("buttonView = " + buttonView + ", isChecked = " + isChecked);
            }
        });
        auctionCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("buttonView = " + buttonView + ", isChecked = " + isChecked);
            }
        });
        auctionEndingSoonCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("buttonView = " + buttonView + ", isChecked = " + isChecked);
            }
        });

        minItemRatingBar = findViewById(R.id.averageItemRatingBar);
        minSellerRatingBar = findViewById(R.id.averageSellerRatingBar);

        minItemRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                System.out.println("ratingBar = " + ratingBar + ", rating = " + rating + ", fromUser = " + fromUser);
            }
        });

        minSellerRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                System.out.println("ratingBar = " + ratingBar + ", rating = " + rating + ", fromUser = " + fromUser);
            }
        });

        applyFiltersBtn = findViewById(R.id.applyFiltersBtn);
        applyFiltersContinueBtn = findViewById(R.id.applyFiltersContinueBtn);

        applyFiltersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("v = " + v);
                mDrawerLayout.closeDrawer(navView);
            }
        });

        applyFiltersContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("v = " + v);
                mDrawerLayout.closeDrawer(navView);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast like print
                Log.i("TAG", "SearchOnQueryTextSubmit: " + query);
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.i("TAG", "SearchOnQueryTextChanged: " + s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }


    private void createTextWatchers() {
    }

    public void onNavigationButtonClick(View view) {
        mDrawerLayout.closeDrawer(navView);

        System.out.println("view = " + view);
        switch (view.getId()) {
            case R.id.button1:
                // Do something with button 1
                break;

            case R.id.button2:
                // Do something with button 2
                break;

            case R.id.button3:
                // Do something with button 3
                break;

        }
    }
}
