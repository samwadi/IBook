package com.example.ibooks.delivery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ibooks.HomeFragment;
import com.example.ibooks.OrderFragment;
import com.example.ibooks.ProfileFragment;
import com.example.ibooks.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DeliveryMain extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_nav);

        BottomNavigationView bottomNavigationView = findViewById(R.id.delivery_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // Load the default fragment
        loadFragment(new AllOrdersFragment());
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
                fragment = new SelectedOrdersFragment();
                //TODO:: set SelectedOrders screen
                break;
            default:
                fragment = new AllOrdersFragment();
        }


        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.delivery_fragment_container, fragment).commit();
            return true;
        }
        return false;
    }


}