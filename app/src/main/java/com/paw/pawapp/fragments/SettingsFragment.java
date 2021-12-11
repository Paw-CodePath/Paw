package com.paw.pawapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.paw.pawapp.R;
import com.paw.pawapp.activities.LoginActivity;
import com.paw.pawapp.activities.PetProfileActivity;
import com.paw.pawapp.activities.UserProfileActivity;
import com.paw.pawapp.models.Pet;
import com.paw.pawapp.models.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";

    private TextView tvUserName;
    private TextView tvPetName;
    private ImageView ivUserImage;
    private ImageView ivPetImage;
    private Button btnEditUser;
    private Button btnEditPet;
    private Button btnSignOut;

    private User user;
    private Pet pet;

    private final FirebaseAuth auth = FirebaseAuth.getInstance();

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

        tvUserName = view.findViewById(R.id.tv_user_name);
        tvPetName = view.findViewById(R.id.tv_pet_name);
        ivUserImage = view.findViewById(R.id.iv_user_image);
        ivPetImage = view.findViewById(R.id.iv_user_image2);
        btnEditUser = view.findViewById(R.id.btn_edit_user);
        btnEditPet = view.findViewById(R.id.btn_edit_pet);
        btnSignOut = view.findViewById(R.id.btn_sign_out);

        btnEditUser.setOnClickListener(v -> {
            launchUserProfileActivity();
        });
        btnEditPet.setOnClickListener(v -> {
            launchPetProfileActivity();
        });
        btnSignOut.setOnClickListener(v -> {
            signOut();
        });

        loadData();
    }

    private void launchUserProfileActivity() {
        assert user != null;

        Log.v(TAG, "Launching UserProfileActivity");
        Intent i = new Intent(getContext(), UserProfileActivity.class);
        i.putExtra(UserProfileActivity.KEY_USER, user);
        startActivity(i);
    }

    private void launchPetProfileActivity() {
        assert pet != null;

        Log.v(TAG, "Launching PetProfileActivity");
        Intent i = new Intent(getContext(), PetProfileActivity.class);
        i.putExtra(PetProfileActivity.KEY_PET, pet);
        startActivity(i);
    }

    private void signOut() {
        auth.signOut();
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    private void loadData() {
        Log.v(TAG, "Loading user and pet information");

        User.getCurrentUser()
                .addOnSuccessListener(user -> {
                    Log.v(TAG, "Loaded user information");
                    this.user = user;
                    tvUserName.setText(user.getName());
                    btnEditUser.setEnabled(true);
                    Glide.with(getContext()).load(user.getImageUrl()).into(ivUserImage);
                })
                .continueWithTask(new Continuation<User, Task<Pet>>() {
                    @Override
                    public Task<Pet> then(@NonNull Task<User> task) throws Exception {
                        return Pet.findForUser(user);
                    }
                })
                .addOnSuccessListener(pet -> {
                    Log.v(TAG, "Loaded pet information");
                    assert pet.getId() != null;
                    this.pet = pet;
                    tvPetName.setText(pet.getName());
                    btnEditPet.setEnabled(true);
                    Glide.with(getContext()).load(pet.getImageUrl()).into(ivPetImage);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to retrieve user or pet information", e);
                });
    }
}

