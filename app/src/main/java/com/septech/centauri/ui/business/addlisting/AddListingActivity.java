package com.septech.centauri.ui.business.addlisting;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.septech.centauri.R;

import java.util.ArrayList;
import java.util.List;

import static com.septech.centauri.data.utils.ImagePath.getPathFromUri;

public class AddListingActivity extends AppCompatActivity {

    private AddListingViewModel addListingViewModel;

    private TextInputLayout auctionLengthTextInput;
    private TextInputLayout startingBidTextInput;
    private TextInputLayout minimumBidTextInput;
    private TextInputLayout buyoutPriceTextInput;

    private TextInputEditText itemNameEditText;
    private TextInputEditText itemQuantityEditText;
    private TextInputEditText auctionLengthEditText;
    private TextInputEditText startingBidEditText;
    private TextInputEditText minimumBidEditText;
    private TextInputEditText buyoutPriceEditText;
    private TextInputEditText mainCategoryEditText;
    private TextInputEditText categoryTwoEditText;
    private TextInputEditText categoryThreeEditText;
    private TextInputEditText categoryFourEditText;
    private TextInputEditText categoryFiveEditText;
    private TextInputEditText itemDescriptionEditText;

    private Button uploadImageButton;
    private ImageButton backArrowButton;
    private Button createItemButton;

    private ImageView itemImageView;

    private SwitchCompat bidSwitch;
    private SwitchCompat buySwitch;

    private List<String> imagePaths;

    private static final int PICK_IMAGE = 100;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_listing);

        addListingViewModel = new ViewModelProvider(this).get(AddListingViewModel.class);

        auctionLengthTextInput = findViewById(R.id.auctionLengthTextbox);
        startingBidTextInput = findViewById(R.id.startingPriceTextbox);
        minimumBidTextInput = findViewById(R.id.minBidTextbox);
        buyoutPriceTextInput = findViewById(R.id.buyPriceTextbox);

        itemNameEditText = findViewById(R.id.itemNameEditText);
        itemQuantityEditText = findViewById(R.id.itemQuantityEditText);
        auctionLengthEditText = findViewById(R.id.auctionLengthEditText);
        startingBidEditText = findViewById(R.id.startingBidEditText);
        minimumBidEditText = findViewById(R.id.minimumBidEditText);
        buyoutPriceEditText = findViewById(R.id.buyoutPriceEditText);
        mainCategoryEditText = findViewById(R.id.mainCategoryEditText);
        categoryTwoEditText = findViewById(R.id.categoryTwoEditText);
        categoryThreeEditText = findViewById(R.id.categoryThreeEditText);
        categoryFourEditText = findViewById(R.id.categoryFourEditText);
        categoryFiveEditText = findViewById(R.id.categoryFiveEditText);
        itemDescriptionEditText = findViewById(R.id.itemDescriptionEditText);

        uploadImageButton = findViewById(R.id.uploadImageButton);
        backArrowButton = findViewById(R.id.backArrow);
        createItemButton = findViewById(R.id.createItemButton);

        itemImageView = findViewById(R.id.ItemAddImageBox);

        bidSwitch = findViewById(R.id.auctionSwitch);
        buySwitch = findViewById(R.id.buyNowSwitch);

        imagePaths = new ArrayList<>();

        createTextWatchers();

        createButtonListeners();

        createSwitchListeners();

        auctionLengthTextInput.setEnabled(false);
        startingBidTextInput.setEnabled(false);
        minimumBidTextInput.setEnabled(false);
        buyoutPriceTextInput.setEnabled(false);
    }

    private void createTextWatchers() {

    }

    private void createButtonListeners() {
        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(AddListingActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */}
                            @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
                            @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                        }).check();

                openGallery();
            }
        });

        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        createItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = itemNameEditText.getText().toString();
                int quantity = Integer.parseInt(itemQuantityEditText.getText().toString());

                boolean bid = bidSwitch.isChecked();

                String auctionLength = bid ? auctionLengthEditText.getText().toString(): "";
                String startingBid = bid ? startingBidEditText.getText().toString(): "";
                String minimumBid = bid ? minimumBidEditText.getText().toString(): "";

                boolean buy = buySwitch.isChecked();

                String buyoutPrice = buy ? buyoutPriceEditText.getText().toString(): "";

                String mainCategory = mainCategoryEditText.getText().toString();
                String categoryTwo = categoryTwoEditText.getText().toString();
                String categoryThree = categoryThreeEditText.getText().toString();
                String categoryFour = categoryFourEditText.getText().toString();
                String categoryFive = categoryFiveEditText.getText().toString();
                String itemDescription = itemDescriptionEditText.getText().toString();


                addListingViewModel.createItem(name, quantity, bid, buy, auctionLength,
                        startingBid, minimumBid, buyoutPrice, mainCategory, categoryTwo,
                        categoryThree, categoryFour, categoryFive, itemDescription, imagePaths);
            }
        });
    }

    private void createSwitchListeners() {
        bidSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            auctionLengthTextInput.setEnabled(isChecked);
            startingBidTextInput.setEnabled(isChecked);
            minimumBidTextInput.setEnabled(isChecked);
        });

        buySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> buyoutPriceTextInput.setEnabled(isChecked));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();

            itemImageView.setImageURI(imageUri);

            imagePaths.add(getPathFromUri(this, imageUri));
        }
    }
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE);
    }
}
