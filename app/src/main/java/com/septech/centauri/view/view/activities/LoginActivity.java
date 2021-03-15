package com.septech.centauri.view.view.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.google.android.material.textfield.TextInputLayout;
import com.septech.centauri.R;
import com.septech.centauri.model.cache.database.betelgeuse.BetelgeuseDatabase;
import com.septech.centauri.model.repository.UserDataRepository;
import com.septech.centauri.viewmodel.models.User;
import com.septech.centauri.viewmodel.repository.UserRepository;


public class LoginActivity extends AppCompatActivity {

    UserRepository userRepo = new UserDataRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);

        BetelgeuseDatabase db = Room.databaseBuilder(getApplicationContext(),
                BetelgeuseDatabase.class, "betelgeuse").build();

        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void login(View view) {
        TextInputLayout emailTextInput = findViewById(R.id.email);
        String email = emailTextInput.getEditText().getText().toString();
        TextInputLayout passwordTextInput = findViewById(R.id.password);
        String password = passwordTextInput.getEditText().getText().toString();

        User user = userRepo.login(email, password);

        System.out.println("view = " + view);
    }

    public void createAccount(View view) {

    }

    public void continueAsGuest(View view) {

    }
}