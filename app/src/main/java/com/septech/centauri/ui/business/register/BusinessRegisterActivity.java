package com.septech.centauri.ui.business.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.EditText;

import com.septech.centauri.R;
import com.septech.centauri.ui.user.register.RegisterViewModel;

public class BusinessRegisterActivity extends AppCompatActivity {

    private BusinessRegisterViewModel mViewModel;

//    private EditText businessNameEditText;
//    private EditText
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_register);

        mViewModel = new ViewModelProvider(this).get(BusinessRegisterViewModel.class);
    }
}