package com.septech.centauri.ui.business.addlisting;

import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.septech.centauri.R;
import com.septech.centauri.domain.models.Business;
import com.septech.centauri.ui.business.home.BusinessViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.septech.centauri.data.utils.ImagePath.getPathFromUri;

public class AddListingFragment extends Fragment {

    private AddListingViewModel mViewModel;
    private BusinessViewModel mBusinessViewModel;

    private TextInputLayout startingBidTextInput;
    private TextInputLayout minimumBidTextInput;
    private TextInputLayout buyoutPriceTextInput;

    private TextInputEditText itemNameEditText;
    private TextInputEditText itemQualityEditText;
    private TextInputEditText itemQuantityEditText;
    private TextInputEditText startingBidEditText;
    private TextInputEditText minimumBidEditText;
    private TextInputEditText buyoutPriceEditText;
    private TextInputEditText itemDescriptionEditText;

    private Button uploadImageButton;
    private ImageButton backArrowButton;
    private Button createItemButton;

    private ImageView itemImageView;

    private SwitchCompat bidSwitch;
    private SwitchCompat buySwitch;

    private List<String> imagePaths;

    private boolean imagesSelected;

    private static final int PICK_IMAGE = 100;

    public AddListingFragment() {
    }

    public static AddListingFragment newInstance() {
        return new AddListingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.business_add_listing_fragment, container, false);

        imagesSelected = false;

        startingBidTextInput = view.findViewById(R.id.startingPriceTextbox);
        minimumBidTextInput = view.findViewById(R.id.minBidTextbox);
        buyoutPriceTextInput = view.findViewById(R.id.buyPriceTextbox);

        itemNameEditText = view.findViewById(R.id.itemNameEditText);
        itemQualityEditText = view.findViewById(R.id.itemQualityEditText);
        itemQuantityEditText = view.findViewById(R.id.itemQuantityEditText);

        startingBidEditText = view.findViewById(R.id.startingBidEditText);
        minimumBidEditText = view.findViewById(R.id.minimumBidEditText);
        buyoutPriceEditText = view.findViewById(R.id.buyoutPriceEditText);
        itemDescriptionEditText = view.findViewById(R.id.itemDescriptionEditText);

        uploadImageButton = view.findViewById(R.id.uploadImageButton);
        backArrowButton = view.findViewById(R.id.backArrow);
        createItemButton = view.findViewById(R.id.createItemButton);

        itemImageView = view.findViewById(R.id.ItemAddImageBox);

        bidSwitch = view.findViewById(R.id.auctionSwitch);
        buySwitch = view.findViewById(R.id.buyNowSwitch);

        imagePaths = new ArrayList<>();

        createTextWatchers();

        createButtonListeners();

        createSwitchListeners();

        startingBidTextInput.setEnabled(false);
        minimumBidTextInput.setEnabled(false);
        buyoutPriceTextInput.setEnabled(false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddListingViewModel.class);
        mBusinessViewModel = new ViewModelProvider(requireActivity()).get(BusinessViewModel.class);

        mBusinessViewModel.getBusinessLiveData().observe(getViewLifecycleOwner(), new Observer<Business>() {
            @Override
            public void onChanged(Business business) {
                System.out.println("business = " + business);
            }
        });

        createItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = itemNameEditText.getText().toString();
                String quality = itemQualityEditText.getText().toString();
                int quantity = Integer.parseInt(itemQuantityEditText.getText().toString());

                boolean bid = bidSwitch.isChecked();

//                String auctionLength = bid ? auctionLengthEditText.getText().toString() : null;
                String startingBid = bid ? startingBidEditText.getText().toString() : null;
                String minimumBid = bid ? minimumBidEditText.getText().toString() : null;

                boolean buy = buySwitch.isChecked();

                String buyoutPrice = buy ? buyoutPriceEditText.getText().toString() : null;

                String itemDescription = itemDescriptionEditText.getText().toString();

                System.out.println("v = " + itemDescription);


                mViewModel.createItem(mBusinessViewModel.getBusinessId(), name, quality, quantity, bid,
                        buy,
                        null, startingBid, minimumBid, buyoutPrice, "",
                        "", "", "", "", itemDescription, imagePaths);
            }
        });
    }

    private void createTextWatchers() {

    }

    private void createButtonListeners() {
        uploadImageButton.setOnClickListener(v -> {
            Dexter.withContext(requireActivity())
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

        backArrowButton.setOnClickListener(v -> {
            System.out.println("v = " + v);
            requireActivity().onBackPressed();
        });


    }

    private void createSwitchListeners() {
        bidSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            startingBidTextInput.setEnabled(isChecked);
            minimumBidTextInput.setEnabled(isChecked);
        });

        buySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> buyoutPriceTextInput.setEnabled(isChecked));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE && null != data) {
            String currentImagePath;

            if (data.getClipData() != null) {
                ClipData clipData = data.getClipData();
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();

                    if (i == 0) {
                        itemImageView.setImageURI(uri);
                    }

                    currentImagePath = getPathFromUri(getActivity(), uri);
                    imagePaths.add(currentImagePath);
                    Log.d("ImageDetails", "Image URI " + i + " = " + uri);
                    Log.d("ImageDetails", "Image Path " + i + " = " + currentImagePath);
                    imagesSelected = true;
                }
            } else if (data.getData() != null) {
                Uri uri = data.getData();
                currentImagePath = getPathFromUri(getActivity(), uri);
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
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }
}
