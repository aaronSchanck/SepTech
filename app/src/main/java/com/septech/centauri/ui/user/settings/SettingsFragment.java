package com.septech.centauri.ui.user.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.septech.centauri.R;

public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;

    private Button accountBtnVar;
    private Button proximaBtnVar;
    private Button notificationBtnVar;
    private Button appearenceBtnVar;
    private Button privSecBtnVar;
    private Button helpSupportBtnVar;
    private Button locationBtnVar;
    private Button aboutBtnVar;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_settings_main_fragment, container, false);

        accountBtnVar = view.findViewById(R.id.accountBtn);
        proximaBtnVar = view.findViewById(R.id.proximaBtn);
        notificationBtnVar = view.findViewById(R.id.notificationsBtn);
        appearenceBtnVar = view.findViewById(R.id.appearenceBtn);
        privSecBtnVar = view.findViewById(R.id.securityBtn);
        helpSupportBtnVar = view.findViewById(R.id.helpBtn);
        locationBtnVar = view.findViewById(R.id.locationBtn);
        aboutBtnVar = view.findViewById(R.id.aboutBtn);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);

        accountBtnVar.setOnClickListener(v -> {
            //TODO transition activity to fragment

        });

        proximaBtnVar.setOnClickListener(v -> {
            //TODO transition activity to fragment

        });

        notificationBtnVar.setOnClickListener(v -> {
            //TODO transition activity to fragment

        });

        appearenceBtnVar.setOnClickListener(v -> {
            AppearenceFragment fragment = AppearenceFragment.newInstance();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentfragment, fragment)
                    .addToBackStack(null)
                    .commit();

        });

        privSecBtnVar.setOnClickListener(v -> {
            PrivacySecurityFragment fragment = PrivacySecurityFragment.newInstance();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentfragment, fragment)
                    .addToBackStack(null)
                    .commit();

        });

        helpSupportBtnVar.setOnClickListener(v -> {
            HelpSupportFragment fragment = HelpSupportFragment.newInstance();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentfragment, fragment)
                    .addToBackStack(null)
                    .commit();

        });

        locationBtnVar.setOnClickListener(v -> {
            LocationFragment fragment = LocationFragment.newInstance();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentfragment, fragment)
                    .addToBackStack(null)
                    .commit();

        });

        aboutBtnVar.setOnClickListener(v -> {
            AboutFragment fragment = AboutFragment.newInstance();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentfragment, fragment)
                    .addToBackStack(null)
                    .commit();

        });
    }

}