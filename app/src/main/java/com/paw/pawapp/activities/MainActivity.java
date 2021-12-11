package com.paw.pawapp.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.paw.pawapp.R;
import com.paw.pawapp.fragments.RecommendationsFragment;
import com.paw.pawapp.fragments.MatchesFragment;
import com.paw.pawapp.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Code that changes the Top bar
         */
        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#b3003b"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Burgundy));
        }
        /**
         *  Ends here
         */

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.action_matches:
                    fragment = new MatchesFragment();
                    break;
                case R.id.action_recommendations:
                    fragment = new RecommendationsFragment();
                    break;
                case R.id.action_settings:
                    fragment = new SettingsFragment();
                    break;
                default:
                    String message = String.format("Unknown navigation item ID: %s", item.getItemId());
                    throw new IllegalArgumentException(message);
            }
            fragmentManager.beginTransaction().replace(R.id.fl_container, fragment).commit();
            return true;
        });

        bottomNavigationView.setSelectedItemId(R.id.action_recommendations);
    }
}