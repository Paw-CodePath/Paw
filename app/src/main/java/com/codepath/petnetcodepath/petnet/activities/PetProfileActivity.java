package com.codepath.petnetcodepath.petnet.activities;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.petnetcodepath.petnet.R;
import com.codepath.petnetcodepath.petnet.helpers.AlertHelper;
import com.codepath.petnetcodepath.petnet.helpers.CameraHelper;
import com.codepath.petnetcodepath.petnet.helpers.ImageHelper;
import com.codepath.petnetcodepath.petnet.models.Pet;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

import java.io.File;

public class PetProfileActivity extends AppCompatActivity {

    public static final String KEY_PET = "pet";

    private static final String TAG = "PetProfileActivity";

    private EditText etName;
    private EditText etDescription;
    private EditText etSpecies;
    private ImageView ivImage;
    private Button btnAddImage;
    private Button btnSave;

    private Pet pet;

    private final CameraHelper cameraHelper = new CameraHelper(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);

        etName = findViewById(R.id.et_name);
        etDescription = findViewById(R.id.et_description);
        etSpecies = findViewById(R.id.et_species);
        ivImage = findViewById(R.id.iv_image);
        btnAddImage = findViewById(R.id.btn_add_image);
        btnSave = findViewById(R.id.btn_save);

        // Load pet from intent
        pet = (Pet) getIntent().getSerializableExtra(KEY_PET);
        etName.setText(pet.getName());
        etDescription.setText(pet.getDescription());
        etSpecies.setText(pet.getSpecies());
        Glide.with(this).load(pet.getImageUrl()).into(ivImage);

        btnAddImage.setOnClickListener(view -> {
            launchCamera();
        });
        btnSave.setOnClickListener(view -> {
            savePet();
        });
    }

    private void launchCamera() {
        cameraHelper.launchCamera(newImageFile -> {
            Glide.with(this).load(newImageFile.getAbsolutePath()).into(ivImage);
        });
    }

    private void savePet() {
        String name = etName.getText().toString();
        String description = etDescription.getText().toString();
        String species = etSpecies.getText().toString();
        File imageFile = cameraHelper.getImageFile();

        if (name.isEmpty()) {
            Toast.makeText(this, "Enter your pet’s name", Toast.LENGTH_SHORT).show();
            return;
        } else if (description.isEmpty()) {
            Toast.makeText(this, "Enter your pet’s description", Toast.LENGTH_SHORT).show();
            return;
        } else if (species.isEmpty()) {
            Toast.makeText(this, "Enter your pet’s species", Toast.LENGTH_SHORT).show();
            return;
        } else if (imageFile == null && pet.getImageUrl() == null) {
            Toast.makeText(this, "Add a photo of your pet", Toast.LENGTH_SHORT).show();
            return;
        }

        savePet(name, description, species, imageFile)
                .addOnSuccessListener(unused -> {
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Cannot save profile", e);
                    AlertHelper.present(this, "Cannot Save Profile", e);
                });
    }

    private Task<Void> savePet(String name, String description, String species, File imageFile) {
        pet.setName(name);
        pet.setDescription(description);
        pet.setSpecies(species);

        if (imageFile == null) {
            return pet.save();
        }

        String path = String.format("pets/%s.jpg", pet.getId());
        return ImageHelper.uploadImage(path, imageFile)
                .continueWithTask(new Continuation<Uri, Task<Void>>() {
                    @Override
                    public Task<Void> then(@NonNull Task<Uri> task) throws Exception {
                        String imageUrl = task.getResult().toString();
                        pet.setImageUrl(imageUrl);
                        return pet.save();
                    }
                });
    }
}