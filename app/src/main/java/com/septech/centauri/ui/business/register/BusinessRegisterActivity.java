package com.septech.centauri.ui.business.register;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.septech.centauri.R;
import com.septech.centauri.ui.business.login.BusinessLoginActivity;

import java.util.ArrayList;
import java.util.List;

import static com.septech.centauri.data.utils.ImagePath.getPathFromUri;

public class BusinessRegisterActivity extends AppCompatActivity {

    private BusinessRegisterViewModel mViewModel;

    private EditText businessNameEditText;
    private EditText businessOwnerNameEditText;
    private EditText emailAddrEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText phoneNumberEditText;

    private Button createAccountBtn;
    private Button addImageBtn;

    private ProgressBar loadingIcon;

    private static final int PICK_IMAGE = 1;

    private List<String> imagePaths;

    private boolean imagesSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_register);

        imagesSelected = false;

        mViewModel = new ViewModelProvider(this).get(BusinessRegisterViewModel.class);

        businessNameEditText = findViewById(R.id.businessNameEditText);
        businessOwnerNameEditText = findViewById(R.id.businessOwnerNameEditText);
        emailAddrEditText = findViewById(R.id.emailAddrEditText);
        passwordEditText = findViewById(R.id.businessRegisterPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);

        createAccountBtn = findViewById(R.id.business_register_create_account_btn);

        addImageBtn = findViewById(R.id.business_creation_upload_image_Btn );

        loadingIcon = findViewById(R.id.loading_icon);

        loadingIcon.setVisibility(View.GONE);

        imagePaths = new ArrayList<>();

        createButtonListeners();

        createTextWatchers();

        createLiveDataObservers();
    }

    private void createButtonListeners() {
        createAccountBtn.setOnClickListener(v -> mViewModel.createBusinessAccount(businessNameEditText.getText().toString(),
                businessOwnerNameEditText.getText().toString(),
                emailAddrEditText.getText().toString(),
                passwordEditText.getText().toString(),
                phoneNumberEditText.getText().toString()));

        addImageBtn.setOnClickListener(v -> {
            Dexter.withContext(BusinessRegisterActivity.this)
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */}

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                    }).check();

            openGallery();
        });
    }

    private void createTextWatchers() {
        //TODO
    }

    private void createLiveDataObservers() {
        mViewModel.getResponseLiveData().observe(this, businessRegisterResponse -> {
            switch (businessRegisterResponse) {
                case EMAIL_EXISTS:
                    break;
                case EMAIL_DOES_NOT_EXIST:
                    break;
                case INFO_INCORRECT:
                    loadingIcon.setVisibility(View.GONE);
                    break;
                case LOADING:
                    loadingIcon.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    loadingIcon.setVisibility(View.GONE);
                    Intent intent = new Intent(this, BusinessLoginActivity.class);
                    startActivity(intent);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE && null != data) {
            String currentImagePath;

            if (data.getClipData() != null) {
                ClipData clipData = data.getClipData();
                for (int i = 0; i < clipData.getItemCount(); i++) {

                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();

                    currentImagePath = getPathFromUri(getApplicationContext(), uri);
                    imagePaths.add(currentImagePath);
                    Log.d("ImageDetails", "Image URI " + i + " = " + uri);
                    Log.d("ImageDetails", "Image Path " + i + " = " + currentImagePath);
                    imagesSelected = true;
                }
            } else if (data.getData() != null) {
                Uri uri = data.getData();
                currentImagePath = getPathFromUri(getApplicationContext(), uri);
                Log.d("ImageDetails", "Single Image URI : " + uri);
                Log.d("ImageDetails", "Single Image Path : " + currentImagePath);
                imagePaths.add(currentImagePath);
                imagesSelected = true;
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }
}