package com.septech.centauri.ui.user.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.septech.centauri.R;
import com.septech.centauri.domain.chat.models.Message;
import com.septech.centauri.domain.models.Order;
import com.septech.centauri.domain.models.User;
import com.septech.centauri.ui.chat.ChatLoginActivity;
import com.septech.centauri.ui.chat.MessagesActivity;
import com.septech.centauri.ui.interfaces.CallBackListener;
import com.septech.centauri.ui.landing.LandingActivity;
import com.septech.centauri.ui.user.cart.CartFragment;
import com.septech.centauri.ui.user.login.LoginActivity;
import com.septech.centauri.ui.user.search.SearchFragment;
import com.septech.centauri.ui.user.settings.SettingsFragment;
import com.septech.centauri.ui.user.wishlist.WishlistFragment;

import java.text.NumberFormat;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements CallBackListener {

    //viewmodels

    private UserViewModel mViewModel;
    private FilterViewModel mFilterViewModel; //used to store filter settings when searching

    //fragments

    private HomeFragment homeFragment;
    private SettingsFragment settingsFragment;

    //home fragment
    private String name;

    //top toolbar stuff

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

    //bottom navigation

    private BottomNavigationView bottomNavigation;

    //bottom badges

    private BadgeDrawable profileBadge;
    private BadgeDrawable notificationBadge;
    private BadgeDrawable cartBadge;
    private BadgeDrawable chatBadge;

    //loading icon

    private ProgressBar loadingIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        mViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mFilterViewModel = new ViewModelProvider(this).get(FilterViewModel.class);

        mViewModel.setUserId(getIntent().getIntExtra("id", 0));
        mViewModel.getUserLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                System.out.println("user = " + user);
            }
        });
        name = getIntent().getStringExtra("name");

        mViewModel.getOrderLiveData().observe(this, new Observer<Order>() {
            @Override
            public void onChanged(Order order) {
                //update cart icon
                if (bottomNavigation == null) {
                    bottomNavigation = findViewById(R.id.bottomMenu);
                }
                if (cartBadge == null) {
                    cartBadge = bottomNavigation.getOrCreateBadge(R.id.bottom_cart);
                }

                System.out.println("new order = " + order);
                System.out.println(order.getOrderItems().size());

                cartBadge.setNumber(order.getOrderItems().size());
            }
        });

        //create fragments
        homeFragment = HomeFragment.newInstance();
        settingsFragment = SettingsFragment.newInstance();

        createTextWatchers();

        toolbar = (MaterialToolbar) findViewById(R.id.topAppBar);

        setSupportActionBar(toolbar);

        setupDrawer();

        setupBottomNavigation();

        loadingIcon = findViewById(R.id.homeLoadingIcon);
        loadingIcon.setVisibility(View.GONE);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentfragment, homeFragment)
                    .commit();
        }

        mFilterViewModel.getLeftSliderLiveData().setValue(slider.getValues().get(0));

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

    private void setupBottomNavigation() {
        bottomNavigation = findViewById(R.id.bottomMenu);

        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            hideLoadingIcon();

            int itemId = item.getItemId();
            if (itemId == R.id.bottom_person) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentfragment, homeFragment)
                        .addToBackStack(null)
                        .commit();
                System.out.println("item = " + item);
                return true;
            } else if (itemId == R.id.bottom_notifications) {
                System.out.println();
                return true;
            } else if (itemId == R.id.bottom_favorites) {
                WishlistFragment fragment = WishlistFragment.newInstance();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentfragment, fragment)
                        .addToBackStack(null)
                        .commit();
                return true;
            } else if (itemId == R.id.bottom_cart) {
                CartFragment cartFragment = CartFragment.newInstance();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentfragment, cartFragment)
                        .addToBackStack(null)
                        .commit();
                return true;
            } else if (itemId == R.id.bottom_messages) {
                System.out.println("item =  " + item);
                Intent il = new Intent(this, MessagesActivity.class);
                startActivity(il);
                return true;
            }
            return false;
        });

        profileBadge = bottomNavigation.getOrCreateBadge(R.id.bottom_person);
        notificationBadge = bottomNavigation.getOrCreateBadge(R.id.bottom_notifications);
        cartBadge = bottomNavigation.getOrCreateBadge(R.id.bottom_cart);
        chatBadge = bottomNavigation.getOrCreateBadge(R.id.bottom_messages);

        cartBadge.setBackgroundColor(ContextCompat.getColor(this, R.color.lighter_black));
        cartBadge.setHorizontalOffset(20);
        cartBadge.setVerticalOffset(20);
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

                SearchFragment fragment = SearchFragment.newInstance();

                Bundle bundle = new Bundle();
                bundle.putString("query", query);

                fragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentfragment, fragment)
                        .addToBackStack("search_latest")
                        .commit();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        hideLoadingIcon();

        int itemId = item.getItemId();

        if (itemId == R.id.settings) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentfragment, settingsFragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        } else if (itemId == R.id.switchAccounts) {
            return true;
        } else if (itemId == R.id.logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Logout this session or all sessions?")
                    .setPositiveButton("All Sessions", (dialog, which) -> {
                        Intent intent = new Intent(this, LandingActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    })
                    .setNeutralButton("Cancel", null)
                    .setNegativeButton("This Session", (dialog, which) -> {
                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    })
                    .setIcon(R.drawable.ic_baseline_warning_24)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        hideLoadingIcon();

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

    @Override
    public void showLoadingIcon() {
        loadingIcon.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIcon() {
        loadingIcon.setVisibility(View.GONE);
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager imm =
                (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void initFragment() {
        hideLoadingIcon();
    }
}
