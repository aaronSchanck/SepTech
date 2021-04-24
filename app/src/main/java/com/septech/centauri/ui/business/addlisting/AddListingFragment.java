package com.septech.centauri.ui.business.addlisting;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
import com.septech.centauri.domain.models.Business;
import com.septech.centauri.ui.business.home.BusinessViewModel;
import com.septech.centauri.ui.interfaces.CallBackListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.septech.centauri.data.utils.ImagePath.getPathFromUri;

public class AddListingFragment extends Fragment {
    private static final int PICK_IMAGE = 100;

    private AddListingViewModel mViewModel;
    private BusinessViewModel mBusinessViewModel;

    private TextInputLayout startingBidTextInput;
    private TextInputLayout minimumBidTextInput;
    private TextInputLayout buyoutPriceTextInput;

    private EditText itemNameET;
    private EditText itemQualityET;
    private EditText itemQuantityET;
    private EditText startingBidET;
    private EditText minBidET;
    private EditText buyoutPriceET;
    private EditText itemDescriptionET;

    private Button uploadImageButton;
    private Button createItemButton;
    private ImageButton backArrowButton;

    private Spinner auctionLengthSpinner;

    private ImageView itemImageView;

    private SwitchCompat bidSwitch;
    private SwitchCompat buySwitch;

    private List<String> imagePaths;
    private boolean imagesSelected;

    private CallBackListener callBackListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            callBackListener = (CallBackListener) context;
            callBackListener.initFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CallBackListener");
        }
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

        itemNameET = view.findViewById(R.id.addlisting_item_name_et);
        itemQualityET = view.findViewById(R.id.addlisting_item_quality_et);
        itemQuantityET = view.findViewById(R.id.addlisting_item_quantity_et);

        startingBidET = view.findViewById(R.id.addlisting_starting_bid_et);
        minBidET = view.findViewById(R.id.addlisting_min_bid_incre_et);
        buyoutPriceET = view.findViewById(R.id.addlisting_buyout_price_et);
        itemDescriptionET = view.findViewById(R.id.addlisting_item_desc_et);

        uploadImageButton = view.findViewById(R.id.addlisting_upload_image_btn);
        backArrowButton = view.findViewById(R.id.backArrow);
        createItemButton = view.findViewById(R.id.addlisting_create_btn);

        itemImageView = view.findViewById(R.id.addlisting_item_image_iv);
        itemImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_image_foreground));

        bidSwitch = view.findViewById(R.id.addlisting_auction_switch);
        buySwitch = view.findViewById(R.id.addlisting_buynow_switch);

        auctionLengthSpinner = view.findViewById(R.id.addlisting_auction_length_spinner);

        imagePaths = new ArrayList<>();

        createTextWatchers();

        createButtonListeners();

        createSwitchListeners();

        bidSwitch.setChecked(true);
        enableOrDisableBid(true);

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

//        auctionLength

        auctionLengthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("parent = " + parent + ", view = " + view + ", position = " + position + ", id = " + id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("parent = " + parent);
            }
        });

        //create spinner choices
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                requireActivity(),
                android.R.layout.simple_list_item_1,
                Arrays.asList("1 Day", "3 Days", "5 Days", "7 Days",
                        "14 Days"));

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        auctionLengthSpinner.setAdapter(dataAdapter);

        createItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = itemNameET.getText().toString();
                String quality = itemQualityET.getText().toString();
                int quantity = Integer.parseInt(itemQuantityET.getText().toString());

                boolean bid = bidSwitch.isChecked();

                String startingBid = bid ? startingBidET.getText().toString() : null;
                String minimumBid = bid ? minBidET.getText().toString() : null;

                boolean buy = buySwitch.isChecked();

                String buyoutPrice = buy ? buyoutPriceET.getText().toString() : null;

                String itemDescription = itemDescriptionET.getText().toString();

                System.out.println("v = " + itemDescription);

                mViewModel.createItem(mBusinessViewModel.getBusinessId(), name, quality, quantity, bid,
                        buy, null, startingBid, minimumBid, buyoutPrice, "",
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
        bidSwitch.setOnClickListener(v -> {
            boolean isChecked = bidSwitch.isChecked();

            buySwitch.setChecked(!isChecked);

            enableOrDisableBid(isChecked);
            enableOrDisableBuy(!isChecked);
        });

        buySwitch.setOnClickListener(v -> {
            boolean isChecked = buySwitch.isChecked();

            bidSwitch.setChecked(!isChecked);

            enableOrDisableBuy(isChecked);
            enableOrDisableBid(!isChecked);
        });
    }

    private void enableOrDisableBid(boolean isChecked) {
        auctionLengthSpinner.setEnabled(isChecked);
        startingBidTextInput.setEnabled(isChecked);
        minimumBidTextInput.setEnabled(isChecked);
    }

    private void enableOrDisableBuy(boolean isChecked) {
        buyoutPriceTextInput.setEnabled(isChecked);
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
