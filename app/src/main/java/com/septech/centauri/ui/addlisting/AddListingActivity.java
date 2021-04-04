package com.septech.centauri.ui.addlisting;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;
import com.septech.centauri.R;

public class AddListingActivity extends AppCompatActivity {

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

    private SwitchCompat bidSwitch;
    private SwitchCompat buySwitch;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_listing);

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

        bidSwitch = findViewById(R.id.auctionSwitch);
        buySwitch = findViewById(R.id.buyNowSwitch);
    }
}
