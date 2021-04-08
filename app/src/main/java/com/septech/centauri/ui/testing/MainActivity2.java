package com.septech.centauri.ui.testing;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.septech.centauri.R;

public class MainActivity2 extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    NavigationView navView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mDrawerLayout = findViewById(R.id.drawer);
        navView = findViewById(R.id.navView);
        toolbar = findViewById(R.id.toolbar_main);

        setupDrawer();

    }


    private void setupDrawer() {
        // Show the burger button on the ActionBar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


    public void onNavigationButtonClick(View view) {

        TextView tvMain = findViewById(R.id.tv_main);
        tvMain.setText(((Button) view).getText().toString());
        mDrawerLayout.closeDrawer(navView);

        switch (view.getId()) {
            case R.id.button1:
                // Do something with button 1
                break;

            case R.id.button2:
                // Do something with button 2
                break;

            case R.id.button3:
                // Do something with button 3
                break;

        }
    }
}