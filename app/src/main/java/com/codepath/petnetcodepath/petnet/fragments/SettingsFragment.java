package com.codepath.petnetcodepath.petnet.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.codepath.petnetcodepath.petnet.R;
import com.codepath.petnetcodepath.petnet.activities.LoginActivity;
import com.codepath.petnetcodepath.petnet.activities.PetProfileActivity;
import com.codepath.petnetcodepath.petnet.activities.UserProfileActivity;
import com.parse.ParseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    Button btnUserProfile;
    Button btnPetProfile;
    Button btnSignOut;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnUserProfile = view.findViewById(R.id.btnUserProfile);
        btnPetProfile = view.findViewById(R.id.btnPetProfile);
        btnSignOut = view.findViewById(R.id.btnSignOut);

        btnUserProfile.setOnClickListener(l -> {
            goTo(UserProfileActivity.class);
        });
        btnPetProfile.setOnClickListener(l -> {
            goTo(PetProfileActivity.class);
        });
        btnSignOut.setOnClickListener(l -> {
            signOut();
        });
    }

    private void goTo(Class<?> activityClass) {
        Intent i = new Intent(getContext(), activityClass);
        startActivity(i);
    }

    private void signOut() {
        ParseUser.logOut();

        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);

        Toast.makeText(getContext(), "You’ve been signed out", Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }
}