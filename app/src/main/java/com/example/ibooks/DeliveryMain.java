package com.example.ibooks;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DeliveryMain extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_bottomnav);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) BottomNavigationView bottomNavigationView = findViewById(R.id.delivery_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // Load the default fragment
        loadFragment(new HomeFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.menu_home:
                fragment = new AllOrdersFragment();
                //TODO:: set AllOrders screen
                break;
            case R.id.menu_books:
                fragment = new HomeFragment();
                break;
            case R.id.menu_orders:
                fragment = new OrderFragment();
                break;
            case R.id.menu_profile:
                fragment = new ProfileFragment();
                break;
            case R.id.menu_selected:
                fragment = new PostFragment();
                //TODO:: set SelectedOrders screen
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