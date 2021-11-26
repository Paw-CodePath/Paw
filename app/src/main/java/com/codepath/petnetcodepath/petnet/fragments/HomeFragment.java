package com.codepath.petnetcodepath.petnet.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.codepath.petnetcodepath.petnet.LoginActivity;
import com.codepath.petnetcodepath.petnet.MainActivity;
import com.codepath.petnetcodepath.petnet.PetProfileActivity;
import com.codepath.petnetcodepath.petnet.R;
import com.codepath.petnetcodepath.petnet.UserProfileActivity;
import com.parse.ParseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    Button btnSignout;
    Button btnuserprofile;
    Button btnpetprofile;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void goToLoginActivity() {
        //using intent
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        getActivity().finish(); // finish the main activity once the log out succeeded
    }

    private void signoutUser() {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
    }


    private void goToUserProfileActivity() {
        //using intent
        Intent i = new Intent(getContext(), UserProfileActivity.class);
        startActivity(i);
    }

    private void goToPetProfileActivity() {
        //using intent
        Intent i = new Intent(getContext(), PetProfileActivity.class);
        startActivity(i);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSignout = view.findViewById(R.id.btnSignout);
        btnuserprofile = view.findViewById(R.id.btnuserprofile);
        btnpetprofile = view.findViewById(R.id.btnpetprofile);
        btnuserprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUserProfileActivity();
            }
        });

        btnpetprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPetProfileActivity();
            }
        });

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signoutUser();
                Toast.makeText(getContext(), "Successfully Logged Out!!", Toast.LENGTH_SHORT).show();
                goToLoginActivity();
            }
        });
    }
}