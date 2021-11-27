package com.codepath.petnetcodepath.petnet.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.codepath.petnetcodepath.petnet.R;
import com.codepath.petnetcodepath.petnet.fragments.HomeFragment;
import com.codepath.petnetcodepath.petnet.fragments.MessagesFragment;
import com.codepath.petnetcodepath.petnet.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.action_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.action_messages:
                    fragment = new MessagesFragment();
                    break;
                case R.id.action_settings:
                    fragment = new SettingsFragment();
                    break;
                default:
                    String message = String.format("Unknown navigation item ID: %s", item.getItemId());
                    throw new IllegalArgumentException(message);
            }
            fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
            return true;
        });

        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}
