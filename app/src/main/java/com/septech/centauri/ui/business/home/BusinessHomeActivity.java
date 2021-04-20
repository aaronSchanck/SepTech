package com.septech.centauri.ui.business.home;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.septech.centauri.R;
import com.septech.centauri.domain.models.Business;
import com.septech.centauri.ui.user.home.CallBackListener;

public class BusinessHomeActivity extends AppCompatActivity implements CallBackListener {

    private BusinessViewModel mViewModel;

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

    }

    @Override
    public void hideLoadingIcon() {

    }

    @Override
    public void hideKeyboard() {

    }
}
