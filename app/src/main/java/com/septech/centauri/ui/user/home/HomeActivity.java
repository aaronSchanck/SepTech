package com.septech.centauri.ui.user.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
import com.septech.centauri.domain.models.User;
import com.septech.centauri.ui.business.login.BusinessLoginActivity;
import com.septech.centauri.ui.chat.MessagesActivity;
import com.septech.centauri.ui.interfaces.CallBackListener;
import com.septech.centauri.ui.user.cart.CartFragment;
import com.septech.centauri.ui.user.login.LoginActivity;
import com.septech.centauri.ui.user.search.SearchCallback;
import com.septech.centauri.ui.user.search.SearchFragment;
import com.septech.centauri.ui.user.search.SearchSortType;
import com.septech.centauri.ui.user.search.SearchViewType;
import com.septech.centauri.ui.user.settings.SettingsFragment;
import com.septech.centauri.ui.user.wishlist.WishlistFragment;

import java.text.NumberFormat;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements CallBackListener, SearchCallback {
    // @formatter:off
    
    private static final String TAG = "HomeActivity";

    //periodic updates
    private final int UPDATE_TIME = 5000;
    private Handler mHandler;

    //viewmodels
    private UserViewModel mViewModel;
    private FilterViewModel mFilterViewModel; //used to store filter settings when searching

    //fragments
    private HomeFragment mHomeFragment;
    private SettingsFragment mSettingsFragment;

    //top toolbar stuff
    private SearchView mSearchView;
    private Toolbar mToolbar;

    //drawer stuff
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavView;

    private MaterialButtonToggleGroup mToggleGroup;

    private RangeSlider mPriceSlider;

    private SwitchMaterial mLowestPriceSwitch;
    private SwitchMaterial mHighestPriceSwitch;
    private SwitchMaterial mPopularitySwitch;
    private SwitchMaterial mRatingSwitch;

    private MaterialCheckBox mBuyNowCheck;
    private MaterialCheckBox mAuctionCheck;
    private MaterialCheckBox mAuctionEndingSoonCheck;

    private RatingBar mMinItemRatingBar;
    private RatingBar mMinSellerRatingBar;

    private Button applyFiltersBtn;
    private Button applyFiltersContinueBtn;

    //bottom navigation
    private BottomNavigationView mBottomNavigationView;

    //bottom badges
    private BadgeDrawable profileBadge;
    private BadgeDrawable notificationBadge;
    private BadgeDrawable wishlistBadge;
    private BadgeDrawable cartBadge;
    private BadgeDrawable chatBadge;

    //loading icon
    private ProgressBar mLoadingIcon;

    private String name;

    // @formatter:on

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
                Log.d(TAG, "user = " + user);
            }
        });
        name = getIntent().getStringExtra("name");

        mViewModel.getOrderLiveData().observe(this, order -> {
            //update cart icon
            if (mBottomNavigationView == null) {
                mBottomNavigationView = findViewById(R.id.bottomMenu);
            }
            if (cartBadge == null) {
                cartBadge = mBottomNavigationView.getOrCreateBadge(R.id.bottom_cart);
            }

            Log.d(TAG, "new order = " + order);
            Log.d(TAG, String.valueOf(order.getOrderItems().size()));

            cartBadge.setNumber(order.getOrderItems().size());
        });

        mViewModel.getWishlistLiveData().observe(this, wishlist -> {
            if (mBottomNavigationView == null) {
                mBottomNavigationView = findViewById(R.id.bottomMenu);
            }
            if (wishlistBadge == null) {
                wishlistBadge = mBottomNavigationView.getOrCreateBadge(R.id.bottom_favorites);
            }

            Log.d(TAG, "new wishlist = " + wishlist);
            Log.d(TAG, String.valueOf(wishlist.getWishlistItems().size()));

            wishlistBadge.setNumber(wishlist.getWishlistItems().size());
        });

        //create fragments
        mHomeFragment = HomeFragment.newInstance();
        mSettingsFragment = SettingsFragment.newInstance();

        mToolbar = (MaterialToolbar) findViewById(R.id.topAppBar);

        setSupportActionBar(mToolbar);

        setupDrawer();

        setupBottomNavigation();

        mLoadingIcon = findViewById(R.id.homeLoadingIcon);
        mLoadingIcon.setVisibility(View.GONE);

        mFilterViewModel.getQueryLiveData().observe(this, query -> {
            SearchFragment fragment = SearchFragment.newInstance();

            Bundle bundle = new Bundle();
            bundle.putString("query", query);

            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentfragment, fragment)
                    .addToBackStack("search_latest")
                    .commit();
        });

        mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mViewModel.refreshUserCart();
                mViewModel.refreshUserWishlist();

                mHandler.postDelayed(this, UPDATE_TIME);
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentfragment, mHomeFragment)
                    .commit();
        }
    }

    private void setupDrawer() {
        mDrawerLayout = findViewById(R.id.drawer);
        mNavView = findViewById(R.id.navView);

        // Show the burger button on the ActionBar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //initialize filters

        mFilterViewModel.initSearchFilters();

        mToggleGroup = findViewById(R.id.toggleButton);

        mToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (group.getCheckedButtonId() == R.id.filters_default_view_btn) {
                mFilterViewModel.getSearchFilters().setSearchViewType(SearchViewType.DEFAULT);
            } else if (group.getCheckedButtonId() == R.id.filters_compact_view_btn) {
                mFilterViewModel.getSearchFilters().setSearchViewType(SearchViewType.COMPACT);
            }
            Log.d(TAG, "group = " + group + ", checkedId = " + checkedId + ", isChecked = " + isChecked);
        });

        //initialize price slider
        mPriceSlider = findViewById(R.id.rangeSlider);

        //add price slider listener
        mPriceSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
                //intentionally left empty
            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                Log.d(TAG, "slider = " + slider);

                mFilterViewModel.getSearchFilters().setLeftSlider(slider.getValues().get(0));
                mFilterViewModel.getSearchFilters().setRightSlider(slider.getValues().get(1));
            }
        });

        mPriceSlider.setLabelFormatter(value -> {
            String COUNTRY = "US";
            String LANGUAGE = "en";

            return NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(value);
        });

        //initialize search sort type switches, mutually exclusive
        mLowestPriceSwitch = findViewById(R.id.lowestPriceSwitch);
        mHighestPriceSwitch = findViewById(R.id.highestPriceSwitch);
        mPopularitySwitch = findViewById(R.id.popularitySwitch);
        mRatingSwitch = findViewById(R.id.ratingSwitch);

        /*
         add listeners to all of the switches. when one is clicked, disable all of the others.
         if this specific switch is clicked while it is already enabled, re-enable it as to always
         have one enabled.
        */
        mLowestPriceSwitch.setOnClickListener(v -> {
            boolean lowestPriceSwitchEnabled = mLowestPriceSwitch.isEnabled();

            if (lowestPriceSwitchEnabled) {
                mHighestPriceSwitch.setEnabled(false);
                mPopularitySwitch.setEnabled(false);
                mRatingSwitch.setEnabled(false);
            }

            if (!lowestPriceSwitchEnabled) {
                mLowestPriceSwitch.setEnabled(true);
            }

            mFilterViewModel.getSearchFilters().setSortType(SearchSortType.LOWEST_PRICE);
        });

        mHighestPriceSwitch.setOnClickListener(v -> {
            boolean highestPriceSwitchEnabled = mHighestPriceSwitch.isEnabled();

            if (highestPriceSwitchEnabled) {
                mLowestPriceSwitch.setEnabled(false);
                mPopularitySwitch.setEnabled(false);
                mRatingSwitch.setEnabled(false);
            }

            if (!highestPriceSwitchEnabled) {
                mHighestPriceSwitch.setEnabled(true);
            }
            mFilterViewModel.getSearchFilters().setSortType(SearchSortType.HIGHEST_PRICE);
        });

        mPopularitySwitch.setOnClickListener(v -> {
            boolean popularitySwitchEnabled = mPopularitySwitch.isEnabled();

            if (popularitySwitchEnabled) {
                mLowestPriceSwitch.setEnabled(false);
                mHighestPriceSwitch.setEnabled(false);
                mRatingSwitch.setEnabled(false);
            }

            if (!popularitySwitchEnabled) {
                mPopularitySwitch.setEnabled(true);
            }

            mFilterViewModel.getSearchFilters().setSortType(SearchSortType.POPULARITY);
        });

        mRatingSwitch.setOnClickListener(v -> {
            boolean ratingSwitchEnabled = mRatingSwitch.isEnabled();

            if (ratingSwitchEnabled) {
                mLowestPriceSwitch.setEnabled(false);
                mHighestPriceSwitch.setEnabled(false);
                mPopularitySwitch.setEnabled(false);
            }

            if (!ratingSwitchEnabled) {
                mRatingSwitch.setEnabled(true);
            }

            mFilterViewModel.getSearchFilters().setSortType(SearchSortType.RATING);
        });

        //initialize radio buttons group
        mBuyNowCheck = findViewById(R.id.buyNowCheck);
        mAuctionCheck = findViewById(R.id.auctionCheck);
        mAuctionEndingSoonCheck = findViewById(R.id.auctionEndingSoonCheck);

        //set listener for buy now radio button
        mBuyNowCheck.setOnClickListener(v -> mFilterViewModel.getSearchFilters().setBuyNow(mBuyNowCheck.isEnabled()));

        //set listener for auction radio button
        mAuctionCheck.setOnClickListener(v -> mFilterViewModel.getSearchFilters().setAuction
                (mAuctionCheck.isEnabled()));

        //set listener for auction ending radio button
        mAuctionEndingSoonCheck.setOnClickListener(v -> mFilterViewModel.getSearchFilters().setAuctionEnding(mAuctionEndingSoonCheck.isEnabled()));

        //initialize minimum item and seller rating bars
        mMinItemRatingBar = findViewById(R.id.averageItemRatingBar);
        mMinSellerRatingBar = findViewById(R.id.averageSellerRatingBar);

        //set listener for item rating bar
        mMinItemRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> mFilterViewModel.getSearchFilters().setMinimumItemRating(mMinItemRatingBar.getNumStars()));

        //set listener for seller rating bar
        mMinSellerRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> mFilterViewModel.getSearchFilters().setMinimumSellerRating(mMinSellerRatingBar.getNumStars()));

        //initialize apply filter buttons
        applyFiltersBtn = findViewById(R.id.applyFiltersBtn);
        applyFiltersContinueBtn = findViewById(R.id.applyFiltersContinueBtn);

        //add listeners to apply filters buttons
        applyFiltersBtn.setOnClickListener(v -> {
            Log.d(TAG, "v = " + v);
            mDrawerLayout.closeDrawer(mNavView);

            //TODO think about design, whether these are necessary
        });

        applyFiltersContinueBtn.setOnClickListener(v -> {
            Log.d(TAG, "v = " + v);
            mDrawerLayout.closeDrawer(mNavView);
        });
    }

    private void setupBottomNavigation() {
        mBottomNavigationView = findViewById(R.id.bottomMenu);

        // user interacts with bottom navigation, pick which button they clicked and accordingly
        // do an action.
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            hideLoadingIcon();

            int itemId = item.getItemId();

            Log.d(TAG, "item = " + item);
            Log.d(TAG, "itemId=" + itemId);

            if (itemId == R.id.bottom_person) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentfragment, mHomeFragment)
                        .addToBackStack(null)
                        .commit();
                return true;
            } else if (itemId == R.id.bottom_notifications) {
                //TODO decide on how to display notifications
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
                Log.d(TAG, "item =  " + item);
                Intent il = new Intent(this, MessagesActivity.class);
                startActivity(il);
                return true;
            }
            return false;
        });

        profileBadge = mBottomNavigationView.getOrCreateBadge(R.id.bottom_person);
        notificationBadge = mBottomNavigationView.getOrCreateBadge(R.id.bottom_notifications);
        wishlistBadge = mBottomNavigationView.getOrCreateBadge(R.id.bottom_favorites);
        cartBadge = mBottomNavigationView.getOrCreateBadge(R.id.bottom_cart);
        chatBadge = mBottomNavigationView.getOrCreateBadge(R.id.bottom_messages);

        wishlistBadge.setBackgroundColor(ContextCompat.getColor(this, R.color.lighter_black));
        wishlistBadge.setHorizontalOffset(20);
        wishlistBadge.setVerticalOffset(20);

        cartBadge.setBackgroundColor(ContextCompat.getColor(this, R.color.lighter_black));
        cartBadge.setHorizontalOffset(20);
        cartBadge.setVerticalOffset(20);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) myActionMenuItem.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast like print
                Log.i("TAG", "SearchOnQueryTextSubmit: " + query);
                if (!mSearchView.isIconified()) {
                    mSearchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();

                mFilterViewModel.setQueryLiveData(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

    /**
     * @param item The item clicked in the menu
     * @return True: options menu was interacted with correctly, don't proceed after
     * False: options menu not interacted with correctly, calls super
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection in the top app menu
        hideLoadingIcon();

        int itemId = item.getItemId();

        if (itemId == R.id.settings) {
            //switch to settings fragment, using previously initialized settings fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentfragment, mSettingsFragment)
                    .addToBackStack(null)
                    .commit();

            return true;
        } else if (itemId == R.id.switchAccounts) {
            //build an alert dialog for when a user wants to switch accounts
            new AlertDialog.Builder(this)
                    .setTitle("Switch Accounts")
                    .setMessage("Are you sure you would like to switch accounts?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        //if the user clicks yes, send the user to the BusinessLoginActivity
                        Intent intent = new Intent(this, BusinessLoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        //TODO: use sessions to figure out if there is a currently logged in
                        // business user on the same device
                    })
                    .setNegativeButton("Cancel", null)
                    .setIcon(R.drawable.ic_baseline_warning_24)
                    .show();
            return true;
        } else if (itemId == R.id.logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Logout this session or all sessions?")
                    .setPositiveButton("All Sessions", (dialog, which) -> {
                        //if the user clicks "All Sessions", send the user back to the
                        // UserLoginActivity
                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        //TODO: close all currently active sessions that this account is tied to
                    })
                    .setNeutralButton("Cancel", null)
                    .setNegativeButton("This Session", (dialog, which) -> {
                        //if the user clicks "This Session", send the user back to the
                        // UserLoginActivity
                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        //TODO: close the currently active session that this user is connected over
                    })
                    .setIcon(R.drawable.ic_baseline_warning_24)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Super call to pop the backstack when the back button is pressed
     */
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

    @Override
    public void showLoadingIcon() {
        mLoadingIcon.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIcon() {
        mLoadingIcon.setVisibility(View.GONE);
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

    @Override
    public void updateSearchSliderPriceValues(float minimumPrice, float maximumPrice) {
        mFilterViewModel.getSearchFilters().setLeftSlider(minimumPrice);
        mFilterViewModel.getSearchFilters().setLeftSlider(minimumPrice);

        mPriceSlider.setValues(minimumPrice, maximumPrice);
    }
}
