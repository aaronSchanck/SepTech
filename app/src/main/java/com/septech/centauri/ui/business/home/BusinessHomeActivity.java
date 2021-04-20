package com.septech.centauri.ui.business.home;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.septech.centauri.R;
import com.septech.centauri.domain.models.Business;
import com.septech.centauri.ui.user.home.CallBackListener;

public class BusinessHomeActivity extends AppCompatActivity implements CallBackListener {

    private BusinessViewModel mViewModel;

    private ProgressBar loadingIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_home);

        mViewModel = new ViewModelProvider(this).get(BusinessViewModel.class);
        mViewModel.setBusinessId(getIntent().getIntExtra("id", 0));

        mViewModel.getBusinessLiveData().observe(this, new Observer<Business>() {
            @Override
            public void onChanged(Business business) {
                Log.i("Business update", "New Business: " + business);
            }
        });

        loadingIcon = findViewById(R.id.business_home_loading_icon);
        hideLoadingIcon();

        if (savedInstanceState == null) {
            BusinessHomeFragment fragment = BusinessHomeFragment.newInstance();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.business_home_content_fragment, fragment)
                    .addToBackStack(null)
                    .commit();
        }
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
}
