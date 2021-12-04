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
import com.codepath.petnetcodepath.petnet.models.User;
import com.codepath.petnetcodepath.petnet.helpers.ImageHelper;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

import java.io.File;

public class UserProfileActivity extends AppCompatActivity {

    public static final String KEY_USER = "user";

    private static final String TAG = "UserProfileActivity";

    private EditText etName;
    private EditText etDescription;
    private ImageView ivImage;
    private Button btnAddImage;
    private Button btnSave;

    private User user;

    private final CameraHelper cameraHelper = new CameraHelper(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_profile);

        etName = findViewById(R.id.et_name);
        etDescription = findViewById(R.id.et_description);
        ivImage = findViewById(R.id.iv_image);
        btnAddImage = findViewById(R.id.btn_add_image);
        btnSave = findViewById(R.id.btn_save);

        // Load user from intent
        user = (User) getIntent().getSerializableExtra(KEY_USER);
        etName.setText(user.getName());
        etDescription.setText(user.getDescription());
        Glide.with(this).load(user.getImageUrl()).into(ivImage);

        btnAddImage.setOnClickListener(view -> {
            launchCamera();
        });
        btnSave.setOnClickListener(view -> {
            saveUser();
        });
    }

    private void launchCamera() {
        cameraHelper.launchCamera(newImageFile -> {
            Glide.with(this).load(newImageFile.getAbsolutePath()).into(ivImage);
        });
    }

    private void saveUser() {
        String name = etName.getText().toString();
        String description = etDescription.getText().toString();
        File imageFile = cameraHelper.getImageFile();

        if (name.isEmpty()) {
            Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show();
            return;
        } else if (description.isEmpty()) {
            Toast.makeText(this, "Enter a description", Toast.LENGTH_SHORT).show();
            return;
        } else if (imageFile == null && user.getImageUrl() == null) {
            Toast.makeText(this, "Add a photo of yourself", Toast.LENGTH_SHORT).show();
            return;
        }

        saveUser(name, description, imageFile)
                .addOnSuccessListener(unused -> {
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Cannot save profile", e);
                    AlertHelper.present(this, "Cannot Save Profile", e);
                });
    }

    private Task<Void> saveUser(String name, String description, File imageFile) {
        user.setName(name);
        user.setDescription(description);

        if (imageFile == null) {
            return user.save();
        }

        String path = String.format("users/%s.jpg", user.getId());
        return ImageHelper.uploadImage(path, imageFile)
                .continueWithTask(new Continuation<Uri, Task<Void>>() {
                    @Override
                    public Task<Void> then(@NonNull Task<Uri> task) throws Exception {
                        String imageUrl = task.getResult().toString();
                        user.setImageUrl(imageUrl);
                        return user.save();
                    }
                });
    }
}
