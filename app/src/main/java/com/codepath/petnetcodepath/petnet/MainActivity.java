package com.codepath.petnetcodepath.petnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.codepath.petnetcodepath.petnet.fragments.HomeFragment;
import com.codepath.petnetcodepath.petnet.fragments.MessagesFragment;
import com.codepath.petnetcodepath.petnet.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    //Reference to the Bottom Navigation Tool Bar
    private BottomNavigationView bottomNavigationView;

    // Calling the fragment manager to use change the fragments
    final FragmentManager fragmentManager = getSupportFragmentManager();


    // The actions to take on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //add reference to the toolbar using the id
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        //Adding a listener on the bottom navigation view to switch between fragments
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_home:        // if the home icon is tapped go to the home fragment
                        fragment = new HomeFragment();
                        break;
                    case R.id.action_messages:    // if the messages icon is tapped go to the messages fragment
                        fragment = new MessagesFragment();
                        break;
                    case R.id.action_settings:    // if the settings icon is tapped go to the settings fragment
                    default:
                        fragment = new SettingsFragment();
                        break;
                }
                // replace the frame layout with the selected fragment from the switch statements
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}
