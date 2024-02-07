package com.example.ibooks.user;

import androidx.annotation.NonNull;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.fragment.app.Fragment;

import com.example.ibooks.HomeFragment;
import com.example.ibooks.OrderFragment;
import com.example.ibooks.ProfileFragment;
import com.example.ibooks.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
public class UserMainHome extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bottom_nav);

        BottomNavigationView bottomNavigationView = findViewById(R.id.user_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // Load the default fragment
        loadFragment(new HomeFragment());
        }

@Override
public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
        case R.id.menu_home:
        fragment = new HomeFragment();
        break;
        case R.id.menu_orders:
        fragment = new OrderFragment();
        break;
        case R.id.menu_profile:
        fragment = new ProfileFragment();
        break;

default:
        fragment = new HomeFragment();
        }


        return loadFragment(fragment);
        }

private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        return true;
        }
        return false;
        }


        }
